package br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;

public interface IDataStorageSource {

    TipoUsuarioDTO incluirTipoUsuario(NovoTipoUsuarioDTO novoTipoUsuarioDTO);

    TipoUsuarioDTO buscarPorTpUsuario(String tpUsuario);
}
