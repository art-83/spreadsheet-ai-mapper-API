package br.devdeloop.ai_spreadsheet.dtos;

public record PersonDto(
        String nome,
        String idade,
        String cpf,
        String uf,
        String cidade
) {}
