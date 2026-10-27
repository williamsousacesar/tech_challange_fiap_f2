package br.com.fiap.horadoprato.horadoprato.adapters.gateways.usuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioDataStorageSource;
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
class UsuarioGatewayTest {

    @Mock
    private IUsuarioDataStorageSource dataStorageSource;

    @Test
    void deveBuscarPorId() {
        UsuarioGateway gateway = new UsuarioGateway(dataStorageSource);
        when(dataStorageSource.buscarPorId("u1")).thenReturn(new UsuarioDTO("u1", "carlos", "Carlos", "carlos@email.com", new TipoUsuarioDTO(1L, "CLIENTE", "Cliente")));

        Usuario result = gateway.buscarPorId("u1");

        assertEquals("u1", result.getId());
        assertEquals("CLIENTE", result.getTipoUsuario().getTpUsuario());
    }

    @Test
    void deveRetornarNullQuandoUsuarioNaoExiste() {
        UsuarioGateway gateway = new UsuarioGateway(dataStorageSource);
        when(dataStorageSource.buscarPorId("u9")).thenReturn(null);

        assertNull(gateway.buscarPorId("u9"));
    }

    @Test
    void deveListarTodos() {
        UsuarioGateway gateway = new UsuarioGateway(dataStorageSource);
        Page<UsuarioDTO> page = new PageImpl<>(List.of(new UsuarioDTO("u1", "carlos", "Carlos", "carlos@email.com", new TipoUsuarioDTO(1L, "CLIENTE", "Cliente"))));
        when(dataStorageSource.listarTodos(any(Pageable.class))).thenReturn(page);

        Page<Usuario> resultado = gateway.listarTodos(Pageable.unpaged());

        assertEquals(1, resultado.getTotalElements());
        assertEquals("carlos", resultado.getContent().getFirst().getLogin());
    }

    @Test
    void deveAlterarTipoUsuario() {
        UsuarioGateway gateway = new UsuarioGateway(dataStorageSource);
        when(dataStorageSource.alterarTipoUsuario("u1", 2L)).thenReturn(new UsuarioDTO("u1", "carlos", "Carlos", "carlos@email.com", new TipoUsuarioDTO(2L, "DONO_RESTAURANTE", "Dono")));

        Usuario result = gateway.alterarTipoUsuario("u1", 2L);

        assertEquals("DONO_RESTAURANTE", result.getTipoUsuario().getTpUsuario());
    }

    @Test
    void deveVerificarSeExisteUsuarioComTipo() {
        UsuarioGateway gateway = new UsuarioGateway(dataStorageSource);
        when(dataStorageSource.existeUsuarioComTipo(1L)).thenReturn(true);

        assertEquals(true, gateway.existeUsuarioComTipo(1L));
    }
}
