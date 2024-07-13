package com.associacao.associacao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.associacao.associacao.exception.AssociacaoNaoExistente;
import com.associacao.associacao.exception.TaxaJaExistente;
import com.associacao.associacao.exception.TaxaNaoExistente;
import com.associacao.associacao.exception.ValorInvalido;
import com.associacao.associacao.model.Associacao;
import com.associacao.associacao.model.Taxa;
import com.associacao.associacao.repository.AssociacaoRepository;
import com.associacao.associacao.repository.TaxaRepository;

@ExtendWith(MockitoExtension.class)
public class TaxaServiceTest {

    @InjectMocks
    private TaxaService taxaService;

    @Mock
    private TaxaRepository taxaRepository;

    @Mock
    private AssociacaoRepository associacaoRepository;

    @Test
    public void testCriarTaxa() throws Exception {
        Taxa taxa = new Taxa();
        taxa.setNome("Taxa Teste");
        taxa.setVigencia(2022);

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(taxaRepository.existsByAssociacaoAndNomeAndVigencia(any(), anyString(), anyInt())).thenReturn(false);
        when(associacaoRepository.findByNumero(anyInt())).thenReturn(new Associacao());
        when(taxaRepository.save(any(Taxa.class))).thenReturn(taxa);

        Taxa taxaCriada = taxaService.criarTaxa(1, taxa);

        assertEquals(taxa, taxaCriada);
    }

    @Test
    public void testCriarTaxaValorInvalido() {
        assertThrows(ValorInvalido.class, () -> {
            taxaService.criarTaxa(0, new Taxa());
        });
    }

    @Test
    public void testCriarTaxaAssociacaoNaoExistente() {
        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(false);
        assertThrows(AssociacaoNaoExistente.class, () -> {
            taxaService.criarTaxa(1, new Taxa());
        });
    }

    @Test
    public void testCriarTaxaTaxaJaExistente() {
        Taxa taxa = new Taxa();
        taxa.setNome("Taxa Teste");
        taxa.setVigencia(2022);

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(associacaoRepository.findByNumero(anyInt())).thenReturn(new Associacao());
        when(taxaRepository.existsByAssociacaoAndNomeAndVigencia(any(), anyString(), anyInt())).thenReturn(true);
        assertThrows(TaxaJaExistente.class, () -> {
            taxaService.criarTaxa(1, taxa);
        });
    }

    @Test
    public void testCalcularTotalDeTaxas() throws Exception {
        List<Taxa> taxas = new ArrayList<>();
        Taxa taxa1 = new Taxa();
        taxa1.setValorAno(100.0);
        taxas.add(taxa1);

        Taxa taxa2 = new Taxa();
        taxa2.setValorAno(200.0);
        taxas.add(taxa2);

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(taxaRepository.existsByAssociacaoAndVigencia(any(), anyInt())).thenReturn(true);
        when(taxaRepository.findByAssociacaoAndVigencia(any(), anyInt())).thenReturn(taxas);

        Double total = taxaService.calcularTotalDeTaxas(1, 2022);

        assertEquals(300.0, total);
    }

    @Test
    public void testCalcularTotalDeTaxasAssociacaoNaoExistente() {
        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(false);
        assertThrows(AssociacaoNaoExistente.class, () -> {
            taxaService.calcularTotalDeTaxas(1, 2022);
        });
    }

    @Test
    public void testCalcularTotalDeTaxasTaxaNaoExistente() {
        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(taxaRepository.existsByAssociacaoAndVigencia(any(), anyInt())).thenReturn(false);
        assertThrows(TaxaNaoExistente.class, () -> {
            taxaService.calcularTotalDeTaxas(1, 2022);
        });
    }
}