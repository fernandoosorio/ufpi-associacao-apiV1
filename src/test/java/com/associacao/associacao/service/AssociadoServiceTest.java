package com.associacao.associacao.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.associacao.associacao.exception.AssociacaoNaoExistente;
import com.associacao.associacao.exception.AssociadoJaExistente;
import com.associacao.associacao.model.Associacao;
import com.associacao.associacao.model.Associado;
import com.associacao.associacao.repository.AssociacaoRepository;
import com.associacao.associacao.repository.AssociadoRepository;

@ExtendWith(MockitoExtension.class)
public class AssociadoServiceTest {

    @InjectMocks
    private AssociadoService associadoService;

    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private AssociacaoRepository associacaoRepository;

    @Test
    public void testCriarAssociado() throws Exception {
        Associado associado = new Associado();
        associado.setNumero(1);
        associado.setCpf("123.456.789-00");

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(associadoRepository.existsByNumero(anyInt())).thenReturn(false);
        when(associadoRepository.existsByCpf(anyString())).thenReturn(false);
        when(associacaoRepository.findByNumero(anyInt())).thenReturn(new Associacao());
        when(associadoRepository.save(any(Associado.class))).thenReturn(new Associado());

        Associado associadoCriado = associadoService.criarAssociado(1, associado);

        assertNotNull(associadoCriado);
    }

    @Test
    public void testCriarAssociadoAssociacaoNaoExistente() {
        Associado associado = new Associado();
        associado.setNumero(1);
        associado.setCpf("123.456.789-00");

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(false);

        assertThrows(AssociacaoNaoExistente.class, () -> {
            associadoService.criarAssociado(1, associado);
        });
    }

    @Test
    public void testCriarAssociadoAssociadoJaExistentePorNumero() {
        Associado associado = new Associado();
        associado.setNumero(1);
        associado.setCpf("123.456.789-00");

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(associadoRepository.existsByNumero(anyInt())).thenReturn(true);

        assertThrows(AssociadoJaExistente.class, () -> {
            associadoService.criarAssociado(1, associado);
        });
    }

    @Test
    public void testCriarAssociadoAssociadoJaExistentePorCpf() {
        Associado associado = new Associado();
        associado.setNumero(1);
        associado.setCpf("123.456.789-00");

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(associadoRepository.existsByNumero(anyInt())).thenReturn(false);
        when(associadoRepository.existsByCpf(anyString())).thenReturn(true);

        assertThrows(AssociadoJaExistente.class, () -> {
            associadoService.criarAssociado(1, associado);
        });
    }
}