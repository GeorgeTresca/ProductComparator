package com.accesa.service;

import com.accesa.dto.BestDiscountDTO;

import java.util.List;

public interface IDiscountService {
    List<BestDiscountDTO> getBestDiscounts(int topN);
}
