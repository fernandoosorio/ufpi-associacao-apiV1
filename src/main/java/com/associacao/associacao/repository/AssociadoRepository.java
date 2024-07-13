package com.associacao.associacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.associacao.associacao.model.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, Long>{

    boolean existsByNumero(int numero);

    boolean existsByCpf(String cpf);

    Associado findByNumero(int numAssociado);

    boolean existsByNumeroAndRemido(int numAssociado, boolean remido );
    
}
