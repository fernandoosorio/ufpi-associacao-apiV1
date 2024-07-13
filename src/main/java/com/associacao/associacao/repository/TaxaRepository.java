package com.associacao.associacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.associacao.associacao.model.Associacao;
import com.associacao.associacao.model.Taxa;

public interface TaxaRepository extends JpaRepository<Taxa, Long>{

    boolean existsByAssociacaoAndNomeAndVigencia(Associacao associacao, String nome, int vigencia);

    boolean existsByAssociacaoAndVigencia(Associacao associacao, int vigencia);

    Taxa findByAssociacaoAndNomeAndVigencia(Associacao byNumero, String taxa, int vigencia);

    List<Taxa> findByAssociacaoAndVigencia(Associacao associacao, int vigencia);
    
}
