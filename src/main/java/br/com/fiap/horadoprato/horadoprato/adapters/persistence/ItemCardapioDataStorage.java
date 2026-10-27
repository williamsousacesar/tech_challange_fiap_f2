package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.ItemCardapioEntity;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.RestauranteEntity;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.ItemCardapioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.NovoItemCardapioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioDataStorageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemCardapioDataStorage implements IItemCardapioDataStorageSource {

    private final ItemCardapioRepository repository;

    private final RestauranteRepository restauranteRepository;

    @Override
    @Transactional
    public ItemCardapioDTO incluirItemCardapio(NovoItemCardapioDTO novoItemCardapioDTO) {
        Optional<RestauranteEntity> restaurante = restauranteRepository.findById(novoItemCardapioDTO.restauranteId());
        if (restaurante.isEmpty()) {
            return null;
        }

        ItemCardapioEntity entity = ItemCardapioEntity.builder()
                .nomeItemCardapio(novoItemCardapioDTO.nomeItemCardapio())
                .descricaoItem(novoItemCardapioDTO.descricaoItem())
                .preco(novoItemCardapioDTO.preco())
                .apenasNoLocal(novoItemCardapioDTO.apenasNoLocal())
                .caminhoFoto(novoItemCardapioDTO.caminhoFoto())
                .restaurante(restaurante.get())
                .build();

        return toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ItemCardapioDTO buscarPorId(String id) {
        return repository.findById(id)
                .map(ItemCardapioDataStorage::toDTO)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemCardapioDTO> listarTodos(String restauranteId, Pageable pageable) {
        return repository.findByRestauranteId(restauranteId, pageable)
                .map(ItemCardapioDataStorage::toDTO);
    }

    @Override
    @Transactional
    public ItemCardapioDTO atualizarItemCardapio(String id, NovoItemCardapioDTO itemCardapioDTO) {
        Optional<ItemCardapioEntity> existente = repository.findById(id);
        if (existente.isEmpty()) {
            return null;
        }

        Optional<RestauranteEntity> restaurante = restauranteRepository.findById(itemCardapioDTO.restauranteId());
        if (restaurante.isEmpty()) {
            return null;
        }

        ItemCardapioEntity entity = existente.get();
        entity.setNomeItemCardapio(itemCardapioDTO.nomeItemCardapio());
        entity.setDescricaoItem(itemCardapioDTO.descricaoItem());
        entity.setPreco(itemCardapioDTO.preco());
        entity.setApenasNoLocal(itemCardapioDTO.apenasNoLocal());
        entity.setCaminhoFoto(itemCardapioDTO.caminhoFoto());
        entity.setRestaurante(restaurante.get());

        return toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public void excluirItemCardapio(String id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeItemComRestaurante(String restauranteId) {
        return repository.existsByRestauranteId(restauranteId);
    }

    private static ItemCardapioDTO toDTO(ItemCardapioEntity entity) {
        return new ItemCardapioDTO(
                entity.getIdItemCardapio(),
                entity.getNomeItemCardapio(),
                entity.getDescricaoItem(),
                entity.getPreco(),
                entity.isApenasNoLocal(),
                entity.getCaminhoFoto(),
                entity.getRestaurante() != null ? entity.getRestaurante().getId() : null,
                entity.getDataCriacao(),
                entity.getDataAlteracao()
        );
    }
}
