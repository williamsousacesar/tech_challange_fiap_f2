package br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio;

import br.com.fiap.horadoprato.horadoprato.domain.entities.ItemCardapio;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ListarItensCardapioUseCase {

    private final IItemCardapioGateway iItemCardapioGateway;

    private ListarItensCardapioUseCase(IItemCardapioGateway iItemCardapioGateway) {
        this.iItemCardapioGateway = iItemCardapioGateway;
    }

    public static ListarItensCardapioUseCase create(IItemCardapioGateway iItemCardapioGateway) {
        return new ListarItensCardapioUseCase(iItemCardapioGateway);
    }

    public Page<ItemCardapio> run(String restauranteId, Pageable pageable) {
        return iItemCardapioGateway.listarTodos(restauranteId, pageable);
    }
}
