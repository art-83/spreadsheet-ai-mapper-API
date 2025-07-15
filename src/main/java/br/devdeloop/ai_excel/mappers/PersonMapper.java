package br.devdeloop.ai_excel.mappers;

import br.devdeloop.ai_excel.dtos.PersonDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    public PersonDto arrayDataToDto(String arrayData[]) {
        return new PersonDto(
                arrayData[0],
                Integer.valueOf(arrayData[1]),
                arrayData[2],
                arrayData[3],
                arrayData[4]
        );
    }
}