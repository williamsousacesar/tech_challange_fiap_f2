package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.ItemCardapioEntity;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.RestauranteEntity;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.ItemCardapioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.NovoItemCardapioDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemCardapioDataStorageTest {

    @Mock
    private ItemCardapioRepository itemRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Test
    void incluirItemDeveSalvarQuandoRestauranteExiste() {
        ItemCardapioDataStorage storage = new ItemCardapioDataStorage(itemRepository, restauranteRepository);
        RestauranteEntity restaurante = RestauranteEntity.builder()
                .id("r1")
                .nomeRestaurante("Sabor")
                .endereco("Rua A")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("Seg a Dom")
                .dataCriacao(LocalDateTime.now())
                .dataAlteracao(LocalDateTime.now())
                .build();
        NovoItemCardapioDTO dto = new NovoItemCardapioDTO("Pizza", "Massa fina", new BigDecimal("25.50"), true, "/pizza.jpg", "r1");

        when(restauranteRepository.findById("r1")).thenReturn(Optional.of(restaurante));
        when(itemRepository.save(any(ItemCardapioEntity.class))).thenAnswer(invocation -> {
            ItemCardapioEntity entity = invocation.getArgument(0);
            entity.setIdItemCardapio("i1");
            return entity;
        });

        ItemCardapioDTO result = storage.incluirItemCardapio(dto);

        assertEquals("i1", result.idItemCardapio());
        assertEquals("Pizza", result.nomeItemCardapio());
    }

    @Test
    void incluirItemDeveRetornarNullQuandoRestauranteNaoExiste() {
        ItemCardapioDataStorage storage = new ItemCardapioDataStorage(itemRepository, restauranteRepository);

        when(restauranteRepository.findById("r9")).thenReturn(Optional.empty());

        assertNull(storage.incluirItemCardapio(new NovoItemCardapioDTO("Pizza", "Massa fina", new BigDecimal("25.50"), true, "/pizza.jpg", "r9")));
    }

    @Test
    void buscarPorIdDeveMapearOuRetornarNull() {
        ItemCardapioDataStorage storage = new ItemCardapioDataStorage(itemRepository, restauranteRepository);
        RestauranteEntity restaurante = RestauranteEntity.builder().id("r1").nomeRestaurante("Sabor").build();
        ItemCardapioEntity entity = ItemCardapioEntity.builder()
                .idItemCardapio("i1")
                .nomeItemCardapio("Pizza")
                .descricaoItem("Massa fina")
                .preco(new BigDecimal("25.50"))
                .apenasNoLocal(true)
                .caminhoFoto("/pizza.jpg")
                .restaurante(restaurante)
                .build();

        when(itemRepository.findById("i1")).thenReturn(Optional.of(entity));
        when(itemRepository.findById("i9")).thenReturn(Optional.empty());

        assertEquals("Pizza", storage.buscarPorId("i1").nomeItemCardapio());
        assertNull(storage.buscarPorId("i9"));
    }

    @Test
    void listarTodosDeveRetornarPaginaMapeada() {
        ItemCardapioDataStorage storage = new ItemCardapioDataStorage(itemRepository, restauranteRepository);
        RestauranteEntity restaurante = RestauranteEntity.builder().id("r1").nomeRestaurante("Sabor").build();
        ItemCardapioEntity entity = ItemCardapioEntity.builder()
                .idItemCardapio("i1")
                .nomeItemCardapio("Pizza")
                .descricaoItem("Massa fina")
                .preco(new BigDecimal("25.50"))
                .apenasNoLocal(true)
                .caminhoFoto("/pizza.jpg")
                .restaurante(restaurante)
                .build();
        Pageable pageable = Pageable.unpaged();

        when(itemRepository.findByRestauranteId("r1", pageable)).thenReturn(new PageImpl<>(List.of(entity)));

        Page<ItemCardapioDTO> page = storage.listarTodos("r1", pageable);

        assertEquals(1, page.getContent().size());
        assertEquals("Pizza", page.getContent().getFirst().nomeItemCardapio());
    }

    @Test
    void atualizarItemDeveAtualizarQuandoExiste() {
        ItemCardapioDataStorage storage = new ItemCardapioDataStorage(itemRepository, restauranteRepository);
        RestauranteEntity restaurante = RestauranteEntity.builder().id("r1").nomeRestaurante("Sabor").build();
        ItemCardapioEntity entity = ItemCardapioEntity.builder()
                .idItemCardapio("i1")
                .nomeItemCardapio("Pizza")
                .descricaoItem("Massa fina")
                .preco(new BigDecimal("20.00"))
                .apenasNoLocal(true)
                .caminhoFoto("/pizza.jpg")
                .restaurante(restaurante)
                .build();
        NovoItemCardapioDTO dto = new NovoItemCardapioDTO("Pizza Especial", "Massa grossa", new BigDecimal("30.00"), false, "/pizza-especial.jpg", "r1");

        when(itemRepository.findById("i1")).thenReturn(Optional.of(entity));
        when(restauranteRepository.findById("r1")).thenReturn(Optional.of(restaurante));
        when(itemRepository.save(entity)).thenReturn(entity);

        ItemCardapioDTO result = storage.atualizarItemCardapio("i1", dto);

        assertEquals("Pizza Especial", result.nomeItemCardapio());
        assertEquals("Massa grossa", result.descricaoItem());
    }

    @Test
    void excluirItemDeveDelegarAoRepository() {
        ItemCardapioDataStorage storage = new ItemCardapioDataStorage(itemRepository, restauranteRepository);

        storage.excluirItemCardapio("i1");

        verify(itemRepository).deleteById("i1");
    }
}
