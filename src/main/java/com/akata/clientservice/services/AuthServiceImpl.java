package com.akata.clientservice.services;

import com.akata.clientservice.dto.ClientResponseDTO;
import com.akata.clientservice.model.RegistrationClientModel;
import com.akata.clientservice.model.SignInModel;
import com.akata.clientservice.services.interfaces.AuthService;
import com.akata.clientservice.services.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthServiceImpl implements AuthService {

    @Autowired
    private ClientService clientService;

    @Override
    public ClientResponseDTO signIn(SignInModel signInModel) {
        return this.clientService.signIn(signInModel.getPassword(), signInModel.getEmail());
    }

    @Override
    public ClientResponseDTO signUp(RegistrationClientModel registrationClientModel) {
        return null;
    }
}
