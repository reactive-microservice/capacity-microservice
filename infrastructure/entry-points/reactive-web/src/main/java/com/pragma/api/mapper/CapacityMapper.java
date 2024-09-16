package com.pragma.api.mapper;

import com.pragma.api.dto.CapacityDTO;
import com.pragma.model.capacities.Capacity;
import com.pragma.model.capacities.Technology;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CapacityMapper {

   default Capacity toDomain(CapacityDTO capacityDTO) {
    List<Technology> technologies = capacityDTO.getTechnologies().stream()
            .map(id -> Technology.builder().id(id).build())
            .collect(Collectors.toList());
    return new Capacity(capacityDTO.getId(), capacityDTO.getName(), capacityDTO.getDescription(), technologies);
    }
}
