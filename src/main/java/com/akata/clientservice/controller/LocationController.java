package com.akata.clientservice.controller;

import com.akata.clientservice.dto.LocationRequestDTO;
import com.akata.clientservice.dto.LocationResponseDTO;
import com.akata.clientservice.services.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/location")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping(path = "/{id}")
    public LocationResponseDTO get(@PathVariable("id") Long id){
        return this.locationService.getLocation(id);
    }

    @GetMapping(path = "/all")
    public List<LocationResponseDTO> getAll(){
        return this.locationService.getAllLocation();
    }

    @DeleteMapping(path = "/{id}")
    public boolean delete(@PathVariable("id") Long id){
        return this.locationService.delete(id);
    }

    @PutMapping(path = "/{id}")
    public LocationResponseDTO update(@PathVariable("id") Long id, LocationRequestDTO locationRequestDTO){
        return this.locationService.update(id, locationRequestDTO);
    }

    @PostMapping(path = "/insert")
    public LocationResponseDTO insert(@RequestBody LocationRequestDTO locationRequestDTO){
        return this.locationService.save(locationRequestDTO);
    }
}
