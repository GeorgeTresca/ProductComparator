package controller;

import com.accesa.controller.AlertController;
import com.accesa.dto.PriceAlertMatchDTO;
import com.accesa.dto.PriceAlertRequestDTO;
import com.accesa.service.impl.AlertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlertController.class)
@ContextConfiguration(classes = com.accesa.PriceComparatorApplication.class)
public class AlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertService alertService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnMatchingAlertResults() throws Exception {
        PriceAlertRequestDTO request = new PriceAlertRequestDTO();
        request.setProductName("lapte zuzu");
        request.setTargetPrice(10.00);

        PriceAlertMatchDTO match = PriceAlertMatchDTO.builder()
                .productName("lapte zuzu")
                .store("Kaufland")
                .price(9.8)
                .build();

        when(alertService.checkAlerts(request)).thenReturn(List.of(match));

        mockMvc.perform(post("/api/alerts/check")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].store").value("Kaufland"))
                .andExpect(jsonPath("$[0].price").value(9.8));
    }
}
