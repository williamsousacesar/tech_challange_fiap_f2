package br.com.fiap.horadoprato.horadoprato.adapters.controllers.itemCardapio;

import br.com.fiap.horadoprato.horadoprato.domain.entities.ItemCardapio;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.AtualizarItemCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.BuscarItemCardapioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.CadastrarItemCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.ExcluirItemCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.ListarItensCardapioUseCase;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemCardapioControllerTest {

    @Mock
    private CadastrarItemCardapioUseCase cadastrarItemCardapioUseCase;

    @Mock
    private ListarItensCardapioUseCase listarItensCardapioUseCase;

    @Mock
    private BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;

    @Mock
    private AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;

    @Mock
    private ExcluirItemCardapioUseCase excluirItemCardapioUseCase;

    @Test
    void deveCadastrarItemCardapio() {
        ItemCardapioController controller = new ItemCardapioController(cadastrarItemCardapioUseCase, listarItensCardapioUseCase, buscarItemCardapioPorIdUseCase, atualizarItemCardapioUseCase, excluirItemCardapioUseCase);
        when(cadastrarItemCardapioUseCase.run("Pizza", "Mussarela", new BigDecimal("49.90"), true, "/pizza.jpg", "r1")).thenReturn(ItemCardapio.builder().idItemCardapio("i1").nomeItemCardapio("Pizza").restaurante(Restaurante.builder().id("r1").build()).build());

        ItemCardapioDTO dto = controller.cadastrar(new NovoItemCardapioDTO("Pizza", "Mussarela", new BigDecimal("49.90"), true, "/pizza.jpg", "r1"));

        assertEquals("Pizza", dto.nomeItemCardapio());
        assertEquals("r1", dto.restauranteId());
    }

    @Test
    void deveListarItensCardapio() {
        ItemCardapioController controller = new ItemCardapioController(cadastrarItemCardapioUseCase, listarItensCardapioUseCase, buscarItemCardapioPorIdUseCase, atualizarItemCardapioUseCase, excluirItemCardapioUseCase);
        Page<ItemCardapio> page = new PageImpl<>(List.of(ItemCardapio.builder().idItemCardapio("i1").nomeItemCardapio("Pizza").restaurante(Restaurante.builder().id("r1").build()).build()));
        when(listarItensCardapioUseCase.run(any(String.class), any(Pageable.class))).thenReturn(page);

        Page<ItemCardapioDTO> resultado = controller.listar("r1", 0, 10);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Pizza", resultado.getContent().getFirst().nomeItemCardapio());
    }

    @Test
    void deveBuscarItemCardapioPorId() {
        ItemCardapioController controller = new ItemCardapioController(cadastrarItemCardapioUseCase, listarItensCardapioUseCase, buscarItemCardapioPorIdUseCase, atualizarItemCardapioUseCase, excluirItemCardapioUseCase);
        when(buscarItemCardapioPorIdUseCase.run("i1")).thenReturn(ItemCardapio.builder().idItemCardapio("i1").nomeItemCardapio("Pizza").restaurante(Restaurante.builder().id("r1").build()).build());

        ItemCardapioDTO dto = controller.buscarPorId("i1");

        assertEquals("i1", dto.idItemCardapio());
        assertEquals("Pizza", dto.nomeItemCardapio());
    }

    @Test
    void deveAtualizarItemCardapio() {
        ItemCardapioController controller = new ItemCardapioController(cadastrarItemCardapioUseCase, listarItensCardapioUseCase, buscarItemCardapioPorIdUseCase, atualizarItemCardapioUseCase, excluirItemCardapioUseCase);
        when(atualizarItemCardapioUseCase.run("i1", "Risoto", "Arroz", new BigDecimal("39.90"), false, "/risoto.jpg", "r1")).thenReturn(ItemCardapio.builder().idItemCardapio("i1").nomeItemCardapio("Risoto").restaurante(Restaurante.builder().id("r1").build()).build());

        ItemCardapioDTO dto = controller.atualizar("i1", new NovoItemCardapioDTO("Risoto", "Arroz", new BigDecimal("39.90"), false, "/risoto.jpg", "r1"));

        assertEquals("Risoto", dto.nomeItemCardapio());
    }
}
