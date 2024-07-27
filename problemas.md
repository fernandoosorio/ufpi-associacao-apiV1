### Problemas
**ID Questão = 003**

*Antes de Iniciar, dê início à gravação da tela*

1. Implementar o método `TaxaService.calcularTotalDeTaxas(numAssociacao, vigencia)` , que calcula o total de taxas previstas para um dado ano (vigência), em uma associação.

O método recebe como parâmetros o número da associação (Campo `numero` na classe `Associacao` ) e um campo `vigencia`, que representa o ano de cobrança da Taxa. 

O método já consta na classe `TaxaService`, conforme abaixo:

```java
public Double calcularTotalDeTaxas (int numAssociacao, int vigencia) throws AssociacaoNaoExistente, TaxaNaoExistente{}

```
Observações: 
  - Caso não exista associação identificada pelo número, disparar a exceção `AssociacaoNaoExistente`;
  - Caso não exista taxa cadastradas para aquele ano e para a associação identificada, disparar `TaxaNaoExistente`, lembrar de buscar em TaxaRepository o método auxiliar para este passo.

**Lembre-se de exportar o chat, caso esteja usando Copilot `Ctrl+Shift+P | Chat: Exportar Chat...`**

### Próximo
**ID Questão = 004**

*Antes de Iniciar, dê início à gravação da tela*

2. Implementar o método `PagamentoService.registrarPagamento(numAssociacao, taxa, vigencia, numAssociado, data, valor)`, que registra o pagamento de uma taxa, em uma associação, dentro uma determinada competência, para um associado. 

O valor a ser pago não pode ser menor que uma parcela, embora não precise ser exatamente duas parcelas. Uma parcela de R$20,00 por mês aceita um pagamento de R$30,00, sendo uma parcela completa e um pedaço da próxima. 

Associados remidos não devem mais realizar pagamentos de taxas administrativas vigentes em datas antes da sua remissão, gerando exceção de AssociadoJaRemido se houver tentativa de se pagar algo para esse caso. 


O método já consta na classe `PagamentoService`, conforme abaixo:
```java
 public Pagamento registrarPagamento(int numAssociacao, String taxa, int vigencia, int numAssociado, LocalDate data,
            double valor) throws AssociacaoNaoExistente, AssociadoNaoExistente, AssociadoJaRemido, TaxaNaoExistente, ValorInvalido {}
```
Observações: 
  - Caso não exista associação identificada pelo número, disparar a exceção `AssociacaoNaoExistente`;
  - Caso não exista Associado identificado pelo número de associado, disparar a exceção `AssociadoNaoExistente`;
  - Caso não o Associado seja remido, disparar a exceção `AssociadoJaRemido`;
  - Caso não exista taxa cadastradas para aquele ano e para a associação identificada, disparar `TaxaNaoExistente`, lembrar de buscar em TaxaRepository o método auxiliar para este passo.
  - Caso o valor a ser pago seja menor que o mínimo ou gerando pagamento maior que a taxa anual, gerar exceção de `ValorInvalido` Lembrar de verificar valores negativos.

**Lembre-se de exportar o chat, caso esteja usando Copilot `Ctrl+Shift+P | Chat: Exportar Chat...`**
