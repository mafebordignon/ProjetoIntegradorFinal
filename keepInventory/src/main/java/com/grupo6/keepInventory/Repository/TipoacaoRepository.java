package com.grupo6.keepInventory.Repository;

import com.grupo6.keepInventory.Model.Tipoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TipoacaoRepository extends JpaRepository<Tipoacao, Long>, PagingAndSortingRepository<Tipoacao,Long> {


    Optional<Tipoacao> findByNome(String nome);
}
