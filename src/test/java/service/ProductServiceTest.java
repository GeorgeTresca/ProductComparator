package service;

import com.accesa.dto.PriceHistoryPointDTO;
import com.accesa.model.Product;
import com.accesa.service.impl.DataLoaderService;
import com.accesa.service.impl.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        DataLoaderService mockLoader = new DataLoaderService() {
            @Override
            public List<Product> getAllProducts() {
                return List.of(
                        createProduct("P001", "lidl", LocalDate.of(2025, 5, 1), 10.0, "Zuzu", "lactate"),
                        createProduct("P001", "lidl", LocalDate.of(2025, 5, 8), 9.8, "Zuzu", "lactate"),
                        createProduct("P001", "profi", LocalDate.of(2025, 5, 1), 11.5, "Zuzu", "lactate"),
                        createProduct("P001", "kaufland", LocalDate.of(2025, 5, 8), 11.2, "Milka", "lactate"),
                        createProduct("P002", "kaufland", LocalDate.of(2025, 5, 8), 11.2, "Milka", "gustari")
                );

            }

            @Override
            public void loadAll() {}
        };

        productService = new ProductService(mockLoader);
    }

    @Test
    void priceHistoryPointsTest() {
        List<PriceHistoryPointDTO> history = productService.getPriceHistory("P001", null, "Zuzu", null);

        assertEquals(3, history.size());
        assertEquals(LocalDate.of(2025, 5, 1), history.get(0).getDate());
        assertEquals("Zuzu", history.get(0).getBrand());
        assertEquals("lactate", history.get(0).getProductCategory());
    }

    @Test
    void emptyListTest() {
        List<PriceHistoryPointDTO> history = productService.getPriceHistory("UNKNOWN",null,null,null);
        assertTrue(history.isEmpty());
    }

    private Product createProduct(String id, String store, LocalDate date, double price, String brand, String category) {
        Product p = new Product();
        p.setProductId(id);
        p.setProductName("Test Product");
        p.setStore(store);
        p.setDate(date);
        p.setPrice(price);
        p.setBrand(brand);
        p.setProductCategory(category);
        return p;
    }

}
