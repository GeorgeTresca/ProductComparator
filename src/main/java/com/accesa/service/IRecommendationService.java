package com.accesa.service;

import com.accesa.dto.BestValueProductDTO;

import java.util.List;

public interface IRecommendationService {
    List<BestValueProductDTO> getBestValueForProduct(String productName);
}
