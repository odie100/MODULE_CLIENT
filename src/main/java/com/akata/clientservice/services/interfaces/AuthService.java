package com.akata.clientservice.services.interfaces;

import com.akata.clientservice.dto.ClientResponseDTO;
import com.akata.clientservice.model.RegistrationClientModel;
import com.akata.clientservice.model.SignInModel;
import com.akata.clientservice.projections.ClientLightProjection;

public interface AuthService {
    ClientLightProjection signIn(SignInModel signInModel);
    ClientResponseDTO signUp(RegistrationClientModel registrationClientModel);
}
