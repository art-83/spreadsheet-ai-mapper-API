package br.devdeloop.ai_excel.controller;

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

    @GetMapping("/add")
    public ResponseEntity<?> getDto() throws JsonProcessingException {
        return sheetdbService.addOnTable("alyson lucena 18 ano pb patos 12312312305");
    }
}
