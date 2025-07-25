package br.devdeloop.ai_spreadsheet.mappers;

import br.devdeloop.ai_spreadsheet.dtos.PersonDto;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    public PersonDto arrayDataToDto(String arrayData[]) {
        return new PersonDto(
                arrayData[0],
                arrayData[1],
                arrayData[2],
                arrayData[3],
                arrayData[4],
                arrayData[5]
        );
    }
}