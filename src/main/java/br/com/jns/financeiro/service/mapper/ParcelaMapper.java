package br.com.jns.financeiro.service.mapper;

import br.com.jns.financeiro.domain.*;
import br.com.jns.financeiro.service.dto.ParcelaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Parcela and its DTO ParcelaDTO.
 */
@Mapper(componentModel = "spring", uses = {LancamentoMapper.class, FormaPagamentoMapper.class})
public interface ParcelaMapper extends EntityMapper<ParcelaDTO, Parcela> {

    @Mapping(source = "lancamento.id", target = "lancamentoId")
    @Mapping(source = "formaPagamento.id", target = "formaPagamentoId")
    @Mapping(source = "formaPagamento.moeda", target = "formaPagamentoMoeda")
    ParcelaDTO toDto(Parcela parcela);

    @Mapping(source = "lancamentoId", target = "lancamento")
    @Mapping(source = "formaPagamentoId", target = "formaPagamento")
    Parcela toEntity(ParcelaDTO parcelaDTO);

    default Parcela fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parcela parcela = new Parcela();
        parcela.setId(id);
        return parcela;
    }
}
