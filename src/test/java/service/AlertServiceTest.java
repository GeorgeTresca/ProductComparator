package service;

import com.accesa.config.AppDateProvider;
import com.accesa.dto.PriceAlertMatchDTO;
import com.accesa.dto.PriceAlertRequestDTO;
import com.accesa.model.Product;
import com.accesa.service.impl.AlertService;
import com.accesa.service.impl.DataLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlertServiceTest {

    private AlertService alertService;

    @BeforeEach
    void setUp() {
        DataLoaderService mockLoader = new DataLoaderService() {
            @Override
            public List<Product> getAllProducts() {
                return List.of(
                        // Lidl – offer 2025-05-01 – too expensive
                        createProduct("lapte zuzu", "Lidl", LocalDate.of(2025, 5, 1), 10.5),
                        // Kaufland – latest ≤ today, matches alert
                        createProduct("lapte zuzu", "Kaufland", LocalDate.of(2025, 5, 3), 9.8),
                        // Profi – future date, should be ignored
                        createProduct("lapte zuzu", "Profi", LocalDate.of(2025, 5, 5), 9.2)
                );
            }

            @Override
            public void loadAll() {}
        };

        AppDateProvider mockDate = new AppDateProvider() {
            @Override
            public LocalDate getToday() {
                return LocalDate.of(2025, 5, 3);
            }
        };

        alertService = new AlertService(mockLoader, mockDate);
    }

    @Test
    void shouldReturnOnlyMatchingOffersBelowOrEqualToTarget() {
        PriceAlertRequestDTO request = new PriceAlertRequestDTO();
        request.setProductName("lapte zuzu");
        request.setTargetPrice(10.0);

        List<PriceAlertMatchDTO> result = alertService.checkAlerts(request);

        assertEquals(1, result.size());
        assertEquals("kaufland", result.get(0).getStore().toLowerCase());
        assertEquals(9.8, result.get(0).getPrice(), 0.01);
    }

    private Product createProduct(String name, String store, LocalDate date, double price) {
        Product p = new Product();
        p.setProductId("P001");
        p.setProductName(name);
        p.setStore(store);
        p.setDate(date);
        p.setPrice(price);
        p.setPackageQuantity(1.0);
        p.setPackageUnit("l");
        p.setBrand("BrandX");
        p.setProductCategory("lactate");
        return p;
    }
}
