package com.grupo6.keepInventory.Repository;
import com.grupo6.keepInventory.Model.Disponibilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> , PagingAndSortingRepository<Disponibilidade,Long> {
    Optional<Disponibilidade> findByNome(String nome);
}
