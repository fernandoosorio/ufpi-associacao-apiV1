package com.associacao.associacao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.associacao.associacao.exception.AssociacaoNaoExistente;
import com.associacao.associacao.exception.AssociadoJaRemido;
import com.associacao.associacao.exception.AssociadoNaoExistente;
import com.associacao.associacao.exception.TaxaNaoExistente;
import com.associacao.associacao.model.Associacao;
import com.associacao.associacao.model.Associado;
import com.associacao.associacao.model.Pagamento;
import com.associacao.associacao.model.Taxa;
import com.associacao.associacao.repository.AssociacaoRepository;
import com.associacao.associacao.repository.AssociadoRepository;
import com.associacao.associacao.repository.PagamentoRepository;
import com.associacao.associacao.repository.TaxaRepository;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {

    @InjectMocks
    private PagamentoService pagamentoService;

    @Mock
    private TaxaRepository taxaRepository;

    @Mock
    private AssociacaoRepository associacaoRepository;

    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Test
    public void testRegistrarPagamento() throws Exception {
        Taxa taxaBD = new Taxa();
        taxaBD.setValorAno(100.0);
        taxaBD.setParcelas(1);

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(taxaRepository.existsByAssociacaoAndNomeAndVigencia(any(), anyString(), anyInt())).thenReturn(true);
        when(taxaRepository.findByAssociacaoAndNomeAndVigencia(any(), anyString(), anyInt())).thenReturn(taxaBD);
        when(associadoRepository.existsByNumeroAndRemido(anyInt(), anyBoolean())).thenReturn(false);
        when(associadoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(associacaoRepository.findByNumero(anyInt())).thenReturn(new Associacao());
        when(associadoRepository.findByNumero(anyInt())).thenReturn(new Associado());
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(new Pagamento());

        Pagamento pagamento = pagamentoService.registrarPagamento(1, "Taxa", 2022, 1, LocalDate.now(), 100.0);

        assertNotNull(pagamento);
    }

    @Test
    public void testRegistrarPagamentoAssociacaoNaoExistente() {
        assertThrows(AssociacaoNaoExistente.class, () -> {
            pagamentoService.registrarPagamento(1, "Taxa", 2022, 1, LocalDate.now(), 100.0);
        });
    }

    @Test
    public void testRegistrarPagamentoTaxaNaoExistente() {
        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);

        assertThrows(TaxaNaoExistente.class, () -> {
            pagamentoService.registrarPagamento(1, "Taxa", 2022, 1, LocalDate.now(), 100.0);
        });
    }

    @Test
    public void testRegistrarPagamentoAssociadoJaRemido() {
        Taxa taxa = new Taxa();
        taxa.setValorAno(100.0);
        taxa.setParcelas(1);

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(taxaRepository.existsByAssociacaoAndNomeAndVigencia(any(), anyString(), anyInt())).thenReturn(true);
        when(associadoRepository.existsByNumeroAndRemido(anyInt(), anyBoolean())).thenReturn(true);
        when(taxaRepository.findByAssociacaoAndNomeAndVigencia(any(), anyString(), anyInt())).thenReturn(taxa);

        assertThrows(AssociadoJaRemido.class, () -> {
            pagamentoService.registrarPagamento(1, "Taxa", 2022, 1, LocalDate.now(), 100.0);
        });
    }

    @Test
    public void testRegistrarPagamentoAssociadoNaoExistente() {
        Taxa taxa = new Taxa();
        taxa.setValorAno(100.0);
        taxa.setParcelas(1);

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(taxaRepository.existsByAssociacaoAndNomeAndVigencia(any(), anyString(), anyInt())).thenReturn(true);
        when(associadoRepository.existsByNumeroAndRemido(anyInt(), anyBoolean())).thenReturn(false);
        when(taxaRepository.findByAssociacaoAndNomeAndVigencia(any(), anyString(), anyInt())).thenReturn(taxa);

        assertThrows(AssociadoNaoExistente.class, () -> {
            pagamentoService.registrarPagamento(1, "Taxa", 2022, 1, LocalDate.now(), 100.0);
        });
    }

    

    @Test
    public void testSomarPagamentosAssociado() throws Exception {
        List<Pagamento> pagamentos = new ArrayList<>();
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setValor(100.0);
        pagamentos.add(pagamento1);

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setValor(200.0);
        pagamentos.add(pagamento2);

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(taxaRepository.existsByAssociacaoAndNomeAndVigencia(any(), anyString(), anyInt())).thenReturn(true);
        when(associadoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(pagamentoRepository.findAllByAssociacaoAndAssociadoAndTaxaAndDataBetween(any(), any(), any(), any(), any())).thenReturn(pagamentos);

        Double total = pagamentoService.somarPagamentosAssociado(1, 1, "Taxa", 2022, LocalDate.now().minusDays(1), LocalDate.now());

        assertEquals(300.0, total);
    }

    @Test
    public void testSomarPagamentosAssociadoAssociacaoNaoExistente() {
        assertThrows(AssociacaoNaoExistente.class, () -> {
            pagamentoService.somarPagamentosAssociado(1, 1, "Taxa", 2022, LocalDate.now().minusDays(1), LocalDate.now());
        });
    }

    @Test
    public void testSomarPagamentosAssociadoTaxaNaoExistente() {
        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);

        assertThrows(TaxaNaoExistente.class, () -> {
            pagamentoService.somarPagamentosAssociado(1, 1, "Taxa", 2022, LocalDate.now().minusDays(1), LocalDate.now());
        });
    }

    @Test
    public void testSomarPagamentosAssociadoAssociadoNaoExistente() {
        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(taxaRepository.existsByAssociacaoAndNomeAndVigencia(any(), anyString(), anyInt())).thenReturn(true);

        assertThrows(AssociadoNaoExistente.class, () -> {
            pagamentoService.somarPagamentosAssociado(1, 1, "Taxa", 2022, LocalDate.now().minusDays(1), LocalDate.now());
        });
    }
}
