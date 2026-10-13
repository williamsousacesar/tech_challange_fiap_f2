package br.com.fiap.horadoprato.horadoprato.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AlterarTipoUsuarioDTO(

        @Schema(description = "Id do tipo de usuario a ser associado", example = "1")
        @NotNull(message = "O id do tipo de usuario e obrigatorio")
        Long tipoUsuarioId
) {
}
