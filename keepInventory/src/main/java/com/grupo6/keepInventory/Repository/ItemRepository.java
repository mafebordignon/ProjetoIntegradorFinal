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
    @Query(value = "SELECT * FROM Item WHERE Estado_id = ?1", nativeQuery = true)
    Page<Item> findByEstado( Long idEstado, Pageable pageable);
    @Query(value = "SELECT * FROM Item WHERE categoria_id = ?1", nativeQuery = true)
    Page<Item> findByCategoria(Long idCategoria, Pageable pageable);
    @Query(value = "SELECT * FROM Item WHERE Disponibilidade_id = ?1", nativeQuery = true)
    Page<Item> findByDisponibilidade(Long idDisponibilidade, Pageable pageable);
    @Query(value = "SELECT * FROM Item WHERE Localizacao_id = ?1", nativeQuery = true)
    Page<Item> findByLocalizacao(Long idLocalizacao, Pageable pageable);
    @Query(value = "SELECT * FROM Item WHERE descricao like ?1", nativeQuery = true)
    Page<Item> findByDescricao(String descricao, Pageable pageable);
    @Query(value = "SELECT * FROM Item WHERE descricao like ?1", nativeQuery = true)
    Page<Item> findByNome(String nome, Pageable pageable);

    @Query(value = "SELECT i.* FROM Item i " + "JOIN Modelo mo ON mo.id = i.modelo_id " + "JOIN Marca ma ON ma.id = mo.marca_id " + "WHERE ma.nome = ?1", nativeQuery = true)
    Page<Item> findByMarca(String marca, Pageable pageable);
}
