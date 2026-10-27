package br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEhDonoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;

public class AtualizarRestauranteUseCase {

    private static final String TIPO_DONO_RESTAURANTE = "DONO_RESTAURANTE";

    private final IRestauranteGateway iRestauranteGateway;

    private final IUsuarioGateway iUsuarioGateway;

    private AtualizarRestauranteUseCase(IRestauranteGateway iRestauranteGateway,
                                        IUsuarioGateway iUsuarioGateway) {
        this.iRestauranteGateway = iRestauranteGateway;
        this.iUsuarioGateway = iUsuarioGateway;
    }

    public static AtualizarRestauranteUseCase create(IRestauranteGateway iRestauranteGateway,
                                                     IUsuarioGateway iUsuarioGateway) {
        return new AtualizarRestauranteUseCase(iRestauranteGateway, iUsuarioGateway);
    }

    public Restaurante run(String id, String nomeRestaurante, String endereco, String tipoCozinha,
                           String horarioFuncionamento, String donoId)
            throws RestauranteNaoEncontradoException, UsuarioNaoEncontradoException, UsuarioNaoEhDonoException {

        final Restaurante restaurante = iRestauranteGateway.buscarPorId(id);

        if (restaurante == null) {
            throw new RestauranteNaoEncontradoException("Restaurante com id " + id + " nao encontrado");
        }

        final var dono = iUsuarioGateway.buscarPorId(donoId);

        if (dono == null) {
            throw new UsuarioNaoEncontradoException("Usuario dono com id " + donoId + " nao encontrado");
        }

        if (dono.getTipoUsuario() == null || !TIPO_DONO_RESTAURANTE.equals(dono.getTipoUsuario().getTpUsuario())) {
            throw new UsuarioNaoEhDonoException("Usuario com id " + donoId + " nao possui perfil de dono de restaurante");
        }

        restaurante.setNomeRestaurante(nomeRestaurante);
        restaurante.setEndereco(endereco);
        restaurante.setTipoCozinha(tipoCozinha);
        restaurante.setHorarioFuncionamento(horarioFuncionamento);
        restaurante.setDono(br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario.builder().id(donoId).build());

        return iRestauranteGateway.atualizar(restaurante);
    }
}
