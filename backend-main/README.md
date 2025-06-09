# Backend do Projeto Sanea

Este é o backend do projeto Sanea, desenvolvido em Java com servidor HTTP embutido.

## Pré-requisitos

- Java 11 ou superior
- Maven
- MySQL 8.0 ou superior

## Configuração

1. Clone o repositório
2. Configure o banco de dados MySQL:
   - Crie um banco de dados chamado `sanea`
   - Execute o script `src/main/resources/schema.sql` para criar as tabelas necessárias
   - Configure as credenciais do banco de dados no arquivo `src/main/java/sanea/dao/MySqlConnection.java`

## Compilação

Para compilar o projeto, execute:

```bash
mvn clean package
```

## Execução

Para executar o projeto, use o comando:

```bash
java -jar target/sanitation-project-1.0-SNAPSHOT-jar-with-dependencies.jar
```

O servidor será iniciado na porta 8080.

## Endpoints

### Autenticação

- `POST /loginUsuario` - Login de usuário
  - Body: `email=email@exemplo.com&senha=senha123`
  - Retorna: Token JWT em caso de sucesso

- `POST /cadastroUsuario` - Cadastro de usuário
  - Body: `nome=Nome Completo&email=email@exemplo.com&telefone=11999999999&cpf=12345678900&senha=senha123`
  - Retorna: Mensagem de sucesso ou erro

## Estrutura do Projeto

- `src/main/java/sanea/`
  - `controller/` - Classes que lidam com as requisições HTTP
  - `dao/` - Classes de acesso ao banco de dados
  - `model/` - Classes de modelo
  - `util/` - Classes utilitárias
  - `Main.java` - Classe principal que inicia o servidor

## Tecnologias Utilizadas

- Java 11
- Maven
- MySQL
- JWT para autenticação
- Servidor HTTP embutido do Java 