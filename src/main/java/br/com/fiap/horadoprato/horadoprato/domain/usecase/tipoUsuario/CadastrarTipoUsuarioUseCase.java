package br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioJaExisteException;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;

public class CadastrarTipoUsuarioUseCase {

    private final ITipoUsuarioGateway iTipoUsuarioGateway;

    private CadastrarTipoUsuarioUseCase(ITipoUsuarioGateway iTipoUsuarioGateway) {
        this.iTipoUsuarioGateway = iTipoUsuarioGateway;
    }

    public static CadastrarTipoUsuarioUseCase create(ITipoUsuarioGateway iTipoUsuarioGateway) {
        return new CadastrarTipoUsuarioUseCase(iTipoUsuarioGateway);
    }

    public TipoUsuario run(NovoTipoUsuarioDTO novoTipoUsuarioDTO) throws TipoUsuarioJaExisteException {

        final TipoUsuario tipoUsuarioExistente = iTipoUsuarioGateway.buscarPorTpUsuario(novoTipoUsuarioDTO.tpUsuario());

        if (tipoUsuarioExistente != null) {
            throw new TipoUsuarioJaExisteException(novoTipoUsuarioDTO.tpUsuario());
        }

        final TipoUsuario novoTipoUsuario = TipoUsuario.builder().tpUsuario(novoTipoUsuarioDTO.tpUsuario()).descricaoTipoUsuario(novoTipoUsuarioDTO.descricaoTipoUsuario()).build();

        return iTipoUsuarioGateway.incluir(novoTipoUsuario);

    }
}
