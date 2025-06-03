# Sanitation Project

Projeto completo de saneamento básico, com backend em Java (Spring Boot) e frontend em Next.js (React).

## Sumário
- [Descrição](#descrição)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do Backend](#configuração-do-backend)
- [Configuração do Frontend](#configuração-do-frontend)
- [Integração Frontend/Backend](#integração-frontendbackend)
- [Scripts Úteis](#scripts-úteis)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Contato](#contato)

---

## Descrição
Sistema para registro, acompanhamento e análise de reclamações relacionadas a problemas de saneamento em regiões urbanas. Permite cadastro de usuários, envio de relatos, autenticação JWT e visualização de dados por região.

## Tecnologias
- **Backend:** Java 17, Spring Boot, Spring Data JPA, MySQL, JWT, Maven
- **Frontend:** Next.js (React), TypeScript, TailwindCSS

## Pré-requisitos
- Java 17+ (JDK)
- Node.js 18+
- MySQL 8+
- (Opcional) Maven instalado globalmente (ou use o Maven Wrapper do projeto)

## Configuração do Backend
1. **Banco de Dados:**
   - Crie um banco MySQL chamado `sanea_mais`.
   - Importe o arquivo `estrutura.sql` para criar as tabelas e dados iniciais.

2. **Configuração:**
   - O arquivo `src/main/resources/application.properties` já está configurado para:
     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/sanea_mais?useSSL=false&serverTimezone=UTC
     spring.datasource.username=root
     spring.datasource.password=root1234
     ```
   - Altere o usuário/senha conforme sua instalação.

3. **Rodando o Backend:**
   - No terminal, acesse a pasta `backend-main`:
     ```sh
     cd backend-main
     .\mvnw.cmd spring-boot:run
     ```
   - O backend estará disponível em: `http://localhost:8080/api`

## Configuração do Frontend
1. **Variáveis de Ambiente:**
   - Crie um arquivo `.env.local` na pasta `frontend-main` com:
     ```
     NEXT_PUBLIC_API_URL=http://localhost:8080/api
     ```

2. **Rodando o Frontend:**
   - No terminal, acesse a pasta `frontend-main`:
     ```sh
     cd frontend-main
     npm install
     npm run dev
     ```
   - O frontend estará disponível em: `http://localhost:3000`

## Integração Frontend/Backend
- O frontend faz chamadas HTTP para o backend usando a variável `NEXT_PUBLIC_API_URL`.
- O backend já está configurado para aceitar CORS do frontend (`localhost:3000`).
- Teste o cadastro de usuário e login para validar a integração.

## Scripts Úteis
- **Compilar backend:**
  ```sh
  cd backend-main
  .\mvnw.cmd clean install
  ```
- **Rodar backend:**
  ```sh
  .\mvnw.cmd spring-boot:run
  ```
- **Rodar frontend:**
  ```sh
  cd frontend-main
  npm run dev
  ```

## Estrutura do Projeto
```
/caminho/do/projeto/
├── backend-main/         # Backend Java Spring Boot
│   ├── src/
│   ├── pom.xml
│   └── ...
├── frontend-main/        # Frontend Next.js
│   ├── app/
│   ├── package.json
│   └── ...
└── estrutura.sql         # Script SQL do banco de dados
```

## Contato
- Dúvidas ou sugestões: abra uma issue ou entre em contato com o desenvolvedor responsável. 