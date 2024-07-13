package com.associacao.associacao.model;

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
public class Taxa {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxa_seq")
    @SequenceGenerator(name = "taxa_seq", sequenceName = "taxa_SEQ", allocationSize = 1)
    private Long id;

	@Column
    private Integer codigo;

	@Column
	private String nome;

	@Column
	private Integer vigencia;

	@Column
	private TipoTaxaEnum tipoTaxa;

	@Column
	private Double valorAno;

	@Column
	private Integer parcelas;

	@ManyToOne
	private Associacao associacao;

}
