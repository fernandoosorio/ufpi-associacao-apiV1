package com.associacao.associacao.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.associacao.associacao.exception.AssociacaoNaoExistente;
import com.associacao.associacao.exception.AssociadoJaRemido;
import com.associacao.associacao.exception.AssociadoNaoExistente;
import com.associacao.associacao.exception.TaxaNaoExistente;
import com.associacao.associacao.exception.ValorInvalido;
import com.associacao.associacao.model.Pagamento;
import com.associacao.associacao.repository.AssociacaoRepository;
import com.associacao.associacao.repository.AssociadoRepository;
import com.associacao.associacao.repository.PagamentoRepository;
import com.associacao.associacao.repository.TaxaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagamentoService {
    private final TaxaRepository taxaRepository;
    private final AssociacaoRepository associacaoRepository;
    private final AssociadoRepository associadoRepository;
    private final PagamentoRepository pagamentoRepository;

    //Este é o problema: **ID Questão = 003**
    //Se usar Copilot para resolver: Lembre-se de exportar o chat
    //       `Ctrl+Shift+P | Chat: Exportar Chat...`
    public Pagamento registrarPagamento(int numAssociacao, String nomeTaxa, int vigencia, int numAssociado, LocalDate data,
            double valor) throws AssociacaoNaoExistente, AssociadoNaoExistente, AssociadoJaRemido, TaxaNaoExistente, ValorInvalido { 
       return null;
    }

    public Double somarPagamentosAssociado(int numAssociacao, int numAssociado, String nomeTaxa, int vigencia,
            LocalDate inicio, LocalDate fim) throws AssociacaoNaoExistente, AssociadoNaoExistente, TaxaNaoExistente {
        if ( numAssociacao <= 0) {
            throw new ValorInvalido();
        }
        
        if (!associacaoRepository.existsByNumero(numAssociacao)) {
            throw new AssociacaoNaoExistente();
        }
        
        if (!taxaRepository.existsByAssociacaoAndNomeAndVigencia(
            associacaoRepository.findByNumero(numAssociacao),
            nomeTaxa,
            vigencia)) {
            throw new TaxaNaoExistente();
        }

        if( !associadoRepository.existsByNumero(numAssociado) ) {
            throw new AssociadoNaoExistente();
        }

        List<Pagamento> pagamentos = pagamentoRepository.findAllByAssociacaoAndAssociadoAndTaxaAndDataBetween(
            associacaoRepository.findByNumero(numAssociacao),
            associadoRepository.findByNumero(numAssociado),
            taxaRepository.findByAssociacaoAndNomeAndVigencia(
                associacaoRepository.findByNumero(numAssociacao),
                nomeTaxa, 
                vigencia),
            inicio, fim);

        Double total = 0.0;
        for( Pagamento pagamento : pagamentos ) {
            total += pagamento.getValor();
        }
        return total;
    
    }

    
    
}
