package br.com.jns.financeiro.repository;

import br.com.jns.financeiro.domain.FormaPagamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FormaPagamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

}
