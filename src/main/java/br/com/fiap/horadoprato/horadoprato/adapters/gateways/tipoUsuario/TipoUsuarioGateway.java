package br.com.fiap.horadoprato.horadoprato.adapters.gateways.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.IDataStorageSource;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoUsuarioGateway implements ITipoUsuarioGateway {

    @Autowired
    private IDataStorageSource dataStorageSource;

    public TipoUsuario incluir(TipoUsuario tipoUsuario) {

        final NovoTipoUsuarioDTO novoTipoUsuario = new NovoTipoUsuarioDTO(tipoUsuario.getTpUsuario(), tipoUsuario.getDescricaoTipoUsuario());

        final TipoUsuarioDTO tipoUsuarioCriado = this.dataStorageSource.incluirTipoUsuario(novoTipoUsuario);

        return TipoUsuario.builder()
                .tpUsuario(tipoUsuarioCriado.tpUsuario())
                .descricaoTipoUsuario(tipoUsuarioCriado.descricaoTipoUsuario())
                .build();
    }

    @Override
    public TipoUsuario buscarPorTpUsuario(String tpUsuario) {
        TipoUsuarioDTO tipoUsuarioDTO = this.dataStorageSource.buscarPorTpUsuario(tpUsuario);
        if (tipoUsuarioDTO == null) {
            return null;
        }
        return TipoUsuario.builder()
                .tpUsuario(tipoUsuarioDTO.tpUsuario())
                .descricaoTipoUsuario(tipoUsuarioDTO.descricaoTipoUsuario())
                .build();
    }
}
