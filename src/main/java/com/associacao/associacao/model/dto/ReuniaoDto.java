package com.associacao.associacao.model.dto;

import java.time.LocalDate;
import java.util.Set;

import com.associacao.associacao.model.Reuniao;

public record ReuniaoDto(Long reuniaoId, LocalDate data, String pauta, Long associacaoId, Set<Long> associadosIds ) {
    
    public Reuniao toReuniao() {
        return new Reuniao(this.data, this.pauta, this.associadosIds);
    }
}
