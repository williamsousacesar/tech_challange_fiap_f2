package br.com.fiap.horadoprato.horadoprato.adapters.presenters;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TipoUsuarioPresenterTest {

    @Test
    void deveConverterTipoUsuarioParaDto() {
        TipoUsuario tipoUsuario = TipoUsuario.builder()
                .id(1L)
                .tpUsuario("CLIENTE")
                .descricaoTipoUsuario("Cliente")
                .build();

        TipoUsuarioDTO dto = TipoUsuarioPresenter.ToDTO(tipoUsuario);

        assertEquals(1L, dto.id());
        assertEquals("CLIENTE", dto.tpUsuario());
        assertEquals("Cliente", dto.descricaoTipoUsuario());
    }

    @Test
    void deveConverterListaParaDto() {
        List<TipoUsuario> tipos = List.of(
                TipoUsuario.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build(),
                TipoUsuario.builder().id(2L).tpUsuario("DONO_RESTAURANTE").descricaoTipoUsuario("Dono").build()
        );

        List<TipoUsuarioDTO> resultado = TipoUsuarioPresenter.ToDTO(tipos);

        assertEquals(2, resultado.size());
        assertEquals("CLIENTE", resultado.get(0).tpUsuario());
        assertEquals("DONO_RESTAURANTE", resultado.get(1).tpUsuario());
    }
}
