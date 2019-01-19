package br.com.jns.financeiro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.com.jns.financeiro.domain.enumeration.Moeda;

/**
 * A FormaPagamento.
 */
@Entity
@Table(name = "forma_pagamento")
@Document(indexName = "formapagamento")
public class FormaPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "moeda")
    private Moeda moeda;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "observacao")
    private String observacao;

    @OneToMany(mappedBy = "formaPagamento")
    private Set<Parcela> parcelas = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public FormaPagamento moeda(Moeda moeda) {
        this.moeda = moeda;
        return this;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public String getDescricao() {
        return descricao;
    }

    public FormaPagamento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public FormaPagamento observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Set<Parcela> getParcelas() {
        return parcelas;
    }

    public FormaPagamento parcelas(Set<Parcela> parcelas) {
        this.parcelas = parcelas;
        return this;
    }

    public FormaPagamento addParcela(Parcela parcela) {
        this.parcelas.add(parcela);
        parcela.setFormaPagamento(this);
        return this;
    }

    public FormaPagamento removeParcela(Parcela parcela) {
        this.parcelas.remove(parcela);
        parcela.setFormaPagamento(null);
        return this;
    }

    public void setParcelas(Set<Parcela> parcelas) {
        this.parcelas = parcelas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FormaPagamento formaPagamento = (FormaPagamento) o;
        if (formaPagamento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formaPagamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormaPagamento{" +
            "id=" + getId() +
            ", moeda='" + getMoeda() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
