package com.grupo6.keepInventory.Controller;


import com.grupo6.keepInventory.Model.Tipoacao;
import com.grupo6.keepInventory.Model.Usuario;
import com.grupo6.keepInventory.Repository.TipoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tiposacao")
public class TipoacaoController {
    @Autowired
    private TipoacaoRepository tipoacaoRepository;

    public List<Tipoacao> tiposacao() {
        return tipoacaoRepository.findAll();
    }
    @GetMapping("/{pageNumber}")
    public ResponseEntity<Page<Tipoacao>> GetAll(@PathVariable int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, 10);
        Page<Tipoacao> AllFields = tipoacaoRepository.findAll(pageable);
        return ResponseEntity.ok(AllFields);
    }
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Tipoacao tipoacao) {
        try {
            for (Tipoacao t : tipoacaoRepository.findAll()) {
                if (tipoacao.getNome().equals(t.getNome())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A tipoacao já existe");
                }
            }
            Tipoacao novaCategoria = tipoacaoRepository.save(tipoacao);
            return ResponseEntity.ok(novaCategoria);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }
    }

    @PutMapping("/{id}")
    public Tipoacao atualizarTipoAcao(@PathVariable Long id, @RequestBody Tipoacao tipoacaoAtualizado) {
        return tipoacaoRepository.findById(id)
                .map(tipoAcao -> {
                    tipoAcao.setNome(tipoacaoAtualizado.getNome());
                    return tipoacaoRepository.save(tipoAcao);
                })
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deletarTipoAcao(@PathVariable Long id) {
        tipoacaoRepository.deleteById(id);
    }
}
