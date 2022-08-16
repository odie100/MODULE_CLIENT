package com.akata.clientservice.services.interfaces;

import com.akata.clientservice.dto.LocationRequestDTO;
import com.akata.clientservice.dto.LocationResponseDTO;

import java.util.List;

public interface LocationService {
    LocationResponseDTO save(LocationRequestDTO locationRequestDTO);
    LocationResponseDTO getLocation(Long id);
    int update(Long id, LocationRequestDTO locationRequestDTO);
    boolean delete(Long id);
    List<LocationResponseDTO> getAllLocation();
}
