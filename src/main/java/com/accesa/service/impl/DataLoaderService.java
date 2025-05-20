package com.accesa.service.impl;

import com.accesa.model.Product;
import com.accesa.model.Discount;
import com.accesa.service.IDataLoaderService;
import com.accesa.service.loader.CsvParserUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class DataLoaderService implements IDataLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);

    private final List<Product> products = new ArrayList<>();
    private final List<Discount> discounts = new ArrayList<>();

    @PostConstruct
    public void loadAll() {
        logger.info("Loading CSV data....");
        try {
            var resolver = new PathMatchingResourcePatternResolver();
            Resource[] files = resolver.getResources("classpath:data/*.csv");

            for (Resource file : files) {
                String filename = file.getFilename();
                if (filename == null) continue;

                try (InputStream is = file.getInputStream()) {
                    if (filename.contains("discount")) {
                        discounts.addAll(CsvParserUtil.parseDiscountCsv(is, filename));
                        logger.info("Loaded discount file: {}", filename);
                    } else {
                        products.addAll(CsvParserUtil.parseProductCsv(is, filename));
                        logger.info("Loaded product file: {}", filename);
                    }
                } catch (Exception e) {
                    logger.error("Failed to process file '{}'", filename, e);
                }
            }
        } catch (Exception e) {
            logger.error("Error during CSV loading", e);
        }
        logger.info("Finished loading. Products: {}, Discounts: {}", products.size(), discounts.size());
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public List<Discount> getAllDiscounts() {
        return discounts;
    }
}


