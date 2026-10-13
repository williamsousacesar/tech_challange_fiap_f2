package br.com.fiap.horadoprato.horadoprato.interfaces.usuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;

import java.util.List;

public interface IUsuarioGateway {

    Usuario buscarPorId(String id);

    List<Usuario> listarTodos();

    Usuario alterarTipoUsuario(String usuarioId, Long tipoUsuarioId);

    boolean existeUsuarioComTipo(Long tipoUsuarioId);
}
