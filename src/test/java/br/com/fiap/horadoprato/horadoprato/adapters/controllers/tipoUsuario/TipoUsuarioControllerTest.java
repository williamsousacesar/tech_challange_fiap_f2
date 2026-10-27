package br.com.fiap.horadoprato.horadoprato.adapters.controllers.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.AtualizarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.CadastrarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.ExcluirTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.ListarTiposUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioControllerTest {

    @Mock
    private CadastrarTipoUsuarioUseCase cadastrarTipoUsuarioUseCase;

    @Mock
    private ListarTiposUsuarioUseCase listarTiposUsuarioUseCase;

    @Mock
    private BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    @Mock
    private AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;

    @Mock
    private ExcluirTipoUsuarioUseCase excluirTipoUsuarioUseCase;

    @Test
    void deveCadastrarTipoUsuario() {
        TipoUsuarioController controller = new TipoUsuarioController(cadastrarTipoUsuarioUseCase, listarTiposUsuarioUseCase, buscarTipoUsuarioPorIdUseCase, atualizarTipoUsuarioUseCase, excluirTipoUsuarioUseCase);
        TipoUsuario tipo = TipoUsuario.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();
        when(cadastrarTipoUsuarioUseCase.run("CLIENTE", "Cliente")).thenReturn(tipo);

        TipoUsuarioDTO dto = controller.cadastrar(new NovoTipoUsuarioDTO("CLIENTE", "Cliente"));

        assertNotNull(dto);
        assertEquals("CLIENTE", dto.tpUsuario());
    }

    @Test
    void deveListarTiposUsuario() {
        TipoUsuarioController controller = new TipoUsuarioController(cadastrarTipoUsuarioUseCase, listarTiposUsuarioUseCase, buscarTipoUsuarioPorIdUseCase, atualizarTipoUsuarioUseCase, excluirTipoUsuarioUseCase);
        Page<TipoUsuario> page = new PageImpl<>(List.of(TipoUsuario.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build()));
        when(listarTiposUsuarioUseCase.run(org.mockito.ArgumentMatchers.any(Pageable.class))).thenReturn(page);

        Page<TipoUsuarioDTO> resultado = controller.listar(0, 10);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("CLIENTE", resultado.getContent().getFirst().tpUsuario());
    }

    @Test
    void deveBuscarTipoUsuarioPorId() {
        TipoUsuarioController controller = new TipoUsuarioController(cadastrarTipoUsuarioUseCase, listarTiposUsuarioUseCase, buscarTipoUsuarioPorIdUseCase, atualizarTipoUsuarioUseCase, excluirTipoUsuarioUseCase);
        when(buscarTipoUsuarioPorIdUseCase.run(1L)).thenReturn(TipoUsuario.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build());

        TipoUsuarioDTO dto = controller.buscarPorId(1L);

        assertEquals(1L, dto.id());
        assertEquals("CLIENTE", dto.tpUsuario());
    }

    @Test
    void deveAtualizarTipoUsuario() {
        TipoUsuarioController controller = new TipoUsuarioController(cadastrarTipoUsuarioUseCase, listarTiposUsuarioUseCase, buscarTipoUsuarioPorIdUseCase, atualizarTipoUsuarioUseCase, excluirTipoUsuarioUseCase);
        when(atualizarTipoUsuarioUseCase.run(1L, "DONO_RESTAURANTE", "Dono")).thenReturn(TipoUsuario.builder().id(1L).tpUsuario("DONO_RESTAURANTE").descricaoTipoUsuario("Dono").build());

        TipoUsuarioDTO dto = controller.atualizar(1L, new NovoTipoUsuarioDTO("DONO_RESTAURANTE", "Dono"));

        assertEquals("DONO_RESTAURANTE", dto.tpUsuario());
    }
}
