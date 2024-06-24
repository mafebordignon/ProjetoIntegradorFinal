package com.grupo6.keepInventory.Service;


import com.grupo6.keepInventory.Model.Categoria;
import com.grupo6.keepInventory.RepositoryTest.CategoriaRepository;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Mock
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

//teste 1
//    public ResponseEntity<Categoria> cadastrar(Categoria categoria) {
//        categoriaRepository.save(categoria);
//        return ResponseEntity.ok(categoria);
//    }

//teste 2
//public ResponseEntity<Categoria> cadastrar(Categoria categoria) {
//    if (categoria.getNome().isEmpty()){
//        return ResponseEntity.badRequest().build();
//    }
//    categoriaRepository.save(categoria);
//    return ResponseEntity.ok(categoria);
//}

//teste 2
//public ResponseEntity<Categoria> cadastrar(Categoria categoria) {
//    if (categoria.getNome() == null || categoria.getNome().isEmpty()){
//        return ResponseEntity.badRequest().build();
//    }
//    categoriaRepository.save(categoria);
//    return ResponseEntity.ok(categoria);
//}

//Teste5
//public ResponseEntity<Categoria> cadastrar(Categoria categoria) {
//    if (categoria.getNome() == null || categoria.getNome().isEmpty()){
//        return ResponseEntity.badRequest().build();
//    }
//    if (categoriaRepository.existsByNome(categoria.getNome())){
//        return ResponseEntity.badRequest().build();
//    }
//    categoriaRepository.save(categoria);
//    return ResponseEntity.ok(categoria);
//}

//teste 6
//public ResponseEntity<Categoria> alterarNome(Long categoriaId, String newName) {
//    Optional<Categoria> temcategoria = categoriaRepository.findById(categoriaId);
//    Categoria categoria = temcategoria.get();
//    categoria.setNome(newName);
//    categoriaRepository.save(categoria);
//    return ResponseEntity.ok(categoria);
//}

//teste 7
//public ResponseEntity<Categoria> alterarNome(Long categoriaId, String newName) {
//    Optional<Categoria> temcategoria = categoriaRepository.findById(categoriaId);
//    if(temcategoria.isPresent()){
//        Categoria categoria = temcategoria.get();
//        categoria.setNome(newName);
//        categoriaRepository.save(categoria);
//        return ResponseEntity.ok(categoria);
//    }
//    return ResponseEntity.notFound().build();
//
//}

//teste 8
//public ResponseEntity<Categoria> alterarNome(Long categoriaId, String newName) {
//    Optional<Categoria> temcategoria = categoriaRepository.findById(categoriaId);
//    if(temcategoria.isPresent()){
//        Categoria categoria = temcategoria.get();
//        categoria.setNome(newName);
//        if(!categoriaRepository.existsByNome(categoria.getNome())){
//            categoriaRepository.save(categoria);
//            return ResponseEntity.ok(categoria);
//        }
//        return ResponseEntity.badRequest().build();
//    }
//    return ResponseEntity.notFound().build();
//
//}

//teste 9
//public ResponseEntity<Categoria> toggleCategoria(Long categoriaId) {
//    Optional<Categoria> temcategoria = categoriaRepository.findById(categoriaId);
//        Categoria categoria = temcategoria.get();
//        Boolean ativo = categoria.isAtivo();
//        categoria.setAtivo(!ativo);
//        categoriaRepository.save(categoria);
//        return ResponseEntity.ok(categoria);

//
//}

//teste 11
//public ResponseEntity<Categoria> toggleCategoria(Long categoriaId) {
//    Optional<Categoria> temcategoria = categoriaRepository.findById(categoriaId);
//    if (temcategoria.isPresent()) {
//        Categoria categoria = temcategoria.get();
//        Boolean ativo = categoria.isAtivo();
//        categoria.setAtivo(!ativo);
//        categoriaRepository.save(categoria);
//        return ResponseEntity.ok(categoria);
//    }
//    return ResponseEntity.notFound().build();
//
//}

//12
//public ResponseEntity<Categoria> buscarPorNome(String nomeCategoria) {
//    Optional<Categoria> categoria = categoriaRepository.findByNome(nomeCategoria);
//    return ResponseEntity.ok(categoria.get());
//}

//13
//public ResponseEntity<Categoria> buscarPorNome(String nomeCategoria) {
//    Optional<Categoria> categoria = categoriaRepository.findByNome(nomeCategoria);
//    if (categoria.isPresent()){
//        return ResponseEntity.ok(categoria.get());
//    }
//    return ResponseEntity.notFound().build();
//}

//14
//public Page<Categoria> pegarItensPaginados(int page, int size) {
//    Pageable pageable = PageRequest.of(page, size);
//    return categoriaRepository.findAll(pageable);
//}