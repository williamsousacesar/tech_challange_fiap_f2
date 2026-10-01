package br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario;

import jakarta.persistence.Column;

public record NovoTipoUsuarioDTO(

        String tpUsuario,

         String descricaoTipoUsuario
) {
}
