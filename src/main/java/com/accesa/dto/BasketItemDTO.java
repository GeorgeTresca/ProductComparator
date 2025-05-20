package com.accesa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasketItemDTO {
    private String productName;
    private String store;
    private double price;
}
