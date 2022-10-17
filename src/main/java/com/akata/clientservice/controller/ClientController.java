package com.akata.clientservice.controller;

import com.akata.clientservice.dto.ClientRequestDTO;
import com.akata.clientservice.dto.ClientResponseDTO;
import com.akata.clientservice.model.ActivationModel;
import com.akata.clientservice.model.ClientModel;
import com.akata.clientservice.model.UniqueStrModel;
import com.akata.clientservice.services.FileStorageService;
import com.akata.clientservice.services.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/client")   
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private FileStorageService fileStorageService;

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

    @GetMapping(path = "/images/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource download (@PathVariable String filename) throws IOException {
        return this.fileStorageService.loadFile(filename);
    }

    @PostMapping(path = "/upload")
    public String uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return this.clientService.uploadPhoto(file);
    }

    @PostMapping(path = "/activate/{id_user}")
    public boolean activate(@PathVariable("id_user") Long id_user, @RequestBody ActivationModel code){
        return this.clientService.activate(id_user, code);
    }

    @PutMapping(path = "/updateDescription/{id}")
    public boolean updateDescription(@PathVariable("id") Long id, @RequestBody UniqueStrModel description){
        return this.clientService.updateDescription(description.getValue(), id);
    }
}
