package com.grupo6.keepInventory.Controller;
import com.grupo6.keepInventory.Model.Anexo;
import com.grupo6.keepInventory.Model.Item;
import com.grupo6.keepInventory.Repository.AnexoRepository;
import com.grupo6.keepInventory.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/anexos")
public class AnexoController {

    private final AnexoRepository anexoRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public AnexoController(AnexoRepository anexoRepository, ItemRepository itemRepository) {
        this.anexoRepository = anexoRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public List<Anexo> listarAnexos() {
        return anexoRepository.findAll();
    }

    @PostMapping
    public Anexo adicionarAnexo(@RequestBody Anexo anexo) {
        return anexoRepository.save(anexo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anexo> getAnexoById(@PathVariable Long id) {
        Optional<Anexo> anexoOptional = anexoRepository.findById(id);
        return anexoOptional.map(anexo -> ResponseEntity.ok().body(anexo))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Anexo atualizarAnexo(@PathVariable Long id, @RequestBody Anexo anexoAtualizado) {
        return anexoRepository.findById(id)
                .map(anexo -> {
                    anexo.setNome(anexoAtualizado.getNome());
                    anexo.setDescricao(anexoAtualizado.getDescricao());
                    anexo.setCaminho(anexoAtualizado.getCaminho());
                    return anexoRepository.save(anexo);
                })
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado com o id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deletarAnexo(@PathVariable Long id) {
        anexoRepository.deleteById(id);
    }

    // Método para associar um item a um anexo
    @PostMapping("/{anexoId}/associar/{itemId}")
    public ResponseEntity<Anexo> associarItemAoAnexo(@PathVariable Long anexoId, @PathVariable Long itemId) {
        Optional<Anexo> anexoOptional = anexoRepository.findById(anexoId);
        Optional<Item> itemOptional = itemRepository.findById(itemId);

        if (anexoOptional.isPresent() && itemOptional.isPresent()) {
            Anexo anexo = anexoOptional.get();
            Item item = itemOptional.get();
            anexo.getItens().add(item);
            anexoRepository.save(anexo);
            return ResponseEntity.ok().body(anexo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}