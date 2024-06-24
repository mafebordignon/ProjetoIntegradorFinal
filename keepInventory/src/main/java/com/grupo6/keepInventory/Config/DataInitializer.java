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

        // Inicializa dados de Categoria
        if (categoriaRepository.count() == 0) {
            categoriaRepository.save(new Categoria("Utilitários",true));
            categoriaRepository.save(new Categoria("Equipamentos",true));
            categoriaRepository.save(new Categoria("Acessórios",true));
        }

        // Initialize brands and models
        if (marcaRepository.count() == 0) {
            marcaRepository.save(new Marca("Shimadzu"));
            marcaRepository.save(new Marca("Pasco"));
            marcaRepository.save(new Marca("Zeiss"));
            marcaRepository.save(new Marca("ABB"));
            marcaRepository.save(new Marca("Ultimaker"));
            marcaRepository.save(new Marca("Malvern"));
            marcaRepository.save(new Marca("Tuttnauer"));
            marcaRepository.save(new Marca("Bio-Rad"));
            marcaRepository.save(new Marca("Sysmex"));
            marcaRepository.save(new Marca("Eppendorf"));
            marcaRepository.save(new Marca("Bruker"));
            marcaRepository.save(new Marca("Agilent"));
            marcaRepository.save(new Marca("Instron"));
            marcaRepository.save(new Marca("LICOR"));
        }

        if (modeloRepository.count() == 0) {
            marcaRepository.findAll().forEach(marca -> {
                switch (marca.getNome()) {
                    case "Shimadzu" -> modeloRepository.save(new Modelo("UV-1800", marca));
                    case "Pasco" -> modeloRepository.save(new Modelo("SF-7266", marca));
                    case "Zeiss" -> modeloRepository.save(new Modelo("Axioscope 5", marca));
                    case "ABB" -> modeloRepository.save(new Modelo("RB 120", marca));
                    case "Ultimaker" -> modeloRepository.save(new Modelo("S5", marca));
                    case "Malvern" -> modeloRepository.save(new Modelo("Mastersizer 3000", marca));
                    case "Tuttnauer" -> modeloRepository.save(new Modelo("2540M", marca));
                    case "Bio-Rad" -> modeloRepository.save(new Modelo("C1000 Touch", marca));
                    case "Sysmex" -> modeloRepository.save(new Modelo("XN-1000", marca));
                    case "Eppendorf" -> modeloRepository.save(new Modelo("5810R", marca));
                    case "Bruker" -> modeloRepository.save(new Modelo("Dimension Icon", marca));
                    case "Agilent" -> modeloRepository.save(new Modelo("1260 Infinity II", marca));
                    case "Instron" -> modeloRepository.save(new Modelo("3369", marca));
                    case "LICOR" -> modeloRepository.save(new Modelo("LI-850", marca));
                    default -> {
                    }
                }
            });
        }

        if (localizacaoRepository.count() == 0) {
            localizacaoRepository.save(new Localizacao("Lab de Química", true));
            localizacaoRepository.save(new Localizacao("Lab de Física", true));
            localizacaoRepository.save(new Localizacao("Lab de Biologia", true));
            localizacaoRepository.save(new Localizacao("Lab de Robótica", true));
            localizacaoRepository.save(new Localizacao("Lab de Engenharia", true));
            localizacaoRepository.save(new Localizacao("Lab de Pes Avançada", true));
            localizacaoRepository.save(new Localizacao("Lab de Microbiologia", true));
            localizacaoRepository.save(new Localizacao("Lab de Informação", true));
            localizacaoRepository.save(new Localizacao("Lab de Genética", true));
            localizacaoRepository.save(new Localizacao("Sala de Análises", true));
            localizacaoRepository.save(new Localizacao("Lab de Bioquímica", true));
            localizacaoRepository.save(new Localizacao("Lab de Nanotec", true));
            localizacaoRepository.save(new Localizacao("Lab de Farmacologia", true));
            localizacaoRepository.save(new Localizacao("Lab de Materiais", true));
        }


    }
}
