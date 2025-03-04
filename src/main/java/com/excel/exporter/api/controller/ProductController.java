package com.excel.exporter.api.controller;

import com.excel.exporter.api.entity.Product;
import com.excel.exporter.api.service.excel.ExcelExporterService;
import com.excel.exporter.api.service.excel.ExportTemplate;
import com.excel.exporter.api.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ExcelExporterService excelExporterService;
    private final ExportTemplate exportTemplate;

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadProducts() throws IOException {
        log.info("Downloading products...");
        try {
            byte[] excelData = excelExporterService.generateExcelFile();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products2.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(excelData);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download-template")
    public ResponseEntity<byte[]> downloadProductTemplate() throws IOException {
        log.info("Download product template");
        try {
            List<Product> products = productService.getAllProducts();
            // 1tr data thif sao
            byte[] excelData = exportTemplate.exportExcelTemplate(products);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("Product_Template_Filled.xlsx").build());

            //service.taofile


            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelData);

        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
