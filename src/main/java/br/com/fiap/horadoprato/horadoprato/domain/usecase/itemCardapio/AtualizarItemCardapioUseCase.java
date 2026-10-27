package br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio;

import br.com.fiap.horadoprato.horadoprato.domain.entities.ItemCardapio;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.exceptions.ItemCardapioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;

import java.math.BigDecimal;

public class AtualizarItemCardapioUseCase {

    private final IItemCardapioGateway iItemCardapioGateway;

    private final IRestauranteGateway iRestauranteGateway;

    private AtualizarItemCardapioUseCase(IItemCardapioGateway iItemCardapioGateway,
                                         IRestauranteGateway iRestauranteGateway) {
        this.iItemCardapioGateway = iItemCardapioGateway;
        this.iRestauranteGateway = iRestauranteGateway;
    }

    public static AtualizarItemCardapioUseCase create(IItemCardapioGateway iItemCardapioGateway,
                                                      IRestauranteGateway iRestauranteGateway) {
        return new AtualizarItemCardapioUseCase(iItemCardapioGateway, iRestauranteGateway);
    }

    public ItemCardapio run(String id, String nomeItemCardapio, String descricaoItem, BigDecimal preco,
                            boolean apenasNoLocal, String caminhoFoto, String restauranteId)
            throws ItemCardapioNaoEncontradoException, RestauranteNaoEncontradoException {

        final ItemCardapio itemCardapio = iItemCardapioGateway.buscarPorId(id);

        if (itemCardapio == null) {
            throw new ItemCardapioNaoEncontradoException("Item de cardapio com id " + id + " nao encontrado");
        }

        if (iRestauranteGateway.buscarPorId(restauranteId) == null) {
            throw new RestauranteNaoEncontradoException("Restaurante com id " + restauranteId + " nao encontrado");
        }

        itemCardapio.setNomeItemCardapio(nomeItemCardapio);
        itemCardapio.setDescricaoItem(descricaoItem);
        itemCardapio.setPreco(preco);
        itemCardapio.setApenasNoLocal(apenasNoLocal);
        itemCardapio.setCaminhoFoto(caminhoFoto);
        itemCardapio.setRestaurante(Restaurante.builder().id(restauranteId).build());

        return iItemCardapioGateway.atualizar(itemCardapio);
    }
}
