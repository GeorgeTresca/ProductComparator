package com.accesa.controller;

import com.accesa.dto.BasketRequestDTO;
import com.accesa.dto.BasketResponseDTO;
import com.accesa.service.impl.BasketService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/optimize")
    public BasketResponseDTO optimizeBasket(@Valid @RequestBody BasketRequestDTO request) {
        return basketService.optimizeBasket(request);
    }
}
