package br.com.fiap.horadoprato.horadoprato.adapters.gateways.restaurante;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.RestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteDataStorageSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteGatewayTest {

    @Mock
    private IRestauranteDataStorageSource dataStorageSource;

    @Test
    void deveIncluirRestaurante() {
        RestauranteGateway gateway = new RestauranteGateway(dataStorageSource);
        Restaurante restaurante = Restaurante.builder().nomeRestaurante("Sabor").endereco("Rua A").tipoCozinha("Italiana").horarioFuncionamento("Seg a Dom").dono(br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario.builder().id("u1").build()).build();
        when(dataStorageSource.incluirRestaurante(new NovoRestauranteDTO("Sabor", "Rua A", "Italiana", "Seg a Dom", "u1"))).thenReturn(new RestauranteDTO("r1", "Sabor", "Rua A", "Italiana", "Seg a Dom", "u1", null, null));

        Restaurante result = gateway.incluir(restaurante);

        assertEquals("r1", result.getId());
        assertEquals("Sabor", result.getNomeRestaurante());
    }

    @Test
    void deveBuscarPorId() {
        RestauranteGateway gateway = new RestauranteGateway(dataStorageSource);
        when(dataStorageSource.buscarPorId("r1")).thenReturn(new RestauranteDTO("r1", "Sabor", "Rua A", "Italiana", "Seg a Dom", "u1", null, null));

        Restaurante result = gateway.buscarPorId("r1");

        assertEquals("Sabor", result.getNomeRestaurante());
    }

    @Test
    void deveRetornarNullQuandoNaoExiste() {
        RestauranteGateway gateway = new RestauranteGateway(dataStorageSource);
        when(dataStorageSource.buscarPorId("r9")).thenReturn(null);

        assertNull(gateway.buscarPorId("r9"));
    }

    @Test
    void deveListarTodos() {
        RestauranteGateway gateway = new RestauranteGateway(dataStorageSource);
        Page<RestauranteDTO> page = new PageImpl<>(List.of(new RestauranteDTO("r1", "Sabor", "Rua A", "Italiana", "Seg a Dom", "u1", null, null)));
        when(dataStorageSource.listarTodos(any(Pageable.class))).thenReturn(page);

        Page<Restaurante> resultado = gateway.listarTodos(Pageable.unpaged());

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Sabor", resultado.getContent().getFirst().getNomeRestaurante());
    }

    @Test
    void deveVerificarSeRestauranteExisteComDono() {
        RestauranteGateway gateway = new RestauranteGateway(dataStorageSource);
        when(dataStorageSource.existeRestauranteComDono("u1")).thenReturn(true);

        assertEquals(true, gateway.existeRestauranteComDono("u1"));
    }
}
