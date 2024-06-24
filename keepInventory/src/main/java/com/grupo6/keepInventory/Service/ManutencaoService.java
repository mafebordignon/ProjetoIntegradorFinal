package com.grupo6.keepInventory.Service;

import com.grupo6.keepInventory.Model.Item;
import com.grupo6.keepInventory.Model.Manutencao;
import com.grupo6.keepInventory.Repository.DisponibilidadeRepository;
import com.grupo6.keepInventory.Repository.ItemRepository;
import com.grupo6.keepInventory.Repository.ManutencaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ManutencaoService {
    @Autowired
    ManutencaoRepository manutencaoRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    DisponibilidadeRepository disponibilidadeRepository;
    // esperase explicação
    public ResponseEntity<?> abrirManutencao(Manutencao manutencao){
        Optional<Item> itemOpt = itemRepository.findById(manutencao.getItem().getId());
        if(manutencao.getDescricaoProblema() == null || manutencao.getDescricaoProblema() == "" || manutencao.getDataInicio() == null){
            return ResponseEntity.badRequest().body("Campo vazio.");
        }
        if (itemOpt.isPresent()) {

            Item item = itemOpt.get();
            item.setDisponibilidade(disponibilidadeRepository.findById(3L).get());

            itemRepository.save(item);

            return ResponseEntity.ok(manutencaoRepository.save(manutencao));
        } else {
            return ResponseEntity.badRequest().body("Item não encontrado.");
        }

    }
    // esperase explicação
    public ResponseEntity<?> fecharManutencao(Manutencao manutencao){
        Optional<Item> itemOpt = itemRepository.findById(manutencao.getItem().getId());
        if(manutencao.getDescricaoSolucao() == null ||manutencao.getDescricaoSolucao().isEmpty() || manutencao.getDataFim() == null){
            return ResponseEntity.badRequest().body("Sem explicação");
        }

        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            item.setDisponibilidade(disponibilidadeRepository.findById(1L).get());

            itemRepository.save(item);


            return ResponseEntity.ok(manutencaoRepository.save(manutencao));
        } else {
            return ResponseEntity.badRequest().body("Item não encontrado.");
        }

    }

    public ResponseEntity<?> mandarManutencao(Manutencao manutencao){
        Optional<Item> itemOpt = itemRepository.findById(manutencao.getItem().getId());
        if (manutencao.getDataEsperada() == null || manutencao.getDataMandado() == null || manutencao.getTecnico() == null || manutencao.getTecnico().isEmpty()) {
            return ResponseEntity.badRequest().body("Campo vazio");
        }

        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            item.setDisponibilidade(disponibilidadeRepository.findById(4L).get());

            itemRepository.save(item);
            manutencao.setEmAcao(true);
            return ResponseEntity.ok(manutencaoRepository.save(manutencao));
        } else {
            return ResponseEntity.badRequest().body("Item não encontrado.");
        }
    }
    //
    public ResponseEntity<?> editarManutencao( Manutencao manutencao, Long manutencaoId) {
        Optional<Manutencao> existingManutencao = manutencaoRepository.findById(manutencaoId);

        if (existingManutencao.isPresent()) {
            Manutencao updatedManutencao = existingManutencao.get();

            // Atualiza os campos permitidos
            if (manutencao.getDataInicio() != null) {
                updatedManutencao.setDataInicio(manutencao.getDataInicio());
            }

            if (manutencao.getDataFim() != null) {
                updatedManutencao.setDataFim(manutencao.getDataFim());
            }
            if (manutencao.getDataMandado() != null) {
                updatedManutencao.setDataMandado(manutencao.getDataMandado());
            }
            if (manutencao.getDataEsperada() != null) {
                updatedManutencao.setDataEsperada(manutencao.getDataEsperada());
            }
            if (manutencao.getDescricaoProblema() != null) {
                updatedManutencao.setDescricaoProblema(manutencao.getDescricaoProblema());
            }
            if (manutencao.getDescricaoSolucao() != null) {
                updatedManutencao.setDescricaoSolucao(manutencao.getDescricaoSolucao());
            }
            if (manutencao.getDataProximaManutencao() != null) {
                updatedManutencao.setDataProximaManutencao(manutencao.getDataProximaManutencao());
            }

            Manutencao savedManutencao = manutencaoRepository.save(updatedManutencao);
            return ResponseEntity.ok(savedManutencao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> pegarManutencao(Long manutencaoId){
        Optional<Manutencao> manutencao = manutencaoRepository.findById(manutencaoId);
        if(manutencao.isPresent()) {
            return ResponseEntity.ok(manutencao.get());
        } else {
            return ResponseEntity. notFound().build();
        }
    }

    public ResponseEntity<?> pegarItemPorManutencao(Long manutencaoId){
        Optional<Manutencao> manutencao = manutencaoRepository.findById(manutencaoId);
        if(manutencao.isPresent()) {
            return ResponseEntity.ok(manutencao.get().getItem());
        } else {
            return ResponseEntity. notFound().build();
        }
    }

    public ResponseEntity<?> pegarUltimaManutencao(Long itemId){
        Optional<Manutencao> manutencao = manutencaoRepository.findTopByItemIdOrderByDataInicioDesc(itemId);
        if(manutencao.isPresent()) {
            return ResponseEntity.ok(manutencao.get());
        } else {
            return ResponseEntity. notFound().build();
        }
    }

    public ResponseEntity<?> listarManutencaoPorItem(Long itemId){
        List<Manutencao> manutencoes = manutencaoRepository.findByItemId(itemId, Sort.by(Sort.Direction.DESC, "dataInicio"));
        if (manutencoes.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(manutencoes);
        }
    }


}