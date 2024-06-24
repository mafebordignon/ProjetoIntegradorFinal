package com.grupo6.keepInventory.Model;

import jakarta.persistence.Entity;

import javax.swing.text.StyledEditorKit;

@Entity
public class Categoria {
    private Long id;
    private String nome;
    private Boolean ativo = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
// teste 3
// private Boolean ativo = true;