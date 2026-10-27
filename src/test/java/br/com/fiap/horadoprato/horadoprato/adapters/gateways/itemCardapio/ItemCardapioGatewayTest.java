package br.com.fiap.horadoprato.horadoprato.adapters.gateways.itemCardapio;

import br.com.fiap.horadoprato.horadoprato.domain.entities.ItemCardapio;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.ItemCardapioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.NovoItemCardapioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioDataStorageSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemCardapioGatewayTest {

    @Mock
    private IItemCardapioDataStorageSource dataStorageSource;

    @Test
    void deveIncluirItemCardapio() {
        ItemCardapioGateway gateway = new ItemCardapioGateway(dataStorageSource);
        ItemCardapio item = ItemCardapio.builder().nomeItemCardapio("Pizza").descricaoItem("Mussarela").preco(new BigDecimal("49.90")).apenasNoLocal(true).caminhoFoto("/pizza.jpg").restaurante(Restaurante.builder().id("r1").build()).build();
        when(dataStorageSource.incluirItemCardapio(new NovoItemCardapioDTO("Pizza", "Mussarela", new BigDecimal("49.90"), true, "/pizza.jpg", "r1"))).thenReturn(new ItemCardapioDTO("i1", "Pizza", "Mussarela", new BigDecimal("49.90"), true, "/pizza.jpg", "r1", null, null));

        ItemCardapio result = gateway.incluir(item);

        assertEquals("i1", result.getIdItemCardapio());
        assertEquals("Pizza", result.getNomeItemCardapio());
    }

    @Test
    void deveBuscarPorId() {
        ItemCardapioGateway gateway = new ItemCardapioGateway(dataStorageSource);
        when(dataStorageSource.buscarPorId("i1")).thenReturn(new ItemCardapioDTO("i1", "Pizza", "Mussarela", new BigDecimal("49.90"), true, "/pizza.jpg", "r1", null, null));

        ItemCardapio result = gateway.buscarPorId("i1");

        assertEquals("Pizza", result.getNomeItemCardapio());
    }

    @Test
    void deveRetornarNullQuandoNaoExiste() {
        ItemCardapioGateway gateway = new ItemCardapioGateway(dataStorageSource);
        when(dataStorageSource.buscarPorId("i9")).thenReturn(null);

        assertNull(gateway.buscarPorId("i9"));
    }

    @Test
    void deveListarTodos() {
        ItemCardapioGateway gateway = new ItemCardapioGateway(dataStorageSource);
        Page<ItemCardapioDTO> page = new PageImpl<>(List.of(new ItemCardapioDTO("i1", "Pizza", "Mussarela", new BigDecimal("49.90"), true, "/pizza.jpg", "r1", null, null)));
        when(dataStorageSource.listarTodos(any(String.class), any(Pageable.class))).thenReturn(page);

        Page<ItemCardapio> resultado = gateway.listarTodos("r1", Pageable.unpaged());

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Pizza", resultado.getContent().getFirst().getNomeItemCardapio());
    }

    @Test
    void deveAtualizarItemCardapio() {
        ItemCardapioGateway gateway = new ItemCardapioGateway(dataStorageSource);
        ItemCardapio item = ItemCardapio.builder().idItemCardapio("i1").nomeItemCardapio("Risoto").descricaoItem("Arroz").preco(new BigDecimal("39.90")).apenasNoLocal(false).caminhoFoto("/risoto.jpg").restaurante(Restaurante.builder().id("r1").build()).build();
        when(dataStorageSource.atualizarItemCardapio("i1", new NovoItemCardapioDTO("Risoto", "Arroz", new BigDecimal("39.90"), false, "/risoto.jpg", "r1"))).thenReturn(new ItemCardapioDTO("i1", "Risoto", "Arroz", new BigDecimal("39.90"), false, "/risoto.jpg", "r1", null, null));

        ItemCardapio result = gateway.atualizar(item);

        assertEquals("Risoto", result.getNomeItemCardapio());
    }
}
