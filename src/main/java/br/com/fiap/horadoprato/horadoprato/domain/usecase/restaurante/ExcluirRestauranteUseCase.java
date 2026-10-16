package br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante;

import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;

public class ExcluirRestauranteUseCase {

    private final IRestauranteGateway iRestauranteGateway;

    private ExcluirRestauranteUseCase(IRestauranteGateway iRestauranteGateway) {
        this.iRestauranteGateway = iRestauranteGateway;
    }

    public static ExcluirRestauranteUseCase create(IRestauranteGateway iRestauranteGateway) {
        return new ExcluirRestauranteUseCase(iRestauranteGateway);
    }

    public void run(String id) throws RestauranteNaoEncontradoException {

        if (iRestauranteGateway.buscarPorId(id) == null) {
            throw new RestauranteNaoEncontradoException("Restaurante com id " + id + " nao encontrado");
        }

        iRestauranteGateway.excluir(id);
    }
}
