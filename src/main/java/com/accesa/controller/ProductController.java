package com.accesa.controller;

import com.accesa.dto.PriceHistoryPointDTO;
import com.accesa.service.IProductService;
import com.accesa.service.impl.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/price-history")
    public List<PriceHistoryPointDTO> getPriceHistory(
            @PathVariable String productId,
            @RequestParam(required = false) String store,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category
    ) {
        return productService.getPriceHistory(productId, store, brand, category);
    }

}
