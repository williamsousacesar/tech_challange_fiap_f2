package br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioComRestauranteNaoPodeAlterarTipoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;

public class AlterarTipoDoUsuarioUseCase {

    private static final String TIPO_DONO_RESTAURANTE = "DONO_RESTAURANTE";

    private final IUsuarioGateway iUsuarioGateway;

    private final ITipoUsuarioGateway iTipoUsuarioGateway;

    private final IRestauranteGateway iRestauranteGateway;

    private AlterarTipoDoUsuarioUseCase(IUsuarioGateway iUsuarioGateway, ITipoUsuarioGateway iTipoUsuarioGateway, IRestauranteGateway iRestauranteGateway) {
        this.iUsuarioGateway = iUsuarioGateway;
        this.iTipoUsuarioGateway = iTipoUsuarioGateway;
        this.iRestauranteGateway = iRestauranteGateway;
    }

    public static AlterarTipoDoUsuarioUseCase create(IUsuarioGateway iUsuarioGateway, ITipoUsuarioGateway iTipoUsuarioGateway, IRestauranteGateway iRestauranteGateway) {
        return new AlterarTipoDoUsuarioUseCase(iUsuarioGateway, iTipoUsuarioGateway, iRestauranteGateway);
    }

    public Usuario run(String usuarioId, Long tipoUsuarioId)
            throws UsuarioNaoEncontradoException, TipoUsuarioNaoEncontradoException {

        final Usuario usuario = iUsuarioGateway.buscarPorId(usuarioId);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuario com id " + usuarioId + " nao encontrado");
        }

        if (iTipoUsuarioGateway.buscarPorId(tipoUsuarioId) == null) {
            throw new TipoUsuarioNaoEncontradoException("Tipo de usuario com id " + tipoUsuarioId + " nao encontrado");
        }

        if (usuario.getTipoUsuario() != null
                && TIPO_DONO_RESTAURANTE.equals(usuario.getTipoUsuario().getTpUsuario())
                && iRestauranteGateway.existeRestauranteComDono(usuarioId)) {
            throw new UsuarioComRestauranteNaoPodeAlterarTipoException("Usuario com id " + usuarioId + " possui restaurante e nao pode ter o tipo alterado");
        }

        return iUsuarioGateway.alterarTipoUsuario(usuarioId, tipoUsuarioId);
    }
}
