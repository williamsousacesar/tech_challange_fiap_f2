package br.com.fiap.horadoprato.horadoprato.dto.usuario;

import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import io.swagger.v3.oas.annotations.media.Schema;

public record UsuarioDTO(

        @Schema(description = "Id do usuario", example = "550e8400-e29b-41d4-a716-446655440001")
        String id,

        @Schema(description = "Login do usuario", example = "carlos_dono")
        String login,

        @Schema(description = "Nome do usuario", example = "Carlos Oliveira")
        String nome,

        @Schema(description = "Email do usuario", example = "carlos.dono@restaurante.com")
        String email,

        @Schema(description = "Tipo de usuario")
        TipoUsuarioDTO tipoUsuario
) {
}
