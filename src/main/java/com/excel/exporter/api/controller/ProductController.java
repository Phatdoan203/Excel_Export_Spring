package com.excel.exporter.api.controller;

import com.excel.exporter.api.dto.ProductDTO;
import com.excel.exporter.api.repository.ProductRepository;
import com.excel.exporter.api.service.excel.ExcelExporterService;
import com.excel.exporter.api.service.excel.ExportTemplateService;
import com.excel.exporter.api.service.implement.ProductServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImp productService;
    private final ExcelExporterService excelExporterService;
    private final ExportTemplateService exportTemplateService;
    private final ProductRepository productRepository;

    @Value(value = "${path.excel}")
    private String TemplatePath;


    @GetMapping("")
    public ResponseEntity<?> getProducts(@Valid @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProductDTO> product = productService.getAllProduct(pageable);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportProductsToExcel() {
        try {
            String templatePath = TemplatePath;
            exportTemplateService.exportProductsToExcel(templatePath);

            return ResponseEntity.ok("Exported successfully to template");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Export failed: " + e.getMessage());
        }
    }
}
