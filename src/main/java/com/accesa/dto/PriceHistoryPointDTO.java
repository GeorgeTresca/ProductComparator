package com.accesa.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PriceHistoryPointDTO {
    private String store;
    private LocalDate date;
    private double price;
    private String brand;
    private String productCategory;
}

