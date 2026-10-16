package br.com.fiap.horadoprato.horadoprato.dto.restaurante;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NovoRestauranteDTO(

        @Schema(description = "Nome do restaurante", example = "Cantina Italiana")
        @NotBlank(message = "O nome do restaurante e obrigatorio")
        @Size(max = 120, message = "O nome deve ter no maximo 120 caracteres")
        String nomeRestaurante,

        @Schema(description = "Endereco do restaurante", example = "Rua das Flores, 123")
        @NotBlank(message = "O endereco e obrigatorio")
        @Size(max = 200, message = "O endereco deve ter no maximo 200 caracteres")
        String endereco,

        @Schema(description = "Tipo de cozinha", example = "Italiana")
        @NotBlank(message = "O tipo de cozinha e obrigatorio")
        @Size(max = 80, message = "O tipo de cozinha deve ter no maximo 80 caracteres")
        String tipoCozinha,

        @Schema(description = "Horario de funcionamento", example = "Seg a Sex das 10h as 22h")
        @NotBlank(message = "O horario de funcionamento e obrigatorio")
        @Size(max = 100, message = "O horario de funcionamento deve ter no maximo 100 caracteres")
        String horarioFuncionamento,

        @Schema(description = "Id do usuario dono do restaurante", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull(message = "O id do dono e obrigatorio")
        String donoId

) {
}
