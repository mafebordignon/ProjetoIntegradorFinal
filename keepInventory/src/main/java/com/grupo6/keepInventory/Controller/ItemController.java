package com.grupo6.keepInventory.Controller;
import com.grupo6.keepInventory.Model.*;
import com.grupo6.keepInventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itens")
public class ItemController {

    private final ItemRepository itemRepository;
    private final CategoriaRepository categoriaRepository;
    private final EstadoRepository estadoRepository;
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final LocalizacaoRepository localizacaoRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository,
                          CategoriaRepository categoriaRepository,
                          EstadoRepository estadoRepository,
                          DisponibilidadeRepository disponibilidadeRepository,
                          LocalizacaoRepository localizacaoRepository) {
        this.itemRepository = itemRepository;
        this.categoriaRepository = categoriaRepository;
        this.estadoRepository = estadoRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.localizacaoRepository = localizacaoRepository;
    }

    @GetMapping("")
    public ResponseEntity<Page<Item>> getAll(@RequestParam(defaultValue = "0") int pageNumber,@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "none") String filter, @RequestParam(defaultValue = "1") Long idFilter){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Item> allFields;
        if("none".equals(filter)){
            allFields = itemRepository.findAll(pageable);
        } else if ("categoria".equals(filter)) {
            allFields = itemRepository.findByCategoria(idFilter, pageable);
        } else if ("estado".equals(filter)) {
            allFields = itemRepository.findByEstado(idFilter, pageable);
        } else if ("disponibilidade".equals(filter)) {
            allFields = itemRepository.findByDisponibilidade(idFilter, pageable);
        } else if ("localizacao".equals(filter)) {
            allFields = itemRepository.findByLocalizacao(idFilter, pageable);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(allFields);
    }
    @GetMapping("descricao/{descricao}")
    public ResponseEntity<Page<Item>> getByDescricao(@RequestParam(defaultValue = "0") int pageNumber,@RequestParam(defaultValue = "10") int pageSize, @PathVariable String descricao){
        System.out.println(descricao);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Item> allFields = itemRepository.findByDescricao(descricao, pageable);
        return ResponseEntity.ok(allFields);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        return itemOptional.map(item -> ResponseEntity.ok().body(item))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public Item adicionarItem(@RequestBody Item item) {
        Categoria categoria = categoriaRepository.findById(item.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        item.setCategoria(categoria);

        Estado estado = estadoRepository.findById(item.getEstado().getId())
                .orElseThrow(() -> new RuntimeException("Estado não encontrado"));
        item.setEstado(estado);

        Disponibilidade disponibilidade = disponibilidadeRepository.findById(item.getDisponibilidade().getId())
                .orElseThrow(() -> new RuntimeException("Disponibilidade não encontrada"));
        item.setDisponibilidade(disponibilidade);

        Localizacao localizacao = localizacaoRepository.findById(item.getLocalizacao().getId())
                .orElseThrow(() -> new RuntimeException("Localização não encontrada"));
        item.setLocalizacao(localizacao);

        return itemRepository.save(item);
    }

    @PutMapping("/{id}")
    public Item atualizarItem(@PathVariable Long id, @RequestBody Item itemAtualizado) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setDescricao(itemAtualizado.getDescricao());
                    item.setNumeroNotaFiscal(itemAtualizado.getNumeroNotaFiscal());
                    item.setModelo(itemAtualizado.getModelo());
                    item.setNumeroSerie(itemAtualizado.getNumeroSerie());
                    item.setPotencia(itemAtualizado.getPotencia());
                    item.setDataEntrada(itemAtualizado.getDataEntrada());

                    Categoria categoria = categoriaRepository.findById(itemAtualizado.getCategoria().getId())
                            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
                    item.setCategoria(categoria);

                    Estado estado = estadoRepository.findById(itemAtualizado.getEstado().getId())
                            .orElseThrow(() -> new RuntimeException("Estado não encontrado"));
                    item.setEstado(estado);

                    Disponibilidade disponibilidade = disponibilidadeRepository.findById(itemAtualizado.getDisponibilidade().getId())
                            .orElseThrow(() -> new RuntimeException("Disponibilidade não encontrada"));
                    item.setDisponibilidade(disponibilidade);

                    Localizacao localizacao = localizacaoRepository.findById(itemAtualizado.getLocalizacao().getId())
                            .orElseThrow(() -> new RuntimeException("Localização não encontrada"));
                    item.setLocalizacao(localizacao);

                    return itemRepository.save(item);
                })
                .orElseThrow(() -> new RuntimeException("Item não encontrado com o id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deletarItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }
}