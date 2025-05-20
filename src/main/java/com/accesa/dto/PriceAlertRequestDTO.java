package com.accesa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PriceAlertRequestDTO {
    @NotBlank
    private String productName;

    @Min(value = 0, message = "Target price must be non-negative.")
    private double targetPrice;
}
