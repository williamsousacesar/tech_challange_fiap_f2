package br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;

public interface ITipoUsuarioGateway {

    TipoUsuario incluir(TipoUsuario tipoUsuario);

    TipoUsuario buscarPorTpUsuario(String tpUsuario);
}
