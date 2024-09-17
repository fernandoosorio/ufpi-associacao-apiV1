package com.associacao.associacao.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.associacao.associacao.exception.AssociacaoNaoExistente;
import com.associacao.associacao.exception.AssociadoJaRemido;
import com.associacao.associacao.exception.AssociadoNaoExistente;
import com.associacao.associacao.exception.TaxaNaoExistente;
import com.associacao.associacao.exception.ValorInvalido;
import com.associacao.associacao.model.Associacao;
import com.associacao.associacao.model.Associado;
import com.associacao.associacao.model.Pagamento;
import com.associacao.associacao.model.Taxa;
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

    //Este é o problema: **ID Questão = 004**
    public Pagamento registrarPagamento(int numAssociacao, String nomeTaxa, int vigencia, int numAssociado, LocalDate data,
            double valor) throws AssociacaoNaoExistente, AssociadoNaoExistente, AssociadoJaRemido, TaxaNaoExistente, ValorInvalido {
           // Verificar se a associação existe
        if (!associacaoRepository.existsByNumero(numAssociacao)) {
            throw new AssociacaoNaoExistente();
        }

        // Verificar se o associado existe
        if (!associadoRepository.existsByNumero(numAssociado)) {
            throw new AssociadoNaoExistente();
        }

        // Verificar se o associado é remido
        Associado associado = associadoRepository.findByNumero(numAssociado);
        if (associadoRepository.existsByNumeroAndRemido(numAssociado, true))  {
            throw new AssociadoJaRemido();
        }

        // Verificar se a taxa existe para a associação e vigência
        Associacao associacao = associacaoRepository.findByNumero(numAssociacao);
        Taxa taxa = taxaRepository.findByAssociacaoAndNomeAndVigencia(associacao, nomeTaxa, vigencia);
        if (taxa == null) {
            throw new TaxaNaoExistente();
        }

        // Verificar se o valor é válido
        double valorMinimo = taxa.getValorAno() / taxa.getParcelas();
        double valorMaximo = taxa.getValorAno();
        if (valor < valorMinimo || valor > valorMaximo || valor < 0) {
            throw new ValorInvalido();
        }

        // Registrar o pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setAssociacao(associacao);
        pagamento.setAssociado(associado);
        pagamento.setTaxa(taxa);
        pagamento.setData(data);
        pagamento.setValor(valor);
        pagamentoRepository.save(pagamento);
        return pagamento;

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
