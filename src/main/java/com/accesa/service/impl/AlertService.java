package com.accesa.service.impl;

import com.accesa.config.AppDateProvider;
import com.accesa.dto.PriceAlertMatchDTO;
import com.accesa.dto.PriceAlertRequestDTO;
import com.accesa.model.Product;
import com.accesa.service.IAlertService;
import com.accesa.service.impl.DataLoaderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlertService implements IAlertService {

    private final DataLoaderService dataLoaderService;
    private final AppDateProvider appDateProvider;

    public AlertService(DataLoaderService dataLoaderService, AppDateProvider appDateProvider) {
        this.dataLoaderService = dataLoaderService;
        this.appDateProvider = appDateProvider;
    }

    /** For a given product name and target price
     retrieves the latest offer (per store) where date <= today, then filters for offers where price <= target price */
    @Override
    public List<PriceAlertMatchDTO> checkAlerts(PriceAlertRequestDTO request) {
        LocalDate today = appDateProvider.getToday();

        Map<String, Optional<Product>> latestPerStore = dataLoaderService.getAllProducts().stream()
                .filter(p -> p.getProductName().equalsIgnoreCase(request.getProductName()))
                .filter(p -> !p.getDate().isAfter(today))
                .collect(Collectors.groupingBy(
                        Product::getStore,
                        Collectors.maxBy(Comparator.comparing(Product::getDate))
                ));

        return latestPerStore.values().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(p -> p.getPrice() <= request.getTargetPrice())
                .map(p -> PriceAlertMatchDTO.builder()
                        .productName(p.getProductName())
                        .store(p.getStore())
                        .price(p.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
