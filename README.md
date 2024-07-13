# Sistema para Controle de uma Associação

### Para executar este projeto
1.  baixe ou clone o projeto `git clone https://github.com/fernandoosorio/ufpi-associacao-api`

2. Acesse o diretório  `cd ufpi-associacao-api`

3. Verificar versão do java no terminal/Prompt de Comando ( `java -version` ) deve estar na versão 17

    * No Windows,se o comando não for reconhecido verificar as variáveis de ambiente. 

4. No linux para escolher diferentes versões do java é preciso fazer `sudo update-alternatives --config java`

5. Execute `mvn dependency:resolve`  (precisa estar na vesão 17 do Java)  
    - Para instalar mvn no [windows](https://dicasdeprogramacao.com.br/como-instalar-o-maven-no-windows/)  ou [linux](https://dicasdeprogramacao.com.br/como-instalar-o-maven-no-windows/)

6. Se tudo ocorreu como esperado: no console deve aparecer `[INFO] BUILD SUCCESS`


7. Abrir o projeto na sua IDE de preferência.

8. Executar o projeto ( executar/debugar o arquivo `AssociacaoApiApplication.java`)

9. Para visualizar o banco de dados:
    -   Acessar  http://localhost:8080/h2-console
        - Preencher os campos:
            - JDBC URL: `jdbc:h2:mem:associacao`
            - User Name: `sa`
            - Password: `123`
            - Click em `Connect`

10. Para visualizar os endereços da API:
    -   Acessar  http://localhost:8080/swagger-ui/index.html

