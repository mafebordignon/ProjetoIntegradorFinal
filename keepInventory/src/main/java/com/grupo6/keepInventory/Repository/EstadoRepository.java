package com.grupo6.keepInventory.Repository;
import com.grupo6.keepInventory.Model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Long> , PagingAndSortingRepository<Estado,Long> {
    Optional<Estado> findByNome(String nome);
}
