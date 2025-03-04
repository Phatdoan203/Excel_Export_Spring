package com.excel.exporter.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.excel.exporter.api.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
