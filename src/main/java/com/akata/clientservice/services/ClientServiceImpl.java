package com.akata.clientservice.services;

import com.akata.clientservice.dto.ClientRequestDTO;
import com.akata.clientservice.dto.ClientResponseDTO;
import com.akata.clientservice.entities.Client;
import com.akata.clientservice.mapper.ClientMapper;
import com.akata.clientservice.repository.ClientRepository;
import com.akata.clientservice.services.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientMapper clientMapper;

    @Override
    public ClientResponseDTO save(ClientRequestDTO clientRequestDTO) {
        clientRequestDTO.setCreation(LocalDate.now());
        Client saved_client = clientRepository.save(clientMapper.clientRequestDTOClient(clientRequestDTO));
        return clientMapper.clientToClientResponseDTO(saved_client);
    }

    @Override
    public ClientResponseDTO getClient(Long id) {
        Client client = clientRepository.findById(id).get();
        return clientMapper.clientToClientResponseDTO(client);
    }


    @Override
    public ClientResponseDTO update(Long id, ClientRequestDTO clientRequestDTO) {
        Client client = clientMapper.clientRequestDTOClient(clientRequestDTO);
        client.setId(id);
        return clientMapper.clientToClientResponseDTO(clientRepository.save(client));
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
        return clientRepository.findAll().stream()
                .map(client -> clientMapper.clientToClientResponseDTO(client)).collect(Collectors.toList());
    }

    @Override
    public ClientResponseDTO signIn(String password, String email) {
        Client client = this.clientRepository.login(email, password);
        ClientResponseDTO clientResponseDTO = null;
        if (client.getUsername() != null || !client.getUsername().isEmpty()) {
            clientResponseDTO = this.clientMapper.clientToClientResponseDTO(client);
        }
        return clientResponseDTO;
    }
}
