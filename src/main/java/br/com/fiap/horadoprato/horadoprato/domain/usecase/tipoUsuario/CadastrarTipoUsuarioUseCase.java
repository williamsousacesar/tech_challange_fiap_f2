package br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
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

    public TipoUsuario run(String tpUsuario, String descricaoTipoUsuario) throws TipoUsuarioJaExisteException {

        final TipoUsuario tipoUsuarioExistente = iTipoUsuarioGateway.buscarPorTpUsuario(tpUsuario);

        if (tipoUsuarioExistente != null) {
            throw new TipoUsuarioJaExisteException("Tipo de usuario '" + tpUsuario + "' ja existe");
        }

        final TipoUsuario novoTipoUsuario = TipoUsuario.builder().tpUsuario(tpUsuario).descricaoTipoUsuario(descricaoTipoUsuario).build();

        return iTipoUsuarioGateway.incluir(novoTipoUsuario);

    }
}
