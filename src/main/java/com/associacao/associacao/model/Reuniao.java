package com.associacao.associacao.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Reuniao {
	

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reuniao_seq")
    @SequenceGenerator(name = "reuniao_seq", sequenceName = "reuniao_SEQ", allocationSize = 1)
    private Long id;

	@Column
	private LocalDate data;

	@Column
	private String pauta;
    
	@ManyToOne
    private Associacao associacao;

	@ManyToMany
	@JoinTable(
	name = "associado_reuniao", 
	joinColumns = @JoinColumn(name = "reuniao_id"), 
	inverseJoinColumns = @JoinColumn(name = "associado_id"))
    private Set<Associado> associados;

	public Reuniao(LocalDate data2, String pauta2, Set<Long> associadosIds) {
       
		this.pauta = pauta2;
		this.data = data2;
		this.associados = new HashSet<>();
		associadosIds.forEach(id -> {
			Associado a = new Associado();
			a.setId(id);
			this.associados.add(a);
		});
    }

}
