package com.akata.clientservice.services.interfaces;

import com.akata.clientservice.dto.ClientResponseDTO;
import com.akata.clientservice.model.RegistrationClientModel;
import com.akata.clientservice.model.SignInModel;

public interface AuthService {
    ClientResponseDTO signIn(SignInModel signInModel);
    ClientResponseDTO signUp(RegistrationClientModel registrationClientModel);
}
