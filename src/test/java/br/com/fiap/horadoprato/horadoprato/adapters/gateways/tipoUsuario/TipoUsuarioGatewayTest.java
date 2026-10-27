package br.com.fiap.horadoprato.horadoprato.adapters.gateways.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.IDataStorageSource;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioGatewayTest {

    @Mock
    private IDataStorageSource dataStorageSource;

    @Test
    void deveIncluirTipoUsuario() {
        TipoUsuarioGateway gateway = new TipoUsuarioGateway(dataStorageSource);
        TipoUsuario tipo = TipoUsuario.builder().tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();
        when(dataStorageSource.incluirTipoUsuario(new NovoTipoUsuarioDTO("CLIENTE", "Cliente"))).thenReturn(new TipoUsuarioDTO(1L, "CLIENTE", "Cliente"));

        TipoUsuario result = gateway.incluir(tipo);

        assertEquals(1L, result.getId());
        assertEquals("CLIENTE", result.getTpUsuario());
    }

    @Test
    void deveBuscarPorTpUsuario() {
        TipoUsuarioGateway gateway = new TipoUsuarioGateway(dataStorageSource);
        when(dataStorageSource.buscarPorTpUsuario("CLIENTE")).thenReturn(new TipoUsuarioDTO(1L, "CLIENTE", "Cliente"));

        TipoUsuario result = gateway.buscarPorTpUsuario("CLIENTE");

        assertEquals("CLIENTE", result.getTpUsuario());
    }

    @Test
    void deveRetornarNullQuandoTipoNaoExiste() {
        TipoUsuarioGateway gateway = new TipoUsuarioGateway(dataStorageSource);
        when(dataStorageSource.buscarPorId(99L)).thenReturn(null);

        assertNull(gateway.buscarPorId(99L));
    }

    @Test
    void deveListarTodos() {
        TipoUsuarioGateway gateway = new TipoUsuarioGateway(dataStorageSource);
        Page<TipoUsuarioDTO> page = new PageImpl<>(List.of(new TipoUsuarioDTO(1L, "CLIENTE", "Cliente")));
        when(dataStorageSource.listarTodos(org.mockito.ArgumentMatchers.any(Pageable.class))).thenReturn(page);

        Page<TipoUsuario> resultado = gateway.listarTodos(Pageable.unpaged());

        assertEquals(1, resultado.getTotalElements());
        assertEquals("CLIENTE", resultado.getContent().getFirst().getTpUsuario());
    }

    @Test
    void deveAtualizarTipoUsuario() {
        TipoUsuarioGateway gateway = new TipoUsuarioGateway(dataStorageSource);
        TipoUsuario tipo = TipoUsuario.builder().id(1L).tpUsuario("DONO_RESTAURANTE").descricaoTipoUsuario("Dono").build();
        when(dataStorageSource.atualizarTipoUsuario(1L, new NovoTipoUsuarioDTO("DONO_RESTAURANTE", "Dono"))).thenReturn(new TipoUsuarioDTO(1L, "DONO_RESTAURANTE", "Dono"));

        TipoUsuario result = gateway.atualizar(tipo);

        assertEquals("DONO_RESTAURANTE", result.getTpUsuario());
    }
}
