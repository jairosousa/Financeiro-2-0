package br.com.jns.financeiro.service.impl;

import br.com.jns.financeiro.service.FormaPagamentoService;
import br.com.jns.financeiro.domain.FormaPagamento;
import br.com.jns.financeiro.repository.FormaPagamentoRepository;
import br.com.jns.financeiro.repository.search.FormaPagamentoSearchRepository;
import br.com.jns.financeiro.service.dto.FormaPagamentoDTO;
import br.com.jns.financeiro.service.mapper.FormaPagamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FormaPagamento.
 */
@Service
@Transactional
public class FormaPagamentoServiceImpl implements FormaPagamentoService {

    private final Logger log = LoggerFactory.getLogger(FormaPagamentoServiceImpl.class);

    private final FormaPagamentoRepository formaPagamentoRepository;

    private final FormaPagamentoMapper formaPagamentoMapper;

    private final FormaPagamentoSearchRepository formaPagamentoSearchRepository;

    public FormaPagamentoServiceImpl(FormaPagamentoRepository formaPagamentoRepository, FormaPagamentoMapper formaPagamentoMapper, FormaPagamentoSearchRepository formaPagamentoSearchRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
        this.formaPagamentoMapper = formaPagamentoMapper;
        this.formaPagamentoSearchRepository = formaPagamentoSearchRepository;
    }

    /**
     * Save a formaPagamento.
     *
     * @param formaPagamentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FormaPagamentoDTO save(FormaPagamentoDTO formaPagamentoDTO) {
        log.debug("Request to save FormaPagamento : {}", formaPagamentoDTO);

        FormaPagamento formaPagamento = formaPagamentoMapper.toEntity(formaPagamentoDTO);
        formaPagamento = formaPagamentoRepository.save(formaPagamento);
        FormaPagamentoDTO result = formaPagamentoMapper.toDto(formaPagamento);
        formaPagamentoSearchRepository.save(formaPagamento);
        return result;
    }

    /**
     * Get all the formaPagamentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormaPagamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FormaPagamentos");
        return formaPagamentoRepository.findAll(pageable)
            .map(formaPagamentoMapper::toDto);
    }


    /**
     * Get one formaPagamento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FormaPagamentoDTO> findOne(Long id) {
        log.debug("Request to get FormaPagamento : {}", id);
        return formaPagamentoRepository.findById(id)
            .map(formaPagamentoMapper::toDto);
    }

    /**
     * Delete the formaPagamento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormaPagamento : {}", id);
        formaPagamentoRepository.deleteById(id);
        formaPagamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the formaPagamento corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormaPagamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FormaPagamentos for query {}", query);
        return formaPagamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(formaPagamentoMapper::toDto);
    }
}
