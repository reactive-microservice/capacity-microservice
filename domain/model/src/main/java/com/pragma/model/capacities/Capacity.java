package com.pragma.model.capacities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Capacity {
    private String id;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must have a maximum length of 50 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 90, message = "Description must have a maximum length of 90 characters")
    private String description;

    @Size(min = 3, max = 20, message = "Technologies must have a minimum of 3 and a maximum of 20 elements")
    private List<Technology> technologies;
}

