package com.grupo6.keepInventory.RepositoryTest;

import com.grupo6.keepInventory.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>, PagingAndSortingRepository<Categoria, Long> {

//    Teste 5
    Boolean existsByNome(String nome);

    Optional<Categoria> findByNome(String nome);
}
