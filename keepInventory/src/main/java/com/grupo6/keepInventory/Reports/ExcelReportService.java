package com.grupo6.keepInventory.Reports;

import com.grupo6.keepInventory.Model.*;
import com.grupo6.keepInventory.Repository.AcaoRepository;
import com.grupo6.keepInventory.Repository.ItemRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Service
public class ExcelReportService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AcaoRepository acaoRepository;

    public ByteArrayOutputStream generateExcelLista(List<Item> items) throws IOException {
        if (items == null || items.isEmpty()) {
            items = itemRepository.findAll();
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Items");

        // Create a header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Descrição", "Número Nota Fiscal", "Marca", "Modelo", "Número de Série", "Potência", "Data de Entrada", "Data Nota Fiscal", "Categoria", "Estado", "Disponibilidade", "Localização"};
        for (int i = 0; i < headers.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);
        }

        // Populate the sheet with data
        int rowNum = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Item item : items) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getDescricao());
            row.createCell(1).setCellValue(item.getNumeroNotaFiscal());
            row.createCell(2).setCellValue(item.getModelo().getMarca().getNome());
            row.createCell(3).setCellValue(item.getModelo().getNome());
            row.createCell(4).setCellValue(item.getNumeroSerie());
            row.createCell(5).setCellValue(item.getPotencia());
            row.createCell(6).setCellValue(item.getDataEntrada().format(formatter));
            row.createCell(7).setCellValue(item.getDataNotaFiscal().format(formatter));
            row.createCell(8).setCellValue(item.getCategoria() != null ? item.getCategoria().getNome() : "");
            row.createCell(9).setCellValue(item.getEstado() != null ? item.getEstado().getNome() : "");
            row.createCell(10).setCellValue(item.getDisponibilidade() != null ? item.getDisponibilidade().getNome() : "");
            row.createCell(11).setCellValue(item.getLocalizacao() != null ? item.getLocalizacao().getNome() : "");
        }

        // Resize the columns to fit the content
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);

        // Closing the workbook
        workbook.close();

        return byteArrayOutputStream;
    }

    public ByteArrayOutputStream generateExcelEspecifico(Long id) throws IOException {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        // Retrieve the list of actions related to the item
        List<Acao> acoes = acaoRepository.findByItemId(id);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Item Especifico");

        // Create a header row for item details
        Row headerRow = sheet.createRow(0);
        String[] itemHeaders = {"Descrição", "Número Nota Fiscal", "Marca", "Modelo", "Número de Série", "Potência", "Data de Entrada", "Categoria", "Estado", "Disponibilidade", "Localização"};
        for (int i = 0; i < itemHeaders.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(itemHeaders[i]);
        }

        // Populate the sheet with item details
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Row itemRow = sheet.createRow(1);
        itemRow.createCell(0).setCellValue(item.getDescricao());
        itemRow.createCell(1).setCellValue(item.getNumeroNotaFiscal());
        itemRow.createCell(2).setCellValue(item.getModelo().getMarca().getNome());
        itemRow.createCell(3).setCellValue(item.getModelo().getNome());
        itemRow.createCell(4).setCellValue(item.getNumeroSerie());
        itemRow.createCell(5).setCellValue(item.getPotencia());
        itemRow.createCell(6).setCellValue(item.getDataEntrada().format(formatter));
        itemRow.createCell(7).setCellValue(item.getCategoria() != null ? item.getCategoria().getNome() : "");
        itemRow.createCell(8).setCellValue(item.getEstado() != null ? item.getEstado().getNome() : "");
        itemRow.createCell(9).setCellValue(item.getDisponibilidade() != null ? item.getDisponibilidade().getNome() : "");
        itemRow.createCell(10).setCellValue(item.getLocalizacao() != null ? item.getLocalizacao().getNome() : "");


        Row actionHeaderRow = sheet.createRow(3);
        String[] actionHeaders = {"Descrição", "Data", "Entidade", "Tipo Ação", "Localização"};
        for (int i = 0; i < actionHeaders.length; i++) {
            Cell headerCell = actionHeaderRow.createCell(i);
            headerCell.setCellValue(actionHeaders[i]);
        }
        // Populate the sheet with actions details
        int rowNum = 4;
        for (Acao acao : acoes) {
            Row actionRow = sheet.createRow(rowNum++);
            actionRow.createCell(0).setCellValue(acao.getDescricao());
            // Conditional display of date based on action type
            if (acao.getTipoacao() != null && "Empréstimo".equalsIgnoreCase(acao.getTipoacao().getNome())) {
                actionRow.createCell(1).setCellValue(acao.getDataEmprestimo().format(formatter));
            } else if (acao.getTipoacao() != null && "Devolução".equalsIgnoreCase(acao.getTipoacao().getNome())) {
                actionRow.createCell(1).setCellValue(acao.getDataDevolucao() != null ? acao.getDataDevolucao().format(formatter) : "");
            } else {
                actionRow.createCell(1).setCellValue(""); // Or some default value if no type matches
            }
            actionRow.createCell(2).setCellValue(acao.getEntidade());
            actionRow.createCell(3).setCellValue(acao.getTipoacao() != null ? acao.getTipoacao().getNome() : "");
            actionRow.createCell(4).setCellValue(acao.getLocalizacao() != null ? acao.getLocalizacao().getNome() : "");
        }

        // Resize the columns to fit the content
        for (int i = 0; i < itemHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }
        for (int i = 0; i < actionHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);

        // Closing the workbook
        workbook.close();

        return byteArrayOutputStream;
    }
}