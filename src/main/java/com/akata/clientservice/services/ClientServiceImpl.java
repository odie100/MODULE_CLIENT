package com.akata.clientservice.services;

import com.akata.clientservice.dto.*;
import com.akata.clientservice.entities.Client;
import com.akata.clientservice.mapper.ClientMapper;
import com.akata.clientservice.mapper.LocationMapper;
import com.akata.clientservice.model.ClientModel;
import com.akata.clientservice.model.ContactModel;
import com.akata.clientservice.model.RegistrationClientModel;
import com.akata.clientservice.repository.ClientRepository;
import com.akata.clientservice.services.interfaces.ClientService;
import com.akata.clientservice.services.interfaces.ContactService;
import com.akata.clientservice.services.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
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

    @Override
    public ClientResponseDTO save(ClientRequestDTO clientRequestDTO) {
        clientRequestDTO.setCreation(LocalDate.now());
        Client saved_client = clientRepository.save(clientMapper.clientRequestDTOClient(clientRequestDTO));
        return clientMapper.clientToClientResponseDTO(saved_client);
    }

    @Override
    public ClientResponseDTO getClient(Long id) {
        Client client = clientRepository.findById(id).get();
        ClientResponseDTO clientResponseDTO = this.clientMapper.clientToClientResponseDTO(client);
        clientResponseDTO.setEmail(this.contactService.findByUserAndType("email",clientResponseDTO.getId()).getValue());
        clientResponseDTO.setPhone(this.contactService.findByUserAndType("tel",clientResponseDTO.getId()).getValue());

        return clientResponseDTO;
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
    public ClientResponseDTO signIn(String password, String email) {
        Client client = null;
        try {
            client = this.clientRepository.login(email, password);
        }catch (DataAccessException e){
            throw new RuntimeException("User not found");
        }
        ClientResponseDTO clientResponseDTO = null;
        if(client != null){
            clientResponseDTO = this.clientMapper.clientToClientResponseDTO(client);
        }
        return clientResponseDTO;
    }

    @Override
    public ClientResponseDTO register(RegistrationClientModel registrationClientModel) {
        //Step 1:
        LocationRequestDTO locationRequestDTO = new LocationRequestDTO();
        locationRequestDTO.setCountry(registrationClientModel.getCountry());
        locationRequestDTO.setTown(registrationClientModel.getTown());
        locationRequestDTO.setAddress(registrationClientModel.getAddress());
        LocationResponseDTO location_saved = this.locationService.save(locationRequestDTO);
        //Step 2:
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
        clientRequestDTO.setDescription(registrationClientModel.getDescription());
        clientRequestDTO.setLocation(this.locationMapper.locationResponseToLocation(location_saved));
        clientRequestDTO.setPassword(registrationClientModel.getPassword());
        clientRequestDTO.setUsername(registrationClientModel.getUsername());
        clientRequestDTO.setType(registrationClientModel.getType());
        clientRequestDTO.setName(registrationClientModel.getName());

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

        if(!registrationClientModel.getTel().isEmpty()){
            ContactRequestDTO tel_contact = new ContactRequestDTO();
            tel_contact.setClient(this.clientMapper.clientResponseDTOClient(client_saved));
            tel_contact.setType("tel");
            tel_contact.setValue(registrationClientModel.getTel());
            this.contactService.save(tel_contact);
        }

        return client_saved;
    }

    @Override
    public String uploadPhoto(MultipartFile file) throws IOException {
        return this.fileStorageService.saveImage(file);
    }
}
