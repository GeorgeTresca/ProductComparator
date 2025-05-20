package com.accesa.service;

import com.accesa.model.Product;
import com.accesa.model.Discount;

import java.util.List;

public interface IDataLoaderService {
    void loadAll();
    List<Product> getAllProducts();
    List<Discount> getAllDiscounts();
}

