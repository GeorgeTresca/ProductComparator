package controller;


import com.accesa.config.AppDateProvider;
import com.accesa.controller.DateController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DateController.class)
@ContextConfiguration(classes = com.accesa.PriceComparatorApplication.class)
public class DateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppDateProvider appDateProvider;


    @Test
    void shouldSetNewDate() throws Exception {
        mockMvc.perform(post("/api/date?date=2025-05-08"))
                .andExpect(status().isOk())
                .andExpect(content().string("Current 'today' set to: 2025-05-08"));
    }
}

