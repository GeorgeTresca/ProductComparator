package controller;

import com.accesa.controller.RecommendationController;
import com.accesa.dto.BestValueProductDTO;
import com.accesa.service.impl.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecommendationController.class)
@ContextConfiguration(classes = com.accesa.PriceComparatorApplication.class)
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService recommendationService;

    @Test
    void shouldReturnBestValueProducts() throws Exception {
        BestValueProductDTO dto = BestValueProductDTO.builder()
                .productId("P001")
                .productName("lapte zuzu")
                .brand("Zuzu")
                .store("Lidl")
                .price(9.9)
                .quantity(1.0)
                .unit("l")
                .pricePerUnit(9.9)
                .build();

        when(recommendationService.getBestValueForProduct("lapte zuzu"))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/products/best-value")
                        .param("productName", "lapte zuzu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].store").value("Lidl"))
                .andExpect(jsonPath("$[0].pricePerUnit").value(9.9));
    }
}

