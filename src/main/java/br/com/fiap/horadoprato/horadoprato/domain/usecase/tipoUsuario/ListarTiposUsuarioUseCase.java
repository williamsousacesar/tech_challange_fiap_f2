package br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListarTiposUsuarioUseCase {

    private final ITipoUsuarioGateway iTipoUsuarioGateway;

    private ListarTiposUsuarioUseCase(ITipoUsuarioGateway iTipoUsuarioGateway) {
        this.iTipoUsuarioGateway = iTipoUsuarioGateway;
    }

    public static ListarTiposUsuarioUseCase create(ITipoUsuarioGateway iTipoUsuarioGateway) {
        return new ListarTiposUsuarioUseCase(iTipoUsuarioGateway);
    }

    public Page<TipoUsuario> run(Pageable pageable) {
        return iTipoUsuarioGateway.listarTodos(pageable);
    }
}
