package br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;

public class BuscarTipoUsuarioPorIdUseCase {

    private final ITipoUsuarioGateway iTipoUsuarioGateway;

    private BuscarTipoUsuarioPorIdUseCase(ITipoUsuarioGateway iTipoUsuarioGateway) {
        this.iTipoUsuarioGateway = iTipoUsuarioGateway;
    }

    public static BuscarTipoUsuarioPorIdUseCase create(ITipoUsuarioGateway iTipoUsuarioGateway) {
        return new BuscarTipoUsuarioPorIdUseCase(iTipoUsuarioGateway);
    }

    public TipoUsuario run(Long id) throws TipoUsuarioNaoEncontradoException {

        final TipoUsuario tipoUsuario = iTipoUsuarioGateway.buscarPorId(id);

        if (tipoUsuario == null) {
            throw new TipoUsuarioNaoEncontradoException("Tipo de usuario com id " + id + " nao encontrado");
        }

        return tipoUsuario;
    }
}
