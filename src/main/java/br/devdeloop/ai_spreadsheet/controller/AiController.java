package br.devdeloop.ai_spreadsheet.controller;

import br.devdeloop.ai_spreadsheet.dtos.PersonDto;
import br.devdeloop.ai_spreadsheet.dtos.PromptRequest;
import br.devdeloop.ai_spreadsheet.service.AiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/open-ai")
public class AiController {

    @Autowired
    private AiService aiService;

    @GetMapping("/person-list")
    public List<PersonDto> getListPersonDto(@RequestBody PromptRequest promptRequest) throws JsonProcessingException {
        return aiService.getPersonDtoListByPrompt(promptRequest.prompt());
    }
}