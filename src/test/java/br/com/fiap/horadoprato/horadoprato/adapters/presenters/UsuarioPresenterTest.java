package br.com.fiap.horadoprato.horadoprato.adapters.presenters;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioPresenterTest {

    @Test
    void deveConverterUsuarioParaDto() {
        Usuario usuario = Usuario.builder()
                .id("u1")
                .login("carlos")
                .nome("Carlos")
                .email("carlos@email.com")
                .tipoUsuario(TipoUsuario.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build())
                .build();

        UsuarioDTO dto = UsuarioPresenter.ToDTO(usuario);

        assertEquals("u1", dto.id());
        assertEquals("carlos", dto.login());
        assertEquals("CLIENTE", dto.tipoUsuario().tpUsuario());
    }

    @Test
    void deveConverterListaParaDto() {
        List<Usuario> usuarios = List.of(
                Usuario.builder().id("u1").login("carlos").nome("Carlos").email("carlos@email.com").tipoUsuario(TipoUsuario.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build()).build(),
                Usuario.builder().id("u2").login("ana").nome("Ana").email("ana@email.com").tipoUsuario(TipoUsuario.builder().id(2L).tpUsuario("DONO_RESTAURANTE").descricaoTipoUsuario("Dono").build()).build()
        );

        List<UsuarioDTO> resultado = UsuarioPresenter.ToDTO(usuarios);

        assertEquals(2, resultado.size());
        assertEquals("u1", resultado.get(0).id());
        assertEquals("DONO_RESTAURANTE", resultado.get(1).tipoUsuario().tpUsuario());
    }
}
