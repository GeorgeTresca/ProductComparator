package service;

import com.accesa.config.AppDateProvider;
import com.accesa.dto.BestDiscountDTO;
import com.accesa.model.Discount;
import com.accesa.model.Product;
import com.accesa.service.impl.DataLoaderService;
import com.accesa.service.impl.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountServiceTest {

    private DiscountService discountService;

    @BeforeEach
    void setup() {
        DataLoaderService mockLoader = new DataLoaderService() {
            @Override
            public List<Discount> getAllDiscounts() {
                return List.of(
                        createDiscount("P1", "Lapte", "Lidl", 20,
                                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 10)),
                        createDiscount("P2", "Cafea", "Kaufland", 10,
                                LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 5)),
                        createDiscount("P3", "Zahar", "Profi", 30,
                                LocalDate.of(2025, 5, 6), LocalDate.of(2025, 5, 9))
                );
            }

            @Override
            public List<Product> getAllProducts() {
                return List.of(); // not needed here
            }

            @Override
            public void loadAll() {
                // not needed in test
            }
        };

        AppDateProvider mockDateProvider = new AppDateProvider() {
            @Override
            public LocalDate getToday() {
                return LocalDate.of(2025, 5, 5);
            }
        };

        discountService = new DiscountService(mockLoader, mockDateProvider);
    }

    @Test
    void shouldReturnDiscountsSortedByPercent() {
        List<BestDiscountDTO> result = discountService.getBestDiscounts(3);

        assertEquals(2, result.size());
        assertEquals("P1", result.get(0).getProductId());
        assertEquals("P2", result.get(1).getProductId());
    }

    @Test
    void shouldLimitDiscountsToTopN() {
        List<BestDiscountDTO> result = discountService.getBestDiscounts(1);
        assertEquals(1, result.size());
        assertEquals("P1", result.get(0).getProductId());
    }

    private Discount createDiscount(String id, String name, String store, int percent, LocalDate from, LocalDate to) {
        Discount d = new Discount();
        d.setProductId(id);
        d.setProductName(name);
        d.setStore(store);
        d.setBrand("Generic");
        d.setPercentageOfDiscount(percent);
        d.setFromDate(from);
        d.setToDate(to);
        return d;
    }
}

