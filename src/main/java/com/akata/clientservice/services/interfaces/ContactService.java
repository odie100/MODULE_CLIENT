package com.akata.clientservice.services.interfaces;

import com.akata.clientservice.dto.ContactRequestDTO;
import com.akata.clientservice.dto.ContactResponseDTO;
import com.akata.clientservice.model.ContactModel;

import java.util.List;

public interface ContactService {
    ContactResponseDTO save(ContactRequestDTO contactRequestDTO);

    ContactResponseDTO getContact(Long id);

    int update(Long id, ContactModel contactModel);

    boolean delete(Long id);

    List<ContactResponseDTO> getAllContact();

    List<ContactResponseDTO> getContactByIdUser(Long id);
}
