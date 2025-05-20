package com.accesa.controller;

import com.accesa.dto.BestValueProductDTO;
import com.accesa.service.impl.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/best-value")
    public List<BestValueProductDTO> getBestValue(@RequestParam String productName) {
        return recommendationService.getBestValueForProduct(productName);
    }
}
