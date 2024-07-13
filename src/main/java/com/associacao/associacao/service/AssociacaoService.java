package com.associacao.associacao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.associacao.associacao.exception.AssociacaoJaExistente;
import com.associacao.associacao.exception.ValorInvalido;
import com.associacao.associacao.model.Associacao;
import com.associacao.associacao.repository.AssociacaoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AssociacaoService {
   
    private AssociacaoRepository associacaoRepository;

    public Associacao criarAssociacao(Associacao associacao) throws AssociacaoJaExistente, ValorInvalido {

        if (associacaoRepository.existsByNumero(associacao.getNumero())) {
            throw new AssociacaoJaExistente();
        }

        if (associacaoRepository.existsByNome(associacao.getNome())) {
            throw new AssociacaoJaExistente();
        }
        return  associacaoRepository.save(associacao);
    }

    public List<Associacao> listarTodas(){
        return this.associacaoRepository.findAll();
    }
    
}
