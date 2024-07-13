# Sistema para Controle de uma Associação

### Resquisitos

Um grupo de pessoas se organiza em uma associação para realização de trabalhos sociais. Essas associações possuem encontros regulares, em que é necessário controlar a frequência de seus associados, além de cobrar taxas mensais para custear tanto o funcionamento da associação, bem como de suas ações solidárias.

As taxas são criadas no início do ano e possuem um nome, um valor anual, a quantidade de parcelas, sua vigência (ano) e a informação se ela é administrativa ou social. O valor mensal de uma taxa é dado pelo valor anual dividido pela quantidade de parcelas. Se uma taxa for cadastrada tendo apenas 6 parcelas, com valor anual de R$120, então sua taxa mensal é de R$20,00. Não deve ser possível pagar um valor menor que uma parcela. A não ser que seja o valor final para fechamento da taxa anual. O pagamento das taxas pode ter como valor mínimo uma mensalidade, mas pode aceitar valores maiores, ficando o resto para ser abatido de futuras mensalidades. É possível criar várias taxas durante um ano (vigência), até com o mesmo nome de outras taxas, porém, de outras vigências. Assim, a tentativa de criar, para uma associação, uma taxa com um mesmo nome de uma taxa já cadastrada, para uma mesma vigência deve gerar exceção. Mas se for para vigência diferente, em que não há uma taxa com esse nome, deve ser permitido.

Um associado tem como atributos `numero positivo (int)`, `nome (String)`, `telefone (String)`, `nascimento (LocalDate)`, `dataAssociacao (LocalDate)`. Existem associados `remidos`, que são aqueles que não pagam mais taxas administrativas. Um dado adicional para tais associados é a sua data de remissão (LocalDate), que é o período em que ele passou a não precisar mais pagar taxas administrativas. Se um associado foi remido em outubro de um ano, ele deve ter pago todas as taxas até um mês antes de outubro, que nesse caso é setembro.

Uma associação possui basicamente um `número e nome`. Além disso, tem uma relação de associados, além de reuniões realizadas, cada reunião com indicação dos participantes da reunião.

Nenhum dado pode ser salvo sem os seus dados preenchidos. Se isso acontecer, deve-se gerar uma exceção ValorInvalido, indicando que algum dado de alguma entidade não foi completamente preenchido, tais como dados de um associado, dados de reunião, dados de taxa, etc. São considerados não preenchidos strings vazias, valores nulos, e no caso de números, valor menores que zero. Tudo isso deveria gerar a exceção de ValorInvalido.

Durante uma reunião é registrada a `frequência` dos participantes, bem como é construída uma ata, que resume tudo o que foi discutido durante a reunião. A `ata` é apenas um texto relatando os acontecimentos naquela `data`.

Você deve criar um sistema para controle de associações. Deve ser possível criar diferentes associações. Esse sistema terá como classe principal a classe MinhaAssociacaoController (exatamente com esse nome!) que deve implementar a **InferfaceAssociacao** descrita aqui. 


Descrição dos métodos da InterfaceAssociacao

```java
public interface InterfaceAssociacao {

// Calcula a frequência de um associado nas reuniões ocorridas durante um determinado período, retornando um número entre 0 e 1 (ex: 0,6, indicando que o associado participou de 60% das reuniões.
public Double calcularFrequencia(int numAssociado, int numAssociacao, LocalDate inicio, LocalDate fim) throws AssociadoNaoExistente, ReuniaoNaoExistente, AssociacaoNaoExistente;

// Registra o pagamento de uma taxa, em uma associação, dentro uma determinada competência, para um associado. O valor a ser pago não pode ser menor que uma parcela, embora não precise ser exatamente duas parcelas. Uma parcela de R$20,00 por mês aceita um pagamento de R$30,00, sendo uma parcela completa e um pedaço da próxima. Associados remidos não deveriam mais realizar pagamentos de taxas administrativas vigentes em datas antes da sua remissão, gerando exceção de AssociadoJaRemido se houver tentativa de se pagar algo para esse caso. Caso o valor a ser pago seja menor que o mínimo (não sendo o ultimo do ano!) ou gerando pagamento maior que a taxa anual, gerar exceção de ValorInvalido. Lembrar de verificar valores negativos.
public ResponseEntity<?> registrarPagamento(int numAssociacao, String taxa, int vigencia, int numAssociado, LocalDate data, double valor) throws AssociacaoNaoExistente, AssociadoNaoExistente, AssociadoJaRemido, TaxaNaoExistente, ValorInvalido;

// Calcula o total de pagamentos realizado por um associado, em uma associação, para uma taxa, que possui uma vigência, dentro de um certo período de tempo.
public Double somarPagamentoDeAssociado (int numAssociacao, int numAssociado, String nomeTaxa, int vigencia, LocalDate inicio, LocalDate fim) throws AssociacaoNaoExistente, AssociadoNaoExistente, TaxaNaoExistente;

// Calcula o total de taxas previstas para um dado ano, em uma associação.
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

```


