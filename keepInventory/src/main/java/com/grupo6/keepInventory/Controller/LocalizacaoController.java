package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.Localizacao;
import com.grupo6.keepInventory.Repository.LocalizacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/localizacoes")
public class LocalizacaoController {
    @Autowired
    private LocalizacaoRepository localizacaoRepository;
    @GetMapping
    public List<Localizacao> localizacoes() {
        return localizacaoRepository.findAll();
    }
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping
    public Localizacao adicionar(@RequestBody Localizacao localizacao) {
        return localizacaoRepository.save(localizacao);
    }

    @PutMapping("/{id}")
    public Localizacao atualizarLocalizacao(@PathVariable Long id, @RequestBody Localizacao localizacaoAtualizado) {
        return localizacaoRepository.findById(id)
                .map(localizacao -> {
                    localizacao.setNome(localizacaoAtualizado.getNome());
                    return localizacaoRepository.save(localizacao);
                })
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deletarLocalizacao(@PathVariable Long id) {
        localizacaoRepository.deleteById(id);
    }

}
