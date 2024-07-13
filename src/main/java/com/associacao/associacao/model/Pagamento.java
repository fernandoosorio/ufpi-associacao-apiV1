package com.associacao.associacao.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Pagamento {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pagamento_seq")
    @SequenceGenerator(name = "pagamento_seq", sequenceName = "pagamento_SEQ", allocationSize = 1)
    private Long id;

	@Column
	private LocalDate data;

	@Column
	private Double valor;

	@ManyToOne
	@JsonIgnore
	private Associado associado;

	@ManyToOne
	@JsonIgnore
	private Associacao associacao;

	@ManyToOne
	@JsonIgnore
	private Taxa taxa;
    
}
