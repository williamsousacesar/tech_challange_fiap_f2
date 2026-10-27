package br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioComRestauranteNaoPodeAlterarTipoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseTest {

    @Mock
    private IUsuarioGateway usuarioGateway;

    @Mock
    private ITipoUsuarioGateway tipoUsuarioGateway;

    @Mock
    private IRestauranteGateway restauranteGateway;

    @Test
    void buscarPorIdDeveRetornarUsuario() {
        BuscarUsuarioPorIdUseCase useCase = BuscarUsuarioPorIdUseCase.create(usuarioGateway);
        Usuario usuario = usuario("u1", tipoUsuario(1L, "CLIENTE", "Cliente"));

        when(usuarioGateway.buscarPorId("u1")).thenReturn(usuario);

        Usuario result = useCase.run("u1");

        assertEquals("u1", result.getId());
    }

    @Test
    void buscarPorIdDeveLancarQuandoNaoExiste() {
        BuscarUsuarioPorIdUseCase useCase = BuscarUsuarioPorIdUseCase.create(usuarioGateway);

        when(usuarioGateway.buscarPorId("u9")).thenReturn(null);

        assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.run("u9"));
    }

    @Test
    void listarDeveDelegarAoGateway() {
        ListarUsuariosUseCase useCase = ListarUsuariosUseCase.create(usuarioGateway);
        Page<Usuario> page = new PageImpl<>(List.of(usuario("u1", tipoUsuario(1L, "CLIENTE", "Cliente"))));
        Pageable pageable = Pageable.unpaged();

        when(usuarioGateway.listarTodos(pageable)).thenReturn(page);

        assertEquals(page, useCase.run(pageable));
    }

    @Test
    void alterarTipoDeveAlterarQuandoValido() {
        AlterarTipoDoUsuarioUseCase useCase = AlterarTipoDoUsuarioUseCase.create(usuarioGateway, tipoUsuarioGateway, restauranteGateway);
        Usuario usuario = usuario("u1", tipoUsuario(1L, "CLIENTE", "Cliente"));
        TipoUsuario novoTipo = tipoUsuario(2L, "DONO_RESTAURANTE", "Dono de restaurante");
        Usuario atualizado = usuario("u1", novoTipo);

        when(usuarioGateway.buscarPorId("u1")).thenReturn(usuario);
        when(tipoUsuarioGateway.buscarPorId(2L)).thenReturn(novoTipo);
        when(usuarioGateway.alterarTipoUsuario("u1", 2L)).thenReturn(atualizado);

        Usuario result = useCase.run("u1", 2L);

        assertEquals("DONO_RESTAURANTE", result.getTipoUsuario().getTpUsuario());
    }

    @Test
    void alterarTipoDeveLancarQuandoUsuarioNaoExiste() {
        AlterarTipoDoUsuarioUseCase useCase = AlterarTipoDoUsuarioUseCase.create(usuarioGateway, tipoUsuarioGateway, restauranteGateway);

        when(usuarioGateway.buscarPorId("u9")).thenReturn(null);

        assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.run("u9", 1L));
    }

    @Test
    void alterarTipoDeveLancarQuandoTipoNaoExiste() {
        AlterarTipoDoUsuarioUseCase useCase = AlterarTipoDoUsuarioUseCase.create(usuarioGateway, tipoUsuarioGateway, restauranteGateway);
        Usuario usuario = usuario("u1", tipoUsuario(1L, "CLIENTE", "Cliente"));

        when(usuarioGateway.buscarPorId("u1")).thenReturn(usuario);
        when(tipoUsuarioGateway.buscarPorId(2L)).thenReturn(null);

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.run("u1", 2L));
    }

    @Test
    void alterarTipoDeveLancarQuandoUsuarioEhDonoComRestaurante() {
        AlterarTipoDoUsuarioUseCase useCase = AlterarTipoDoUsuarioUseCase.create(usuarioGateway, tipoUsuarioGateway, restauranteGateway);
        Usuario usuario = usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono de restaurante"));
        TipoUsuario novoTipo = tipoUsuario(2L, "CLIENTE", "Cliente");

        when(usuarioGateway.buscarPorId("u1")).thenReturn(usuario);
        when(tipoUsuarioGateway.buscarPorId(2L)).thenReturn(novoTipo);
        when(restauranteGateway.existeRestauranteComDono("u1")).thenReturn(true);

        assertThrows(UsuarioComRestauranteNaoPodeAlterarTipoException.class, () -> useCase.run("u1", 2L));
    }

    private static TipoUsuario tipoUsuario(Long id, String tpUsuario, String descricao) {
        return TipoUsuario.builder().id(id).tpUsuario(tpUsuario).descricaoTipoUsuario(descricao).build();
    }

    private static Usuario usuario(String id, TipoUsuario tipoUsuario) {
        return Usuario.builder()
                .id(id)
                .login("login")
                .nome("Nome")
                .email("email@email.com")
                .senha("123")
                .tipoUsuario(tipoUsuario)
                .build();
    }
}
