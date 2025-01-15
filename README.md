
# API de Contatos - Projeto em Java com Spark

Este é um projeto de API de gerenciamento de contatos, desenvolvido em Java utilizando o framework **Spark**, com gerenciamento de dependências via **Maven**. A API permite criar, listar, atualizar e excluir contatos através de endpoints RESTful.

## Tecnologias Utilizadas

- **Java** (versão 21.0.1)
- **Spark** (framework web para Java)
- **Maven** (gerenciador de dependências)
- **Thunder Client** (para testar os endpoints da API)

## Funcionalidades

### 1. **Criar Contato (POST)**
- **Endpoint:** `/contato/incluir`
- **Método:** POST
- **Parâmetros:** `cpf`, `nome`, `idade` (via body da requisição)
- **Descrição:** Adiciona um novo contato à lista de contatos.

### 2. **Listar Contatos (GET)**
- **Endpoint:** `/contato/listar`
- **Método:** GET
- **Descrição:** Retorna todos os contatos cadastrados.

### 3. **Listar Contato por CPF (GET)**
- **Endpoint:** `/contato/listar/:cpf`
- **Método:** GET
- **Parâmetros:** `cpf` (via URL)
- **Descrição:** Retorna o contato correspondente ao CPF fornecido.

### 4. **Listar Contato por Idade (GET)**
- **Endpoint:** `/contato/listar/:idade`
- **Método:** GET
- **Parâmetros:** `idade` (via URL)
- **Descrição:** Retorna os contatos filtrados por idade.

### 5. **Listar Contato por Nome (GET)**
- **Endpoint:** `/contato/listar/nome/:nome`
- **Método:** GET
- **Parâmetros:** `nome` (via URL)
- **Descrição:** Retorna os contatos filtrados por nome.

### 6. **Atualizar Contato (PUT)**
- **Endpoint:** `/contato/atualizar/:codigo`
- **Método:** PUT
- **Parâmetros:** `codigo` (via URL) e `cpf`, `nome`, `idade` (via query params)
- **Descrição:** Atualiza os dados de um contato existente, identificando-o pelo ID.

### 7. **Excluir Contato (DELETE)**
- **Endpoint:** `/contato/deletar/:codigo/:nome`
- **Método:** DELETE
- **Parâmetros:** `codigo` (via URL) e `nome` (via URL)
- **Descrição:** Exclui um contato baseado no ID e no nome fornecidos.

## Estrutura de Arquivos

- **Main.java**: Contém o código da API, definindo os endpoints e a lógica para cada operação.
- **Contato.java**: Classe modelo para representar um contato, com os atributos `cpf`, `nome`, `idade`, e `id`.

## Como Rodar o Projeto

### 1. **Clone o Repositório**
```bash
git clone https://github.com/seu-usuario/contato-api.git
```

### 2. **Instale as Dependências**
- Navegue até o diretório do projeto e execute o seguinte comando para instalar as dependências com o Maven:
```bash
mvn install
```

### 3. **Execute a API**
- Para rodar a API, execute o seguinte comando:
```bash
mvn exec:java
```

### 4. **Teste os Endpoints**
- Utilize o **Thunder Client** ou **Postman** para testar os endpoints da API, utilizando as URLs e parâmetros definidos acima.

## Exemplos de Testes com Thunder Client

### 1. **Criar Contato**
- **Método:** POST
- **URL:** `http://localhost:4567/contato/incluir`
- **Body (form-data):**
  - `cpf`: "12345678901"
  - `nome`: "Osvaldo Cruz"
  - `idade`: "30"

### 2. **Listar Todos os Contatos**
- **Método:** GET
- **URL:** `http://localhost:4567/contato/listar`

### 3. **Listar Contato por Nome**
- **Método:** GET
- **URL:** `http://localhost:4567/contato/listar/nome/Osvaldo%20Cruz`

### 4. **Atualizar Contato**
- **Método:** PUT
- **URL:** `http://localhost:4567/contato/atualizar/1?cpf=09876543210&nome=João%20Silva&idade=25`

### 5. **Excluir Contato**
- **Método:** DELETE
- **URL:** `http://localhost:4567/contato/deletar/1/Osvaldo%20Cruz`

## Observações
- O sistema armazena os contatos em uma lista temporária, ou seja, se a aplicação for reiniciada, todos os dados serão perdidos.
- O sistema trata casos em que a lista está vazia ou o contato não é encontrado, retornando mensagens de erro apropriadas.
- É possível filtrar os contatos por nome, CPF ou idade usando os endpoints específicos.

## Possíveis Melhorias
- Persistência dos dados (ex: utilizar um banco de dados).
- Validação de dados de entrada.
- Implementar autenticação e segurança para acesso aos endpoints.

---

Feito por Joseph da Costa Ribeiro
