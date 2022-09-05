package com.akata.clientservice.services.interfaces;

import com.akata.clientservice.dto.ClientRequestDTO;
import com.akata.clientservice.dto.ClientResponseDTO;
import com.akata.clientservice.model.ClientModel;
import com.akata.clientservice.model.RegistrationClientModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClientService {
    ClientResponseDTO save(ClientRequestDTO clientRequestDTO);
    ClientResponseDTO getClient(Long id);
    int update(Long id, ClientModel clientModel);
    boolean delete(Long id);
    List<ClientResponseDTO> getAllClients();
    ClientResponseDTO signIn(String password, String email);
    ClientResponseDTO register(RegistrationClientModel registrationClientModel);
    String uploadPhoto(MultipartFile file) throws IOException;
}
