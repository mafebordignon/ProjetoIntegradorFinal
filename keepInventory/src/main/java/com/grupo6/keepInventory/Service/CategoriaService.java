package com.grupo6.keepInventory.Service;

import com.grupo6.keepInventory.Model.Categoria;
import com.grupo6.keepInventory.Model.Localizacao;
import com.grupo6.keepInventory.Repository.CategoriaRepository;
import com.grupo6.keepInventory.Repository.LocalizacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public ResponseEntity<Categoria> cadastrar(Categoria categoria) {
        if (categoria.getNome() == null || categoria.getNome().isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        if (categoriaRepository.existsByNome(categoria.getNome())){
            return ResponseEntity.badRequest().build();
        }
        categoriaRepository.save(categoria);
        return ResponseEntity.ok(categoria);
    }

    public ResponseEntity<Categoria> alterarNome(Long categoriaId, String newName) {
        Optional<Categoria> temcategoria = categoriaRepository.findById(categoriaId);
        if(temcategoria.isPresent()){
            Categoria categoria = temcategoria.get();
            categoria.setNome(newName);
            if(!categoriaRepository.existsByNome(categoria.getNome())){
                categoriaRepository.save(categoria);
                return ResponseEntity.ok(categoria);
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();

    }

    public ResponseEntity<Categoria> toggleCategoria(Long categoriaId) {
        Optional<Categoria> temcategoria = categoriaRepository.findById(categoriaId);
        if (temcategoria.isPresent()) {
            Categoria categoria = temcategoria.get();
            Boolean ativo = categoria.isAtivo();
            categoria.setAtivo(!ativo);
            categoriaRepository.save(categoria);
            return ResponseEntity.ok(categoria);
        }
        return ResponseEntity.notFound().build();

    }

    public ResponseEntity<Categoria> buscarPorNome(String nomeCategoria) {
        Optional<Categoria> categoria = categoriaRepository.findByNome(nomeCategoria);
        if (categoria.isPresent()){
            return ResponseEntity.ok(categoria.get());
        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<List<Categoria>> listarTodas(){
        List<Categoria> categorias = categoriaRepository.findAll();
        if (!categorias.isEmpty()){
            return ResponseEntity.ok(categorias);
        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<Categoria> buscarPorId(Long idCategoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
        if (categoria.isPresent()){
            return ResponseEntity.ok(categoria.get());
        }
        return ResponseEntity.notFound().build();
    }

    public Page<Categoria> pegarItensPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoriaRepository.findAll(pageable);
    }

}
