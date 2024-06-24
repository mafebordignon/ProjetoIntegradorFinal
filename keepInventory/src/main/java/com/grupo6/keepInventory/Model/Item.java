package com.grupo6.keepInventory.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String descricao;
    @Column(nullable = true)
    private String numeroNotaFiscal;
    @Column(nullable = true)
    private String numeroSerie;
    @Column(nullable = true)
    private int potencia;
    @Column(nullable = false)
    private LocalDateTime dataEntrada;
    @Column(nullable = false)
    private LocalDateTime dataNotaFiscal;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "disponibilidade_id")
    private Disponibilidade disponibilidade;
    @ManyToOne
    @JoinColumn(name = "localizacao_id")
    private Localizacao localizacao;

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    @ManyToMany(mappedBy = "itens")
    private List<Anexo> anexos = new ArrayList<>();

    public Item() {
    }

    public Item(String descricao, String numeroNotaFiscal, String numeroSerie, int potencia, LocalDateTime dataEntrada, LocalDateTime dataNotaFiscal, Categoria categoria, Estado estado, Disponibilidade disponibilidade, Localizacao localizacao, Modelo modelo) {
        this.descricao = descricao;
        this.numeroNotaFiscal = numeroNotaFiscal;
        this.numeroSerie = numeroSerie;
        this.potencia = potencia;
        this.dataEntrada = dataEntrada;
        this.dataNotaFiscal = dataNotaFiscal;
        this.categoria = categoria;
        this.estado = estado;
        this.disponibilidade = disponibilidade;
        this.localizacao = localizacao;
        this.modelo = modelo;
    }

    public LocalDateTime getDataNotaFiscal() {
        return dataNotaFiscal;
    }

    public void setDataNotaFiscal(LocalDateTime dataNotaFiscal) {
        this.dataNotaFiscal = dataNotaFiscal;
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

    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Disponibilidade getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Disponibilidade disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }



    public List<Anexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Anexo> anexos) {
        this.anexos = anexos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
