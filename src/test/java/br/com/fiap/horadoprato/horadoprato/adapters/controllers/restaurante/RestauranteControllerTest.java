package br.com.fiap.horadoprato.horadoprato.adapters.controllers.restaurante;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.AtualizarRestauranteUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.BuscarRestaurantePorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.CadastrarRestauranteUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.ExcluirRestauranteUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.ListarRestaurantesUseCase;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.RestauranteDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteControllerTest {

    @Mock
    private CadastrarRestauranteUseCase cadastrarRestauranteUseCase;

    @Mock
    private ListarRestaurantesUseCase listarRestaurantesUseCase;

    @Mock
    private BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    @Mock
    private AtualizarRestauranteUseCase atualizarRestauranteUseCase;

    @Mock
    private ExcluirRestauranteUseCase excluirRestauranteUseCase;

    @Test
    void deveCadastrarRestaurante() {
        RestauranteController controller = new RestauranteController(cadastrarRestauranteUseCase, listarRestaurantesUseCase, buscarRestaurantePorIdUseCase, atualizarRestauranteUseCase, excluirRestauranteUseCase);
        when(cadastrarRestauranteUseCase.run("Sabor", "Rua A", "Italiana", "Seg a Dom", "u1")).thenReturn(Restaurante.builder().id("r1").nomeRestaurante("Sabor").dono(Usuario.builder().id("u1").build()).build());

        RestauranteDTO dto = controller.cadastrar(new NovoRestauranteDTO("Sabor", "Rua A", "Italiana", "Seg a Dom", "u1"));

        assertEquals("Sabor", dto.nomeRestaurante());
        assertEquals("u1", dto.donoId());
    }

    @Test
    void deveListarRestaurantes() {
        RestauranteController controller = new RestauranteController(cadastrarRestauranteUseCase, listarRestaurantesUseCase, buscarRestaurantePorIdUseCase, atualizarRestauranteUseCase, excluirRestauranteUseCase);
        Page<Restaurante> page = new PageImpl<>(List.of(Restaurante.builder().id("r1").nomeRestaurante("Sabor").build()));
        when(listarRestaurantesUseCase.run(any(Pageable.class))).thenReturn(page);

        Page<RestauranteDTO> resultado = controller.listar(0, 10);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Sabor", resultado.getContent().getFirst().nomeRestaurante());
    }

    @Test
    void deveBuscarRestaurantePorId() {
        RestauranteController controller = new RestauranteController(cadastrarRestauranteUseCase, listarRestaurantesUseCase, buscarRestaurantePorIdUseCase, atualizarRestauranteUseCase, excluirRestauranteUseCase);
        when(buscarRestaurantePorIdUseCase.run("r1")).thenReturn(Restaurante.builder().id("r1").nomeRestaurante("Sabor").dono(Usuario.builder().id("u1").build()).build());

        RestauranteDTO dto = controller.buscarPorId("r1");

        assertEquals("r1", dto.id());
        assertEquals("Sabor", dto.nomeRestaurante());
    }

    @Test
    void deveAtualizarRestaurante() {
        RestauranteController controller = new RestauranteController(cadastrarRestauranteUseCase, listarRestaurantesUseCase, buscarRestaurantePorIdUseCase, atualizarRestauranteUseCase, excluirRestauranteUseCase);
        when(atualizarRestauranteUseCase.run("r1", "Bistro", "Rua B", "Japonesa", "Todos", "u1")).thenReturn(Restaurante.builder().id("r1").nomeRestaurante("Bistro").dono(Usuario.builder().id("u1").build()).build());

        RestauranteDTO dto = controller.atualizar("r1", new NovoRestauranteDTO("Bistro", "Rua B", "Japonesa", "Todos", "u1"));

        assertEquals("Bistro", dto.nomeRestaurante());
    }

    @Test
    void deveExcluirRestaurante() {
        RestauranteController controller = new RestauranteController(cadastrarRestauranteUseCase, listarRestaurantesUseCase, buscarRestaurantePorIdUseCase, atualizarRestauranteUseCase, excluirRestauranteUseCase);

        controller.excluir("r1");
    }
}
