package br.com.fiap.horadoprato.horadoprato.adapters.presenters;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;

import java.util.List;

public class UsuarioPresenter {

    public static UsuarioDTO ToDTO(Usuario usuario) {

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getNome(),
                usuario.getEmail(),
                TipoUsuarioPresenter.ToDTO(usuario.getTipoUsuario()));

    }

    public static List<UsuarioDTO> ToDTO(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(UsuarioPresenter::ToDTO)
                .toList();
    }

}
