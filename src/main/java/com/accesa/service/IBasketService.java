package com.accesa.service;

import com.accesa.dto.BasketRequestDTO;
import com.accesa.dto.BasketResponseDTO;

public interface IBasketService {
    BasketResponseDTO optimizeBasket(BasketRequestDTO request);
}
