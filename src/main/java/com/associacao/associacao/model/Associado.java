package com.associacao.associacao.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "associado_seq")
    @SequenceGenerator(name = "associado_seq", sequenceName = "ASSOCIADO_SEQ", allocationSize = 1)
    private Long id;

    @Column
    @NotNull
    private Integer numero;

    @Column
    @NotNull
	private String cpf;

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

    @Column
	private String telefone;

    @Column
	private String whatsapp;

    @Column
	private String email;

    @Column
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataAssociacao;

    @Column
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate nascimento;

    @Column
    private Boolean remido;

    @Column
    private LocalDate dataRemissao;

    @ManyToOne
	private Associacao associacao;
    
    @ManyToMany(mappedBy = "associados")
    @JsonIgnore
	private Set<Reuniao> reunioes;

    @OneToMany(mappedBy = "associado")
    @JsonIgnore
	private List<Pagamento> pagamentos;

    @OneToMany(mappedBy = "associado")
    @JsonIgnore
	private List<MudancaDeNivel> mudancas;
}
