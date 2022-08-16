package com.akata.clientservice.controller;

import com.akata.clientservice.dto.ClientRequestDTO;
import com.akata.clientservice.dto.ClientResponseDTO;
import com.akata.clientservice.model.ClientModel;
import com.akata.clientservice.services.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/client")   
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping(path = "/insert")
    public ClientResponseDTO insert(@RequestBody ClientRequestDTO clientRequestDTO){
        return clientService.save(clientRequestDTO);
    }

    @GetMapping(path = "/all")
    public List<ClientResponseDTO> clients(){
        return clientService.getAllClients();
    }

    @GetMapping(path = "/{id}")
    public ClientResponseDTO get(@PathVariable("id") Long id){
        return clientService.getClient(id);
    }

    @PutMapping(path = "/update/{id}")
    public int update(@PathVariable("id") Long id, @RequestBody ClientModel clientRequestDTO){
        return clientService.update(id,clientRequestDTO);
    }

    @DeleteMapping(path = "/delete/{id}")
    public boolean deleteById(@PathVariable("id") Long id){
        return clientService.delete(id);
    }
}
