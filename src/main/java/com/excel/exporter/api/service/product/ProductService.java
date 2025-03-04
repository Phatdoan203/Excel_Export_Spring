package com.excel.exporter.api.service.product;

import com.excel.exporter.api.entity.Product;
import com.excel.exporter.api.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        log.info("Getting all products");
        try {
            List<Product> products = productRepository.findAll();
            return products;
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
