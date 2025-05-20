package com.accesa.service;

import com.accesa.dto.BestDiscountDTO;
import com.accesa.dto.NewDiscountDTO;

import java.util.List;

public interface IDiscountService {
    List<BestDiscountDTO> getBestDiscounts(int topN);
    List<NewDiscountDTO> getNewDiscounts();
}
