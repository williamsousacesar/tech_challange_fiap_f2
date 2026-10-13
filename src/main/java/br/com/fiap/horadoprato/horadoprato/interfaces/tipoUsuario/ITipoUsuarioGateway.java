package br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;

import java.util.List;

public interface ITipoUsuarioGateway {

    TipoUsuario incluir(TipoUsuario tipoUsuario);

    TipoUsuario buscarPorTpUsuario(String tpUsuario);

    TipoUsuario buscarPorId(Long id);

    List<TipoUsuario> listarTodos();

    TipoUsuario atualizar(TipoUsuario tipoUsuario);

    void excluir(Long id);
}
