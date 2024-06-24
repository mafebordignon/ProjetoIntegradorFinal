package com.grupo6.keepInventory.Config;

import com.grupo6.keepInventory.Model.*;
import com.grupo6.keepInventory.Repository.*;
import com.grupo6.keepInventory.Service.PasswordHashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    @Autowired
    private AcaoRepository acaoRepository;

    @Autowired
    private TipoacaoRepository tipoacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario("admin", "admin", "admin@admin.com", "ADMIN", PasswordHashingService.hashPassword("admin"));
            usuarioRepository.save(admin);
        }

        if (tipoacaoRepository.count() == 0) {
            tipoacaoRepository.save(new Tipoacao("Empréstimo"));
            tipoacaoRepository.save(new Tipoacao("Devolução"));
        }

        if (estadoRepository.count() == 0) {
            estadoRepository.save(new Estado("Ativo"));
            estadoRepository.save(new Estado("Inativo"));
        }

        if (disponibilidadeRepository.count() == 0){
            disponibilidadeRepository.save(new Disponibilidade("Disponível"));
            disponibilidadeRepository.save(new Disponibilidade("Emprestado"));
            disponibilidadeRepository.save(new Disponibilidade("Necessita de manutenção"));
            disponibilidadeRepository.save(new Disponibilidade("Em manutencao"));
            disponibilidadeRepository.save(new Disponibilidade("Irrecuperável"));
        }
    }
}
