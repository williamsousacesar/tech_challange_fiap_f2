package br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITipoUsuarioGateway {

    TipoUsuario incluir(TipoUsuario tipoUsuario);

    TipoUsuario buscarPorTpUsuario(String tpUsuario);

    TipoUsuario buscarPorId(Long id);

    Page<TipoUsuario> listarTodos(Pageable pageable);

    TipoUsuario atualizar(TipoUsuario tipoUsuario);

    void excluir(Long id);
}
