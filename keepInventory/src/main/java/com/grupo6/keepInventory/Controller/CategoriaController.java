package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.Categoria;
import com.grupo6.keepInventory.Model.Disponibilidade;
import com.grupo6.keepInventory.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;
    @GetMapping
    public List<Categoria> categorias() {
        return categoriaRepository.findAll();
    }
    @GetMapping("/{pageNumber}")
    public ResponseEntity<Page<Categoria>> GetAll(@PathVariable int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, 10);
        Page<Categoria> AllFields = categoriaRepository.findAll(pageable);
        return ResponseEntity.ok(AllFields);
    }
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Categoria categoria) {
        try {
            for (Categoria c : categoriaRepository.findAll()) {
                if (categoria.getNome().equals(c.getNome())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A categoria já existe");
                }
            }
            Categoria novaCategoria = categoriaRepository.save(categoria);
            return ResponseEntity.ok(novaCategoria);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }
    }

    @PutMapping("/{id}")
    public Categoria atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaAtualizado) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNome(categoriaAtualizado.getNome());
                    return categoriaRepository.save(categoria);
                })
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deletarCategoria(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
    }

}
