package br.com.fiap.horadoprato.horadoprato.adapters.presenters;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.RestauranteDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestaurantePresenterTest {

    @Test
    void deveConverterRestauranteParaDto() {
        LocalDateTime agora = LocalDateTime.now();
        Restaurante restaurante = Restaurante.builder()
                .id("r1")
                .nomeRestaurante("Sabor")
                .endereco("Rua A")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("Seg a Dom")
                .dono(Usuario.builder().id("u1").build())
                .dataCriacao(agora)
                .dataAlteracao(agora)
                .build();

        RestauranteDTO dto = RestaurantePresenter.ToDTO(restaurante);

        assertEquals("r1", dto.id());
        assertEquals("Sabor", dto.nomeRestaurante());
        assertEquals("u1", dto.donoId());
    }

    @Test
    void deveConverterListaParaDto() {
        List<Restaurante> restaurantes = List.of(
                Restaurante.builder().id("r1").nomeRestaurante("Sabor").dono(Usuario.builder().id("u1").build()).build(),
                Restaurante.builder().id("r2").nomeRestaurante("Bistro").dono(Usuario.builder().id("u2").build()).build()
        );

        List<RestauranteDTO> resultado = RestaurantePresenter.ToDTO(restaurantes);

        assertEquals(2, resultado.size());
        assertEquals("Sabor", resultado.get(0).nomeRestaurante());
        assertEquals("Bistro", resultado.get(1).nomeRestaurante());
    }
}
