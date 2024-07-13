package com.associacao.associacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.associacao.associacao.model.Associacao;

public interface AssociacaoRepository extends JpaRepository<Associacao, Long>{

    boolean existsByNumero(int numero);

    boolean existsByNome(String nome);

    Associacao findByNumero(int numeroAssociacao);
    
}
