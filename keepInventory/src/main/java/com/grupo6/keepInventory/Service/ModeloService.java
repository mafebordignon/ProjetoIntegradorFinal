package com.grupo6.keepInventory.Service;

import com.grupo6.keepInventory.Model.Modelo;
import com.grupo6.keepInventory.Model.Marca;
import com.grupo6.keepInventory.Repository.ModeloRepository;
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
public class ModeloService {

    @Autowired
    ModeloRepository modeloRepository;

    @Autowired
    MarcaRepository marcaRepository;

    public ResponseEntity<Modelo> cadastrar(Modelo modelo) {
        // Verifica se o nome do modelo está presente e não é vazio
        if (modelo.getNome() == null || modelo.getNome().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Verifica se já existe um modelo com o mesmo nome e a mesma marca
        if (modeloRepository.existsByNomeAndMarca(modelo.getNome(), modelo.getMarca())) {
            return ResponseEntity.badRequest().build();
        }

        // Verifica se a marca associada ao modelo existe no banco de dados
        Optional<Marca> marcaOpt = marcaRepository.findById(modelo.getMarca().getId());
        if (!marcaOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // Atualiza o modelo com a marca recuperada do banco de dados
        modelo.setMarca(marcaOpt.get());

        // Salva o modelo no banco de dados
        modeloRepository.save(modelo);

        // Retorna uma resposta de sucesso com o modelo salvo
        return ResponseEntity.ok(modelo);
    }

    public ResponseEntity<Modelo> alterarNome(Long modeloId, String newName, Long novaMarcaId) {
        Optional<Modelo> temModelo = modeloRepository.findById(modeloId);

        if (temModelo.isPresent()) {
            Modelo modelo = temModelo.get();
            Marca marcaAtual = modelo.getMarca(); // Obtém a marca atual do modelo

            // Verifica se o novo nome é diferente do nome atual do modelo
            if (!newName.equals(modelo.getNome())) {
                // Verifica se já existe um modelo com o novo nome e a mesma marca
                if (modeloRepository.existsByNomeAndMarca(newName, marcaAtual)) {
                    return ResponseEntity.badRequest().build();
                }

                // Atualiza o nome do modelo
                modelo.setNome(newName);
            }

            // Verifica se houve alteração na marca
            if (novaMarcaId != null && !novaMarcaId.equals(marcaAtual.getId())) {
                Optional<Marca> novaMarcaOpt = marcaRepository.findById(novaMarcaId);
                if (!novaMarcaOpt.isPresent()) {
                    return ResponseEntity.badRequest().build(); // Marca não encontrada
                }
                // Atualiza a marca do modelo
                modelo.setMarca(novaMarcaOpt.get());
            }

            // Salva o modelo atualizado no banco de dados
            modeloRepository.save(modelo);

            // Retorna uma resposta de sucesso com o modelo atualizado
            return ResponseEntity.ok(modelo);
        }

        // Retorna resposta de modelo não encontrado caso o modelo não exista
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Modelo> buscarPorNome(String nomeModelo) {
        Optional<Modelo> modelo = modeloRepository.findByNome(nomeModelo);
        if (modelo.isPresent()){
            return ResponseEntity.ok(modelo.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Modelo>> listarTodos(){
        List<Modelo> modelos = modeloRepository.findAll();
        if (!modelos.isEmpty()){
            return ResponseEntity.ok(modelos);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Modelo> buscarPorId(Long idModelo) {
        Optional<Modelo> modelo = modeloRepository.findById(idModelo);
        if (modelo.isPresent()){
            return ResponseEntity.ok(modelo.get());
        }
        return ResponseEntity.notFound().build();
    }

    public Page<Modelo> pegarItensPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return modeloRepository.findAll(pageable);
    }
}