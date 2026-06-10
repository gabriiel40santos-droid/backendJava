# 🌱 Saúde Comunitária: Transformando Vidas

> API REST back-end desenvolvida com Spring Boot para apoiar o sistema de cadastro e gestão de unidades de saúde comunitária.

---

## 📋 Descrição do Projeto

O **Saúde Comunitária** é uma plataforma desenvolvida para enfrentar os maiores desafios que a população enfrenta ao tentar acessar atendimento médico. O back-end desta aplicação é responsável por gerenciar as unidades de saúde disponíveis na comunidade, servindo como base para funcionalidades como agendamento de consultas, educação em saúde e campanhas de conscientização.

---

## 🎯 Problema que Resolve

A população brasileira enfrenta 4 grandes barreiras no acesso à saúde:

- 🔒 **Acesso limitado** — filas enormes e dificuldade de conseguir consultas
- ℹ️ **Falta de informação** — desconhecimento sobre prevenção e serviços disponíveis
- ⚖️ **Desigualdade no atendimento** — distribuição desigual dos serviços de saúde
- 🏥 **Doenças crônicas sem tratamento** — falta de acompanhamento contínuo

Nossa solução propõe um sistema de agendamento acessível, área de educação em saúde, colaboração com profissionais locais e campanhas de conscientização diretamente pelo celular.

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 17 / 21 | Linguagem principal |
| Spring Boot | 3.3.5 | Framework back-end |
| Spring Web | — | Criação da API REST |
| Spring Data JPA | — | Persistência de dados |
| Hibernate | 6.5 | ORM / mapeamento objeto-relacional |
| H2 Database | — | Banco de dados em desenvolvimento |
| Lombok | 1.18.32 | Redução de boilerplate |
| Maven | 3.x | Gerenciamento de dependências |

---

## ▶️ Como Rodar o Projeto

### Pré-requisitos

