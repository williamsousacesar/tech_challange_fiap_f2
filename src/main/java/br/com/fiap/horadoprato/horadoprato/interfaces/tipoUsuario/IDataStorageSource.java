package br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDataStorageSource {

    TipoUsuarioDTO incluirTipoUsuario(NovoTipoUsuarioDTO novoTipoUsuarioDTO);

    TipoUsuarioDTO buscarPorTpUsuario(String tpUsuario);

    TipoUsuarioDTO buscarPorId(Long id);

    Page<TipoUsuarioDTO> listarTodos(Pageable pageable);

    TipoUsuarioDTO atualizarTipoUsuario(Long id, NovoTipoUsuarioDTO tipoUsuarioDTO);

    void excluirTipoUsuario(Long id);
}
