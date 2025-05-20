package com.accesa.service.loader;

import com.accesa.model.Product;
import com.accesa.model.Discount;
import com.accesa.util.FileNameUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses product and discount CSV files using OpenCSV.
 * Extracts store name and date from the filename and attaches them to each entry.
 * Skips malformed lines and logs warnings instead of failing.
 * Assumes semicolon-separated format based on dataset specification.
 */
public class CsvParserUtil {

    private static final Logger logger = LoggerFactory.getLogger(CsvParserUtil.class);

    public static List<Product> parseProductCsv(InputStream is, String fileName) {
        List<Product> list = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] line;
            reader.readNext();
            String store = FileNameUtils.extractStore(fileName);
            LocalDate date = FileNameUtils.extractDate(fileName);

            while ((line = reader.readNext()) != null) {
                if (line.length < 8) {
                    logger.warn("Skipping malformed product line in '{}': {}", fileName, String.join(";", line));
                    continue;
                }
                try {
                    Product product = new Product();
                    product.setProductId(line[0]);
                    product.setProductName(line[1]);
                    product.setProductCategory(line[2]);
                    product.setBrand(line[3]);
                    product.setPackageQuantity(Double.parseDouble(line[4]));
                    product.setPackageUnit(line[5]);
                    product.setPrice(Double.parseDouble(line[6]));
                    product.setCurrency(line[7]);
                    product.setStore(store);
                    product.setDate(date);
                    list.add(product);
                } catch (Exception e) {
                    logger.warn("Parsing error in product file '{}': {}", fileName, String.join(";", line), e);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to read product CSV '{}'", fileName, e);
        }
        return list;
    }

    public static List<Discount> parseDiscountCsv(InputStream is, String fileName) {
        List<Discount> list = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] line;
            reader.readNext(); // skip header
            String store = FileNameUtils.extractStore(fileName);

            while ((line = reader.readNext()) != null) {
                if (line.length < 9) {
                    logger.warn("Skipping malformed discount line in '{}': {}", fileName, String.join(";", line));
                    continue;
                }
                try {
                    Discount discount = new Discount();
                    discount.setProductId(line[0]);
                    discount.setProductName(line[1]);
                    discount.setBrand(line[2]);
                    discount.setPackageQuantity(Double.parseDouble(line[3]));
                    discount.setPackageUnit(line[4]);
                    discount.setProductCategory(line[5]);
                    discount.setFromDate(LocalDate.parse(line[6]));
                    discount.setToDate(LocalDate.parse(line[7]));
                    discount.setPercentageOfDiscount(Integer.parseInt(line[8]));
                    discount.setStore(store);
                    list.add(discount);
                } catch (Exception e) {
                    logger.warn("Parsing error in discount file '{}': {}", fileName, String.join(";", line), e);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to read discount CSV '{}'", fileName, e);
        }
        return list;
    }
}

