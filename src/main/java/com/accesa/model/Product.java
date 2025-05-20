package com.accesa.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Product {
    private String productId;
    private String productName;
    private String productCategory;
    private String brand;
    private double packageQuantity;
    private String packageUnit;
    private double price;
    private String currency;

    private String store;
    private LocalDate date;
}
