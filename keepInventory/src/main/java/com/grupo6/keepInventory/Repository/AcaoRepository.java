package com.grupo6.keepInventory.Repository;

import com.grupo6.keepInventory.Model.Acao;
import com.grupo6.keepInventory.Model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AcaoRepository extends JpaRepository<Acao, Long>, PagingAndSortingRepository<Acao,Long> {
    @Query(value = "SELECT a FROM Acao a where a.item = :idItem", nativeQuery = true)
    Page<Acao> findByItem(Long idItem, Pageable pageable);

    List<Acao> findByItemId(Long id);
}
