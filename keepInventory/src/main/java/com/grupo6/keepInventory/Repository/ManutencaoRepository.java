package com.grupo6.keepInventory.Repository;

import com.grupo6.keepInventory.Model.Manutencao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long>, PagingAndSortingRepository<Manutencao,Long> {
    List<Manutencao> findByItemId(long itemId, Sort sort);
    @Query("SELECT m FROM manutencao m WHERE m.item.id = :itemId ORDER BY m.dataInicio DESC LIMIT 1")
    Optional<Manutencao> findTopByItemIdOrderByDataInicioDesc(Long itemId);
}
