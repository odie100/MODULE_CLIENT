package com.akata.clientservice.controller;

import com.akata.clientservice.dto.ContactRequestDTO;
import com.akata.clientservice.dto.ContactResponseDTO;
import com.akata.clientservice.services.interfaces.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/contact")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping(path = "/{id}")
    public ContactResponseDTO get(@PathVariable("id") Long id){
        return this.contactService.getContact(id);
    }

    @GetMapping(path = "/all")
    public List<ContactResponseDTO> getAll(){
        return this.contactService.getAllContact();
    }

    @PostMapping(path = "/insert")
    public ContactResponseDTO insert(@RequestBody ContactRequestDTO contactRequestDTO){
        return this.contactService.save(contactRequestDTO);
    }

    @DeleteMapping(path = "/{id}")
    public boolean delete(@PathVariable("id") Long id){
        return this.contactService.delete(id);
    }

    @PutMapping(path = "/{id}")
    public ContactResponseDTO update(@PathVariable("id") Long id, @RequestBody ContactRequestDTO contactRequestDTO){
        return this.contactService.update(id, contactRequestDTO);
    }

    @GetMapping(path = "/filter/{id}")
    public List<ContactResponseDTO> getAllByIdUser(@PathVariable("id") Long id){
        return this.contactService.getContactByIdUser(id);
    }
}
