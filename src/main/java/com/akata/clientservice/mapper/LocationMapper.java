package com.akata.clientservice.mapper;

import com.akata.clientservice.dto.LocationRequestDTO;
import com.akata.clientservice.dto.LocationResponseDTO;
import com.akata.clientservice.entities.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationResponseDTO locationToLocationResponseDTO(Location location);
    Location locationRequestDTOLocation (LocationRequestDTO locationRequestDTO);
    Location locationResponseToLocation(LocationResponseDTO locationResponseDTO);
}
