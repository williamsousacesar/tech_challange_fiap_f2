package br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante;

import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteComItensCardapioException;
import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;

public class ExcluirRestauranteUseCase {

    private final IRestauranteGateway iRestauranteGateway;

    private final IItemCardapioGateway iItemCardapioGateway;

    private ExcluirRestauranteUseCase(IRestauranteGateway iRestauranteGateway, IItemCardapioGateway iItemCardapioGateway) {
        this.iRestauranteGateway = iRestauranteGateway;
        this.iItemCardapioGateway = iItemCardapioGateway;
    }

    public static ExcluirRestauranteUseCase create(IRestauranteGateway iRestauranteGateway, IItemCardapioGateway iItemCardapioGateway) {
        return new ExcluirRestauranteUseCase(iRestauranteGateway, iItemCardapioGateway);
    }

    public void run(String id) throws RestauranteNaoEncontradoException, RestauranteComItensCardapioException {

        if (iRestauranteGateway.buscarPorId(id) == null) {
            throw new RestauranteNaoEncontradoException("Restaurante com id " + id + " nao encontrado");
        }

        if (iItemCardapioGateway.existeItemComRestaurante(id)) {
            throw new RestauranteComItensCardapioException("Restaurante com id " + id + " possui itens no cardapio e nao pode ser excluido");
        }

        iRestauranteGateway.excluir(id);
    }
}
