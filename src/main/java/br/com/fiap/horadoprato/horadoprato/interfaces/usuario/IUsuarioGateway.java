package br.com.fiap.horadoprato.horadoprato.interfaces.usuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioGateway {

    Usuario buscarPorId(String id);

    Page<Usuario> listarTodos(Pageable pageable);

    Usuario alterarTipoUsuario(String usuarioId, Long tipoUsuarioId);

    boolean existeUsuarioComTipo(Long tipoUsuarioId);
}
