package br.com.jns.financeiro.repository.search;

import br.com.jns.financeiro.domain.FormaPagamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FormaPagamento entity.
 */
public interface FormaPagamentoSearchRepository extends ElasticsearchRepository<FormaPagamento, Long> {
}
