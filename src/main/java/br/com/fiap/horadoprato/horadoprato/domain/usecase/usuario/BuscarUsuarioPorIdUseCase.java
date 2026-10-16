package br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;

public class BuscarUsuarioPorIdUseCase {

    private final IUsuarioGateway iUsuarioGateway;

    private BuscarUsuarioPorIdUseCase(IUsuarioGateway iUsuarioGateway) {
        this.iUsuarioGateway = iUsuarioGateway;
    }

    public static BuscarUsuarioPorIdUseCase create(IUsuarioGateway iUsuarioGateway) {
        return new BuscarUsuarioPorIdUseCase(iUsuarioGateway);
    }

    public Usuario run(String id) throws UsuarioNaoEncontradoException {

        final Usuario usuario = iUsuarioGateway.buscarPorId(id);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuario com id " + id + " nao encontrado");
        }

        return usuario;
    }
}
