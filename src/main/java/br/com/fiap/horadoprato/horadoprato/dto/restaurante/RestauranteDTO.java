package br.com.fiap.horadoprato.horadoprato.dto.restaurante;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record RestauranteDTO(

        @Schema(description = "Id do restaurante", example = "550e8400-e29b-41d4-a716-446655440000")
        String id,

        @Schema(description = "Nome do restaurante", example = "Cantina Italiana")
        String nomeRestaurante,

        @Schema(description = "Endereco do restaurante", example = "Rua das Flores, 123")
        String endereco,

        @Schema(description = "Tipo de cozinha", example = "Italiana")
        String tipoCozinha,

        @Schema(description = "Horario de funcionamento", example = "Seg a Sex das 10h as 22h")
        String horarioFuncionamento,

        @Schema(description = "Id do usuario dono do restaurante", example = "550e8400-e29b-41d4-a716-446655440000")
        String donoId,

        @Schema(description = "Data de criacao")
        LocalDateTime dataCriacao,

        @Schema(description = "Data de ultima alteracao")
        LocalDateTime dataAlteracao

) {
}
