package com.grupo6.keepInventory.Repository;

import com.grupo6.keepInventory.Model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MarcaRepository  extends JpaRepository<Marca, Long>, PagingAndSortingRepository<Marca,Long> {
    Boolean existsByNome(String nome);

    Optional<Marca> findByNome(String nome);
}
