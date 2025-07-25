# API para automação de planilhas no Google SpreadSheets usando AI + SheetDB

---

### Sobre o projeto
Esse foi um projeto rápido desenvolvido para reimaginar a aplicabilidade do projeto [Spring-Security-CRUD-RestAPI](https://github.com/art-83/spring-security-crud-restAPI), que infelizmente não foi para frente como um _freelance_, devido a resistência da empresa para a migração para um sistema próprio, que preferiu manter a digitalização dos dados via mensagens de _Whatsapp_ de forma amadora, não-profissional e improdutiva. Agora, o propósito dessa aplicação, é fazer o mapeamento de colunas e inserção automática em planilhas no Google SpreadSheets a partir de linguagem natural, ou seja, com uma mensagem (que pode, ou não estar padronizada), preencher automaticamente  a planilha oficial com apenas um botão.

--- 

### Tecnologias usadas

- Java 17
- Spring Boot
- Spring Web
- Spring Security
- Lombok
- Docker / Docker-Compose

---

### Estrutura dos packages

🗀 br.devdeloop.ai_spreadsheet\
├── 🗀 configuration\
├── 🗀 controller\
├── 🗀 dtos\
├── 🗀 mappers\
├── 🗀 security\
├── 🗀 services\
└── ©️ AiSpreadSheetApplication

---

### Como executar a aplicação


#### Requisitos:
- Docker e docker-compose
- Maven

#### Executando:
> [ ! **IMPORTANTE** ! ] Lembre-se de gerar um arquivo `.env` para o `docker-compose` referenciar as **variáveis de ambiente** Não se esqueça de **gerar** uma **_URL_ válida** da API do [SheetDB](https://sheetdb.io/) e adicionar teu **Token de acesso** do _GitHub_. A _Planilha do Google_ precisa ter as colunas: "**NOME**", "**LINGUAGENS**", "**FRAMEWORKS**", "**DATABASES**", "**CONHECIMENTO**", "**OBS**"

Arquivo `.env` esperado:
```
AI_API_URL=https://models.github.ai/inference/chat/completions
AI_MODEL=openai/gpt-4.1-mini
AI_API_KEY= insira-seu-token-de-acesso-github
SHEET_DB_URL= insira-a-url-do-sheetdb-aqui
```

Em um repositório vazio, abra o terminal e digite:
```bash
  git clone https://github.com/art-83/spreadsheet-ai-mapper-API.git
```
No diretório da aplicação, execute no terminal:
```bash
  mvn clean package
```
```bash
  docker-compose build
```
```bash
  docker-compose up
```

---

### JSON Request esperados:

#### Prompt JSON (example):
```json
{
  "prompt": "name languages frameworks databases (OBS is auto-generated)"
}
```

#### Person List JSON (example):
```json
[
  {
    "NOME": "Name",
    "LINGUAGENS": "Programming languages",
    "FRAMEWORKS": "Frameworks",
    "DATABASES": "Data bases",
    "CONHECIMENTO": "Skills",
    "OBS": "Observations"
  },
  {
    "NOME": "Name",
    "LINGUAGENS": "Programming languages",
    "FRAMEWORKS": "Frameworks",
    "DATABASES": "Data bases",
    "CONHECIMENTO": "Skills",
    "OBS": "Observations"
  }
]
```

---
### Endpoints

| Método | Endpoint                | Descrição                                                 | Corpo da Requisição     |
| ------ |-------------------------|-----------------------------------------------------------|-------------------------|
| `GET`  | `/open-ai/person-list`  | Gera e retorna uma lista de pessoas a partir de um prompt.| `Prompt Request JSON`   |
| `POST` | `/sheetdb/prompt-add`   | Adiciona pessoas na planilha a partir de um prompt.       | `Prompt Request JSON`   |
| `POST` | `/sheetdb/list-add`     | Adiciona diretamente uma lista de pessoas na planilha.    | `Person List JSON`      |
