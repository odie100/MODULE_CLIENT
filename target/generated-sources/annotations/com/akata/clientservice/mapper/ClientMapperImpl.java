package com.akata.clientservice.mapper;

import com.akata.clientservice.dto.ClientRequestDTO;
import com.akata.clientservice.dto.ClientResponseDTO;
import com.akata.clientservice.entities.Client;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-21T17:53:09+0300",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.16 (Ubuntu)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientResponseDTO clientToClientResponseDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();

        clientResponseDTO.setId( client.getId() );
        clientResponseDTO.setUsername( client.getUsername() );
        clientResponseDTO.setLocation( client.getLocation() );
        clientResponseDTO.setName( client.getName() );
        clientResponseDTO.setType( client.getType() );
        clientResponseDTO.setDescription( client.getDescription() );
        clientResponseDTO.setPhoto( client.getPhoto() );
        clientResponseDTO.setCreation( client.getCreation() );
        clientResponseDTO.setActivated( client.getActivated() );

        return clientResponseDTO;
    }

    @Override
    public Client clientRequestDTOClient(ClientRequestDTO clientRequestDTO) {
        if ( clientRequestDTO == null ) {
            return null;
        }

        Client client = new Client();

        client.setUsername( clientRequestDTO.getUsername() );
        client.setPassword( clientRequestDTO.getPassword() );
        client.setName( clientRequestDTO.getName() );
        client.setType( clientRequestDTO.getType() );
        client.setDescription( clientRequestDTO.getDescription() );
        client.setCreation( clientRequestDTO.getCreation() );
        client.setActivated( clientRequestDTO.getActivated() );
        client.setLocation( clientRequestDTO.getLocation() );

        return client;
    }

    @Override
    public Client clientResponseDTOClient(ClientResponseDTO clientResponseDTO) {
        if ( clientResponseDTO == null ) {
            return null;
        }

        Client client = new Client();

        client.setId( clientResponseDTO.getId() );
        client.setUsername( clientResponseDTO.getUsername() );
        client.setName( clientResponseDTO.getName() );
        client.setType( clientResponseDTO.getType() );
        client.setDescription( clientResponseDTO.getDescription() );
        client.setCreation( clientResponseDTO.getCreation() );
        client.setActivated( clientResponseDTO.getActivated() );
        client.setLocation( clientResponseDTO.getLocation() );
        client.setPhoto( clientResponseDTO.getPhoto() );

        return client;
    }
}
