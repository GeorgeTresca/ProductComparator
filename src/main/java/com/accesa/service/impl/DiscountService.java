package com.accesa.service.impl;

import com.accesa.dto.BestDiscountDTO;
import com.accesa.model.Discount;
import com.accesa.service.IDiscountService;
import com.accesa.config.AppDateProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService implements IDiscountService {

    private final DataLoaderService dataLoaderService;
    private final AppDateProvider appDateProvider;

    public DiscountService(DataLoaderService dataLoaderService, AppDateProvider appDateProvider) {
        this.dataLoaderService = dataLoaderService;
        this.appDateProvider = appDateProvider;
    }

    @Override
    public List<BestDiscountDTO> getBestDiscounts(int topN) {
        LocalDate today = appDateProvider.getToday();

        return dataLoaderService.getAllDiscounts().stream()
                .filter(d -> !today.isBefore(d.getFromDate()) && !today.isAfter(d.getToDate()))
                .sorted(Comparator.comparingInt(Discount::getPercentageOfDiscount).reversed())
                .limit(topN)
                .map(BestDiscountDTO::from)
                .collect(Collectors.toList());
    }
}
