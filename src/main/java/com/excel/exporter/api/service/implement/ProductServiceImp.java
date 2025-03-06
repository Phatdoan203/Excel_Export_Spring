package com.excel.exporter.api.service.implement;

import com.excel.exporter.api.dto.ProductDTO;
import com.excel.exporter.api.entity.Product;
import com.excel.exporter.api.repository.ProductRepository;
import com.excel.exporter.api.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;


    @Override
    public Page<ProductDTO> getAllProduct(Pageable pageable) {
        log.info("Getting Products");
        try{
            Page<Product> products = Optional.ofNullable(productRepository.findAll(pageable)).orElse(Page.empty());
            return products.map(product -> new ProductDTO(product));
        } catch (RuntimeException e) {
            log.error("Error when getting products" + e.getMessage());
            return null;
        }
    }

    @Override
    public Page<ProductDTO> getProductsSorted(Pageable pageable) {
        return null;
    }
}
