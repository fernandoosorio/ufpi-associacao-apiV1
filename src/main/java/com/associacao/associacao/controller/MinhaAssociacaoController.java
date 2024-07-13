package com.associacao.associacao.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.associacao.associacao.model.Pagamento;
import com.associacao.associacao.model.Taxa;
import com.associacao.associacao.model.dto.ReuniaoDto;
import com.associacao.associacao.service.AssociacaoService;
import com.associacao.associacao.service.AssociadoService;
import com.associacao.associacao.service.PagamentoService;
import com.associacao.associacao.service.ReuniaoService;
import com.associacao.associacao.service.TaxaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/associacao")
@RequiredArgsConstructor
public class MinhaAssociacaoController  implements InterfaceAssociacao{
    private final AssociacaoService associacaoService;
    private final AssociadoService associadoService;
    private final ReuniaoService reuniaoService;
    private final TaxaService taxaService;
    private final PagamentoService pagamentoService;

    @Override
    @GetMapping("/calcular/frequencia/{numAssociacao}/{numAssociado}/{inicio}/{fim}") 
    public Double calcularFrequencia(@PathVariable int numAssociado, @PathVariable int numAssociacao, 
    @PathVariable LocalDate inicio, @PathVariable LocalDate fim)
            throws AssociadoNaoExistente, ReuniaoNaoExistente, AssociacaoNaoExistente {
       
        return reuniaoService.calcularFrequencia(numAssociado, numAssociacao, inicio, fim);
    }
    @GetMapping("listar-todas-associacoes")
    public ResponseEntity<List<Associacao>> listarTodasAssociacoes(){
        return ResponseEntity.ok( this.associacaoService.listarTodas() );
        
    }

    @GetMapping("listar-associados")
    public ResponseEntity<List<Associado>> listarTodosAssociados(){
        return ResponseEntity.ok( this.associadoService.listarTodas() );
        
    }

    @GetMapping("listar-taxas")
    public ResponseEntity<List<Taxa>> listarTodosTaxas(){
        return ResponseEntity.ok( this.taxaService.listarTodas() );
        
    }

    @Override
    @PostMapping("/registar/pagamento/{numAssociacao}/{nomeTaxa}/{vigencia}/{numAssociado}/{data}/{valor}") 
    public ResponseEntity<Pagamento> registrarPagamento(@PathVariable int numAssociacao, 
            @PathVariable String nomeTaxa, 
            @PathVariable int vigencia, 
            @PathVariable int numAssociado, 
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy")  LocalDate data,
            @PathVariable double valor)
            throws AssociacaoNaoExistente, AssociadoNaoExistente, AssociadoJaRemido, TaxaNaoExistente, ValorInvalido {
       
        return ResponseEntity.ok( pagamentoService.registrarPagamento(numAssociacao, nomeTaxa, vigencia, numAssociado, data, valor) );
    }

    @Override
    @PostMapping("/totalpagamento/associado/{numAssociacao}/{numAssociado}/{nomeTaxa}/{vigencia}/{inicio}/{fim}")
    public Double somarPagamentoDeAssociado(@PathVariable int numAssociacao, 
    @PathVariable int numAssociado, 
    @PathVariable String nomeTaxa, 
    @PathVariable int vigencia,
    @PathVariable  LocalDate inicio, 
    @PathVariable  LocalDate fim) throws AssociacaoNaoExistente, AssociadoNaoExistente, TaxaNaoExistente {
        
        return pagamentoService.somarPagamentosAssociado(numAssociacao, numAssociado,
        nomeTaxa, vigencia,inicio, fim ) ;
    }

    @Override
    @PostMapping("/totaltaxas/{numAssociacao}/{vigencia}")
    public Double calcularTotalDeTaxas(@PathVariable int numAssociacao, @PathVariable int vigencia)
            throws AssociacaoNaoExistente, TaxaNaoExistente {
       
        return taxaService.calcularTotalDeTaxas(numAssociacao, vigencia);
    }

    @Override
    @PostMapping("/create/associacao") 
    public ResponseEntity<Associacao> adicionar(@Valid @RequestBody Associacao a) throws AssociacaoJaExistente, ValorInvalido {
        return  ResponseEntity.ok(associacaoService.criarAssociacao(a)) ;
    }

    @Override
    @PostMapping("/create/associado/{associacao}") 
    public ResponseEntity<?> adicionar(@PathVariable int associacao, @Valid @RequestBody Associado a)
            throws AssociacaoNaoExistente, AssociadoJaExistente, ValorInvalido {

        return ResponseEntity.ok(associadoService.criarAssociado(associacao, a));
        
    }

    @Override
    @PostMapping("/create/reuniao/{associacao}") 
    public ResponseEntity<?> adicionar(@PathVariable int associacao, @RequestBody ReuniaoDto dto ) throws AssociacaoNaoExistente, ReuniaoJaExistente, ValorInvalido {
        return ResponseEntity.ok(reuniaoService.criarReuniao(associacao, dto));
    }

    @Override
    @PostMapping("/create/taxa/{associacao}") 
    public ResponseEntity<?> adicionar(@PathVariable int associacao, @RequestBody Taxa t) throws AssociacaoNaoExistente, TaxaJaExistente, ValorInvalido {
        return ResponseEntity.ok( taxaService.criarTaxa(associacao, t));
    }
    
}
