package com.akata.clientservice.services.interfaces;

import com.akata.clientservice.dto.ContactRequestDTO;
import com.akata.clientservice.dto.ContactResponseDTO;

import java.util.List;

public interface ContactService {
    ContactResponseDTO save(ContactRequestDTO contactRequestDTO);

    ContactResponseDTO getContact(Long id);

    ContactResponseDTO update(Long id, ContactRequestDTO contactRequestDTO);

    boolean delete(Long id);

    List<ContactResponseDTO> getAllContact();

    List<ContactResponseDTO> getContactByIdUser(Long id);
}
