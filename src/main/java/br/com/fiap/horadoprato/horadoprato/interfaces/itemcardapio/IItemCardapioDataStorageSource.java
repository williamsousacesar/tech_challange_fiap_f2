package br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio;

import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.ItemCardapioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.NovoItemCardapioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IItemCardapioDataStorageSource {

    ItemCardapioDTO incluirItemCardapio(NovoItemCardapioDTO novoItemCardapioDTO);

    ItemCardapioDTO buscarPorId(String id);

    Page<ItemCardapioDTO> listarTodos(String restauranteId, Pageable pageable);

    ItemCardapioDTO atualizarItemCardapio(String id, NovoItemCardapioDTO itemCardapioDTO);

    void excluirItemCardapio(String id);

    boolean existeItemComRestaurante(String restauranteId);
}
