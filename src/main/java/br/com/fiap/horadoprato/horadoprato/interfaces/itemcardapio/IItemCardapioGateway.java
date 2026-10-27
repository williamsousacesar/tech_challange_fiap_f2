package br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio;

import br.com.fiap.horadoprato.horadoprato.domain.entities.ItemCardapio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IItemCardapioGateway {

    ItemCardapio incluir(ItemCardapio itemCardapio);

    ItemCardapio buscarPorId(String id);

    Page<ItemCardapio> listarTodos(String restauranteId, Pageable pageable);

    ItemCardapio atualizar(ItemCardapio itemCardapio);

    void excluir(String id);

    boolean existeItemComRestaurante(String restauranteId);
}
