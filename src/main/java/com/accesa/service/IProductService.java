package com.accesa.service;

import com.accesa.dto.PriceHistoryPointDTO;

import java.util.List;

public interface IProductService {
    List<PriceHistoryPointDTO> getPriceHistory(String productId, String store, String brand, String category);


}