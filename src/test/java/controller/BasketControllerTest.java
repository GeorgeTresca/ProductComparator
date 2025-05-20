package controller;


import com.accesa.controller.BasketController;
import com.accesa.dto.BasketItemDTO;
import com.accesa.dto.BasketRequestDTO;
import com.accesa.dto.BasketResponseDTO;
import com.accesa.service.impl.BasketService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BasketController.class)
@ContextConfiguration(classes = com.accesa.PriceComparatorApplication.class)
public class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketService basketService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnOptimizedBasket() throws Exception {
        BasketRequestDTO request = new BasketRequestDTO();
        request.setProductNames(List.of("lapte zuzu", "spaghetti nr.5"));

        List<BasketItemDTO> items = List.of(
                BasketItemDTO.builder().productName("lapte zuzu").store("Lidl").price(9.90).build(),
                BasketItemDTO.builder().productName("spaghetti nr.5").store("Profi").price(5.70).build()
        );

        BasketResponseDTO response = BasketResponseDTO.builder()
                .items(items)
                .totalPrice(15.60)
                .build();

        when(basketService.optimizeBasket(request)).thenReturn(response);

        mockMvc.perform(post("/api/basket/optimize")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.totalPrice").value(15.60));
    }
}
