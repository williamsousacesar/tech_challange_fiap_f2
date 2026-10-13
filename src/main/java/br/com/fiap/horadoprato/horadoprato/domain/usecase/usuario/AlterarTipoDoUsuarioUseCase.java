package br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;

public class AlterarTipoDoUsuarioUseCase {

    private final IUsuarioGateway iUsuarioGateway;

    private final ITipoUsuarioGateway iTipoUsuarioGateway;

    private AlterarTipoDoUsuarioUseCase(IUsuarioGateway iUsuarioGateway, ITipoUsuarioGateway iTipoUsuarioGateway) {
        this.iUsuarioGateway = iUsuarioGateway;
        this.iTipoUsuarioGateway = iTipoUsuarioGateway;
    }

    public static AlterarTipoDoUsuarioUseCase create(IUsuarioGateway iUsuarioGateway, ITipoUsuarioGateway iTipoUsuarioGateway) {
        return new AlterarTipoDoUsuarioUseCase(iUsuarioGateway, iTipoUsuarioGateway);
    }

    public Usuario run(String usuarioId, Long tipoUsuarioId)
            throws UsuarioNaoEncontradoException, TipoUsuarioNaoEncontradoException {

        if (iUsuarioGateway.buscarPorId(usuarioId) == null) {
            throw new UsuarioNaoEncontradoException("Usuario com id " + usuarioId + " nao encontrado");
        }

        if (iTipoUsuarioGateway.buscarPorId(tipoUsuarioId) == null) {
            throw new TipoUsuarioNaoEncontradoException("Tipo de usuario com id " + tipoUsuarioId + " nao encontrado");
        }

        return iUsuarioGateway.alterarTipoUsuario(usuarioId, tipoUsuarioId);
    }
}
