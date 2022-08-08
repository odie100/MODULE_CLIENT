package com.akata.clientservice.mapper;

import com.akata.clientservice.dto.ContactRequestDTO;
import com.akata.clientservice.dto.ContactResponseDTO;
import com.akata.clientservice.entities.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactResponseDTO contactToContactResponseDTO(Contact contact);
    Contact contactRequestDTOContact (ContactRequestDTO contactRequestDTO);
}
