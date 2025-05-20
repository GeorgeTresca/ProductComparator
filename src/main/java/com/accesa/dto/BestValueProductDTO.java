package com.accesa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BestValueProductDTO {
    private String productId;
    private String productName;
    private String brand;
    private String store;
    private double price;
    private double quantity;
    private String unit;
    private double pricePerUnit;
}
