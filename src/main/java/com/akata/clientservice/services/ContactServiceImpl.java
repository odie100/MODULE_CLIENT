package com.akata.clientservice.services;

import com.akata.clientservice.dto.ContactRequestDTO;
import com.akata.clientservice.dto.ContactResponseDTO;
import com.akata.clientservice.entities.Client;
import com.akata.clientservice.entities.Contact;
import com.akata.clientservice.mapper.ClientMapper;
import com.akata.clientservice.mapper.ContactMapper;
import com.akata.clientservice.model.ContactModel;
import com.akata.clientservice.repository.ContactRepository;
import com.akata.clientservice.services.interfaces.ClientService;
import com.akata.clientservice.services.interfaces.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public ContactResponseDTO save(ContactRequestDTO contactRequestDTO) {
        Contact contact = this.contactMapper.contactRequestDTOContact(contactRequestDTO);
        return this.contactMapper.contactToContactResponseDTO(this.contactRepository.save(contact));
    }

    @Override
    public ContactResponseDTO getContact(Long id) {
        return this.contactMapper.contactToContactResponseDTO(this.contactRepository.findById(id).get());
    }

    @Override
    public int update(Long id, ContactModel contactModel) {
        if(contactModel.getType().equals("email")){
            return this.contactRepository.updateEmail(contactModel.getValue(), id);
        }else{
            Contact checked_contact = this.contactRepository.findExisting("tel", id);
            System.out.println("Contact tel checked: "+checked_contact);
            if(checked_contact != null){
                return this.contactRepository.updateTel(contactModel.getValue(), id);
            }else{
                Client client = this.clientMapper.clientResponseDTOClient(this.clientService.getClient(id));
                Contact contact = new Contact(null, "tel", contactModel.getValue(), client);
                this.contactRepository.save(contact);
                return 1;
            }
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            this.contactRepository.deleteById(id);
            return true;
        }catch (DataAccessException e){
            return false;
        }
    }

    @Override
    public List<ContactResponseDTO> getAllContact() {
        List<Contact> contacts = this.contactRepository.findAll();
        return contacts.stream().map(contact -> this.contactMapper.contactToContactResponseDTO(contact))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactResponseDTO> getContactByIdUser(Long id) {
        List<Contact> contacts = this.contactRepository.findAllByIdUser(id);
        return contacts.stream().map(contact -> this.contactMapper.contactToContactResponseDTO(contact))
                .collect(Collectors.toList());
    }

    @Override
    public ContactResponseDTO findByUserAndType(String type, Long id) {
        return this.contactMapper.contactToContactResponseDTO(this.contactRepository.findExisting(type, id));
    }
}
