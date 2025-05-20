package com.accesa.service;

import com.accesa.dto.PriceAlertRequestDTO;
import com.accesa.dto.PriceAlertMatchDTO;

import java.util.List;

public interface IAlertService {
    List<PriceAlertMatchDTO> checkAlerts(PriceAlertRequestDTO request);
}
