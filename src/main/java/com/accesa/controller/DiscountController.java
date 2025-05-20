package com.accesa.controller;

import com.accesa.dto.BestDiscountDTO;
import com.accesa.dto.NewDiscountDTO;
import com.accesa.service.impl.DiscountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/best")
    public List<BestDiscountDTO> getBestDiscounts(@RequestParam(defaultValue = "10") int top) {
        return discountService.getBestDiscounts(top);
    }

    @GetMapping("/new")
    public List<NewDiscountDTO> getNewDiscounts() {
        return discountService.getNewDiscounts();
    }

}
