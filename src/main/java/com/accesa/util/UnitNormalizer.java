package com.accesa.util;

import java.util.Map;

public class UnitNormalizer {

    private static final Map<String, Double> conversionMap = Map.of(
            "kg", 1.0,
            "g", 0.001,
            "l", 1.0,
            "ml", 0.001

    );

    public static boolean canNormalize(String unit) {
        return conversionMap.containsKey(unit.toLowerCase());
    }

    public static double normalize(double quantity, String unit) {
        return quantity * conversionMap.getOrDefault(unit.toLowerCase(), 1.0);
    }
}
