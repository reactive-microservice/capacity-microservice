package com.pragma.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class CapacityDTO {

    private String id;
    private String name;
    private String description;
    private List<String> technologies;
}
