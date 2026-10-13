package br.com.fiap.horadoprato.horadoprato.adapters.gateways.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.IDataStorageSource;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoUsuarioGateway implements ITipoUsuarioGateway {

    @Autowired
    private IDataStorageSource dataStorageSource;

    public TipoUsuario incluir(TipoUsuario tipoUsuario) {

        final NovoTipoUsuarioDTO novoTipoUsuario = new NovoTipoUsuarioDTO(tipoUsuario.getTpUsuario(), tipoUsuario.getDescricaoTipoUsuario());

        final TipoUsuarioDTO tipoUsuarioCriado = this.dataStorageSource.incluirTipoUsuario(novoTipoUsuario);

        return toEntity(tipoUsuarioCriado);
    }

    @Override
    public TipoUsuario buscarPorTpUsuario(String tpUsuario) {
        return toEntity(this.dataStorageSource.buscarPorTpUsuario(tpUsuario));
    }

    @Override
    public TipoUsuario buscarPorId(Long id) {
        return toEntity(this.dataStorageSource.buscarPorId(id));
    }

    @Override
    public List<TipoUsuario> listarTodos() {
        return this.dataStorageSource.listarTodos().stream()
                .map(TipoUsuarioGateway::toEntity)
                .toList();
    }

    @Override
    public TipoUsuario atualizar(TipoUsuario tipoUsuario) {
        final NovoTipoUsuarioDTO dadosAtualizados = new NovoTipoUsuarioDTO(tipoUsuario.getTpUsuario(), tipoUsuario.getDescricaoTipoUsuario());

        return toEntity(this.dataStorageSource.atualizarTipoUsuario(tipoUsuario.getId(), dadosAtualizados));
    }

    @Override
    public void excluir(Long id) {
        this.dataStorageSource.excluirTipoUsuario(id);
    }

    public static TipoUsuario toEntity(TipoUsuarioDTO tipoUsuarioDTO) {
        if (tipoUsuarioDTO == null) {
            return null;
        }
        return TipoUsuario.builder()
                .id(tipoUsuarioDTO.id())
                .tpUsuario(tipoUsuarioDTO.tpUsuario())
                .descricaoTipoUsuario(tipoUsuarioDTO.descricaoTipoUsuario())
                .build();
    }
}
