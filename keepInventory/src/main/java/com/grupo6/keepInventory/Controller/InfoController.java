package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.Acao;
import com.grupo6.keepInventory.Model.Item;
import com.grupo6.keepInventory.Repository.AcaoRepository;
import com.grupo6.keepInventory.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/infogeral")
public class InfoController {

    private final ItemRepository itemRepository;
    private final AcaoRepository acaoRepository;

    @Autowired
    public InfoController(ItemRepository itemRepository, AcaoRepository acaoRepository) {
        this.itemRepository = itemRepository;
        this.acaoRepository = acaoRepository;
    }

    @GetMapping("/itens")
    public ResponseEntity<Page<Item>> getAll(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "none") String filter, @RequestParam(defaultValue = "1") Long idFilter, @RequestParam(defaultValue = "none") String nome){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Item> allFields;
        System.out.println(nome);
        if ("none".equals(filter)) {
            allFields = itemRepository.findAll(pageable);
        } else if ("categoria".equals(filter)) {
            allFields = itemRepository.findByCategoria(idFilter, pageable);
        } else if ("estado".equals(filter)) {
            allFields = itemRepository.findByEstado(idFilter, pageable);
        } else if ("disponibilidade".equals(filter)) {
            allFields = itemRepository.findByDisponibilidade(idFilter, pageable);
        } else if ("localizacao".equals(filter)) {
            allFields = itemRepository.findByLocalizacao(idFilter, pageable);
        } else if ("nome".equals(filter)) {
            allFields = itemRepository.findByNome(nome, pageable);
            if (allFields.isEmpty()) {
                allFields = itemRepository.findByMarca(nome, pageable);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(allFields);
    }

    @GetMapping("/acoes/{itemid}")
    public ResponseEntity<Page<Acao>> getByItem(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @PathVariable Long itemid) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Acao> listaAcoes = acaoRepository.findByItem(itemid, pageable);
        return ResponseEntity.ok(listaAcoes);
    }
}
