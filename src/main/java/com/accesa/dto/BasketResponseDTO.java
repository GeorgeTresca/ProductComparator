package com.accesa.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BasketResponseDTO {
    private List<BasketItemDTO> items;
    private double totalPrice;
}
