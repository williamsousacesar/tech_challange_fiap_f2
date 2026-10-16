package br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NovoTipoUsuarioDTO(

        @Schema(description = "Nome do tipo de usuario", example = "DONO_RESTAURANTE")
        @NotBlank(message = "O tipo de usuario e obrigatorio")
        @Size(max = 80, message = "O tipo de usuario deve ter no maximo 80 caracteres")
        String tpUsuario,

        @Schema(description = "Descricao opcional do tipo de usuario", example = "Dono de Restaurante")
        @Size(max = 255, message = "A descricao deve ter no maximo 255 caracteres")
        String descricaoTipoUsuario
) {
}
