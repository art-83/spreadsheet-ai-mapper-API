# API for Automating Google Spreadsheets using AI + SheetDB

---

### About the Project

This was a quick project developed to reimagine the use of the [Spring-Security-CRUD-RestAPI](https://github.com/art-83/spring-security-crud-restAPI) project. Unfortunately, it was not pursued as a freelance job due to the company's resistance to migrating to a custom system. They opted to continue digitizing data via WhatsApp messages—an amateurish, unprofessional, and unproductive method.

Now, the purpose of this application is to **map columns and automatically insert data into Google Spreadsheets** from natural language input. In other words, using a message (which may or may not follow a standard format), you can populate the official spreadsheet automatically with the click of a button.

---

### Technologies Used

- Java 17
- Spring Boot
- Spring Web
- Spring Security
- Lombok
- Docker / Docker-Compose

---

### Package Sdocsructure

```
🗀 br.devdeloop.ai_spreadsheet
├── 🗀 configuration
├── 🗀 controller
├── 🗀 dtos
├── 🗀 mappers
├── 🗀 security
├── 🗀 services
└── ©️ AiSpreadSheetApplication
```

---

### How to Run the Application

#### Requirements:
- Docker and Docker Compose
- Maven

#### Running the App:

> [ ! **IMPORTANT** ! ]  
> Be sure to create a `.env` file to be referenced by `docker-compose` with your **environment variables**.  
> Don’t forget to **generate** a valid **API URL** from [SheetDB](https://sheetdb.io/) and add your **GitHub access token**.
> The _Google Spreadsheet_ needs to have the columns "**NOME**", "**LINGUAGENS**", "**FRAMEWORKS**", "**DATABASES**", "**CONHECIMENTO**", "**OBS**"

Expected `.env` file:
```
AI_API_URL=https://models.github.ai/inference/chat/completions
AI_MODEL=openai/gpt-4.1-mini
AI_API_KEY= your-github-access-token
SHEET_DB_URL= your-sheetdb-url-here
```

Clone the repository:
```bash
  git clone https://github.com/art-83/spreadsheet-ai-mapper-API.git
```

In the project directory, run:
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

### Expected JSON Requests

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

### API Endpoints

| Method | Endpoint                | Description                                               | Request Body                                |
|--------|-------------------------|-----------------------------------------------------------|---------------------------------------------|
| `GET`  | `/open-ai/person-list`  | Generates and returns a list of people from a prompt.     | `Prompt Request JSON` in the request body   |
| `POST` | `/sheetdb/prompt-add`   | Adds people to the spreadsheet from a natural language prompt. | `Prompt Request JSON`                  |
| `POST` | `/sheetdb/list-add`     | Directly adds a list of people to the spreadsheet.         | `Person List JSON`                          |

---