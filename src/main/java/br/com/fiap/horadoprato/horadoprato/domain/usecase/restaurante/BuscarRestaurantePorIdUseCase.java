package br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;

public class BuscarRestaurantePorIdUseCase {

    private final IRestauranteGateway iRestauranteGateway;

    private BuscarRestaurantePorIdUseCase(IRestauranteGateway iRestauranteGateway) {
        this.iRestauranteGateway = iRestauranteGateway;
    }

    public static BuscarRestaurantePorIdUseCase create(IRestauranteGateway iRestauranteGateway) {
        return new BuscarRestaurantePorIdUseCase(iRestauranteGateway);
    }

    public Restaurante run(String id) throws RestauranteNaoEncontradoException {

        final Restaurante restaurante = iRestauranteGateway.buscarPorId(id);

        if (restaurante == null) {
            throw new RestauranteNaoEncontradoException("Restaurante com id " + id + " nao encontrado");
        }

        return restaurante;
    }
}
