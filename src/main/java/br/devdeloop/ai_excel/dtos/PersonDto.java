package br.devdeloop.ai_excel.dtos;

public record PersonDto(
        String nome,
        String idade,
        String cpf,
        String uf,
        String cidade
) {}
