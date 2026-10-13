package br.com.fiap.horadoprato.horadoprato.dto.usuario;

import jakarta.validation.constraints.NotNull;

public record AlterarTipoUsuarioDTO(

        @NotNull(message = "O id do tipo de usuario e obrigatorio")
        Long tipoUsuarioId
) {
}
