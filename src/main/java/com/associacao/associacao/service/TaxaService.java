package com.associacao.associacao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.associacao.associacao.exception.AssociacaoNaoExistente;
import com.associacao.associacao.exception.TaxaJaExistente;
import com.associacao.associacao.exception.TaxaNaoExistente;
import com.associacao.associacao.exception.ValorInvalido;
import com.associacao.associacao.model.Associacao;
import com.associacao.associacao.model.Taxa;
import com.associacao.associacao.repository.AssociacaoRepository;
import com.associacao.associacao.repository.TaxaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaxaService {
    private final TaxaRepository taxaRepository;
    private final AssociacaoRepository associacaoRepository;

     public List<Taxa> listarTodas(){
        return this.taxaRepository.findAll();
    }

    public Taxa criarTaxa( int numeroAssociacao, Taxa taxa) throws AssociacaoNaoExistente, TaxaJaExistente, ValorInvalido{

        if ( numeroAssociacao <= 0) {
            throw new ValorInvalido();
        }
        
        if (!associacaoRepository.existsByNumero(numeroAssociacao)) {
            throw new AssociacaoNaoExistente();
        }

        if (taxaRepository.existsByAssociacaoAndNomeAndVigencia(
            associacaoRepository.findByNumero(numeroAssociacao),
            taxa.getNome(),
            taxa.getVigencia())) {
            throw new TaxaJaExistente();
        }

        taxa.setAssociacao( associacaoRepository.findByNumero(numeroAssociacao) );
        
        return taxaRepository.save(taxa);

    }

    //Este é o problema: **ID Questão = 003**
    public Double calcularTotalDeTaxas(int numAssociacao, int vigencia)  throws AssociacaoNaoExistente, TaxaNaoExistente {
        // Verificar se a associação existe
        if (!associacaoRepository.existsByNumero(numAssociacao)) {
            throw new AssociacaoNaoExistente();
        }

        // Buscar todas as taxas para a associação e vigência fornecidas
        Associacao associacao = associacaoRepository.findByNumero(numAssociacao);
        List<Taxa> taxas = taxaRepository.findByAssociacaoAndVigencia(associacao, vigencia);
        
        // Verificar se existem taxas cadastradas
        if (taxas.isEmpty()) {
            throw new TaxaNaoExistente();
        }

        // Calcular o total das taxas
        double total = 0.0;
        for (Taxa taxa : taxas) {
            total += taxa.getValorAno();
        }

        // Retornar o total calculado
        return total;
    }
    
}
