package br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;

import java.util.List;

public class ListarRestaurantesUseCase {

    private final IRestauranteGateway iRestauranteGateway;

    private ListarRestaurantesUseCase(IRestauranteGateway iRestauranteGateway) {
        this.iRestauranteGateway = iRestauranteGateway;
    }

    public static ListarRestaurantesUseCase create(IRestauranteGateway iRestauranteGateway) {
        return new ListarRestaurantesUseCase(iRestauranteGateway);
    }

    public List<Restaurante> run() {
        return iRestauranteGateway.listarTodos();
    }
}
