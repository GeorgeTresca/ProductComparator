package com.accesa.service.impl;

import com.accesa.config.AppDateProvider;
import com.accesa.dto.BestValueProductDTO;
import com.accesa.model.Product;
import com.accesa.service.IRecommendationService;
import com.accesa.util.UnitNormalizer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecommendationService implements IRecommendationService {

    private final DataLoaderService dataLoaderService;
    private final AppDateProvider appDateProvider;

    public RecommendationService(DataLoaderService dataLoaderService, AppDateProvider appDateProvider) {
        this.dataLoaderService = dataLoaderService;
        this.appDateProvider = appDateProvider;
    }

    /** For a given product name - retrieves all offers with a date <= today, then keeps the latest offer per store
    After that, it normalizes quantity units and finally calculates price per unit and sorts ascending */
    @Override
    public List<BestValueProductDTO> getBestValueForProduct(String productName) {
        LocalDate today = appDateProvider.getToday();

        Map<String, Optional<Product>> latestPerStore = dataLoaderService.getAllProducts().stream()
                .filter(p -> p.getProductName().equalsIgnoreCase(productName))
                .filter(p -> p.getDate().isBefore(today.plusDays(1))) // date <= today
                .filter(p -> UnitNormalizer.canNormalize(p.getPackageUnit()))
                .collect(Collectors.groupingBy(
                        Product::getStore,
                        Collectors.maxBy(Comparator.comparing(Product::getDate))
                ));

        return latestPerStore.values().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(p -> {
                    double normalizedQuantity = UnitNormalizer.normalize(p.getPackageQuantity(), p.getPackageUnit());
                    double pricePerUnit = p.getPrice() / normalizedQuantity;

                    return BestValueProductDTO.builder()
                            .productId(p.getProductId())
                            .productName(p.getProductName())
                            .brand(p.getBrand())
                            .store(p.getStore())
                            .price(p.getPrice())
                            .quantity(p.getPackageQuantity())
                            .unit(p.getPackageUnit())
                            .pricePerUnit(pricePerUnit)
                            .build();
                })
                .sorted(Comparator.comparingDouble(BestValueProductDTO::getPricePerUnit))
                .collect(Collectors.toList());
    }

}
