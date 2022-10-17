package com.akata.clientservice.services;

import com.akata.clientservice.dto.*;
import com.akata.clientservice.entities.Client;
import com.akata.clientservice.entities.ValidationCode;
import com.akata.clientservice.mapper.ClientMapper;
import com.akata.clientservice.mapper.LocationMapper;
import com.akata.clientservice.model.*;
import com.akata.clientservice.projections.ClientLightProjection;
import com.akata.clientservice.repository.ClientRepository;
import com.akata.clientservice.repository.ValidationRepository;
import com.akata.clientservice.services.interfaces.ClientService;
import com.akata.clientservice.services.interfaces.ContactService;
import com.akata.clientservice.services.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ValidationRepository validationRepository;

    @Autowired
    private final SpringTemplateEngine templateEngine;

    @Autowired
    private final JavaMailSender emailSender;

    public ClientServiceImpl(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public ClientResponseDTO save(ClientRequestDTO clientRequestDTO) {
        clientRequestDTO.setCreation(LocalDate.now());
        Client saved_client = clientRepository.save(clientMapper.clientRequestDTOClient(clientRequestDTO));
        return clientMapper.clientToClientResponseDTO(saved_client);
    }

    @Override
    public ClientResponseDTO getClient(Long id) {
        Client client;
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();

        ContactResponseDTO email = new ContactResponseDTO();
        ContactResponseDTO phone = new ContactResponseDTO();
        try {
            client = clientRepository.findById(id).get();
            clientResponseDTO = this.clientMapper.clientToClientResponseDTO(client);
            System.out.println("response dto: "+ clientResponseDTO);
            System.out.println("client: "+client );
            try {
                email = this.contactService.findByUserAndType("email",clientResponseDTO.getId());
                phone= this.contactService.findByUserAndType("tel",clientResponseDTO.getId());
            }catch (DataAccessException e){
                System.out.println("Can't find any contact matche to client: "+client);
            }
            if(email != null){
                clientResponseDTO.setEmail(email.getValue());
            }else{
                clientResponseDTO.setEmail("null");
            }

            if(phone != null){
                clientResponseDTO.setPhone(phone.getValue());
            }else {
                clientResponseDTO.setPhone("null");
            }


            return clientResponseDTO;
        }catch (DataAccessException e){
            return clientResponseDTO;
        }
    }


    @Override
    public int update(Long id, ClientModel cr) {
        Client client = this.clientRepository.findById(id).get();

        ContactModel email_model = new ContactModel("email", cr.getEmail());
        ContactModel tel_model = new ContactModel("tel", cr.getTel());

        LocationRequestDTO locationRequestDTO = new LocationRequestDTO(cr.getCountry(), cr.getAddress(), cr.getTown());
        this.locationService.update(client.getLocation().getId(), locationRequestDTO);

        this.contactService.update(id, email_model);
        this.contactService.update(id, tel_model);

        return this.clientRepository.update(cr.getUsername(), cr.getName(), cr.getType(), cr.getDescription(), cr.getPhoto(), id);
    }

    @Override
    public boolean delete(Long id) {
        try {
            clientRepository.deleteById(id);
            return true;
        }catch (DataAccessException e){
            return false;
        }
    }

    @Override
    public List<ClientResponseDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientResponseDTO> clientResponseDTOS = clients.stream().map(client -> this.clientMapper.clientToClientResponseDTO(client))
                .collect(Collectors.toList());
        for(ClientResponseDTO clientResponseDTO:clientResponseDTOS){
            clientResponseDTO.setEmail(this.contactService.getClientContact(clientResponseDTO.getId(), "email").getValue());
            clientResponseDTO.setPhone(this.contactService.getClientContact(clientResponseDTO.getId(), "tel").getValue());
        }
        return clientResponseDTOS;
    }

    @Override
    public ClientLightProjection signIn(String password, String email) {
        Long id = null;
        try {
            id = this.clientRepository.login(email, password);
        }catch (DataAccessException e){
            throw new RuntimeException("User not found");
        }
        ClientLightProjection clientProjection = new ClientLightProjection();
        if(id != null){
            clientProjection.setId(id);
            clientProjection.setType("client");
        }
        return clientProjection;
    }

    @Override
    public ClientResponseDTO register(RegistrationClientModel registrationClientModel) throws MessagingException {
        //Step 1:
        LocationRequestDTO locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setCountry("vide");
        locationRequestDTO.setTown("vide");
        locationRequestDTO.setAddress("vide");
        LocationResponseDTO location_saved = this.locationService.save(locationRequestDTO);
        //Step 2:
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
        clientRequestDTO.setDescription("vide");
        clientRequestDTO.setLocation(this.locationMapper.locationResponseToLocation(location_saved));
        clientRequestDTO.setPassword(registrationClientModel.getPassword());
        clientRequestDTO.setUsername(registrationClientModel.getUsername());
        clientRequestDTO.setType("vide");
        clientRequestDTO.setName("vide");
        clientRequestDTO.setActivated("false");

        ClientResponseDTO client_saved = save(clientRequestDTO);
        //last Step:
        if(!registrationClientModel.getEmail().isEmpty()){
            ContactRequestDTO email_contact = new ContactRequestDTO();
            email_contact.setClient(this.clientMapper.clientResponseDTOClient(client_saved));
            email_contact.setType("email");
            email_contact.setValue(registrationClientModel.getEmail());
            System.out.println("email client saved : "+ email_contact.getClient());
            this.contactService.save(email_contact);
        }

        //Send code for verification
        int min = 1000;
        int max = 9999;
        int code_validation = (int) Math.floor(Math.random()*(max-min+1)+min);

        this.validationRepository.save(new ValidationCode(null, client_saved.getId(), code_validation));

        String from = "andriampeno.odilon@gmail.com";
        String subject = "Activation de votre compte sur Do++";
        String to = registrationClientModel.getEmail();
        String content = "Votre code de validation est: "+code_validation;

        ValidationModel validationModel = new ValidationModel();
        validationModel.setTo(to);
        validationModel.setText(content);
        validationModel.setFrom(from);
        validationModel.setSubject(subject);
        validationModel.setTemplate("");

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", registrationClientModel.getUsername());
        properties.put("message", content);
        validationModel.setProperties(properties);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(validationModel.getProperties());
        helper.setFrom(validationModel.getFrom());
        helper.setTo(validationModel.getTo());
        helper.setSubject(validationModel.getSubject());
        String html = templateEngine.process("email-validation-template.html", context);
        helper.setText(html, true);

        try {
            emailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }

        return client_saved;
    }

    @Override
    public String uploadPhoto(MultipartFile file) throws IOException {
        return this.fileStorageService.saveImage(file);
    }

    @Override
    public boolean activate(Long id_user, ActivationModel code) {
        ValidationCode validationCode = this.validationRepository.getByIdUser(id_user);
        if(Objects.equals(validationCode.getCode_validation(), code.getCode())){
            this.clientRepository.activate(id_user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateDescription(String description, Long id) {
        try {
            this.clientRepository.updateDescription(description, id);
            return true;
        }catch (DataAccessException e){
            return false;
        }
    }
}
