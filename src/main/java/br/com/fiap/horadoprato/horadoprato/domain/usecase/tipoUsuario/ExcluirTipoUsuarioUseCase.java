package br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioEmUsoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;

public class ExcluirTipoUsuarioUseCase {

    private final ITipoUsuarioGateway iTipoUsuarioGateway;

    private final IUsuarioGateway iUsuarioGateway;

    private ExcluirTipoUsuarioUseCase(ITipoUsuarioGateway iTipoUsuarioGateway, IUsuarioGateway iUsuarioGateway) {
        this.iTipoUsuarioGateway = iTipoUsuarioGateway;
        this.iUsuarioGateway = iUsuarioGateway;
    }

    public static ExcluirTipoUsuarioUseCase create(ITipoUsuarioGateway iTipoUsuarioGateway, IUsuarioGateway iUsuarioGateway) {
        return new ExcluirTipoUsuarioUseCase(iTipoUsuarioGateway, iUsuarioGateway);
    }

    public void run(Long id) throws TipoUsuarioNaoEncontradoException, TipoUsuarioEmUsoException {

        if (iTipoUsuarioGateway.buscarPorId(id) == null) {
            throw new TipoUsuarioNaoEncontradoException("Tipo de usuario com id " + id + " nao encontrado");
        }

        if (iUsuarioGateway.existeUsuarioComTipo(id)) {
            throw new TipoUsuarioEmUsoException("Tipo de usuario com id " + id + " possui usuarios vinculados e nao pode ser excluido");
        }

        iTipoUsuarioGateway.excluir(id);
    }
}
