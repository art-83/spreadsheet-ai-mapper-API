package br.devdeloop.ai_spreadsheet.controller;

import br.devdeloop.ai_spreadsheet.dtos.PersonDto;
import br.devdeloop.ai_spreadsheet.dtos.PromptRequest;
import br.devdeloop.ai_spreadsheet.service.SheetdbService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sheetdb")
public class SheetdbController {

    @Autowired
    private SheetdbService sheetdbService;

    @PostMapping("/prompt-add")
    public ResponseEntity<?> addByPrompt(@RequestBody PromptRequest promptRequest) throws JsonProcessingException {
        return sheetdbService.addOnTableByPrompt(promptRequest.prompt());
    }

    @PostMapping("/list-add")
    public ResponseEntity<?> addByList(@RequestBody List<PersonDto> personDtoList) {
        return sheetdbService.addOnTableByList(personDtoList);
    }
}