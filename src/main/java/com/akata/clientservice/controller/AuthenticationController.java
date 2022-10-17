package com.akata.clientservice.controller;

import com.akata.clientservice.dto.ClientResponseDTO;
import com.akata.clientservice.mapper.ClientMapper;
import com.akata.clientservice.mapper.LocationMapper;
import com.akata.clientservice.model.RegistrationClientModel;
import com.akata.clientservice.model.SignInModel;
import com.akata.clientservice.projections.ClientLightProjection;
import com.akata.clientservice.services.interfaces.ClientService;
import com.akata.clientservice.services.interfaces.ContactService;
import com.akata.clientservice.services.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping(path = "/api/authentication")
public class AuthenticationController {
    @Autowired
    private LocationService locationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private ClientMapper clientMapper;

    @PostMapping(path = "/register/client")
    public ClientResponseDTO register(@RequestBody RegistrationClientModel clientModel) throws MessagingException {
        return this.clientService.register(clientModel);
    }

    @PostMapping(path = "/signin/client")
    public ClientLightProjection signIn(@RequestBody SignInModel signInModel){
        return this.clientService.signIn(signInModel.getPassword(), signInModel.getEmail());
    }
}
