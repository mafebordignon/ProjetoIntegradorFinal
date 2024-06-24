    package com.grupo6.keepInventory.Model;

    import jakarta.persistence.*;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;

    @Entity
    public class Manutencao {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Column(nullable = false)
        private LocalDateTime dataInicio;
        @Column(nullable = true)
        private LocalDateTime dataMandado;
        @Column(nullable = true)
        private LocalDateTime dataEsperada;
        @Column(nullable = true)
        private LocalDateTime dataFim;
        @Column(nullable = false)
        private String descricaoProblema;
        @Column(nullable = true)
        private String tecnico;
        @Column(nullable = true)
        private String descricaoSolucao;
        @Column(nullable = true)
        private LocalDateTime dataProximaManutencao;
        @Column(nullable = true)
        private Boolean emAcao = false;

        @ManyToOne
        @JoinColumn(name = "item_id", nullable = false)
        private Item item;
        @ManyToMany(mappedBy = "manutencoes")
        private List<Anexo> anexos = new ArrayList<>();

        public Manutencao() {
        }



        public Manutencao(LocalDateTime dataInicio, String descricaoProblema, Item item, List<Anexo> anexos, LocalDateTime dataFim) {
            this.dataInicio = dataInicio;
            this.descricaoProblema = descricaoProblema;
            this.item = item;
            this.anexos = anexos;
            this.dataFim = dataFim;

        }

        public Manutencao(LocalDateTime dataInicio, String descricaoProblema, Item item, List<Anexo> anexos) {
            this.dataInicio = dataInicio;
            this.descricaoProblema = descricaoProblema;
            this.item = item;
            this.anexos = anexos;


        }

        public Manutencao(LocalDateTime dataInicio, LocalDateTime dataMandado, LocalDateTime dataEsperada, LocalDateTime dataFim, String descricaoProblema, String tecnico, String descricaoSolucao, LocalDateTime dataProximaManutencao, Item item) {
            this.dataInicio = dataInicio;
            this.dataMandado = dataMandado;
            this.dataEsperada = dataEsperada;
            this.dataFim = dataFim;
            this.descricaoProblema = descricaoProblema;
            this.tecnico = tecnico;
            this.descricaoSolucao = descricaoSolucao;
            this.dataProximaManutencao = dataProximaManutencao;
            this.item = item;
        }

        public LocalDateTime getDataMandado() {
            return dataMandado;
        }

        public void setDataMandado(LocalDateTime dataMandado) {
            this.dataMandado = dataMandado;
        }

        public LocalDateTime getDataEsperada() {
            return dataEsperada;
        }

        public void setDataEsperada(LocalDateTime dataEsperada) {
            this.dataEsperada = dataEsperada;
        }

        public String getTecnico() {
            return tecnico;
        }

        public void setTecnico(String tecnico) {
            this.tecnico = tecnico;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public LocalDateTime getDataInicio() {
            return dataInicio;
        }

        public void setDataInicio(LocalDateTime dataInicio) {
            this.dataInicio = dataInicio;
        }

        public LocalDateTime getDataFim() {
            return dataFim;
        }

        public void setDataFim(LocalDateTime dataFim) {
            this.dataFim = dataFim;
        }

        public String getDescricaoProblema() {
            return descricaoProblema;
        }

        public void setDescricaoProblema(String descricaoProblema) {
            this.descricaoProblema = descricaoProblema;
        }

        public String getDescricaoSolucao() {
            return descricaoSolucao;
        }

        public void setDescricaoSolucao(String descricaoSolucao) {
            this.descricaoSolucao = descricaoSolucao;
        }

        public LocalDateTime getDataProximaManutencao() {
            return dataProximaManutencao;
        }

        public void setDataProximaManutencao(LocalDateTime dataProximaManutencao) {
            this.dataProximaManutencao = dataProximaManutencao;
        }

        public Boolean getEmAcao() {
            return emAcao;
        }

        public void setEmAcao(Boolean emAcao) {
            this.emAcao = emAcao;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public List<Anexo> getAnexos() {
            return anexos;
        }

        public void setAnexos(List<Anexo> anexos) {
            this.anexos = anexos;
        }
    }
