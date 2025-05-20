package com.accesa.service.impl;

import com.accesa.dto.PriceHistoryPointDTO;
import com.accesa.service.IProductService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final DataLoaderService dataLoaderService;

    public ProductService(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    // Retrieves all previous prices for a given product ID, while also implementing optional filters include store,
    // brand, and categorv. The result is sorted chronologically to support trend visualization
    @Override
    public List<PriceHistoryPointDTO> getPriceHistory(String productId, String store, String brand, String category) {
        return dataLoaderService.getAllProducts().stream()
                .filter(p -> p.getProductId().equalsIgnoreCase(productId))
                .filter(p -> store == null || p.getStore().equalsIgnoreCase(store))
                .filter(p -> brand == null || p.getBrand().equalsIgnoreCase(brand))
                .filter(p -> category == null || p.getProductCategory().equalsIgnoreCase(category))
                .map(p -> PriceHistoryPointDTO.builder()
                        .store(p.getStore())
                        .date(p.getDate())
                        .price(p.getPrice())
                        .brand(p.getBrand())
                        .productCategory(p.getProductCategory())
                        .build())
                .sorted(Comparator.comparing(PriceHistoryPointDTO::getDate))
                .collect(Collectors.toList());
    }

}
