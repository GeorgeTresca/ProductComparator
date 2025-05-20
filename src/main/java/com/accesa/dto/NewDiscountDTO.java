package com.accesa.dto;

import com.accesa.model.Discount;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class NewDiscountDTO {
    private String productId;
    private String productName;
    private String brand;
    private double quantity;
    private String unit;
    private String category;
    private int discountPercent;
    private String store;
    private LocalDate fromDate;
    private LocalDate toDate;

    public static NewDiscountDTO from(Discount d) {
        return NewDiscountDTO.builder()
                .productId(d.getProductId())
                .productName(d.getProductName())
                .brand(d.getBrand())
                .quantity(d.getPackageQuantity())
                .unit(d.getPackageUnit())
                .category(d.getProductCategory())
                .discountPercent(d.getPercentageOfDiscount())
                .store(d.getStore())
                .fromDate(d.getFromDate())
                .toDate(d.getToDate())
                .build();
    }
}
