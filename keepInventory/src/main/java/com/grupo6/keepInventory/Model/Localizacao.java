package com.grupo6.keepInventory.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Localizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nome;

    public Localizacao() {
    }

    public Localizacao( String nome, Boolean ativo) {
        this.nome = nome;
        this.ativo = ativo;
    }

    @Column(nullable = false)
    private Boolean ativo = true;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localizacao that = (Localizacao) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
