package br.com.jns.financeiro.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.jns.financeiro.domain.enumeration.Moeda;

/**
 * A DTO for the FormaPagamento entity.
 */
public class FormaPagamentoDTO implements Serializable {

    private Long id;

    private Moeda moeda;

    @NotNull
    private String descricao;

    private String observacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormaPagamentoDTO formaPagamentoDTO = (FormaPagamentoDTO) o;
        if (formaPagamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formaPagamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormaPagamentoDTO{" +
            "id=" + getId() +
            ", moeda='" + getMoeda() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
