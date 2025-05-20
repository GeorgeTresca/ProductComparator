package com.accesa.controller;

import com.accesa.config.AppDateProvider;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/date")
public class DateController {

    private final AppDateProvider appDateProvider;

    public DateController(AppDateProvider appDateProvider) {
        this.appDateProvider = appDateProvider;
    }

    @GetMapping
    public LocalDate getToday() {
        return appDateProvider.getToday();
    }

    @PostMapping
    public String setToday(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        appDateProvider.setToday(date);
        return "Current 'today' set to: " + date;
    }
}
