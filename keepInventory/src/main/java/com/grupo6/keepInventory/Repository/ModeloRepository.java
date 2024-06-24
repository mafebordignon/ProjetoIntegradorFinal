package com.grupo6.keepInventory.Repository;

import com.grupo6.keepInventory.Model.Localizacao;
import com.grupo6.keepInventory.Model.Marca;
import com.grupo6.keepInventory.Model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface ModeloRepository  extends JpaRepository<Modelo, Long>, PagingAndSortingRepository<Modelo,Long> {
    Boolean existsByNome(String nome);

    Optional<Modelo> findByNome(String nome);

    boolean existsByNomeAndMarca(String nome, Marca marca);

    Optional<Modelo> findByNomeAndMarca(String modeloNome, Marca marca);
    List<Modelo> findByMarca(Optional<Marca> marca);

}