# Sanea - Sistema de Gestão de Saneamento

O Sanea é um sistema web desenvolvido para gerenciar e monitorar problemas de saneamento básico. Permite que os cidadãos registrem problemas relacionados à infraestrutura de saneamento e acompanhem o status das solicitações.

## Tecnologias Utilizadas

### Backend
- Java 17
- Maven
- MySQL
- JWT para autenticação
- Jakarta JSON

### Frontend
- Next.js 14
- TypeScript
- Tailwind CSS
- Shadcn/ui
- Axios
- React Hook Form
- Zod

## Pré-requisitos

- Java 17 ou superior
- Node.js 18 ou superior
- MySQL 8.0 ou superior
- Maven
- NPM ou Yarn

## Configuração do Ambiente

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/sanitation-project.git
cd sanitation-project
```

### 2. Configuração do Banco de Dados

1. Crie um banco de dados MySQL chamado `sanea`:
```sql
CREATE DATABASE sanea;
```

2. Configure as credenciais do banco de dados no arquivo `backend-main/src/main/java/sanea/dao/MySqlConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/sanea";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

### 3. Configuração do Backend

1. Navegue até a pasta do backend:
```bash
cd backend-main
```

2. Compile o projeto:
```bash
mvn clean install
```

3. Execute o servidor:
```bash
java -jar target/sanitation-project-1.0-SNAPSHOT-jar-with-dependencies.jar
```

O servidor backend estará rodando em `http://localhost:8080`

### 4. Configuração do Frontend

1. Navegue até a pasta do frontend:
```bash
cd frontend-main
```

2. Instale as dependências:
```bash
npm install
# ou
yarn install
```

3. Execute o servidor de desenvolvimento:
```bash
npm run dev
# ou
yarn dev
```

O frontend estará disponível em `http://localhost:3000`

## Estrutura do Projeto

```
sanitation-project/
├── backend-main/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── sanea/
│   │   │   │       ├── controller/
│   │   │   │       ├── dao/
│   │   │   │       ├── model/
│   │   │   │       └── util/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
└── frontend-main/
    ├── app/
    ├── components/
    ├── lib/
    ├── public/
    └── package.json
```

## Funcionalidades

- Cadastro e autenticação de usuários
- Perfil do usuário com informações pessoais
- Registro de problemas de saneamento
- Acompanhamento de solicitações
- Interface responsiva e moderna

## Endpoints da API

### Autenticação
- `POST /cadastroUsuario` - Cadastro de novo usuário
- `POST /loginUsuario` - Login de usuário

### Perfil
- `GET /perfil` - Obter dados do perfil
- `PUT /perfil` - Atualizar dados do perfil

### Relatos
- `POST /relatar` - Registrar novo problema de saneamento

## Contribuindo

1. Faça um Fork do projeto
2. Crie uma Branch para sua Feature (`git checkout -b feature/AmazingFeature`)
3. Faça o Commit das suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Faça o Push para a Branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## Agradecimentos

- [Next.js](https://nextjs.org/)
- [Tailwind CSS](https://tailwindcss.com/)
- [Shadcn/ui](https://ui.shadcn.com/)
- [Java](https://www.java.com/)
- [MySQL](https://www.mysql.com/) 