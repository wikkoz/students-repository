package com.services.file;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {
    public Workbook getWorkbook(String fileName, InputStream inputStream) {
        Workbook offices;
        try {
            if (fileName.endsWith(".xlsx")) {
                offices = new XSSFWorkbook(inputStream);
            } else if (fileName.endsWith(".xls")) {
                offices = new HSSFWorkbook(inputStream);
            } else {
                throw new RuntimeException("Invalid type of file expected xlsx or xls");
            }
            return offices;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
