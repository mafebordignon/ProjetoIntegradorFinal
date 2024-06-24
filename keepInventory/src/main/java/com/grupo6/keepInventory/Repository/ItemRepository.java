package com.grupo6.keepInventory.Repository;
import com.grupo6.keepInventory.Model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, PagingAndSortingRepository<Item,Long> {
    @Query("SELECT i FROM Item i WHERE i.estado = :idEstado")
    Page<Item> findByEstado( Long idEstado, Pageable pageable);
    @Query("SELECT i FROM Item i WHERE i.categoria = :idCategoria")
    Page<Item> findByCategoria(Long idCategoria, Pageable pageable);
    @Query("SELECT i FROM Item i WHERE i.disponibilidade = :idDisponibilidade")
    Page<Item> findByDisponibilidade(Long idDisponibilidade, Pageable pageable);
    @Query("SELECT i FROM Item i WHERE i.localizacao = :idLocalizacao")
    Page<Item> findByLocalizacao(Long idLocalizacao, Pageable pageable);
    @Query("SELECT i FROM Item i WHERE i.descricao like :descricao")
    Page<Item> findByDescricao(String descricao, Pageable pageable);
    @Query("SELECT i FROM Item i WHERE i.descricao like :nome")
    Page<Item> findByNome(String nome, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.modelo.marca.nome = :marcanome")
    Page<Item> findByMarca(String marcanome, Pageable pageable);
}
