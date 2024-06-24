package com.grupo6.keepInventory.Reports;

import com.grupo6.keepInventory.Model.Item;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/gerar-relatorio")
public class ExcelReportController {

    @Autowired
    private ExcelReportService excelReportService;

    @PostMapping("/lista")
    public ResponseEntity<byte[]> gerarLista(@RequestBody @Nullable List<Item> itens) {
        System.out.println(itens);
        try {
            ByteArrayOutputStream byteArrayOutputStream = excelReportService.generateExcelLista(itens);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "relatorio_lista.xlsx");

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/especifico/{id}")
    public ResponseEntity<byte[]> gerarEspecifico(@PathVariable Long id) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = excelReportService.generateExcelEspecifico(id);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "relatorio_especifico.xlsx");

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}