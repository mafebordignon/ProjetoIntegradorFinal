package com.grupo6.keepInventory.Model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Tipoacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nome;

    public Tipoacao() {
    }

    public Tipoacao(String nome) {
        this.nome = nome;
    }

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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tipoacao tipoAcao = (Tipoacao) o;
        return id == tipoAcao.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
