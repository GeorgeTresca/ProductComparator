package com.accesa.config;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AppDateProvider {

    private LocalDate today = LocalDate.of(2025, 5, 6);

    public LocalDate getToday() {
        return today;
    }

    public void setToday(LocalDate newDate) {
        this.today = newDate;
    }

}

