package com.grupo6.keepInventory.Repository;

import com.grupo6.keepInventory.Model.Item;
import com.grupo6.keepInventory.Model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, PagingAndSortingRepository<Usuario,Long> {
    @Query(value = "SELECT * FROM usuario WHERE email = ?1", nativeQuery = true)
    Optional<Usuario> findByEmail(String Email);
//    @Modifying
//    @Query("UPDATE Usuario u SET u.nome = :newNome, u.sobrenome = :newSobrenome, u.numeroDeCadastro = :newNumeroDeCadastro WHERE u.id = :clientId")
//    void updateUsuarioCommon(@Param("clientId") Long clientId, @Param("newNome") String newNome, @Param("newSobrenome") String newSobrenome,  @Param("newNumeroDeCadastro") String newNumeroDeCadastro);
//
    @Transactional
    @Modifying
    @Query(value = "UPDATE usuario u SET u.senha = :newSenha WHERE u.id = :usuarioId", nativeQuery = true)
    void updateSenha(@Param("usuarioId") Long usuarioId, @Param("newSenha") String newSenha);
//
//    @Modifying
//    @Query("UPDATE Usuario u SET u.email = :newEmail WHERE u.id = :usuarioId")
//    void updateEmail(@Param("usuarioId") Long usuarioId, @Param("newEmail") String newEmail);

}
