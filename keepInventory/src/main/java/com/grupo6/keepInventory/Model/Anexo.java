package com.grupo6.keepInventory.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Anexo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = true)
    private String descricao;
    @Column(nullable = false)
    private String caminho;
    @ManyToMany
    @JoinTable(name = "anexo_item",
            joinColumns = @JoinColumn(name = "anexo_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> itens = new ArrayList<>();




    @ManyToMany
    @JoinTable(name = "anexo_manutencao",
            joinColumns = @JoinColumn(name = "anexo_id"),
            inverseJoinColumns = @JoinColumn(name = "manutencao_id"))
    private List<Manutencao> manutencoes = new ArrayList<>();

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anexo anexo = (Anexo) o;
        return id == anexo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
