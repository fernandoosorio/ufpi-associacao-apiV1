package com.associacao.associacao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.associacao.associacao.exception.AssociacaoNaoExistente;
import com.associacao.associacao.exception.AssociadoJaExistente;
import com.associacao.associacao.exception.ValorInvalido;
import com.associacao.associacao.model.Associado;
import com.associacao.associacao.repository.AssociacaoRepository;
import com.associacao.associacao.repository.AssociadoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AssociadoService {
    private AssociadoRepository associadoRepository;
    private AssociacaoRepository associacaoRepository;

    public Associado criarAssociado( int numeroAssociacao, Associado associado) 
            throws AssociacaoNaoExistente, AssociadoJaExistente, ValorInvalido{
        if (!associacaoRepository.existsByNumero(numeroAssociacao)) {
            throw new AssociacaoNaoExistente();
        }

        if (associadoRepository.existsByNumero(associado.getNumero())) {
            throw new AssociadoJaExistente();
        }

        if (associadoRepository.existsByCpf(associado.getCpf())) {
            throw new AssociadoJaExistente();
        }

        associado.setAssociacao(associacaoRepository.findByNumero(numeroAssociacao));
        return associadoRepository.save(associado);

    }

    public List<Associado> listarTodas() {
        return this.associadoRepository.findAll();
    }
    
}
