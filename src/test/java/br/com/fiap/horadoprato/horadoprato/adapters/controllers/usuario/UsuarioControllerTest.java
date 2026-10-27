package br.com.fiap.horadoprato.horadoprato.adapters.controllers.usuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.AlterarTipoDoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.BuscarUsuarioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.ListarUsuariosUseCase;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.AlterarTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;
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
class UsuarioControllerTest {

    @Mock
    private ListarUsuariosUseCase listarUsuariosUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private AlterarTipoDoUsuarioUseCase alterarTipoDoUsuarioUseCase;

    @Test
    void deveListarUsuarios() {
        UsuarioController controller = new UsuarioController(listarUsuariosUseCase, buscarUsuarioPorIdUseCase, alterarTipoDoUsuarioUseCase);
        Page<Usuario> page = new PageImpl<>(List.of(Usuario.builder().id("u1").login("carlos").tipoUsuario(TipoUsuario.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build()).build()));
        when(listarUsuariosUseCase.run(any(Pageable.class))).thenReturn(page);

        Page<UsuarioDTO> resultado = controller.listar(0, 10);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("carlos", resultado.getContent().getFirst().login());
    }

    @Test
    void deveBuscarUsuarioPorId() {
        UsuarioController controller = new UsuarioController(listarUsuariosUseCase, buscarUsuarioPorIdUseCase, alterarTipoDoUsuarioUseCase);
        when(buscarUsuarioPorIdUseCase.run("u1")).thenReturn(Usuario.builder().id("u1").login("carlos").tipoUsuario(TipoUsuario.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build()).build());

        UsuarioDTO dto = controller.buscarPorId("u1");

        assertEquals("u1", dto.id());
        assertEquals("carlos", dto.login());
    }

    @Test
    void deveAlterarTipoDoUsuario() {
        UsuarioController controller = new UsuarioController(listarUsuariosUseCase, buscarUsuarioPorIdUseCase, alterarTipoDoUsuarioUseCase);
        when(alterarTipoDoUsuarioUseCase.run("u1", 2L)).thenReturn(Usuario.builder().id("u1").login("carlos").tipoUsuario(TipoUsuario.builder().id(2L).tpUsuario("DONO_RESTAURANTE").descricaoTipoUsuario("Dono").build()).build());

        UsuarioDTO dto = controller.alterarTipo("u1", new AlterarTipoUsuarioDTO(2L));

        assertEquals("DONO_RESTAURANTE", dto.tipoUsuario().tpUsuario());
    }
}
