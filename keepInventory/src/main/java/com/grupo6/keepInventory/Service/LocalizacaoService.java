package com.grupo6.keepInventory.Service;

import com.grupo6.keepInventory.Model.Categoria;
import com.grupo6.keepInventory.Model.Localizacao;
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
public class LocalizacaoService {
    @Autowired
    LocalizacaoRepository localizacaoRepository;

    // Método para cadastrar uma localização
    public ResponseEntity<Localizacao> cadastrar(Localizacao localizacao) {
        if (localizacao.getNome() == null || localizacao.getNome().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (localizacaoRepository.existsByNome(localizacao.getNome())) {
            return ResponseEntity.badRequest().build();
        }
        localizacaoRepository.save(localizacao);
        return ResponseEntity.ok(localizacao);
    }

    // Método para alterar o nome de uma localização
    public ResponseEntity<Localizacao> alterarNomeLocalizacao(Long localizacaoId, String newName) {
        Optional<Localizacao> temlocalizacao = localizacaoRepository.findById(localizacaoId);
        if (temlocalizacao.isPresent()) {
            Localizacao localizacao = temlocalizacao.get();
            localizacao.setNome(newName);
            if (!localizacaoRepository.existsByNome(localizacao.getNome())) {
                localizacaoRepository.save(localizacao);
                return ResponseEntity.ok(localizacao);
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método para ativar/desativar uma localização
    public ResponseEntity<Localizacao> toggleLocalizacao(Long localizacaoId) {
        Optional<Localizacao> temlocalizacao = localizacaoRepository.findById(localizacaoId);
        if (temlocalizacao.isPresent()) {
            Localizacao localizacao = temlocalizacao.get();
            Boolean ativo = localizacao.isAtivo();
            localizacao.setAtivo(!ativo);
            localizacaoRepository.save(localizacao);
            return ResponseEntity.ok(localizacao);
        }
        return ResponseEntity.notFound().build();
    }

    // Método para buscar uma localização por nome
    public ResponseEntity<Localizacao> buscarLocalizacaoPorNome(String nomeLocalizacao) {
        Optional<Localizacao> localizacao = localizacaoRepository.findByNome(nomeLocalizacao);
        if (localizacao.isPresent()) {
            return ResponseEntity.ok(localizacao.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Método para buscar uma localização por ID
    public ResponseEntity<Localizacao> buscarLocalizacaoPorId(Long idLocalizacao) {
        Optional<Localizacao> localizacao = localizacaoRepository.findById(idLocalizacao);
        if (localizacao.isPresent()) {
            return ResponseEntity.ok(localizacao.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Localizacao>> listarTodas(){
        List<Localizacao> localizacoes = localizacaoRepository.findAll();
        if (!localizacoes.isEmpty()){
            return ResponseEntity.ok(localizacoes);
        }
        return ResponseEntity.notFound().build();
    }
    // Método para pegar itens de localização paginados
    public Page<Localizacao> pegarLocalizacaoItensPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return localizacaoRepository.findAll(pageable);
    }
}
