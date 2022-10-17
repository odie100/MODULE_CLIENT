package com.akata.clientservice.mapper;

import com.akata.clientservice.dto.LocationRequestDTO;
import com.akata.clientservice.dto.LocationResponseDTO;
import com.akata.clientservice.entities.Location;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-17T12:18:06+0300",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.16 (Ubuntu)"
)
@Component
public class LocationMapperImpl implements LocationMapper {

    @Override
    public LocationResponseDTO locationToLocationResponseDTO(Location location) {
        if ( location == null ) {
            return null;
        }

        LocationResponseDTO locationResponseDTO = new LocationResponseDTO();

        locationResponseDTO.setId( location.getId() );
        locationResponseDTO.setCountry( location.getCountry() );
        locationResponseDTO.setAddress( location.getAddress() );
        locationResponseDTO.setTown( location.getTown() );

        return locationResponseDTO;
    }

    @Override
    public Location locationRequestDTOLocation(LocationRequestDTO locationRequestDTO) {
        if ( locationRequestDTO == null ) {
            return null;
        }

        Location location = new Location();

        location.setCountry( locationRequestDTO.getCountry() );
        location.setAddress( locationRequestDTO.getAddress() );
        location.setTown( locationRequestDTO.getTown() );

        return location;
    }

    @Override
    public Location locationResponseToLocation(LocationResponseDTO locationResponseDTO) {
        if ( locationResponseDTO == null ) {
            return null;
        }

        Location location = new Location();

        location.setId( locationResponseDTO.getId() );
        location.setCountry( locationResponseDTO.getCountry() );
        location.setAddress( locationResponseDTO.getAddress() );
        location.setTown( locationResponseDTO.getTown() );

        return location;
    }
}
