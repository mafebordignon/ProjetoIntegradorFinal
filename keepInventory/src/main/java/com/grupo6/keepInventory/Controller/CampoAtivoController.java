package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.Categoria;
import com.grupo6.keepInventory.Model.Localizacao;
import com.grupo6.keepInventory.Model.Marca;
import com.grupo6.keepInventory.Model.Modelo;
import com.grupo6.keepInventory.Service.CategoriaService;
import com.grupo6.keepInventory.Service.LocalizacaoService;
import com.grupo6.keepInventory.Service.MarcaService;
import com.grupo6.keepInventory.Service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campo-ativo")
public class CampoAtivoController {
    @Autowired
    CategoriaService categoriaService;
    @Autowired
    LocalizacaoService localizacaoService;
    @Autowired
    MarcaService marcaService;
    @Autowired
    ModeloService modeloService;

    @GetMapping("/marca/tem")
    public Boolean temMarca(){
        return marcaService.temUmaMarca();
    }
    @GetMapping("/marcas/todas")
    public ResponseEntity<List<Marca>> listarMarcas(){
        return marcaService.listarTodas();
    }

    @GetMapping("/marcas")
    public Page<Marca> marcasPaginadas(@RequestParam int page, @RequestParam int size){
        return marcaService.pegarItensPaginados(page, size);
    }

    @GetMapping("/localizacoes")
    public Page<Localizacao> localizacoesPaginadas(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return localizacaoService.pegarLocalizacaoItensPaginados(page, size);
    }

    @GetMapping("/categorias")
    public Page<Categoria> categoriasPaginadas(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoriaService.pegarItensPaginados(page, size);
    }

    @GetMapping("/modelos")
    public Page<Modelo> modelosPaginados(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return modeloService.pegarItensPaginados(page, size);
    }

    @PostMapping("/categorias")
    public ResponseEntity<Categoria> cadastrarCategoria(@RequestBody Categoria categoria) {
        return categoriaService.cadastrar(categoria);
    };

    @PostMapping("/localizacoes")
    public ResponseEntity<Localizacao> cadastrarLocalizacao(@RequestBody Localizacao localizacao) {
        return localizacaoService.cadastrar(localizacao);
    };

    @PostMapping("/marcas")
    public ResponseEntity<Marca> cadastrarMarca(@RequestBody Marca marca) {
        return marcaService.cadastrar(marca);
    };

    @PostMapping("/modelos")
    public ResponseEntity<Modelo> cadastrarModelo(@RequestBody Modelo modelo) {
        System.out.println(modelo.getNome());
        return modeloService.cadastrar(modelo);
    };

    @PutMapping("/marcas/{id}")
    public ResponseEntity<Marca> alterarMarca(@PathVariable Long id,@RequestBody Marca marca){
        return marcaService.alterarNome(id, marca.getNome());
    }
    @PutMapping("/localizacoes/{id}")
    public ResponseEntity<Localizacao> alterarLocalizacao(@PathVariable Long id,@RequestBody Localizacao localizacao){
        return localizacaoService.alterarNomeLocalizacao(id, localizacao.getNome());
    }
    @PutMapping("/categorias/{id}")
    public ResponseEntity<Categoria> alterarCategoria(@PathVariable Long id,@RequestBody Categoria categoria){
        return categoriaService.alterarNome(id, categoria.getNome());
    }
    @PutMapping("/modelos/{id}")
    public ResponseEntity<Modelo> alterarModelo(@PathVariable Long id,@RequestBody Modelo modelo){
        return modeloService.alterarNome(id, modelo.getNome(), modelo.getMarca().getId());
    }
}
