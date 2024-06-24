package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.Manutencao;
import com.grupo6.keepInventory.Service.ManutencaoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/especificacao")
public class EspecificacaoController {

    private final ManutencaoService manutencaoService;

    @Autowired
    public EspecificacaoController(ManutencaoService manutencaoService) {
        this.manutencaoService = manutencaoService;
    }

    @PostMapping("/abrir")
    public ResponseEntity<?> abrirManutencao(@RequestBody Manutencao manutencao) {
        try {
            ResponseEntity<?> novaManutencao = manutencaoService.abrirManutencao(manutencao);
            return ResponseEntity.ok(novaManutencao);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/manutencaoLista/{itemId}")
    public ResponseEntity<?> listarManutencaoPorItem(@PathVariable Long itemId){
        return manutencaoService.listarManutencaoPorItem(itemId);
    }

    @GetMapping("/manutencao/{itemId}")
    public ResponseEntity<?> ultimaManutencao(@PathVariable Long itemId){
        return manutencaoService.pegarUltimaManutencao(itemId);
}
}