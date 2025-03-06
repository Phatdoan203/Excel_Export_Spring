package com.excel.exporter.api.dto;

import com.excel.exporter.api.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProductDTO {
    public long id;
    public String name;
    public String category;
    public BigDecimal price;
    public Integer stockQuantity;
    private LocalDate dateAdded;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.dateAdded = product.getDateAdded();
    }

}
