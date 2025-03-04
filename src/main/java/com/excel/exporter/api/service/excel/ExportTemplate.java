package com.excel.exporter.api.service.excel;

import com.excel.exporter.api.entity.Product;
import com.excel.exporter.api.repository.ProductRepository;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class ExportTemplate {
    @Autowired
    private ProductRepository productRepository;


    private static final String TEMPLATE_PATH = "C:\\Users\\DELL\\Downloads\\template3.xlsx";

    public byte[] exportExcelTemplate(List<Product> products) throws IOException , InvalidFormatException {
        FileInputStream fileInputStream = new FileInputStream(TEMPLATE_PATH);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        int rowNum = 1;
        for(Product product : products) {
            Row row = sheet.getRow(rowNum); // Check xem dòng đã tồn tại chưa
            if (row == null) {
                row = sheet.createRow(rowNum); // Nếu chưa tạo dòng mới
            }
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getCategory());
            row.createCell(3).setCellValue(product.getPrice().doubleValue());
            row.createCell(4).setCellValue(product.getStockQuantity());
            row.createCell(5).setCellValue(product.getDateAdded());
            rowNum++;
        }

        fileInputStream.close();
        FileOutputStream fileOutputStream = new FileOutputStream(TEMPLATE_PATH);
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}
