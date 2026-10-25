package br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario;

import io.swagger.v3.oas.annotations.media.Schema;

public record TipoUsuarioDTO(

        @Schema(description = "Id do tipo de usuario", example = "1")
        Long id,

        @Schema(description = "Nome do tipo de usuario", example = "DONO_RESTAURANTE")
        String tpUsuario,

        @Schema(description = "Descricao do tipo de usuario", example = "Dono de Restaurante")
        String descricaoTipoUsuario
) {
}
