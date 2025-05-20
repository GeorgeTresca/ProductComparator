package com.accesa.controller;

import com.accesa.dto.PriceAlertMatchDTO;
import com.accesa.dto.PriceAlertRequestDTO;
import com.accesa.service.impl.AlertService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping("/check")
    public List<PriceAlertMatchDTO> checkAlerts(@Valid @RequestBody PriceAlertRequestDTO request) {
        return alertService.checkAlerts(request);
    }
}
