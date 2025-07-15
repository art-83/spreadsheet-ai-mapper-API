package br.devdeloop.ai_excel.service;

import br.devdeloop.ai_excel.dtos.PersonDto;
import br.devdeloop.ai_excel.mappers.PersonMapper;
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


    private final String template = "Make a CSV line, need to be on these sequence: nome, idade, cpf, uf e cidade. o csv tem apenas o conteúdo extraido do texto.";

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

    public String[] getPersonDataArray(String prompt) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper
                .readTree(getOpenAiResponse(prompt))
                .path("choices")
                .path(0)
                .path("message")
                .path("content");
        return jsonNode.asText().split(",");
    }

    public PersonDto getPersonDtoByPrompt(String prompt) throws JsonProcessingException {
        return personMapper.arrayDataToDto(getPersonDataArray(prompt));
    }
}
