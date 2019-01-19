package br.com.jns.financeiro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.jns.financeiro.service.FormaPagamentoService;
import br.com.jns.financeiro.web.rest.errors.BadRequestAlertException;
import br.com.jns.financeiro.web.rest.util.HeaderUtil;
import br.com.jns.financeiro.web.rest.util.PaginationUtil;
import br.com.jns.financeiro.service.dto.FormaPagamentoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing FormaPagamento.
 */
@RestController
@RequestMapping("/api")
public class FormaPagamentoResource {

    private final Logger log = LoggerFactory.getLogger(FormaPagamentoResource.class);

    private static final String ENTITY_NAME = "formaPagamento";

    private final FormaPagamentoService formaPagamentoService;

    public FormaPagamentoResource(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    /**
     * POST  /forma-pagamentos : Create a new formaPagamento.
     *
     * @param formaPagamentoDTO the formaPagamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formaPagamentoDTO, or with status 400 (Bad Request) if the formaPagamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forma-pagamentos")
    @Timed
    public ResponseEntity<FormaPagamentoDTO> createFormaPagamento(@Valid @RequestBody FormaPagamentoDTO formaPagamentoDTO) throws URISyntaxException {
        log.debug("REST request to save FormaPagamento : {}", formaPagamentoDTO);
        if (formaPagamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new formaPagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormaPagamentoDTO result = formaPagamentoService.save(formaPagamentoDTO);
        return ResponseEntity.created(new URI("/api/forma-pagamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forma-pagamentos : Updates an existing formaPagamento.
     *
     * @param formaPagamentoDTO the formaPagamentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formaPagamentoDTO,
     * or with status 400 (Bad Request) if the formaPagamentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the formaPagamentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forma-pagamentos")
    @Timed
    public ResponseEntity<FormaPagamentoDTO> updateFormaPagamento(@Valid @RequestBody FormaPagamentoDTO formaPagamentoDTO) throws URISyntaxException {
        log.debug("REST request to update FormaPagamento : {}", formaPagamentoDTO);
        if (formaPagamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormaPagamentoDTO result = formaPagamentoService.save(formaPagamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formaPagamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forma-pagamentos : get all the formaPagamentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of formaPagamentos in body
     */
    @GetMapping("/forma-pagamentos")
    @Timed
    public ResponseEntity<List<FormaPagamentoDTO>> getAllFormaPagamentos(Pageable pageable) {
        log.debug("REST request to get a page of FormaPagamentos");
        Page<FormaPagamentoDTO> page = formaPagamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/forma-pagamentos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /forma-pagamentos/:id : get the "id" formaPagamento.
     *
     * @param id the id of the formaPagamentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formaPagamentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/forma-pagamentos/{id}")
    @Timed
    public ResponseEntity<FormaPagamentoDTO> getFormaPagamento(@PathVariable Long id) {
        log.debug("REST request to get FormaPagamento : {}", id);
        Optional<FormaPagamentoDTO> formaPagamentoDTO = formaPagamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formaPagamentoDTO);
    }

    /**
     * DELETE  /forma-pagamentos/:id : delete the "id" formaPagamento.
     *
     * @param id the id of the formaPagamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forma-pagamentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormaPagamento(@PathVariable Long id) {
        log.debug("REST request to delete FormaPagamento : {}", id);
        formaPagamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/forma-pagamentos?query=:query : search for the formaPagamento corresponding
     * to the query.
     *
     * @param query the query of the formaPagamento search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/forma-pagamentos")
    @Timed
    public ResponseEntity<List<FormaPagamentoDTO>> searchFormaPagamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FormaPagamentos for query {}", query);
        Page<FormaPagamentoDTO> page = formaPagamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/forma-pagamentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
