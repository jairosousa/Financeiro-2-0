package br.com.jns.financeiro.service;

import br.com.jns.financeiro.service.dto.FormaPagamentoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing FormaPagamento.
 */
public interface FormaPagamentoService {

    /**
     * Save a formaPagamento.
     *
     * @param formaPagamentoDTO the entity to save
     * @return the persisted entity
     */
    FormaPagamentoDTO save(FormaPagamentoDTO formaPagamentoDTO);

    /**
     * Get all the formaPagamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FormaPagamentoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" formaPagamento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FormaPagamentoDTO> findOne(Long id);

    /**
     * Delete the "id" formaPagamento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the formaPagamento corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FormaPagamentoDTO> search(String query, Pageable pageable);
}
