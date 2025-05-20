package controller;


import com.accesa.controller.ProductController;
import com.accesa.dto.PriceHistoryPointDTO;
import com.accesa.service.impl.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = com.accesa.PriceComparatorApplication.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void shouldReturnPriceHistoryForProduct() throws Exception {
        PriceHistoryPointDTO dto1 = PriceHistoryPointDTO.builder()
                .store("lidl")
                .date(LocalDate.of(2025, 5, 1))
                .price(9.9)
                .brand("Zuzu")
                .productCategory("lactate")
                .build();

        when(productService.getPriceHistory("P001", null, null, null))
                .thenReturn(List.of(dto1));

        mockMvc.perform(get("/api/products/P001/price-history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].store").value("lidl"))
                .andExpect(jsonPath("$[0].brand").value("Zuzu"));
    }
}

