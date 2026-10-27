package br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio;

import br.com.fiap.horadoprato.horadoprato.exceptions.ItemCardapioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioGateway;

public class ExcluirItemCardapioUseCase {

    private final IItemCardapioGateway iItemCardapioGateway;

    private ExcluirItemCardapioUseCase(IItemCardapioGateway iItemCardapioGateway) {
        this.iItemCardapioGateway = iItemCardapioGateway;
    }

    public static ExcluirItemCardapioUseCase create(IItemCardapioGateway iItemCardapioGateway) {
        return new ExcluirItemCardapioUseCase(iItemCardapioGateway);
    }

    public void run(String id) throws ItemCardapioNaoEncontradoException {
        if (iItemCardapioGateway.buscarPorId(id) == null) {
            throw new ItemCardapioNaoEncontradoException("Item de cardapio com id " + id + " nao encontrado");
        }

        iItemCardapioGateway.excluir(id);
    }
}
