package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.Categoria;
import com.grupo6.keepInventory.Model.Disponibilidade;
import com.grupo6.keepInventory.Repository.DisponibilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disponibilidades")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;
    @GetMapping
    public List<Disponibilidade> disponibilidades() {
        return disponibilidadeRepository.findAll();
    }
    @GetMapping("/{pageNumber}")
    public ResponseEntity<Page<Disponibilidade>> GetAll(@PathVariable int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, 10);
        Page<Disponibilidade> AllFields = disponibilidadeRepository.findAll(pageable);
        return ResponseEntity.ok(AllFields);
    }
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Disponibilidade disponibilidade) {
        try {
            for (Disponibilidade d : disponibilidadeRepository.findAll()) {
                if (disponibilidade.getNome().equals(d.getNome())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A disponibilidade já existe");
                }
            }
            Disponibilidade novaDisponibilidade = disponibilidadeRepository.save(disponibilidade);
            return ResponseEntity.ok(novaDisponibilidade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }
    }

    @PutMapping("/{id}")
    public Disponibilidade atualizarDisponibilidade(@PathVariable Long id, @RequestBody Disponibilidade disponibilidadeAtualizado) {
        return disponibilidadeRepository.findById(id)
                .map(disponibilidade -> {
                    disponibilidade.setNome(disponibilidadeAtualizado.getNome());
                    return disponibilidadeRepository.save(disponibilidade);
                })
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deletarDisponibilidade(@PathVariable Long id) {
        disponibilidadeRepository.deleteById(id);
    }

}
