package br.com.fiap.horadoprato.horadoprato.interfaces.usuario;

import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;

import java.util.List;

public interface IUsuarioDataStorageSource {

    UsuarioDTO buscarPorId(String id);

    List<UsuarioDTO> listarTodos();

    UsuarioDTO alterarTipoUsuario(String usuarioId, Long tipoUsuarioId);

    boolean existeUsuarioComTipo(Long tipoUsuarioId);
}
