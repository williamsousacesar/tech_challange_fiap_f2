package br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio;

import br.com.fiap.horadoprato.horadoprato.domain.entities.ItemCardapio;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;

import java.math.BigDecimal;

public class CadastrarItemCardapioUseCase {

    private final IItemCardapioGateway iItemCardapioGateway;

    private final IRestauranteGateway iRestauranteGateway;

    private CadastrarItemCardapioUseCase(IItemCardapioGateway iItemCardapioGateway,
                                         IRestauranteGateway iRestauranteGateway) {
        this.iItemCardapioGateway = iItemCardapioGateway;
        this.iRestauranteGateway = iRestauranteGateway;
    }

    public static CadastrarItemCardapioUseCase create(IItemCardapioGateway iItemCardapioGateway,
                                                      IRestauranteGateway iRestauranteGateway) {
        return new CadastrarItemCardapioUseCase(iItemCardapioGateway, iRestauranteGateway);
    }

    public ItemCardapio run(String nomeItemCardapio, String descricaoItem, BigDecimal preco,
                            boolean apenasNoLocal, String caminhoFoto, String restauranteId)
            throws RestauranteNaoEncontradoException {

        if (iRestauranteGateway.buscarPorId(restauranteId) == null) {
            throw new RestauranteNaoEncontradoException("Restaurante com id " + restauranteId + " nao encontrado");
        }

        final ItemCardapio novoItemCardapio = ItemCardapio.builder()
                .nomeItemCardapio(nomeItemCardapio)
                .descricaoItem(descricaoItem)
                .preco(preco)
                .apenasNoLocal(apenasNoLocal)
                .caminhoFoto(caminhoFoto)
                .restaurante(Restaurante.builder().id(restauranteId).build())
                .build();

        return iItemCardapioGateway.incluir(novoItemCardapio);
    }
}
