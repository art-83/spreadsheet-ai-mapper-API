package br.devdeloop.ai_excel.dtos;

public record PersonDto(
        String nome,
        Integer idade,
        String cpf,
        String uf,
        String cidade
) {}
