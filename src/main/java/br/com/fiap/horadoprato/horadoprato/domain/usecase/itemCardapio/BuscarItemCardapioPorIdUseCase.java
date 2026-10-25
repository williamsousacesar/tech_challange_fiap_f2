package br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio;

import br.com.fiap.horadoprato.horadoprato.domain.entities.ItemCardapio;
import br.com.fiap.horadoprato.horadoprato.exceptions.ItemCardapioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioGateway;

public class BuscarItemCardapioPorIdUseCase {

    private final IItemCardapioGateway iItemCardapioGateway;

    private BuscarItemCardapioPorIdUseCase(IItemCardapioGateway iItemCardapioGateway) {
        this.iItemCardapioGateway = iItemCardapioGateway;
    }

    public static BuscarItemCardapioPorIdUseCase create(IItemCardapioGateway iItemCardapioGateway) {
        return new BuscarItemCardapioPorIdUseCase(iItemCardapioGateway);
    }

    public ItemCardapio run(String id) throws ItemCardapioNaoEncontradoException {
        final ItemCardapio itemCardapio = iItemCardapioGateway.buscarPorId(id);

        if (itemCardapio == null) {
            throw new ItemCardapioNaoEncontradoException("Item de cardapio com id " + id + " nao encontrado");
        }

        return itemCardapio;
    }
}
