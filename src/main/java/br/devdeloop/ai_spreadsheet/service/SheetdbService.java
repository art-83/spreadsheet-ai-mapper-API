package br.devdeloop.ai_spreadsheet.service;

import br.devdeloop.ai_spreadsheet.dtos.PersonDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SheetdbService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AiService aiService;

    @Value("${SHEET_DB_URL}")
    private String sheetDbUrl;

    public HttpEntity<?> personPostRequestBuilder(String prompt) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<PersonDto> personDtoList = aiService.getPersonDtoListByPrompt(prompt);

        httpHeaders.add("Accept", "application/json");
        httpHeaders.add("Content-Type", "application/json");

        System.out.println(new HttpEntity<>(personDtoList, httpHeaders));

        return new HttpEntity<>(personDtoList, httpHeaders);
    }

    public ResponseEntity<?> addOnTableByPrompt(String prompt) throws JsonProcessingException {
        restTemplate.exchange(
                sheetDbUrl,
                HttpMethod.POST,
                personPostRequestBuilder(prompt),
                String.class
        );
        return ResponseEntity.ok("Added.");
    }

    public ResponseEntity<?> addOnTableByList(List<PersonDto> personDtoList) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<?> httpEntity = new HttpEntity<>(personDtoList, httpHeaders);

        restTemplate.exchange(
                sheetDbUrl,
                HttpMethod.POST,
                httpEntity,
                String.class
        );
        return ResponseEntity.ok("Added.");
    }
}
