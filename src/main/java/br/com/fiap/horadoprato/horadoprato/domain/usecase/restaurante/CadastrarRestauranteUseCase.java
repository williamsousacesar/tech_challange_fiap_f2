package br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;

public class CadastrarRestauranteUseCase {

    private final IRestauranteGateway iRestauranteGateway;

    private final IUsuarioGateway iUsuarioGateway;

    private CadastrarRestauranteUseCase(IRestauranteGateway iRestauranteGateway,
                                        IUsuarioGateway iUsuarioGateway) {
        this.iRestauranteGateway = iRestauranteGateway;
        this.iUsuarioGateway = iUsuarioGateway;
    }

    public static CadastrarRestauranteUseCase create(IRestauranteGateway iRestauranteGateway,
                                                     IUsuarioGateway iUsuarioGateway) {
        return new CadastrarRestauranteUseCase(iRestauranteGateway, iUsuarioGateway);
    }

    public Restaurante run(String nomeRestaurante, String endereco, String tipoCozinha,
                           String horarioFuncionamento, String donoId)
            throws UsuarioNaoEncontradoException {

        if (iUsuarioGateway.buscarPorId(donoId) == null) {
            throw new UsuarioNaoEncontradoException("Usuario dono com id " + donoId + " nao encontrado");
        }

        final Restaurante novoRestaurante = Restaurante.builder()
                .nomeRestaurante(nomeRestaurante)
                .endereco(endereco)
                .tipoCozinha(tipoCozinha)
                .horarioFuncionamento(horarioFuncionamento)
                .dono(br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario.builder().id(donoId).build())
                .build();

        return iRestauranteGateway.incluir(novoRestaurante);
    }
}
