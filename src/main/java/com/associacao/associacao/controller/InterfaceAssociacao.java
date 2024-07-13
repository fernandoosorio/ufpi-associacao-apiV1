package com.associacao.associacao.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.associacao.associacao.exception.AssociacaoJaExistente;
import com.associacao.associacao.exception.AssociacaoNaoExistente;
import com.associacao.associacao.exception.AssociadoJaExistente;
import com.associacao.associacao.exception.AssociadoJaRemido;
import com.associacao.associacao.exception.AssociadoNaoExistente;
import com.associacao.associacao.exception.ReuniaoJaExistente;
import com.associacao.associacao.exception.ReuniaoNaoExistente;
import com.associacao.associacao.exception.TaxaJaExistente;
import com.associacao.associacao.exception.TaxaNaoExistente;
import com.associacao.associacao.exception.ValorInvalido;
import com.associacao.associacao.model.Associacao;
import com.associacao.associacao.model.Associado;
import com.associacao.associacao.model.Taxa;
import com.associacao.associacao.model.dto.ReuniaoDto;

import io.swagger.v3.oas.annotations.Operation;

public interface InterfaceAssociacao {

// Calcula a frequência de um associado nas reuniões ocorridas durante um determinado período, retornando um número entre 0 e 1 (ex: 0,6, indicando que o associado participou de 60% das reuniões.
public Double calcularFrequencia(int numAssociado, int numAssociacao, LocalDate inicio, LocalDate fim) throws AssociadoNaoExistente, ReuniaoNaoExistente, AssociacaoNaoExistente;

// Registra o pagamento de uma taxa, em uma associação, dentro uma determinada competência, para um associado. O valor a ser pago não pode ser menor que uma parcela, embora não precise ser exatamente duas parcelas. Uma parcela de R$20,00 por mês aceita um pagamento de R$30,00, sendo uma parcela completa e um pedaço da próxima. Associados remidos não deveriam mais realizar pagamentos de taxas administrativas vigentes em datas antes da sua remissão, gerando exceção de AssociadoJaRemido se houver tentativa de se pagar algo para esse caso. Caso o valor a ser pago seja menor que o mínimo (não sendo o ultimo do ano!) ou gerando pagamento maior que a taxa anual, gerar exceção de ValorInvalido. Lembrar de verificar valores negativos.
@Operation(summary = "Registrar Pagamento", 
    description = " Formato da data: dd-MM-yyyy" )
public ResponseEntity<?> registrarPagamento(int numAssociacao, String nomeTaxa, int vigencia, int numAssociado, LocalDate data, double valor) throws AssociacaoNaoExistente, AssociadoNaoExistente, AssociadoJaRemido, TaxaNaoExistente, ValorInvalido;

// Calcula o total de pagamentos realizado por um associado, em uma associação, para uma taxa, que possui uma vigência, dentro de um certo período de tempo.
public Double somarPagamentoDeAssociado (int numAssociacao, int numAssociado, String nomeTaxa, int vigencia, LocalDate inicio, LocalDate fim) throws AssociacaoNaoExistente, AssociadoNaoExistente, TaxaNaoExistente;

// Calcula o total de taxas previstas para um dado ano, em uma associação.
@Operation(summary = "calcular Total de Taxas de uma associação" )
public Double calcularTotalDeTaxas (int numAssociacao, int vigencia) throws AssociacaoNaoExistente, TaxaNaoExistente;

// Adiciona uma associação a ser gerenciada. Valida todos os campos para evitar dados não preenchidos.
public ResponseEntity<?> adicionar(Associacao a) throws AssociacaoJaExistente, ValorInvalido;

// Adiciona um associado a uma associação. Valida todos os campos para evitar dados não preenchidos.
public ResponseEntity<?> adicionar(int associacao, Associado a) throws AssociacaoNaoExistente, AssociadoJaExistente, ValorInvalido;

// Adiciona uma reunião a uma associação. Valida todos os campos para evitar dados não preenchidos. não deveria registrar participacao de associado em reunioes acontecidas antes da sua filiacao na associação.
public ResponseEntity<?> adicionar(int associacao, @RequestBody ReuniaoDto dto) throws AssociacaoNaoExistente, ReuniaoJaExistente, ValorInvalido;

// Adiciona uma taxa a uma associação. Valida todos os campos para evitar dados não preenchidos.
public ResponseEntity<?> adicionar(int associacao, Taxa t) throws AssociacaoNaoExistente, TaxaJaExistente, ValorInvalido;

}