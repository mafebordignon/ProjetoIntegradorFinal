package com.grupo6.keepInventory.Reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/import")
public class ImportController {



    private static final Logger LOGGER = Logger.getLogger(ImportController.class.getName());

    @Autowired
    private ExcelImportService excelImportService;


    @PostMapping("/excel")
    public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            LOGGER.log(Level.WARNING, "Uploaded file is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a valid Excel file.");
        }

        try {
            LOGGER.log(Level.INFO, "File received: {0}", file.getOriginalFilename());
            excelImportService.importExcelLista(file);
            return ResponseEntity.ok("File uploaded and data imported successfully.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while importing the file.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while importing the file: " + e.getMessage());
        }
    }
}
