package br.com.jns.financeiro.web.rest;

import br.com.jns.financeiro.FinanceiroApp;

import br.com.jns.financeiro.domain.FormaPagamento;
import br.com.jns.financeiro.repository.FormaPagamentoRepository;
import br.com.jns.financeiro.repository.search.FormaPagamentoSearchRepository;
import br.com.jns.financeiro.service.FormaPagamentoService;
import br.com.jns.financeiro.service.dto.FormaPagamentoDTO;
import br.com.jns.financeiro.service.mapper.FormaPagamentoMapper;
import br.com.jns.financeiro.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static br.com.jns.financeiro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jns.financeiro.domain.enumeration.Moeda;
/**
 * Test class for the FormaPagamentoResource REST controller.
 *
 * @see FormaPagamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinanceiroApp.class)
public class FormaPagamentoResourceIntTest {

    private static final Moeda DEFAULT_MOEDA = Moeda.DINHEIRO;
    private static final Moeda UPDATED_MOEDA = Moeda.CARTAO;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoMapper formaPagamentoMapper;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    /**
     * This repository is mocked in the br.com.jns.financeiro.repository.search test package.
     *
     * @see br.com.jns.financeiro.repository.search.FormaPagamentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private FormaPagamentoSearchRepository mockFormaPagamentoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFormaPagamentoMockMvc;

    private FormaPagamento formaPagamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormaPagamentoResource formaPagamentoResource = new FormaPagamentoResource(formaPagamentoService);
        this.restFormaPagamentoMockMvc = MockMvcBuilders.standaloneSetup(formaPagamentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaPagamento createEntity(EntityManager em) {
        FormaPagamento formaPagamento = new FormaPagamento()
            .moeda(DEFAULT_MOEDA)
            .descricao(DEFAULT_DESCRICAO)
            .observacao(DEFAULT_OBSERVACAO);
        return formaPagamento;
    }

    @Before
    public void initTest() {
        formaPagamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormaPagamento() throws Exception {
        int databaseSizeBeforeCreate = formaPagamentoRepository.findAll().size();

        // Create the FormaPagamento
        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoMapper.toDto(formaPagamento);
        restFormaPagamentoMockMvc.perform(post("/api/forma-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaPagamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the FormaPagamento in the database
        List<FormaPagamento> formaPagamentoList = formaPagamentoRepository.findAll();
        assertThat(formaPagamentoList).hasSize(databaseSizeBeforeCreate + 1);
        FormaPagamento testFormaPagamento = formaPagamentoList.get(formaPagamentoList.size() - 1);
        assertThat(testFormaPagamento.getMoeda()).isEqualTo(DEFAULT_MOEDA);
        assertThat(testFormaPagamento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testFormaPagamento.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);

        // Validate the FormaPagamento in Elasticsearch
        verify(mockFormaPagamentoSearchRepository, times(1)).save(testFormaPagamento);
    }

    @Test
    @Transactional
    public void createFormaPagamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formaPagamentoRepository.findAll().size();

        // Create the FormaPagamento with an existing ID
        formaPagamento.setId(1L);
        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoMapper.toDto(formaPagamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormaPagamentoMockMvc.perform(post("/api/forma-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaPagamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormaPagamento in the database
        List<FormaPagamento> formaPagamentoList = formaPagamentoRepository.findAll();
        assertThat(formaPagamentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the FormaPagamento in Elasticsearch
        verify(mockFormaPagamentoSearchRepository, times(0)).save(formaPagamento);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = formaPagamentoRepository.findAll().size();
        // set the field null
        formaPagamento.setDescricao(null);

        // Create the FormaPagamento, which fails.
        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoMapper.toDto(formaPagamento);

        restFormaPagamentoMockMvc.perform(post("/api/forma-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaPagamentoDTO)))
            .andExpect(status().isBadRequest());

        List<FormaPagamento> formaPagamentoList = formaPagamentoRepository.findAll();
        assertThat(formaPagamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormaPagamentos() throws Exception {
        // Initialize the database
        formaPagamentoRepository.saveAndFlush(formaPagamento);

        // Get all the formaPagamentoList
        restFormaPagamentoMockMvc.perform(get("/api/forma-pagamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].moeda").value(hasItem(DEFAULT_MOEDA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getFormaPagamento() throws Exception {
        // Initialize the database
        formaPagamentoRepository.saveAndFlush(formaPagamento);

        // Get the formaPagamento
        restFormaPagamentoMockMvc.perform(get("/api/forma-pagamentos/{id}", formaPagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formaPagamento.getId().intValue()))
            .andExpect(jsonPath("$.moeda").value(DEFAULT_MOEDA.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormaPagamento() throws Exception {
        // Get the formaPagamento
        restFormaPagamentoMockMvc.perform(get("/api/forma-pagamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormaPagamento() throws Exception {
        // Initialize the database
        formaPagamentoRepository.saveAndFlush(formaPagamento);

        int databaseSizeBeforeUpdate = formaPagamentoRepository.findAll().size();

        // Update the formaPagamento
        FormaPagamento updatedFormaPagamento = formaPagamentoRepository.findById(formaPagamento.getId()).get();
        // Disconnect from session so that the updates on updatedFormaPagamento are not directly saved in db
        em.detach(updatedFormaPagamento);
        updatedFormaPagamento
            .moeda(UPDATED_MOEDA)
            .descricao(UPDATED_DESCRICAO)
            .observacao(UPDATED_OBSERVACAO);
        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoMapper.toDto(updatedFormaPagamento);

        restFormaPagamentoMockMvc.perform(put("/api/forma-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaPagamentoDTO)))
            .andExpect(status().isOk());

        // Validate the FormaPagamento in the database
        List<FormaPagamento> formaPagamentoList = formaPagamentoRepository.findAll();
        assertThat(formaPagamentoList).hasSize(databaseSizeBeforeUpdate);
        FormaPagamento testFormaPagamento = formaPagamentoList.get(formaPagamentoList.size() - 1);
        assertThat(testFormaPagamento.getMoeda()).isEqualTo(UPDATED_MOEDA);
        assertThat(testFormaPagamento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testFormaPagamento.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);

        // Validate the FormaPagamento in Elasticsearch
        verify(mockFormaPagamentoSearchRepository, times(1)).save(testFormaPagamento);
    }

    @Test
    @Transactional
    public void updateNonExistingFormaPagamento() throws Exception {
        int databaseSizeBeforeUpdate = formaPagamentoRepository.findAll().size();

        // Create the FormaPagamento
        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoMapper.toDto(formaPagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormaPagamentoMockMvc.perform(put("/api/forma-pagamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaPagamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormaPagamento in the database
        List<FormaPagamento> formaPagamentoList = formaPagamentoRepository.findAll();
        assertThat(formaPagamentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FormaPagamento in Elasticsearch
        verify(mockFormaPagamentoSearchRepository, times(0)).save(formaPagamento);
    }

    @Test
    @Transactional
    public void deleteFormaPagamento() throws Exception {
        // Initialize the database
        formaPagamentoRepository.saveAndFlush(formaPagamento);

        int databaseSizeBeforeDelete = formaPagamentoRepository.findAll().size();

        // Get the formaPagamento
        restFormaPagamentoMockMvc.perform(delete("/api/forma-pagamentos/{id}", formaPagamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FormaPagamento> formaPagamentoList = formaPagamentoRepository.findAll();
        assertThat(formaPagamentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FormaPagamento in Elasticsearch
        verify(mockFormaPagamentoSearchRepository, times(1)).deleteById(formaPagamento.getId());
    }

    @Test
    @Transactional
    public void searchFormaPagamento() throws Exception {
        // Initialize the database
        formaPagamentoRepository.saveAndFlush(formaPagamento);
        when(mockFormaPagamentoSearchRepository.search(queryStringQuery("id:" + formaPagamento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(formaPagamento), PageRequest.of(0, 1), 1));
        // Search the formaPagamento
        restFormaPagamentoMockMvc.perform(get("/api/_search/forma-pagamentos?query=id:" + formaPagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].moeda").value(hasItem(DEFAULT_MOEDA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormaPagamento.class);
        FormaPagamento formaPagamento1 = new FormaPagamento();
        formaPagamento1.setId(1L);
        FormaPagamento formaPagamento2 = new FormaPagamento();
        formaPagamento2.setId(formaPagamento1.getId());
        assertThat(formaPagamento1).isEqualTo(formaPagamento2);
        formaPagamento2.setId(2L);
        assertThat(formaPagamento1).isNotEqualTo(formaPagamento2);
        formaPagamento1.setId(null);
        assertThat(formaPagamento1).isNotEqualTo(formaPagamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormaPagamentoDTO.class);
        FormaPagamentoDTO formaPagamentoDTO1 = new FormaPagamentoDTO();
        formaPagamentoDTO1.setId(1L);
        FormaPagamentoDTO formaPagamentoDTO2 = new FormaPagamentoDTO();
        assertThat(formaPagamentoDTO1).isNotEqualTo(formaPagamentoDTO2);
        formaPagamentoDTO2.setId(formaPagamentoDTO1.getId());
        assertThat(formaPagamentoDTO1).isEqualTo(formaPagamentoDTO2);
        formaPagamentoDTO2.setId(2L);
        assertThat(formaPagamentoDTO1).isNotEqualTo(formaPagamentoDTO2);
        formaPagamentoDTO1.setId(null);
        assertThat(formaPagamentoDTO1).isNotEqualTo(formaPagamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(formaPagamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(formaPagamentoMapper.fromId(null)).isNull();
    }
}
