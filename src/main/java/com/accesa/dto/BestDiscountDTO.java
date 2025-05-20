package com.accesa.dto;

import com.accesa.model.Discount;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BestDiscountDTO {
    private String productId;
    private String productName;
    private String brand;
    private String store;
    private int discountPercent;

    public static BestDiscountDTO from(Discount d) {
        return BestDiscountDTO.builder()
                .productId(d.getProductId())
                .productName(d.getProductName())
                .brand(d.getBrand())
                .store(d.getStore())
                .discountPercent(d.getPercentageOfDiscount())
                .build();
    }
}
