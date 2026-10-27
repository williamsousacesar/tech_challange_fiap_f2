package br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEhDonoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteUseCaseTest {

    @Mock
    private IRestauranteGateway restauranteGateway;

    @Mock
    private IUsuarioGateway usuarioGateway;

    @Test
    void cadastrarDeveCadastrarQuandoUsuarioEhDono() {
        CadastrarRestauranteUseCase useCase = CadastrarRestauranteUseCase.create(restauranteGateway, usuarioGateway);
        Usuario dono = usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono"));
        Restaurante restaurante = Restaurante.builder()
                .nomeRestaurante("Sabor")
                .endereco("Rua A")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("Seg a Dom")
                .dono(dono)
                .build();

        when(usuarioGateway.buscarPorId("u1")).thenReturn(dono);
        when(restauranteGateway.incluir(any(Restaurante.class))).thenReturn(restaurante);

        Restaurante result = useCase.run("Sabor", "Rua A", "Italiana", "Seg a Dom", "u1");

        assertEquals("Sabor", result.getNomeRestaurante());
    }

    @Test
    void cadastrarDeveLancarQuandoUsuarioNaoEhDono() {
        CadastrarRestauranteUseCase useCase = CadastrarRestauranteUseCase.create(restauranteGateway, usuarioGateway);

        when(usuarioGateway.buscarPorId("u1")).thenReturn(usuario("u1", tipoUsuario(1L, "CLIENTE", "Cliente")));

        assertThrows(UsuarioNaoEhDonoException.class, () -> useCase.run("Sabor", "Rua A", "Italiana", "Seg a Dom", "u1"));
    }

    @Test
    void atualizarDeveAtualizarQuandoValido() {
        AtualizarRestauranteUseCase useCase = AtualizarRestauranteUseCase.create(restauranteGateway, usuarioGateway);
        Restaurante restaurante = Restaurante.builder()
                .id("r1")
                .nomeRestaurante("Velho")
                .endereco("Rua X")
                .tipoCozinha("Brasileira")
                .horarioFuncionamento("Seg")
                .dono(usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")))
                .build();
        Usuario dono = usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono"));

        when(restauranteGateway.buscarPorId("r1")).thenReturn(restaurante);
        when(usuarioGateway.buscarPorId("u1")).thenReturn(dono);
        when(restauranteGateway.atualizar(restaurante)).thenReturn(restaurante);

        Restaurante result = useCase.run("r1", "Novo Nome", "Rua Z", "Japonesa", "Todos os dias", "u1");

        assertEquals("Novo Nome", result.getNomeRestaurante());
    }

    @Test
    void atualizarDeveLancarQuandoRestauranteNaoExiste() {
        AtualizarRestauranteUseCase useCase = AtualizarRestauranteUseCase.create(restauranteGateway, usuarioGateway);

        when(restauranteGateway.buscarPorId("r9")).thenReturn(null);

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.run("r9", "Nome", "Rua", "Italiana", "Seg", "u1"));
    }

    @Test
    void buscarPorIdDeveRetornarRestaurante() {
        BuscarRestaurantePorIdUseCase useCase = BuscarRestaurantePorIdUseCase.create(restauranteGateway);
        Restaurante restaurante = restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")));

        when(restauranteGateway.buscarPorId("r1")).thenReturn(restaurante);

        assertEquals("r1", useCase.run("r1").getId());
    }

    @Test
    void buscarPorIdDeveLancarQuandoNaoExiste() {
        BuscarRestaurantePorIdUseCase useCase = BuscarRestaurantePorIdUseCase.create(restauranteGateway);

        when(restauranteGateway.buscarPorId("r9")).thenReturn(null);

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.run("r9"));
    }

    @Test
    void listarDeveDelegarAoGateway() {
        ListarRestaurantesUseCase useCase = ListarRestaurantesUseCase.create(restauranteGateway);
        Page<Restaurante> page = new PageImpl<>(List.of(restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")))));
        Pageable pageable = Pageable.unpaged();

        when(restauranteGateway.listarTodos(pageable)).thenReturn(page);

        assertEquals(page, useCase.run(pageable));
    }

    @Test
    void excluirDeveExcluirQuandoExiste() {
        ExcluirRestauranteUseCase useCase = ExcluirRestauranteUseCase.create(restauranteGateway);

        when(restauranteGateway.buscarPorId("r1")).thenReturn(restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono"))));

        assertDoesNotThrow(() -> useCase.run("r1"));
        verify(restauranteGateway).excluir("r1");
    }

    @Test
    void excluirDeveLancarQuandoNaoExiste() {
        ExcluirRestauranteUseCase useCase = ExcluirRestauranteUseCase.create(restauranteGateway);

        when(restauranteGateway.buscarPorId("r9")).thenReturn(null);

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.run("r9"));
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

    private static Restaurante restaurante(String id, Usuario dono) {
        return Restaurante.builder()
                .id(id)
                .nomeRestaurante("Restaurante")
                .endereco("Rua A, 123")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("Seg a Dom")
                .dono(dono)
                .dataCriacao(LocalDateTime.now())
                .dataAlteracao(LocalDateTime.now())
                .build();
    }
}
