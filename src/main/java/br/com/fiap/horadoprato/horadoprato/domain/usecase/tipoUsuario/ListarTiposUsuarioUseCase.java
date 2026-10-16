package br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;

import java.util.List;

public class ListarTiposUsuarioUseCase {

    private final ITipoUsuarioGateway iTipoUsuarioGateway;

    private ListarTiposUsuarioUseCase(ITipoUsuarioGateway iTipoUsuarioGateway) {
        this.iTipoUsuarioGateway = iTipoUsuarioGateway;
    }

    public static ListarTiposUsuarioUseCase create(ITipoUsuarioGateway iTipoUsuarioGateway) {
        return new ListarTiposUsuarioUseCase(iTipoUsuarioGateway);
    }

    public List<TipoUsuario> run() {
        return iTipoUsuarioGateway.listarTodos();
    }
}
