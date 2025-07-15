package br.devdeloop.ai_excel.controller;

import br.devdeloop.ai_excel.dtos.PersonDto;
import br.devdeloop.ai_excel.dtos.PromptRequest;
import br.devdeloop.ai_excel.service.SheetdbService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sheetdb")
public class SheetdbController {

    @Autowired
    private SheetdbService sheetdbService;

    @PostMapping("/add")
    public ResponseEntity<?> getDto(@RequestBody PromptRequest promptRequest) throws JsonProcessingException {
        return sheetdbService.addOnTable(promptRequest.prompt());
    }
}