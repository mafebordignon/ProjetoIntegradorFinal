package com.grupo6.keepInventory.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nome;

    public Estado(String nome) {
        this.nome = nome;
    }

    public Estado() {
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

}
