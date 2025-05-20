package com.accesa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class FileNameUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileNameUtils.class);

    public static String extractStore(String filename) {
        if (filename == null || !filename.contains("_")) {
            logger.warn("Invalid filename format: '{}'", filename);
            return "unknown";
        }
        return filename.split("_")[0].toLowerCase();
    }

    public static LocalDate extractDate(String filename) {
        try {
            String[] parts = filename.replace(".csv", "").split("_");
            if (parts.length < 2) {
                logger.warn("Filename missing date segment: '{}'", filename);
                return null;
            }
            return LocalDate.parse(parts[1]);
        } catch (DateTimeParseException e) {
            logger.error("Failed to parse date from filename '{}': {}", filename, e.getMessage());
            return null;
        }
    }
}

