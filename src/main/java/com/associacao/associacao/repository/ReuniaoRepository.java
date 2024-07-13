package com.associacao.associacao.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.associacao.associacao.model.Associacao;
import com.associacao.associacao.model.Reuniao;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Long>{

    boolean existsByDataAndAssociacao(LocalDate data, Associacao associacao);

    List<Reuniao> findByAssociacaoAndDataBetween(Associacao byNumero, LocalDate inicio, LocalDate fim);

    
}
