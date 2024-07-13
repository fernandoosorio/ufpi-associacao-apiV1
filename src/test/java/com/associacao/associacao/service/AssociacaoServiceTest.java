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

import com.associacao.associacao.exception.AssociacaoJaExistente;
import com.associacao.associacao.model.Associacao;
import com.associacao.associacao.repository.AssociacaoRepository;

@ExtendWith(MockitoExtension.class)
public class AssociacaoServiceTest {

    @InjectMocks
    private AssociacaoService associacaoService;

    @Mock
    private AssociacaoRepository associacaoRepository;

    @Test
    public void testCriarAssociacao() throws Exception {
        Associacao associacao = new Associacao();
        associacao.setNumero(1);
        associacao.setNome("Associacao 1");

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(false);
        when(associacaoRepository.existsByNome(anyString())).thenReturn(false);
        when(associacaoRepository.save(any(Associacao.class))).thenReturn(new Associacao());

        Associacao associacaoCriada = associacaoService.criarAssociacao(associacao);

        assertNotNull(associacaoCriada);
    }

    @Test
    public void testCriarAssociacaoJaExistentePorNumero() {
        Associacao associacao = new Associacao();
        associacao.setNumero(1);
        associacao.setNome("Associacao 1");

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);

        assertThrows(AssociacaoJaExistente.class, () -> {
            associacaoService.criarAssociacao(associacao);
        });
    }

    @Test
    public void testCriarAssociacaoJaExistentePorNome() {
        Associacao associacao = new Associacao();
        associacao.setNumero(1);
        associacao.setNome("Associacao 1");

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(false);
        when(associacaoRepository.existsByNome(anyString())).thenReturn(true);

        assertThrows(AssociacaoJaExistente.class, () -> {
            associacaoService.criarAssociacao(associacao);
        });
    }
}
