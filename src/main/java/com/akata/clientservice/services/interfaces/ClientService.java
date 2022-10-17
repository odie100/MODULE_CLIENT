package com.akata.clientservice.services.interfaces;

import com.akata.clientservice.dto.ClientRequestDTO;
import com.akata.clientservice.dto.ClientResponseDTO;
import com.akata.clientservice.model.ActivationModel;
import com.akata.clientservice.model.ClientModel;
import com.akata.clientservice.model.RegistrationClientModel;
import com.akata.clientservice.projections.ClientLightProjection;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface ClientService {
    ClientResponseDTO save(ClientRequestDTO clientRequestDTO);
    ClientResponseDTO getClient(Long id);
    int update(Long id, ClientModel clientModel);
    boolean delete(Long id);
    List<ClientResponseDTO> getAllClients();
    ClientLightProjection signIn(String password, String email);
    ClientResponseDTO register(RegistrationClientModel registrationClientModel) throws MessagingException;
    String uploadPhoto(MultipartFile file) throws IOException;

    boolean activate(Long id_user, ActivationModel code);

    boolean updateDescription(String description, Long id);
}
