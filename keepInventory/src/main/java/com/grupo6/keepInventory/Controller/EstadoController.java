package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.Disponibilidade;
import com.grupo6.keepInventory.Model.Estado;
import com.grupo6.keepInventory.Repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;
   
    @GetMapping
    public List<Estado> estados() {
        return estadoRepository.findAll();
    }
    @GetMapping("/{pageNumber}")
    public ResponseEntity<Page<Estado>> GetAll(@PathVariable int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, 10);
        Page<Estado> AllFields = estadoRepository.findAll(pageable);
        return ResponseEntity.ok(AllFields);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Estado estado) {
        try {
            for (Estado e : estadoRepository.findAll()) {
                if (estado.getNome().equals(e.getNome())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O estado já existe");
                }
            }
            Estado novaDisponibilidade = estadoRepository.save(estado);
            return ResponseEntity.ok(novaDisponibilidade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }
    }

    @PutMapping("/{id}")
    public Estado atualizarEstado(@PathVariable Long id, @RequestBody Estado estadoAtualizado) {
        return estadoRepository.findById(id)
                .map(estado -> {
                    estado.setNome(estadoAtualizado.getNome());
                    return estadoRepository.save(estado);
                })
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deletarEstado(@PathVariable Long id) {
        estadoRepository.deleteById(id);
    }

}