- [Java 17+](https://adoptium.net/) instalado
- [Maven 3.8+](https://maven.apache.org/) instalado
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) (recomendado) ou VS Code

### Passo a passo

**1. Clone o repositório**
```bash
git clone https://github.com/seu-usuario/saude-api.git
cd saude-api
```

**2. Compile o projeto**
```bash
mvn clean install -DskipTests
```

**3. Execute a aplicação**
```bash
./mvnw spring-boot:run
```

**4. Acesse a API**
```
http://localhost:8080/api/v1/unidades-saude
```

**5. Acesse o banco de dados H2 (desenvolvimento)**
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:file:./database/saude-db
Usuário:  sa
Senha:    (deixar em branco)
```

---

## 📡 Endpoints da API

Base URL: `http://localhost:8080/api/v1`

### Unidades de Saúde

| Método | Rota | Descrição | Status |
|---|---|---|---|
| `POST` | `/unidades-saude` | Cadastrar nova unidade | `201 Created` |
| `GET` | `/unidades-saude` | Listar com filtros e paginação | `200 OK` |
| `GET` | `/unidades-saude/{id}` | Buscar unidade por ID | `200 OK` |
| `PUT` | `/unidades-saude/{id}` | Atualizar unidade completa | `200 OK` |
| `DELETE` | `/unidades-saude/{id}` | Deletar unidade | `204 No Content` |
| `PATCH` | `/unidades-saude/{id}/alternar-ativo` | Ativar / desativar | `200 OK` |

### Filtros disponíveis no GET `/unidades-saude`

```
?nome=UBS          → filtra por nome (parcial)
?tipo=Hospital     → filtra por tipo exato
?ativo=true        → filtra por status
?page=0&size=10    → paginação
?sort=nome,asc     → ordenação
```

### Exemplos de Requisição

**Cadastrar unidade (POST)**
```json
POST /api/v1/unidades-saude
Content-Type: application/json

{
  "nome": "UBS Centro",
  "endereco": "Rua das Flores, 100 - Centro",
  "tipo": "UBS",
  "telefone": "(81) 3000-0001",
  "email": "ubs.centro@saude.gov.br",
  "ativo": true
}
```

**Resposta de erro padronizada**
```json
{
  "timestamp": "2026-05-19T20:00:00",
  "status": 404,
  "erro": "Não Encontrado",
  "mensagem": "Unidade de Saúde não encontrado(a) com id: 99"
}
```

---

## 🧪 Como Rodar os Testes

```bash
# Rodar todos os testes
mvn test

# Rodar com relatório detalhado
mvn test -Dsurefire.failIfNoSpecifiedTests=false
```

> Os testes utilizam o banco H2 em memória — nenhuma configuração adicional é necessária.

---

## 📁 Estrutura de Pastas

```
src/
└── main/
    ├── java/com/example/demo/
    │   ├── controller/          # Endpoints HTTP e roteamento
    │   │   └── UnidadeSaudeController.java
    │   ├── service/             # Regras de negócio
    │   │   ├── UnidadeSaudeService.java
    │   │   └── impl/
    │   │       └── UnidadeSaudeServiceImpl.java
    │   ├── repository/          # Acesso ao banco de dados
    │   │   └── UnidadeSaudeRepository.java
    │   ├── entity/              # Mapeamento JPA / tabelas
    │   │   └── UnidadeSaude.java
    │   ├── dto/
    │   │   ├── request/         # Contrato de entrada da API
    │   │   │   └── UnidadeSaudeRequest.java
    │   │   └── response/        # Contrato de saída da API
    │   │       └── UnidadeSaudeResponse.java
    │   ├── mapper/              # Conversão Entity <-> DTO
    │   │   └── UnidadeSaudeMapper.java
    │   └── exception/           # Tratamento centralizado de erros
    │       ├── GlobalExceptionHandler.java
    │       ├── ResourceNotFoundException.java
    │       └── BusinessException.java
    └── resources/
        ├── application.properties   # Configurações da aplicação
        └── data.sql                 # Dados iniciais de exemplo
```

---

## 👥 Integrantes do Grupo

| Nome | Função |
|---|---|
| *Isandra Micaelle Lima Dos Santos* | Back-end |
| *(Gabriel Augusto Santos Alves)* | Back-end |
| *(Lislanny Silva Alves)* | Back-end |
| *(Darllan Jeferson Santiago)* | Back-end |
| *(Ismael Silva de Melo)* | Back-end |
| *(Ivanildo Jose de Holanda)* | Back-end |
| *(Nycollas Douglas da Silva)* | Back-end |


---

## 📌 Status do Projeto

| Semana | Entrega | Status |
|---|---|---|
| Semana 4 | Configuração do ambiente, primeira rota, estrutura inicial | ✅ Concluído |
| Semana 5 | README, DER do banco, Pitch | ✅ Concluído |
| Semana 6 | CRUD completo, integração com banco, DTOs, erros 404/400 | ✅ Concluído |

---

<p align="center">Desenvolvido com 💚 para comunidades mais saudáveis</p>

## 🔒 Autenticação e Segurança (Semana 7)

A API agora conta com autenticação baseada em **JSON Web Tokens (JWT)** para proteção de rotas privadas e criptografia **BCrypt** para o armazenamento seguro de senhas no banco de dados.

### 🛣️ Controle de Rotas da API

As rotas foram divididas entre públicas (livres para acesso) e privadas (requerem o token JWT no cabeçalho da requisição).

| Método | Rota | Acesso | Descrição |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/auth/register` | **Público** | Cadastro de novos usuários (LoginRequestDTO) |
| `POST` | `/api/v1/auth/login` | **Público** | Autenticação e geração do Token JWT |
| `GET` | `/api/v1/status` | **Público** | Verificação de status da API |
| `POST` | `/api/v1/unidades-saude` | 🔒 **Privado** | Cadastrar nova unidade de saúde |
| `PUT` | `/api/v1/unidades-saude/{id}` | 🔒 **Privado** | Atualizar dados de uma unidade |
| `DELETE` | `/api/v1/unidades-saude/{id}` | 🔒 **Privado** | Deletar uma unidade do sistema |
| `GET` | `/api/v1/usuarios` | 🔒 **Privado** | Listagem de usuários cadastrados |

---

### 🚀 Como testar no Postman / Insomnia

#### 1. Cadastrar um Usuário (`POST /api/v1/auth/register`)
Envie os dados do novo usuário no corpo da requisição (a senha será criptografada automaticamente com BCrypt antes de salvar).
* **Body (JSON):**
```json
{
  "nome": "Gabriel Aluno",
  "email": "gabriel@saude.gov.br",
  "senha": "senhaSegura123",
  "role": "ADMIN"
}
