package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.Manutencao;
import com.grupo6.keepInventory.Service.ManutencaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manutencoes")
public class ManutencaoController {

    private final ManutencaoService manutencaoService;

    @Autowired
    public ManutencaoController(ManutencaoService manutencaoService) {
        this.manutencaoService = manutencaoService;
    }

    @PostMapping("/abrir")
    public ResponseEntity<?> abrirManutencao(@RequestBody Manutencao manutencao) {
        return manutencaoService.abrirManutencao(manutencao);
    }

    @PostMapping("/fechar")
    public ResponseEntity<?> fecharManutencao(@RequestBody Manutencao manutencao) {
        return manutencaoService.fecharManutencao(manutencao);
    }

    @PostMapping("/mandar")
    public ResponseEntity<?> mandarManutencao(@RequestBody Manutencao manutencao) {
        return manutencaoService.mandarManutencao(manutencao);
    }

    @PutMapping("/{manutencaoId}")
    public ResponseEntity<?> editarManutencao(@RequestBody Manutencao manutencao, @PathVariable Long manutencaoId) {
        return manutencaoService.editarManutencao(manutencao, manutencaoId);
    }

    @GetMapping("/{manutencaoId}")
    public ResponseEntity<?> pegarManutencao(@PathVariable Long manutencaoId) {
        return manutencaoService.pegarManutencao(manutencaoId);
    }

    @GetMapping("/{manutencaoId}/item")
    public ResponseEntity<?> pegarItemPorManutencao(@PathVariable Long manutencaoId) {
        return manutencaoService.pegarItemPorManutencao(manutencaoId);
    }

    @GetMapping("/ultima/{itemId}")
    public ResponseEntity<?> pegarUltimaManutencao(@PathVariable Long itemId) {
        return manutencaoService.pegarUltimaManutencao(itemId);
    }

    @GetMapping("/listar/{itemId}")
    public ResponseEntity<?> listarManutencaoPorItem(@PathVariable Long itemId) {
        return manutencaoService.listarManutencaoPorItem(itemId);
    }
}