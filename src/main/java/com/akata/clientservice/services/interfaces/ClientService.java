package com.akata.clientservice.services.interfaces;

import com.akata.clientservice.dto.ClientRequestDTO;
import com.akata.clientservice.dto.ClientResponseDTO;

import java.util.List;

public interface ClientService {
    ClientResponseDTO save(ClientRequestDTO clientRequestDTO);
    ClientResponseDTO getClient(Long id);
    ClientResponseDTO update(Long id, ClientRequestDTO clientRequestDTO);
    boolean delete(Long id);
    List<ClientResponseDTO> getAllClients();
    ClientResponseDTO signIn(String password, String email);
}
