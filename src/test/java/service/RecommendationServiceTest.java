package service;

import com.accesa.config.AppDateProvider;
import com.accesa.dto.BestValueProductDTO;
import com.accesa.model.Product;
import com.accesa.service.impl.DataLoaderService;
import com.accesa.service.impl.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecommendationServiceTest {

    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        DataLoaderService mockLoader = new DataLoaderService() {
            @Override
            public List<Product> getAllProducts() {
                return List.of(
                        // Lidl – multiple offers, should choose 2025-05-01
                        createProduct("P001", "lapte zuzu", "Lidl", LocalDate.of(2025, 4, 30), 10.0, 1.0, "l"),
                        createProduct("P001", "lapte zuzu", "Lidl", LocalDate.of(2025, 5, 01), 9.5, 1.0, "l"),
                        // Kaufland – offer after today should be ignored
                        createProduct("P001", "lapte zuzu", "Kaufland", LocalDate.of(2025, 5, 04), 9.0, 1.0, "l"),
                        // Profi – offer exactly on 2025-05-03 (today)
                        createProduct("P001", "lapte zuzu", "Profi", LocalDate.of(2025, 5, 03), 9.9, 1.0, "l")
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

        recommendationService = new RecommendationService(mockLoader, mockDate);
    }

    @Test
    void BestValuePerUnitTest() {
        List<BestValueProductDTO> result = recommendationService.getBestValueForProduct("lapte zuzu");

        assertEquals(2, result.size());

        BestValueProductDTO first = result.get(0);
        BestValueProductDTO second = result.get(1);

        assertEquals("Lidl", first.getStore());
        assertEquals(9.5, first.getPrice());
        assertEquals(9.5, first.getPricePerUnit());

        assertEquals("Profi", second.getStore());
        assertEquals(9.9, second.getPrice());
        assertEquals(9.9, second.getPricePerUnit());
    }

    private Product createProduct(String id, String name, String store, LocalDate date, double price, double quantity, String unit) {
        Product p = new Product();
        p.setProductId(id);
        p.setProductName(name);
        p.setStore(store);
        p.setDate(date);
        p.setPrice(price);
        p.setPackageQuantity(quantity);
        p.setPackageUnit(unit);
        p.setBrand("TestBrand");
        p.setProductCategory("TestCategory");
        return p;
    }
}
