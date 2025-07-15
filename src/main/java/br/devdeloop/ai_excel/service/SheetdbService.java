package br.devdeloop.ai_excel.service;

import br.devdeloop.ai_excel.dtos.PersonDto;
import br.devdeloop.ai_excel.mappers.PersonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SheetdbService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AiService aiService;

    @Value("${SHEET_DB_URL}")
    private String sheetDbUrl;

    public HttpEntity<?> personRequestBuilder(String prompt) throws JsonProcessingException {
        PersonDto personDto = aiService.getPersonDtoByPrompt(prompt);
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Accept", "application/json");
        httpHeaders.add("Content-Type", "application/json");

        String jsonBody = """
                {
                    "data": [
                        {
                            "nome": "%s",
                            "idade": %d,
                            "cpf":"%s",
                            "uf": "%s",
                            "cidade": "%s"
                        }
                    ]
                }
                """
                .formatted(
                        personDto.nome(),
                        personDto.idade(),
                        personDto.cpf(),
                        personDto.uf(),
                        personDto.cidade());

        return new HttpEntity<>(jsonBody, httpHeaders);
    }

    public ResponseEntity<?> addOnTable(String prompt) throws JsonProcessingException {
        restTemplate.exchange(
                sheetDbUrl,
                HttpMethod.POST,
                personRequestBuilder(prompt),
                String.class
        );
        return ResponseEntity.ok("Added.");
    }
}
