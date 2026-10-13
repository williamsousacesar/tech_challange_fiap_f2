package br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioJaExisteException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;

public class AtualizarTipoUsuarioUseCase {

    private final ITipoUsuarioGateway iTipoUsuarioGateway;

    private AtualizarTipoUsuarioUseCase(ITipoUsuarioGateway iTipoUsuarioGateway) {
        this.iTipoUsuarioGateway = iTipoUsuarioGateway;
    }

    public static AtualizarTipoUsuarioUseCase create(ITipoUsuarioGateway iTipoUsuarioGateway) {
        return new AtualizarTipoUsuarioUseCase(iTipoUsuarioGateway);
    }

    public TipoUsuario run(Long id, NovoTipoUsuarioDTO dadosAtualizados)
            throws TipoUsuarioNaoEncontradoException, TipoUsuarioJaExisteException {

        final TipoUsuario tipoUsuario = iTipoUsuarioGateway.buscarPorId(id);

        if (tipoUsuario == null) {
            throw new TipoUsuarioNaoEncontradoException("Tipo de usuario com id " + id + " nao encontrado");
        }

        final TipoUsuario tipoComMesmoNome = iTipoUsuarioGateway.buscarPorTpUsuario(dadosAtualizados.tpUsuario());

        if (tipoComMesmoNome != null && !tipoComMesmoNome.getId().equals(id)) {
            throw new TipoUsuarioJaExisteException("Tipo de usuario '" + dadosAtualizados.tpUsuario() + "' ja existe");
        }

        tipoUsuario.setTpUsuario(dadosAtualizados.tpUsuario());
        tipoUsuario.setDescricaoTipoUsuario(dadosAtualizados.descricaoTipoUsuario());

        return iTipoUsuarioGateway.atualizar(tipoUsuario);
    }
}
