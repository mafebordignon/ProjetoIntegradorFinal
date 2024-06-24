 package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.*;
import com.grupo6.keepInventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    private final ItemRepository itemRepository;
    private final CategoriaRepository categoriaRepository;
    private final EstadoRepository estadoRepository;
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final LocalizacaoRepository localizacaoRepository;

    private final MarcaRepository marcaRepository;

    private final ModeloRepository modeloRepository;
    @Autowired
    public RegisterController(ItemRepository itemRepository, CategoriaRepository categoriaRepository, EstadoRepository estadoRepository, DisponibilidadeRepository disponibilidadeRepository, LocalizacaoRepository localizacaoRepository, MarcaRepository marcaRepository, ModeloRepository modeloRepository) {
        this.itemRepository = itemRepository;
        this.categoriaRepository = categoriaRepository;
        this.estadoRepository = estadoRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.localizacaoRepository = localizacaoRepository;
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
    }



    @GetMapping("/categorias")
    public List<Categoria> categorias() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/localizacoes")
    public List<Localizacao> localizacoes() {
        return localizacaoRepository.findAll();
    }

    @GetMapping("/marcas")
    public List<Marca> marcas() {return marcaRepository.findAll();}

    @GetMapping("/modelos/{id}")
    public List<Modelo> modelos(@PathVariable Long id) {return modeloRepository.findByMarca(marcaRepository.findById(id));}

    @PostMapping("/salvarItem")
    public Item cadastro(@RequestBody Item item){
        return itemRepository.save(item);
    }

}