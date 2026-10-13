package br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NovoTipoUsuarioDTO(

        @NotBlank(message = "O tipo de usuario e obrigatorio")
        @Size(max = 80, message = "O tipo de usuario deve ter no maximo 80 caracteres")
        String tpUsuario,

        @NotBlank(message = "A descricao do tipo de usuario e obrigatoria")
        @Size(max = 255, message = "A descricao deve ter no maximo 255 caracteres")
        String descricaoTipoUsuario
) {
}
