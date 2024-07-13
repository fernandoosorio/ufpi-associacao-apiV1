package com.associacao.associacao.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.associacao.associacao.exception.AssociacaoNaoExistente;
import com.associacao.associacao.exception.ReuniaoJaExistente;
import com.associacao.associacao.exception.ValorInvalido;
import com.associacao.associacao.model.Associacao;
import com.associacao.associacao.model.Associado;
import com.associacao.associacao.model.Reuniao;
import com.associacao.associacao.model.dto.ReuniaoDto;
import com.associacao.associacao.repository.AssociacaoRepository;
import com.associacao.associacao.repository.ReuniaoRepository;

@ExtendWith(MockitoExtension.class)
public class ReuniaoServiceTest {

    @InjectMocks
    private ReuniaoService reuniaoService;

    @Mock
    private ReuniaoRepository reuniaoRepository;

    @Mock
    private AssociacaoRepository associacaoRepository;

    @Test
    public void testCriarReuniao() throws Exception {
        ReuniaoDto dto = new ReuniaoDto(1L, LocalDate.now(), "Descricao", 1L, new HashSet<Long>());

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(reuniaoRepository.existsByDataAndAssociacao(any(), any())).thenReturn(false);
        when(associacaoRepository.findByNumero(anyInt())).thenReturn(new Associacao());
        when(reuniaoRepository.save(any(Reuniao.class))).thenReturn(new Reuniao());

        Reuniao reuniaoCriada = reuniaoService.criarReuniao(1, dto);

        assertNotNull(reuniaoCriada);
    }

    @Test
    public void testCriarReuniaoValorInvalido() {
        ReuniaoDto dto = new ReuniaoDto(1L, LocalDate.now(), "Descricao", 1L, new HashSet<Long>());

        assertThrows(ValorInvalido.class, () -> {
            reuniaoService.criarReuniao(0, dto);
        });
    }

    @Test
    public void testCriarReuniaoAssociacaoNaoExistente() {
        ReuniaoDto dto = new ReuniaoDto(1L, LocalDate.now(), "Descricao", 1L, new HashSet<Long>());

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(false);

        assertThrows(AssociacaoNaoExistente.class, () -> {
            reuniaoService.criarReuniao(1, dto);
        });
    }

    @Test
    public void testCriarReuniaoReuniaoJaExistente() {
        ReuniaoDto dto = new ReuniaoDto(1L, LocalDate.now(), "Descricao", 1L, new HashSet<Long>());

        when(associacaoRepository.existsByNumero(anyInt())).thenReturn(true);
        when(reuniaoRepository.existsByDataAndAssociacao(any(), any())).thenReturn(true);

        assertThrows(ReuniaoJaExistente.class, () -> {
            reuniaoService.criarReuniao(1, dto);
        });
    }

    @Test
    public void testCalcularFrequencia() {
        Associado  associado1 = new Associado();
        associado1.setId(1L);
        associado1.setNome("Associado 1");
        Associado  associado2 = new Associado();
        associado2.setId(2L);
        associado2.setNome("Associado 2"); 
        
        List<Reuniao> reunioes = new ArrayList<>();
        Reuniao reuniao1 = new Reuniao();
        Set<Associado> associados = new HashSet<>();
        associados.add(associado1);
        reuniao1.setAssociados(associados);
        reunioes.add(reuniao1);

        Reuniao reuniao2 = new Reuniao();
        reuniao2.setAssociados(new HashSet<>(Arrays.asList(associado1, associado2)));
        reunioes.add(reuniao2);

        when(reuniaoRepository.findByAssociacaoAndDataBetween(any(), any(), any())).thenReturn(reunioes);

        Double frequencia = reuniaoService.calcularFrequencia(1, 1, LocalDate.now().minusDays(1), LocalDate.now());

        assertEquals(1.0, frequencia);
    }

    @Test
    public void testCalcularFrequenciaSemReunioes() {
        when(reuniaoRepository.findByAssociacaoAndDataBetween(any(), any(), any())).thenReturn(new ArrayList<>());

        assertThrows(Error.class, () -> {
            reuniaoService.calcularFrequencia(1, 1, LocalDate.now().minusDays(1), LocalDate.now());
        });
    }
}