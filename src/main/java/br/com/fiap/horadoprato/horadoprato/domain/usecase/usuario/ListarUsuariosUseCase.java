package br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListarUsuariosUseCase {

    private final IUsuarioGateway iUsuarioGateway;

    private ListarUsuariosUseCase(IUsuarioGateway iUsuarioGateway) {
        this.iUsuarioGateway = iUsuarioGateway;
    }

    public static ListarUsuariosUseCase create(IUsuarioGateway iUsuarioGateway) {
        return new ListarUsuariosUseCase(iUsuarioGateway);
    }

    public Page<Usuario> run(Pageable pageable) {
        return iUsuarioGateway.listarTodos(pageable);
    }
}
