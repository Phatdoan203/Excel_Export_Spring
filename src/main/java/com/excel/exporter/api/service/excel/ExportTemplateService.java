package com.excel.exporter.api.service.excel;


import com.excel.exporter.api.entity.Product;
import com.excel.exporter.api.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.*;

@Slf4j
@Service
public class ExportTemplateService {
    @Autowired
    private ProductRepository productRepository;

    public void exportProductsToExcel(String templatePath) throws IOException {
        // Đọc file template
        try (FileInputStream templateInputStream = new FileInputStream(templatePath);
             Workbook workbook = new XSSFWorkbook(templateInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Bắt đầu ghi từ dòng 16 (index 15), cột B
            int startRow = 15;
            int currentRow = startRow;

            // Batch export dữ liệu
            int pageSize = 1000;

            for (int pageNum = 0; ; pageNum++) {
                Page<Product> productPage = productRepository.findAll(PageRequest.of(pageNum, pageSize));

                // Xuất dữ liệu từng trang
                for (Product product : productPage.getContent()) {
                    Row row = sheet.getRow(currentRow);
                    if (row == null) {
                        row = sheet.createRow(currentRow);
                    }

                    // Ghi dữ liệu bắt đầu từ cột B (index 1)
                    row.createCell(1).setCellValue(product.getId());
                    row.createCell(2).setCellValue(product.getName());
                    row.createCell(3).setCellValue(product.getCategory());
                    row.createCell(4).setCellValue(product.getPrice().doubleValue());
                    row.createCell(5).setCellValue(product.getStockQuantity());
                    row.createCell(6).setCellValue(product.getDateAdded().toString());

                    currentRow++;
                }

                // Dừng khi hết dữ liệu
                if (productPage.isLast()) {
                    break;
                }
            }

            // Ghi lại vào chính file template
            try (FileOutputStream outputStream = new FileOutputStream(templatePath)) {
                workbook.write(outputStream);
            }
        }
    }
}



