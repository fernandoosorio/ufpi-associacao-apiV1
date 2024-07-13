package com.associacao.associacao.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class MudancaDeNivel {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mudancanivel_seq")
    @SequenceGenerator(name = "mudancanivel_seq", sequenceName = "mudancanivel_seq", allocationSize = 1)
    private Long id;

	@Column
	private Integer anterior;
	
	@Column
	private Integer novo;
	
	@Column
	private LocalDate data;

	@ManyToOne
    private Associado associado;
}
