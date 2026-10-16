package br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;

import java.util.List;

public interface IDataStorageSource {

    TipoUsuarioDTO incluirTipoUsuario(NovoTipoUsuarioDTO novoTipoUsuarioDTO);

    TipoUsuarioDTO buscarPorTpUsuario(String tpUsuario);

    TipoUsuarioDTO buscarPorId(Long id);

    List<TipoUsuarioDTO> listarTodos();

    TipoUsuarioDTO atualizarTipoUsuario(Long id, NovoTipoUsuarioDTO tipoUsuarioDTO);

    void excluirTipoUsuario(Long id);
}
