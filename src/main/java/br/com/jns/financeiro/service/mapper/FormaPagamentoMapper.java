package br.com.jns.financeiro.service.mapper;

import br.com.jns.financeiro.domain.*;
import br.com.jns.financeiro.service.dto.FormaPagamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FormaPagamento and its DTO FormaPagamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FormaPagamentoMapper extends EntityMapper<FormaPagamentoDTO, FormaPagamento> {


    @Mapping(target = "parcelas", ignore = true)
    FormaPagamento toEntity(FormaPagamentoDTO formaPagamentoDTO);

    default FormaPagamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(id);
        return formaPagamento;
    }
}
