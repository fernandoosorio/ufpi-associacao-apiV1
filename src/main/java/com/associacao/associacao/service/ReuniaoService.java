package com.associacao.associacao.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.associacao.associacao.exception.AssociacaoNaoExistente;
import com.associacao.associacao.exception.ReuniaoJaExistente;
import com.associacao.associacao.exception.ValorInvalido;
import com.associacao.associacao.model.Reuniao;
import com.associacao.associacao.model.dto.ReuniaoDto;
import com.associacao.associacao.repository.AssociacaoRepository;
import com.associacao.associacao.repository.ReuniaoRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReuniaoService {
    private ReuniaoRepository reuniaoRepository;
    private AssociacaoRepository associacaoRepository;

    public Reuniao criarReuniao( int numeroAssociacao, ReuniaoDto dto) throws AssociacaoNaoExistente, ReuniaoJaExistente, ValorInvalido{

        if ( numeroAssociacao <= 0) {
            throw new ValorInvalido();
        }
        
        if (!associacaoRepository.existsByNumero(numeroAssociacao)) {
            throw new AssociacaoNaoExistente();
        }

        if (reuniaoRepository.existsByDataAndAssociacao(dto.data(), associacaoRepository.findByNumero(numeroAssociacao))) {
            throw new ReuniaoJaExistente();
        }
        Reuniao r = dto.toReuniao();
        r.setAssociacao( associacaoRepository.findByNumero(numeroAssociacao) );
        
        return reuniaoRepository.save(r);

    }
    @Transactional
    public Double calcularFrequencia(int numAssociado, int numAssociacao, LocalDate inicio, LocalDate fim) {
        List<Reuniao> reunioes = reuniaoRepository.findByAssociacaoAndDataBetween(associacaoRepository.findByNumero(numAssociacao), inicio, fim);
        int totalReunioes = reunioes.size();
        if ( totalReunioes == 0) {
           throw new Error("Não há reuniões no período");
        }
        int reunioesDoAssociado = 0;
        for (Reuniao r : reunioes) {
            if (r.getAssociados().stream()
                .peek(a -> System.out.println(a.getId()))
                .anyMatch(a -> a.getId() == numAssociado)) {
                reunioesDoAssociado++;
            }
        }
        if (totalReunioes > 0) {
            return (double) reunioesDoAssociado / totalReunioes;
        }

        return 0.0;
        
    }
    
}
