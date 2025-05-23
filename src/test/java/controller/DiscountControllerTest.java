package controller;

import com.accesa.config.AppDateProvider;
import com.accesa.controller.DiscountController;
import com.accesa.dto.BestDiscountDTO;
import com.accesa.dto.NewDiscountDTO;
import com.accesa.service.impl.DiscountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DiscountController.class)
@ContextConfiguration(classes = com.accesa.PriceComparatorApplication.class)
public class DiscountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiscountService discountService;

    @MockBean
    private AppDateProvider dateProvider;

    @Test
    void shouldReturnBestDiscounts() throws Exception {
        when(discountService.getBestDiscounts(2)).thenReturn(List.of(
                BestDiscountDTO.builder().productId("P1").productName("Lapte").brand("Zuzu").store("Lidl").discountPercent(20).build(),
                BestDiscountDTO.builder().productId("P2").productName("Zahar").brand("Sweet").store("Profi").discountPercent(10).build()
        ));

        mockMvc.perform(get("/api/discounts/best?top=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].productId").value("P1"))
                .andExpect(jsonPath("$[1].productId").value("P2"));
    }

    @Test
    void testGetNewDiscounts() throws Exception {
        NewDiscountDTO dto = NewDiscountDTO.builder()
                .productId("P123")
                .productName("spaghetti nr.5")
                .brand("Barilla")
                .discountPercent(20)
                .fromDate(LocalDate.of(2025, 5, 8))
                .toDate(LocalDate.of(2025, 5, 15))
                .store("kaufland")
                .build();

        when(discountService.getNewDiscounts()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/discounts/new"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value("P123"))
                .andExpect(jsonPath("$[0].discountPercent").value(20));
    }

}
