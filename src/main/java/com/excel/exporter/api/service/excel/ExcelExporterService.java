package com.excel.exporter.api.service.excel;

import com.excel.exporter.api.entity.Product;
import com.excel.exporter.api.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ExcelExporterService {
    @Autowired
    private ProductRepository productRepository;

    public byte[] generateExcelFile() throws IOException {
        List<Product> products = productRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        // Tao sheet Products
        Sheet sheet = workbook.createSheet("Products");


        Row headerRow = sheet.createRow(0);
        String[] columns = {"ID", "Name", "Category", "Price", "Stock Quantity", "Date Added"};

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));
        }

        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getCategory());
            row.createCell(3).setCellValue(product.getPrice().doubleValue());
            row.createCell(4).setCellValue(product.getStockQuantity());
            row.createCell(5).setCellValue(product.getDateAdded());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerCellStyle.setFont(font);
        return headerCellStyle;
    }
}
