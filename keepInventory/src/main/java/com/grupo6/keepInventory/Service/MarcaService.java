package com.grupo6.keepInventory.Service;

import com.grupo6.keepInventory.Model.Marca;
import com.grupo6.keepInventory.Repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    MarcaRepository marcaRepository;

    public ResponseEntity<Marca> cadastrar(Marca marca) {
        if (marca.getNome() == null || marca.getNome().isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        if (marcaRepository.existsByNome(marca.getNome())){
            return ResponseEntity.badRequest().build();
        }
        marcaRepository.save(marca);
        return ResponseEntity.ok(marca);
    }

    public ResponseEntity<Marca> alterarNome(Long marcaId, String newName) {
        Optional<Marca> temMarca = marcaRepository.findById(marcaId);
        if(temMarca.isPresent()){
            Marca marca = temMarca.get();
            marca.setNome(newName);
            if(!marcaRepository.existsByNome(marca.getNome())){
                marcaRepository.save(marca);
                return ResponseEntity.ok(marca);
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Marca> buscarPorNome(String nomeMarca) {
        Optional<Marca> marca = marcaRepository.findByNome(nomeMarca);
        if (marca.isPresent()){
            return ResponseEntity.ok(marca.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Marca>> listarTodas(){
        List<Marca> marcas = marcaRepository.findAll();
        if (!marcas.isEmpty()){
            return ResponseEntity.ok(marcas);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Marca> buscarPorId(Long idMarca) {
        Optional<Marca> marca = marcaRepository.findById(idMarca);
        if (marca.isPresent()){
            return ResponseEntity.ok(marca.get());
        }
        return ResponseEntity.notFound().build();
    }

    public Page<Marca> pegarItensPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return marcaRepository.findAll(pageable);
    }

    public Boolean temUmaMarca(){
        if (marcaRepository.count()>0){
            return true;
        }
        return false;

    }

}