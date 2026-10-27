package br.com.fiap.horadoprato.horadoprato.interfaces.usuario;

import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioDataStorageSource {

    UsuarioDTO buscarPorId(String id);

    Page<UsuarioDTO> listarTodos(Pageable pageable);

    UsuarioDTO alterarTipoUsuario(String usuarioId, Long tipoUsuarioId);

    boolean existeUsuarioComTipo(Long tipoUsuarioId);
}
