package com.akata.clientservice.services;

import com.akata.clientservice.dto.LocationRequestDTO;
import com.akata.clientservice.dto.LocationResponseDTO;
import com.akata.clientservice.entities.Location;
import com.akata.clientservice.mapper.LocationMapper;
import com.akata.clientservice.repository.LocationRepository;
import com.akata.clientservice.services.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public LocationResponseDTO save(LocationRequestDTO locationRequestDTO) {
        Location location = this.locationMapper.locationRequestDTOLocation(locationRequestDTO);
        return this.locationMapper.locationToLocationResponseDTO(this.locationRepository.save(location));
    }

    @Override
    public LocationResponseDTO getLocation(Long id) {
        return this.locationMapper.locationToLocationResponseDTO(this.locationRepository.findById(id).get());
    }

    @Override
    public int update(Long id, LocationRequestDTO locationRequestDTO) {
       return this.locationRepository.update(locationRequestDTO.getAddress(), locationRequestDTO.getCountry(),
               locationRequestDTO.getTown(), id);
    }

    @Override
    public boolean delete(Long id) {
        try {
            this.locationRepository.deleteById(id);
            return true;
        }catch (DataAccessException e){
            return false;
        }
    }

    @Override
    public List<LocationResponseDTO> getAllLocation() {
        List<Location> locations = this.locationRepository.findAll();
        return locations.stream().map(location -> this.locationMapper.locationToLocationResponseDTO(location))
                .collect(Collectors.toList());
    }
}
