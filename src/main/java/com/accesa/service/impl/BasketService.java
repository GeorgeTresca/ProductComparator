package com.accesa.service.impl;

import com.accesa.config.AppDateProvider;
import com.accesa.dto.BasketItemDTO;
import com.accesa.dto.BasketRequestDTO;
import com.accesa.dto.BasketResponseDTO;
import com.accesa.model.Product;
import com.accesa.service.IBasketService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BasketService implements IBasketService {

    private final DataLoaderService dataLoaderService;
    private final AppDateProvider appDateProvider;

    public BasketService(DataLoaderService dataLoaderService, AppDateProvider appDateProvider) {
        this.dataLoaderService = dataLoaderService;
        this.appDateProvider = appDateProvider;
    }

    // For each product in the user's basket it retrieves all matching offers (date <= today), keeps the latest offer per store
    // Then, selects the offer with the lowest price and returns the cheapest combination and the total cost
    @Override
    public BasketResponseDTO optimizeBasket(BasketRequestDTO request) {
        LocalDate today = appDateProvider.getToday();
        List<Product> allProducts = dataLoaderService.getAllProducts();

        List<BasketItemDTO> selectedItems = new ArrayList<>();

        for (String name : request.getProductNames()) {
            Map<String, Optional<Product>> latestPerStore = allProducts.stream()
                    .filter(p -> p.getProductName().equalsIgnoreCase(name))
                    .filter(p -> !p.getDate().isAfter(today))
                    .collect(Collectors.groupingBy(
                            Product::getStore,
                            Collectors.maxBy(Comparator.comparing(Product::getDate))
                    ));

            Optional<Product> best = latestPerStore.values().stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .min(Comparator.comparingDouble(Product::getPrice));

            best.ifPresent(product -> selectedItems.add(BasketItemDTO.builder()
                    .productName(name)
                    .store(product.getStore())
                    .price(product.getPrice())
                    .build()));
        }

        double total = selectedItems.stream()
                .mapToDouble(BasketItemDTO::getPrice)
                .sum();

        return BasketResponseDTO.builder()
                .items(selectedItems)
                .totalPrice(total)
                .build();
    }
}
