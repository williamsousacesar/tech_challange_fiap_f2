package br.com.fiap.horadoprato.horadoprato.dto.usuario;

import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;

public record UsuarioDTO(

        String id,

        String login,

        String nome,

        String email,

        TipoUsuarioDTO tipoUsuario
) {
}
