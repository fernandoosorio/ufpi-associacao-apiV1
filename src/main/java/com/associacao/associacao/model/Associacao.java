package com.associacao.associacao.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Associacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "associacao_seq")
    @SequenceGenerator(name = "associacao_seq", sequenceName = "ASSOCIACAO_SEQ", allocationSize = 1)
    private Long id;

    @Column
    @NotNull
    private Integer numero;

    @Column
    @NotNull
	private String nome;

    @Column
    @NotNull
	private String endereco;

    @Column
    @NotNull
	private String cidade;

    @Column
    @NotNull
	private String estado;
    
    @Column(columnDefinition = "boolean default true")
    private boolean ativo = true;

    @OneToMany(mappedBy = "associacao")
    @JsonIgnore
    @Transient
	private List<Associado> associados;

    @OneToMany(mappedBy = "associacao")
    @JsonIgnore
    @Transient
	private List<Taxa> taxas;

    @OneToMany(mappedBy = "associacao")
    @JsonIgnore
    @Transient
	private List<Reuniao> reunioes;

    
    
}
