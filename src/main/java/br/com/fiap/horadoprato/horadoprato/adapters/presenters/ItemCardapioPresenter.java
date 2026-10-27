package br.com.fiap.horadoprato.horadoprato.adapters.presenters;

import br.com.fiap.horadoprato.horadoprato.domain.entities.ItemCardapio;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.ItemCardapioDTO;

import java.util.List;

public class ItemCardapioPresenter {

    public static ItemCardapioDTO ToDTO(ItemCardapio itemCardapio) {
        return new ItemCardapioDTO(
                itemCardapio.getIdItemCardapio(),
                itemCardapio.getNomeItemCardapio(),
                itemCardapio.getDescricaoItem(),
                itemCardapio.getPreco(),
                itemCardapio.isApenasNoLocal(),
                itemCardapio.getCaminhoFoto(),
                itemCardapio.getRestaurante() != null ? itemCardapio.getRestaurante().getId() : null,
                itemCardapio.getDataCriacao(),
                itemCardapio.getDataAlteracao()
        );
    }

    public static List<ItemCardapioDTO> ToDTO(List<ItemCardapio> itensCardapio) {
        return itensCardapio.stream()
                .map(ItemCardapioPresenter::ToDTO)
                .toList();
    }
}
