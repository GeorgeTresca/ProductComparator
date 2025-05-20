package service;

import com.accesa.config.AppDateProvider;
import com.accesa.dto.BasketRequestDTO;
import com.accesa.dto.BasketResponseDTO;
import com.accesa.model.Product;
import com.accesa.service.impl.BasketService;
import com.accesa.service.impl.DataLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BasketServiceTest {

    private BasketService basketService;

    @BeforeEach
    void setUp() {
        DataLoaderService mockLoader = new DataLoaderService() {
            @Override
            public List<Product> getAllProducts() {
                return List.of(
                        // Multiple stores for same product, choose latest ≤ today, then lowest price
                        createProduct("P001", "lapte zuzu", "Lidl", LocalDate.of(2025, 5, 1), 9.90),
                        createProduct("P001", "lapte zuzu", "Kaufland", LocalDate.of(2025, 5, 2), 9.70),
                        createProduct("P001", "lapte zuzu", "Profi", LocalDate.of(2025, 5, 3), 10.50),

                        createProduct("P020", "spaghetti nr.5", "Lidl", LocalDate.of(2025, 5, 3), 5.80)
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


        basketService = new BasketService(mockLoader, mockDate);
    }

    @Test
    void shouldChooseLatestValidOffersAndMinPricePerProduct() {
        BasketRequestDTO request = new BasketRequestDTO();
        request.setProductNames(List.of("lapte zuzu", "spaghetti nr.5"));

        BasketResponseDTO response = basketService.optimizeBasket(request);

        assertEquals(2, response.getItems().size());
        assertEquals("Kaufland", response.getItems().get(0).getStore()); // lowest price: 9.70
        assertEquals("Lidl", response.getItems().get(1).getStore());

        assertEquals(15.50, response.getTotalPrice(), 0.01);
    }

    private Product createProduct(String id, String name, String store, LocalDate date, double price) {
        Product p = new Product();
        p.setProductId(id);
        p.setProductName(name);
        p.setStore(store);
        p.setDate(date);
        p.setPrice(price);
        p.setPackageQuantity(1.0);
        p.setPackageUnit("l");
        p.setBrand("TestBrand");
        p.setProductCategory("TestCat");
        return p;
    }
}
