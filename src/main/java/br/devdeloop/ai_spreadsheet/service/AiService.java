package br.devdeloop.ai_spreadsheet.service;

import br.devdeloop.ai_spreadsheet.dtos.PersonDto;
import br.devdeloop.ai_spreadsheet.mappers.PersonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonMapper personMapper;

    @Value("${AI_API_KEY}")
    private String aiApiKey;

    @Value("${AI_API_URL}")
    private String aiApiUrl;

    @Value("${AI_MODEL}")
    private String aiModel;


    private final String template = "Resuma a seguinte ficha técnica em uma linha CSV no seguinte formato: nome, linguagens, frameworks, banco de dados, conhecimentos gerais, descrição breve. Separe múltiplos itens com '-' e deixe campos vazios se não houver informação. Exemplo de entrada: Nome: Edson Alves Langs: Typescript, Python, Java. Framework: Express, Fastify, Nestjs, Spring Boot, Flask, Django. Database: Postgres, Sqlite, Firebase, MongoDB. Resutado esperado: Edson Alves, Typescript - Python - Java, Express - Fastify - Nestjs - Spring Boot - Flask - Django, Postgres - Sqlite - Firebase - MongoDB, , 'Descrição baseada nas infomações anteriores', ignore números aleatórios no texto, são os horários que as informações foram enviadas";

    public HttpEntity<?> promptRequestBuilder(String prompt) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization","Bearer " + aiApiKey);

        String jsonBody = """
                {
                    "messages": [
                        {"role": "system", "content": "%s"},
                        {"role": "user", "content": "%s"}
                    ],
                    "temperature": 0,
                    "top_p": 0.3,
                    "model": "%s"
                }
                """.formatted(template, prompt, aiModel);

        HttpEntity<?> requestBody = new HttpEntity<>(jsonBody, httpHeaders);

        return requestBody;
    }

    public String getOpenAiResponse(String prompt) {
        return restTemplate.exchange(
                aiApiUrl,
                HttpMethod.POST,
                promptRequestBuilder(prompt),
                String.class
        ).getBody();
    }

    public String[] getJsonAiReponseContent(String prompt) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper
                .readTree(getOpenAiResponse(prompt))
                .path("choices")
                .path(0)
                .path("message")
                .path("content");

        return jsonNode.asText().split("[|]");
    }

    public List<PersonDto> getPersonDtoListByPrompt(String prompt) throws JsonProcessingException {
        List<PersonDto> personDtoList = new ArrayList<>();

        String[] personDataArray = getJsonAiReponseContent(prompt);

        for(String data : personDataArray) {
            personDtoList.add(personMapper.arrayDataToDto(data.split("[,]")));
        }

        return personDtoList;
    }
}