package com.akata.clientservice.mapper;

import com.akata.clientservice.dto.ContactRequestDTO;
import com.akata.clientservice.dto.ContactResponseDTO;
import com.akata.clientservice.entities.Contact;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-17T12:18:06+0300",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.16 (Ubuntu)"
)
@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public ContactResponseDTO contactToContactResponseDTO(Contact contact) {
        if ( contact == null ) {
            return null;
        }

        ContactResponseDTO contactResponseDTO = new ContactResponseDTO();

        contactResponseDTO.setId( contact.getId() );
        contactResponseDTO.setType( contact.getType() );
        contactResponseDTO.setValue( contact.getValue() );

        return contactResponseDTO;
    }

    @Override
    public Contact contactRequestDTOContact(ContactRequestDTO contactRequestDTO) {
        if ( contactRequestDTO == null ) {
            return null;
        }

        Contact contact = new Contact();

        contact.setType( contactRequestDTO.getType() );
        contact.setValue( contactRequestDTO.getValue() );
        contact.setClient( contactRequestDTO.getClient() );

        return contact;
    }
}
