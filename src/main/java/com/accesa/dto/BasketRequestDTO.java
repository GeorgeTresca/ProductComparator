package com.accesa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class BasketRequestDTO {
    @NotNull
    @Size(min = 1, message = "At least one product name must be provided.")
    private List<@NotBlank String> productNames;
}
