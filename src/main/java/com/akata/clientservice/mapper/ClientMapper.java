package com.akata.clientservice.mapper;

import com.akata.clientservice.dto.ClientRequestDTO;
import com.akata.clientservice.dto.ClientResponseDTO;
import com.akata.clientservice.entities.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientResponseDTO clientToClientResponseDTO(Client client);
    Client clientRequestDTOClient(ClientRequestDTO clientRequestDTO);
    Client clientResponseDTOClient(ClientResponseDTO clientResponseDTO);
}
