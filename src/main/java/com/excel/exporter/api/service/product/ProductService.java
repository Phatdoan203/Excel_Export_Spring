package com.excel.exporter.api.service.product;

import com.excel.exporter.api.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductDTO> getAllProduct(Pageable pageable);
    Page<ProductDTO> getProductsSorted(Pageable pageable);
}
