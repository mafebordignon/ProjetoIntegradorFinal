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

                // Descricao
                Cell descricaoCell = row.getCell(0);
                if (descricaoCell != null && descricaoCell.getCellType() == CellType.STRING) {
                    item.setDescricao(descricaoCell.getStringCellValue());
                }

                // Numero Nota Fiscal
                Cell numeroNotaFiscalCell = row.getCell(1);
                if (numeroNotaFiscalCell != null && numeroNotaFiscalCell.getCellType() == CellType.STRING) {
                    item.setNumeroNotaFiscal(numeroNotaFiscalCell.getStringCellValue());
                }

                // Marca
                Cell marcaCell = row.getCell(2);
                if (marcaCell != null && marcaCell.getCellType() == CellType.STRING) {
                    String marcaNome = marcaCell.getStringCellValue();
                    Marca marca = marcaRepository.findByNome(marcaNome).orElseGet(() -> {
                        Marca newMarca = new Marca();
                        newMarca.setNome(marcaNome);
                        return marcaRepository.save(newMarca);
                    });

                    // Modelo
                    Cell modeloCell = row.getCell(3);
                    if (modeloCell != null && modeloCell.getCellType() == CellType.STRING) {
                        String modeloNome = modeloCell.getStringCellValue();
                        Modelo modelo = modeloRepository.findByNomeAndMarca(modeloNome, marca).orElseGet(() -> {
                            Modelo newModelo = new Modelo();
                            newModelo.setNome(modeloNome);
                            newModelo.setMarca(marca);
                            return modeloRepository.save(newModelo);
                        });
                        item.setModelo(modelo);
                    }
                }

                // Numero Serie
                Cell numeroSerieCell = row.getCell(4);
                if (numeroSerieCell != null && numeroSerieCell.getCellType() == CellType.STRING) {
                    item.setNumeroSerie(numeroSerieCell.getStringCellValue());
                }

                // Potencia
                Cell potenciaCell = row.getCell(5);
                if (potenciaCell != null && potenciaCell.getCellType() == CellType.NUMERIC) {
                    item.setPotencia((int) potenciaCell.getNumericCellValue());
                }

                // Data Entrada
                Cell dataEntradaCell = row.getCell(6);
                if (dataEntradaCell != null && dataEntradaCell.getCellType() == CellType.STRING) {
                    LocalDateTime dataEntrada = LocalDateTime.parse(dataEntradaCell.getStringCellValue(), formatter);
                    item.setDataEntrada(dataEntrada);
                }

                // Data Nota Fiscal
                Cell dataNotaFiscalCell = row.getCell(7);
                if (dataNotaFiscalCell != null && dataNotaFiscalCell.getCellType() == CellType.STRING) {
                    LocalDateTime dataNotaFiscal = LocalDateTime.parse(dataNotaFiscalCell.getStringCellValue(), formatter);
                    item.setDataNotaFiscal(dataNotaFiscal);
                }

                // Categoria
                Cell categoriaCell = row.getCell(8);
                if (categoriaCell != null && categoriaCell.getCellType() == CellType.STRING) {
                    String categoriaNome = categoriaCell.getStringCellValue();
                    Categoria categoria = categoriaRepository.findByNome(categoriaNome).orElseGet(() -> {
                        Categoria newCategoria = new Categoria();
                        newCategoria.setNome(categoriaNome);
                        return categoriaRepository.save(newCategoria);
                    });
                    item.setCategoria(categoria);
                }

                // Estado
                Cell estadoCell = row.getCell(9);
                if (estadoCell != null && estadoCell.getCellType() == CellType.STRING) {
                    String estadoNome = estadoCell.getStringCellValue();
                    Estado estado = estadoRepository.findByNome(estadoNome).orElseGet(() -> {
                        Estado newEstado = new Estado();
                        newEstado.setNome(estadoNome);
                        return estadoRepository.save(newEstado);
                    });
                    item.setEstado(estado);
                }

                // Disponibilidade
                Cell disponibilidadeCell = row.getCell(10);
                if (disponibilidadeCell != null && disponibilidadeCell.getCellType() == CellType.STRING) {
                    String disponibilidadeNome = disponibilidadeCell.getStringCellValue();
                    Disponibilidade disponibilidade = disponibilidadeRepository.findByNome(disponibilidadeNome).orElseGet(() -> {
                        Disponibilidade newDisponibilidade = new Disponibilidade();
                        newDisponibilidade.setNome(disponibilidadeNome);
                        return disponibilidadeRepository.save(newDisponibilidade);
                    });
                    item.setDisponibilidade(disponibilidade);
                }

                // Localizacao
                Cell localizacaoCell = row.getCell(11);
                if (localizacaoCell != null && localizacaoCell.getCellType() == CellType.STRING) {
                    String localizacaoNome = localizacaoCell.getStringCellValue();
                    Localizacao localizacao = localizacaoRepository.findByNome(localizacaoNome).orElseGet(() -> {
                        Localizacao newLocalizacao = new Localizacao();
                        newLocalizacao.setNome(localizacaoNome);
                        return localizacaoRepository.save(newLocalizacao);
                    });
                    item.setLocalizacao(localizacao);
                }

                items.add(item);
            }

            workbook.close();
        }

        return items;
    }
}

