package br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioEmUsoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioJaExisteException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioUseCaseTest {

    @Mock
    private ITipoUsuarioGateway tipoUsuarioGateway;

    @Mock
    private IUsuarioGateway usuarioGateway;

    @Test
    void cadastrarDeveCadastrarQuandoNaoExiste() {
        CadastrarTipoUsuarioUseCase useCase = CadastrarTipoUsuarioUseCase.create(tipoUsuarioGateway);
        TipoUsuario tipoUsuario = tipoUsuario(1L, "CLIENTE", "Cliente");

        when(tipoUsuarioGateway.buscarPorTpUsuario("CLIENTE")).thenReturn(null);
        when(tipoUsuarioGateway.incluir(any(TipoUsuario.class))).thenReturn(tipoUsuario);

        TipoUsuario result = useCase.run("CLIENTE", "Cliente");

        assertNotNull(result);
        assertEquals("CLIENTE", result.getTpUsuario());
        verify(tipoUsuarioGateway).incluir(any(TipoUsuario.class));
    }

    @Test
    void cadastrarDeveLancarQuandoJaExiste() {
        CadastrarTipoUsuarioUseCase useCase = CadastrarTipoUsuarioUseCase.create(tipoUsuarioGateway);

        when(tipoUsuarioGateway.buscarPorTpUsuario("CLIENTE")).thenReturn(tipoUsuario(1L, "CLIENTE", "Cliente"));

        assertThrows(TipoUsuarioJaExisteException.class, () -> useCase.run("CLIENTE", "Cliente"));
    }

    @Test
    void atualizarDeveAtualizarQuandoValido() {
        AtualizarTipoUsuarioUseCase useCase = AtualizarTipoUsuarioUseCase.create(tipoUsuarioGateway);
        TipoUsuario tipo = tipoUsuario(1L, "CLIENTE", "Cliente");

        when(tipoUsuarioGateway.buscarPorId(1L)).thenReturn(tipo);
        when(tipoUsuarioGateway.buscarPorTpUsuario("DONO_RESTAURANTE")).thenReturn(null);
        when(tipoUsuarioGateway.atualizar(tipo)).thenReturn(tipo);

        tipo.setTpUsuario("DONO_RESTAURANTE");
        tipo.setDescricaoTipoUsuario("Dono de restaurante");

        TipoUsuario result = useCase.run(1L, "DONO_RESTAURANTE", "Dono de restaurante");

        assertEquals("DONO_RESTAURANTE", result.getTpUsuario());
    }

    @Test
    void atualizarDeveLancarQuandoTipoNaoExiste() {
        AtualizarTipoUsuarioUseCase useCase = AtualizarTipoUsuarioUseCase.create(tipoUsuarioGateway);

        when(tipoUsuarioGateway.buscarPorId(99L)).thenReturn(null);

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.run(99L, "CLIENTE", "Cliente"));
    }

    @Test
    void buscarPorIdDeveRetornarTipo() {
        BuscarTipoUsuarioPorIdUseCase useCase = BuscarTipoUsuarioPorIdUseCase.create(tipoUsuarioGateway);
        TipoUsuario tipo = tipoUsuario(1L, "CLIENTE", "Cliente");

        when(tipoUsuarioGateway.buscarPorId(1L)).thenReturn(tipo);

        TipoUsuario result = useCase.run(1L);

        assertEquals("CLIENTE", result.getTpUsuario());
    }

    @Test
    void buscarPorIdDeveLancarQuandoNaoExiste() {
        BuscarTipoUsuarioPorIdUseCase useCase = BuscarTipoUsuarioPorIdUseCase.create(tipoUsuarioGateway);

        when(tipoUsuarioGateway.buscarPorId(50L)).thenReturn(null);

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.run(50L));
    }

    @Test
    void listarDeveDelegarAoGateway() {
        ListarTiposUsuarioUseCase useCase = ListarTiposUsuarioUseCase.create(tipoUsuarioGateway);
        Page<TipoUsuario> page = new PageImpl<>(List.of(tipoUsuario(1L, "CLIENTE", "Cliente")));
        Pageable pageable = Pageable.unpaged();

        when(tipoUsuarioGateway.listarTodos(pageable)).thenReturn(page);

        assertEquals(page, useCase.run(pageable));
    }

    @Test
    void excluirDeveExcluirQuandoValido() {
        ExcluirTipoUsuarioUseCase useCase = ExcluirTipoUsuarioUseCase.create(tipoUsuarioGateway, usuarioGateway);

        when(tipoUsuarioGateway.buscarPorId(1L)).thenReturn(tipoUsuario(1L, "CLIENTE", "Cliente"));
        when(usuarioGateway.existeUsuarioComTipo(1L)).thenReturn(false);

        assertDoesNotThrow(() -> useCase.run(1L));
        verify(tipoUsuarioGateway).excluir(1L);
    }

    @Test
    void excluirDeveLancarQuandoTipoNaoExiste() {
        ExcluirTipoUsuarioUseCase useCase = ExcluirTipoUsuarioUseCase.create(tipoUsuarioGateway, usuarioGateway);

        when(tipoUsuarioGateway.buscarPorId(1L)).thenReturn(null);

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.run(1L));
    }

    @Test
    void excluirDeveLancarQuandoTipoEmUso() {
        ExcluirTipoUsuarioUseCase useCase = ExcluirTipoUsuarioUseCase.create(tipoUsuarioGateway, usuarioGateway);

        when(tipoUsuarioGateway.buscarPorId(1L)).thenReturn(tipoUsuario(1L, "CLIENTE", "Cliente"));
        when(usuarioGateway.existeUsuarioComTipo(1L)).thenReturn(true);

        assertThrows(TipoUsuarioEmUsoException.class, () -> useCase.run(1L));
    }

    private static TipoUsuario tipoUsuario(Long id, String tpUsuario, String descricao) {
        return TipoUsuario.builder().id(id).tpUsuario(tpUsuario).descricaoTipoUsuario(descricao).build();
    }
}
