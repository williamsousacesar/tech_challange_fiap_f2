package br.com.fiap.horadoprato.horadoprato.adapters.gateways.itemCardapio;

import br.com.fiap.horadoprato.horadoprato.domain.entities.ItemCardapio;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.ItemCardapioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.NovoItemCardapioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioDataStorageSource;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemCardapioGateway implements IItemCardapioGateway {

    private final IItemCardapioDataStorageSource dataStorageSource;

    @Override
    public ItemCardapio incluir(ItemCardapio itemCardapio) {
        final NovoItemCardapioDTO novoItemCardapio = new NovoItemCardapioDTO(
                itemCardapio.getNomeItemCardapio(),
                itemCardapio.getDescricaoItem(),
                itemCardapio.getPreco(),
                itemCardapio.isApenasNoLocal(),
                itemCardapio.getCaminhoFoto(),
                itemCardapio.getRestaurante() != null ? itemCardapio.getRestaurante().getId() : null
        );

        final ItemCardapioDTO itemCriado = this.dataStorageSource.incluirItemCardapio(novoItemCardapio);

        return toEntity(itemCriado);
    }

    @Override
    public ItemCardapio buscarPorId(String id) {
        return toEntity(this.dataStorageSource.buscarPorId(id));
    }

    @Override
    public Page<ItemCardapio> listarTodos(String restauranteId, Pageable pageable) {
        return this.dataStorageSource.listarTodos(restauranteId, pageable)
                .map(ItemCardapioGateway::toEntity);
    }

    @Override
    public ItemCardapio atualizar(ItemCardapio itemCardapio) {
        final NovoItemCardapioDTO dadosAtualizados = new NovoItemCardapioDTO(
                itemCardapio.getNomeItemCardapio(),
                itemCardapio.getDescricaoItem(),
                itemCardapio.getPreco(),
                itemCardapio.isApenasNoLocal(),
                itemCardapio.getCaminhoFoto(),
                itemCardapio.getRestaurante() != null ? itemCardapio.getRestaurante().getId() : null
        );

        return toEntity(this.dataStorageSource.atualizarItemCardapio(itemCardapio.getIdItemCardapio(), dadosAtualizados));
    }

    @Override
    public void excluir(String id) {
        this.dataStorageSource.excluirItemCardapio(id);
    }

    public static ItemCardapio toEntity(ItemCardapioDTO itemCardapioDTO) {
        if (itemCardapioDTO == null) {
            return null;
        }
        return ItemCardapio.builder()
                .idItemCardapio(itemCardapioDTO.idItemCardapio())
                .nomeItemCardapio(itemCardapioDTO.nomeItemCardapio())
                .descricaoItem(itemCardapioDTO.descricaoItem())
                .preco(itemCardapioDTO.preco())
                .apenasNoLocal(itemCardapioDTO.apenasNoLocal())
                .caminhoFoto(itemCardapioDTO.caminhoFoto())
                .restaurante(br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante.builder()
                        .id(itemCardapioDTO.restauranteId())
                        .build())
                .dataCriacao(itemCardapioDTO.dataCriacao())
                .dataAlteracao(itemCardapioDTO.dataAlteracao())
                .build();
    }
}
