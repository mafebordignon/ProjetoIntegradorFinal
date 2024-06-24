package com.grupo6.keepInventory.Reports;
import com.grupo6.keepInventory.Model.*;
import com.grupo6.keepInventory.Repository.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelImportService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;
    @Autowired
    private LocalizacaoRepository localizacaoRepository;
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MarcaRepository marcaRepository;

    public List<Item> importExcelLista(MultipartFile file) throws IOException {
        List<Item> items = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Skip the header row (assumed to be the first row)
            int firstRow = sheet.getFirstRowNum() + 1;
            int lastRow = sheet.getLastRowNum();

            for (int i = firstRow; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue; // Skip empty rows
                }

                Item item = new Item();
                item.setDescricao(row.getCell(0).getStringCellValue());
                item.setNumeroNotaFiscal(row.getCell(1).getStringCellValue());

                // Marca
                String marcaNome = row.getCell(2).getStringCellValue();
                Marca marca = marcaRepository.findByNome(marcaNome).orElseGet(() -> {
                    Marca newMarca = new Marca();
                    newMarca.setNome(marcaNome);
                    return marcaRepository.save(newMarca);
                });

                // Modelo
                String modeloNome = row.getCell(3).getStringCellValue();
                Modelo modelo = modeloRepository.findByNomeAndMarca(modeloNome, marca).orElseGet(() -> {
                    Modelo newModelo = new Modelo();
                    newModelo.setNome(modeloNome);
                    newModelo.setMarca(marca);
                    return modeloRepository.save(newModelo);
                });
                item.setModelo(modelo);

                item.setNumeroSerie(row.getCell(4).getStringCellValue());
                item.setPotencia((int) row.getCell(5).getNumericCellValue());

                LocalDateTime dataEntrada = LocalDateTime.parse(row.getCell(6).getStringCellValue(), formatter);
                item.setDataEntrada(dataEntrada);

                LocalDateTime dataNotaFiscal = LocalDateTime.parse(row.getCell(7).getStringCellValue(), formatter);
                item.setDataNotaFiscal(dataNotaFiscal);

                // Categoria
                String categoriaNome = row.getCell(8) != null ? row.getCell(8).getStringCellValue() : null;
                Categoria categoria = categoriaNome != null ? categoriaRepository.findByNome(categoriaNome).orElseGet(() -> {
                    Categoria newCategoria = new Categoria();
                    newCategoria.setNome(categoriaNome);
                    return categoriaRepository.save(newCategoria);
                }) : null;
                item.setCategoria(categoria);

                // Estado
                String estadoNome = row.getCell(9) != null ? row.getCell(9).getStringCellValue() : null;
                Estado estado = estadoNome != null ? estadoRepository.findByNome(estadoNome).orElseGet(() -> {
                    Estado newEstado = new Estado();
                    newEstado.setNome(estadoNome);
                    return estadoRepository.save(newEstado);
                }) : null;
                item.setEstado(estado);

                // Disponibilidade
                String disponibilidadeNome = row.getCell(10) != null ? row.getCell(10).getStringCellValue() : null;
                Disponibilidade disponibilidade = disponibilidadeNome != null ? disponibilidadeRepository.findByNome(disponibilidadeNome).orElseGet(() -> {
                    Disponibilidade newDisponibilidade = new Disponibilidade();
                    newDisponibilidade.setNome(disponibilidadeNome);
                    return disponibilidadeRepository.save(newDisponibilidade);
                }) : null;
                item.setDisponibilidade(disponibilidade);

                // Localizacao
                String localizacaoNome = row.getCell(11) != null ? row.getCell(11).getStringCellValue() : null;
                Localizacao localizacao = localizacaoNome != null ? localizacaoRepository.findByNome(localizacaoNome).orElseGet(() -> {
                    Localizacao newLocalizacao = new Localizacao();
                    newLocalizacao.setNome(localizacaoNome);
                    return localizacaoRepository.save(newLocalizacao);
                }) : null;
                item.setLocalizacao(localizacao);

                items.add(item);
            }

            workbook.close();
        }

        // Save all items
        itemRepository.saveAll(items);

        return items;
    }
}
