package com.associacao.associacao.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.associacao.associacao.model.Associacao;
import com.associacao.associacao.model.Associado;
import com.associacao.associacao.model.Pagamento;
import com.associacao.associacao.model.Taxa;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

    List <Pagamento> findAllByAssociacaoAndAssociadoAndTaxaAndDataBetween(
        Associacao associacao,
        Associado associado,
        Taxa taxa,
        LocalDate inicio, LocalDate fim) ;
    
}
