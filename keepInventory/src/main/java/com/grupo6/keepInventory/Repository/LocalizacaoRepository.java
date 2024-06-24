package com.grupo6.keepInventory.Repository;

import com.grupo6.keepInventory.Model.Categoria;
import com.grupo6.keepInventory.Model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long>, PagingAndSortingRepository<Localizacao,Long> {
    Boolean existsByNome(String nome);

    Optional<Localizacao> findByNome(String nome);
}

