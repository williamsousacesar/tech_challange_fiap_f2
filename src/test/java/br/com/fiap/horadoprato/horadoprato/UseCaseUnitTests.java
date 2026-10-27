package br.com.fiap.horadoprato.horadoprato;

import br.com.fiap.horadoprato.horadoprato.domain.entities.ItemCardapio;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.AtualizarItemCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.BuscarItemCardapioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.CadastrarItemCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.ExcluirItemCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.ListarItensCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.AtualizarRestauranteUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.BuscarRestaurantePorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.CadastrarRestauranteUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.ExcluirRestauranteUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.ListarRestaurantesUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.AtualizarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.CadastrarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.ExcluirTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.ListarTiposUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.AlterarTipoDoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.BuscarUsuarioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.ListarUsuariosUseCase;
import br.com.fiap.horadoprato.horadoprato.exceptions.ItemCardapioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteComItensCardapioException;
import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioEmUsoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioJaExisteException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioComRestauranteNaoPodeAlterarTipoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEhDonoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UseCaseUnitTests {

    @Test
    void cadastrarTipoUsuarioUseCase_deveCadastrarQuandoNaoExiste() {
        ITipoUsuarioGateway gateway = mock(ITipoUsuarioGateway.class);
        CadastrarTipoUsuarioUseCase useCase = CadastrarTipoUsuarioUseCase.create(gateway);
        TipoUsuario tipoUsuario = tipoUsuario(1L, "CLIENTE", "Cliente");

        when(gateway.buscarPorTpUsuario("CLIENTE")).thenReturn(null);
        when(gateway.incluir(any(TipoUsuario.class))).thenReturn(tipoUsuario);

        TipoUsuario result = useCase.run("CLIENTE", "Cliente");

        assertNotNull(result);
        assertEquals("CLIENTE", result.getTpUsuario());
        verify(gateway).incluir(any(TipoUsuario.class));
    }

    @Test
    void cadastrarTipoUsuarioUseCase_deveLancarQuandoJaExiste() {
        ITipoUsuarioGateway gateway = mock(ITipoUsuarioGateway.class);
        CadastrarTipoUsuarioUseCase useCase = CadastrarTipoUsuarioUseCase.create(gateway);

        when(gateway.buscarPorTpUsuario("CLIENTE")).thenReturn(tipoUsuario(1L, "CLIENTE", "Cliente"));

        assertThrows(TipoUsuarioJaExisteException.class, () -> useCase.run("CLIENTE", "Cliente"));
    }

    @Test
    void atualizarTipoUsuarioUseCase_deveAtualizarQuandoValido() {
        ITipoUsuarioGateway gateway = mock(ITipoUsuarioGateway.class);
        AtualizarTipoUsuarioUseCase useCase = AtualizarTipoUsuarioUseCase.create(gateway);
        TipoUsuario tipo = tipoUsuario(1L, "CLIENTE", "Cliente");

        when(gateway.buscarPorId(1L)).thenReturn(tipo);
        when(gateway.buscarPorTpUsuario("DONO_RESTAURANTE")).thenReturn(null);
        when(gateway.atualizar(tipo)).thenReturn(tipo);

        tipo.setTpUsuario("DONO_RESTAURANTE");
        tipo.setDescricaoTipoUsuario("Dono de restaurante");

        TipoUsuario result = useCase.run(1L, "DONO_RESTAURANTE", "Dono de restaurante");

        assertEquals("DONO_RESTAURANTE", result.getTpUsuario());
    }

    @Test
    void atualizarTipoUsuarioUseCase_deveLancarQuandoTipoNaoExiste() {
        ITipoUsuarioGateway gateway = mock(ITipoUsuarioGateway.class);
        AtualizarTipoUsuarioUseCase useCase = AtualizarTipoUsuarioUseCase.create(gateway);

        when(gateway.buscarPorId(99L)).thenReturn(null);

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.run(99L, "CLIENTE", "Cliente"));
    }

    @Test
    void buscarTipoUsuarioPorIdUseCase_deveRetornarTipo() {
        ITipoUsuarioGateway gateway = mock(ITipoUsuarioGateway.class);
        BuscarTipoUsuarioPorIdUseCase useCase = BuscarTipoUsuarioPorIdUseCase.create(gateway);
        TipoUsuario tipo = tipoUsuario(1L, "CLIENTE", "Cliente");

        when(gateway.buscarPorId(1L)).thenReturn(tipo);

        TipoUsuario result = useCase.run(1L);

        assertEquals("CLIENTE", result.getTpUsuario());
    }

    @Test
    void buscarTipoUsuarioPorIdUseCase_deveLancarQuandoNaoExiste() {
        ITipoUsuarioGateway gateway = mock(ITipoUsuarioGateway.class);
        BuscarTipoUsuarioPorIdUseCase useCase = BuscarTipoUsuarioPorIdUseCase.create(gateway);

        when(gateway.buscarPorId(50L)).thenReturn(null);

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.run(50L));
    }

    @Test
    void listarTiposUsuarioUseCase_deveDelegarAoGateway() {
        ITipoUsuarioGateway gateway = mock(ITipoUsuarioGateway.class);
        ListarTiposUsuarioUseCase useCase = ListarTiposUsuarioUseCase.create(gateway);
        Page<TipoUsuario> page = new PageImpl<>(List.of(tipoUsuario(1L, "CLIENTE", "Cliente")));
        Pageable pageable = Pageable.unpaged();

        when(gateway.listarTodos(pageable)).thenReturn(page);

        assertEquals(page, useCase.run(pageable));
    }

    @Test
    void excluirTipoUsuarioUseCase_deveExcluirQuandoValido() {
        ITipoUsuarioGateway gateway = mock(ITipoUsuarioGateway.class);
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        ExcluirTipoUsuarioUseCase useCase = ExcluirTipoUsuarioUseCase.create(gateway, usuarioGateway);

        when(gateway.buscarPorId(1L)).thenReturn(tipoUsuario(1L, "CLIENTE", "Cliente"));
        when(usuarioGateway.existeUsuarioComTipo(1L)).thenReturn(false);

        assertDoesNotThrow(() -> useCase.run(1L));
        verify(gateway).excluir(1L);
    }

    @Test
    void excluirTipoUsuarioUseCase_deveLancarQuandoTipoNaoExiste() {
        ITipoUsuarioGateway gateway = mock(ITipoUsuarioGateway.class);
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        ExcluirTipoUsuarioUseCase useCase = ExcluirTipoUsuarioUseCase.create(gateway, usuarioGateway);

        when(gateway.buscarPorId(1L)).thenReturn(null);

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.run(1L));
    }

    @Test
    void excluirTipoUsuarioUseCase_deveLancarQuandoTipoEmUso() {
        ITipoUsuarioGateway gateway = mock(ITipoUsuarioGateway.class);
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        ExcluirTipoUsuarioUseCase useCase = ExcluirTipoUsuarioUseCase.create(gateway, usuarioGateway);

        when(gateway.buscarPorId(1L)).thenReturn(tipoUsuario(1L, "CLIENTE", "Cliente"));
        when(usuarioGateway.existeUsuarioComTipo(1L)).thenReturn(true);

        assertThrows(TipoUsuarioEmUsoException.class, () -> useCase.run(1L));
    }

    @Test
    void buscarUsuarioPorIdUseCase_deveRetornarUsuario() {
        IUsuarioGateway gateway = mock(IUsuarioGateway.class);
        BuscarUsuarioPorIdUseCase useCase = BuscarUsuarioPorIdUseCase.create(gateway);
        Usuario usuario = usuario("u1", tipoUsuario(1L, "CLIENTE", "Cliente"));

        when(gateway.buscarPorId("u1")).thenReturn(usuario);

        Usuario result = useCase.run("u1");

        assertEquals("u1", result.getId());
    }

    @Test
    void buscarUsuarioPorIdUseCase_deveLancarQuandoNaoExiste() {
        IUsuarioGateway gateway = mock(IUsuarioGateway.class);
        BuscarUsuarioPorIdUseCase useCase = BuscarUsuarioPorIdUseCase.create(gateway);

        when(gateway.buscarPorId("u9")).thenReturn(null);

        assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.run("u9"));
    }

    @Test
    void listarUsuariosUseCase_deveDelegarAoGateway() {
        IUsuarioGateway gateway = mock(IUsuarioGateway.class);
        ListarUsuariosUseCase useCase = ListarUsuariosUseCase.create(gateway);
        Page<Usuario> page = new PageImpl<>(List.of(usuario("u1", tipoUsuario(1L, "CLIENTE", "Cliente"))));
        Pageable pageable = Pageable.unpaged();

        when(gateway.listarTodos(pageable)).thenReturn(page);

        assertEquals(page, useCase.run(pageable));
    }

    @Test
    void alterarTipoDoUsuarioUseCase_deveAlterarQuandoValido() {
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        ITipoUsuarioGateway tipoUsuarioGateway = mock(ITipoUsuarioGateway.class);
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        AlterarTipoDoUsuarioUseCase useCase = AlterarTipoDoUsuarioUseCase.create(usuarioGateway, tipoUsuarioGateway, restauranteGateway);
        Usuario usuario = usuario("u1", tipoUsuario(1L, "CLIENTE", "Cliente"));
        TipoUsuario novoTipo = tipoUsuario(2L, "DONO_RESTAURANTE", "Dono de restaurante");
        Usuario atualizado = usuario("u1", novoTipo);

        when(usuarioGateway.buscarPorId("u1")).thenReturn(usuario);
        when(tipoUsuarioGateway.buscarPorId(2L)).thenReturn(novoTipo);
        when(restauranteGateway.existeRestauranteComDono("u1")).thenReturn(false);
        when(usuarioGateway.alterarTipoUsuario("u1", 2L)).thenReturn(atualizado);

        Usuario result = useCase.run("u1", 2L);

        assertEquals("DONO_RESTAURANTE", result.getTipoUsuario().getTpUsuario());
    }

    @Test
    void alterarTipoDoUsuarioUseCase_deveLancarQuandoUsuarioNaoExiste() {
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        ITipoUsuarioGateway tipoUsuarioGateway = mock(ITipoUsuarioGateway.class);
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        AlterarTipoDoUsuarioUseCase useCase = AlterarTipoDoUsuarioUseCase.create(usuarioGateway, tipoUsuarioGateway, restauranteGateway);

        when(usuarioGateway.buscarPorId("u9")).thenReturn(null);

        assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.run("u9", 1L));
    }

    @Test
    void alterarTipoDoUsuarioUseCase_deveLancarQuandoTipoNaoExiste() {
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        ITipoUsuarioGateway tipoUsuarioGateway = mock(ITipoUsuarioGateway.class);
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        AlterarTipoDoUsuarioUseCase useCase = AlterarTipoDoUsuarioUseCase.create(usuarioGateway, tipoUsuarioGateway, restauranteGateway);
        Usuario usuario = usuario("u1", tipoUsuario(1L, "CLIENTE", "Cliente"));

        when(usuarioGateway.buscarPorId("u1")).thenReturn(usuario);
        when(tipoUsuarioGateway.buscarPorId(2L)).thenReturn(null);

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.run("u1", 2L));
    }

    @Test
    void alterarTipoDoUsuarioUseCase_deveLancarQuandoUsuarioEhDonoComRestaurante() {
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        ITipoUsuarioGateway tipoUsuarioGateway = mock(ITipoUsuarioGateway.class);
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        AlterarTipoDoUsuarioUseCase useCase = AlterarTipoDoUsuarioUseCase.create(usuarioGateway, tipoUsuarioGateway, restauranteGateway);
        Usuario usuario = usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono de restaurante"));
        TipoUsuario novoTipo = tipoUsuario(2L, "CLIENTE", "Cliente");

        when(usuarioGateway.buscarPorId("u1")).thenReturn(usuario);
        when(tipoUsuarioGateway.buscarPorId(2L)).thenReturn(novoTipo);
        when(restauranteGateway.existeRestauranteComDono("u1")).thenReturn(true);

        assertThrows(UsuarioComRestauranteNaoPodeAlterarTipoException.class, () -> useCase.run("u1", 2L));
    }

    @Test
    void cadastrarRestauranteUseCase_deveCadastrarQuandoUsuarioEhDono() {
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
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
    void cadastrarRestauranteUseCase_deveLancarQuandoUsuarioNaoEhDono() {
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        CadastrarRestauranteUseCase useCase = CadastrarRestauranteUseCase.create(restauranteGateway, usuarioGateway);

        when(usuarioGateway.buscarPorId("u1")).thenReturn(usuario("u1", tipoUsuario(1L, "CLIENTE", "Cliente")));

        assertThrows(UsuarioNaoEhDonoException.class, () -> useCase.run("Sabor", "Rua A", "Italiana", "Seg a Dom", "u1"));
    }

    @Test
    void atualizarRestauranteUseCase_deveAtualizarQuandoValido() {
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        AtualizarRestauranteUseCase useCase = AtualizarRestauranteUseCase.create(restauranteGateway, usuarioGateway);
        Restaurante restaurante = Restaurante.builder().id("r1").nomeRestaurante("Velho").endereco("Rua X").tipoCozinha("Brasileira").horarioFuncionamento("Seg").dono(usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono"))).build();
        Usuario dono = usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono"));

        when(restauranteGateway.buscarPorId("r1")).thenReturn(restaurante);
        when(usuarioGateway.buscarPorId("u1")).thenReturn(dono);
        when(restauranteGateway.atualizar(restaurante)).thenReturn(restaurante);

        Restaurante result = useCase.run("r1", "Novo Nome", "Rua Z", "Japonesa", "Todos os dias", "u1");

        assertEquals("Novo Nome", result.getNomeRestaurante());
    }

    @Test
    void atualizarRestauranteUseCase_deveLancarQuandoRestauranteNaoExiste() {
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        AtualizarRestauranteUseCase useCase = AtualizarRestauranteUseCase.create(restauranteGateway, usuarioGateway);

        when(restauranteGateway.buscarPorId("r9")).thenReturn(null);

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.run("r9", "Nome", "Rua", "Italiana", "Seg", "u1"));
    }

    @Test
    void buscarRestaurantePorIdUseCase_deveRetornarRestaurante() {
        IRestauranteGateway gateway = mock(IRestauranteGateway.class);
        BuscarRestaurantePorIdUseCase useCase = BuscarRestaurantePorIdUseCase.create(gateway);
        Restaurante restaurante = restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")));

        when(gateway.buscarPorId("r1")).thenReturn(restaurante);

        assertEquals("r1", useCase.run("r1").getId());
    }

    @Test
    void buscarRestaurantePorIdUseCase_deveLancarQuandoNaoExiste() {
        IRestauranteGateway gateway = mock(IRestauranteGateway.class);
        BuscarRestaurantePorIdUseCase useCase = BuscarRestaurantePorIdUseCase.create(gateway);

        when(gateway.buscarPorId("r9")).thenReturn(null);

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.run("r9"));
    }

    @Test
    void listarRestaurantesUseCase_deveDelegarAoGateway() {
        IRestauranteGateway gateway = mock(IRestauranteGateway.class);
        ListarRestaurantesUseCase useCase = ListarRestaurantesUseCase.create(gateway);
        Page<Restaurante> page = new PageImpl<>(List.of(restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")))));
        Pageable pageable = Pageable.unpaged();

        when(gateway.listarTodos(pageable)).thenReturn(page);

        assertEquals(page, useCase.run(pageable));
    }

    @Test
    void excluirRestauranteUseCase_deveExcluirQuandoExiste() {
        IRestauranteGateway gateway = mock(IRestauranteGateway.class);
        IItemCardapioGateway itemGateway = mock(IItemCardapioGateway.class);
        ExcluirRestauranteUseCase useCase = ExcluirRestauranteUseCase.create(gateway, itemGateway);

        when(gateway.buscarPorId("r1")).thenReturn(restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono"))));
        when(itemGateway.existeItemComRestaurante("r1")).thenReturn(false);

        assertDoesNotThrow(() -> useCase.run("r1"));
        verify(gateway).excluir("r1");
    }

    @Test
    void excluirRestauranteUseCase_deveLancarQuandoNaoExiste() {
        IRestauranteGateway gateway = mock(IRestauranteGateway.class);
        IItemCardapioGateway itemGateway = mock(IItemCardapioGateway.class);
        ExcluirRestauranteUseCase useCase = ExcluirRestauranteUseCase.create(gateway, itemGateway);

        when(gateway.buscarPorId("r9")).thenReturn(null);

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.run("r9"));
    }

    @Test
    void excluirRestauranteUseCase_deveLancarQuandoPossuiItensNoCardapio() {
        IRestauranteGateway gateway = mock(IRestauranteGateway.class);
        IItemCardapioGateway itemGateway = mock(IItemCardapioGateway.class);
        ExcluirRestauranteUseCase useCase = ExcluirRestauranteUseCase.create(gateway, itemGateway);

        when(gateway.buscarPorId("r1")).thenReturn(restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono"))));
        when(itemGateway.existeItemComRestaurante("r1")).thenReturn(true);

        assertThrows(RestauranteComItensCardapioException.class, () -> useCase.run("r1"));
        verify(gateway, never()).excluir("r1");
    }

    @Test
    void cadastrarItemCardapioUseCase_deveCadastrarQuandoRestauranteExiste() {
        IItemCardapioGateway itemGateway = mock(IItemCardapioGateway.class);
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        CadastrarItemCardapioUseCase useCase = CadastrarItemCardapioUseCase.create(itemGateway, restauranteGateway);
        Restaurante restaurante = restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")));
        ItemCardapio item = itemCardapio("i1", "Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", restaurante);

        when(restauranteGateway.buscarPorId("r1")).thenReturn(restaurante);
        when(itemGateway.incluir(any(ItemCardapio.class))).thenReturn(item);

        ItemCardapio result = useCase.run("Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", "r1");

        assertEquals("Pasta", result.getNomeItemCardapio());
    }

    @Test
    void cadastrarItemCardapioUseCase_deveLancarQuandoRestauranteNaoExiste() {
        IItemCardapioGateway itemGateway = mock(IItemCardapioGateway.class);
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        CadastrarItemCardapioUseCase useCase = CadastrarItemCardapioUseCase.create(itemGateway, restauranteGateway);

        when(restauranteGateway.buscarPorId("r9")).thenReturn(null);

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.run("Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", "r9"));
    }

    @Test
    void atualizarItemCardapioUseCase_deveAtualizarQuandoValido() {
        IItemCardapioGateway itemGateway = mock(IItemCardapioGateway.class);
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        AtualizarItemCardapioUseCase useCase = AtualizarItemCardapioUseCase.create(itemGateway, restauranteGateway);
        Restaurante restaurante = restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")));
        ItemCardapio item = itemCardapio("i1", "Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", restaurante);

        when(itemGateway.buscarPorId("i1")).thenReturn(item);
        when(restauranteGateway.buscarPorId("r1")).thenReturn(restaurante);
        when(itemGateway.atualizar(item)).thenReturn(item);

        ItemCardapio result = useCase.run("i1", "Risoto", "Arroz", new BigDecimal("49.90"), false, "foto2.jpg", "r1");

        assertEquals("Risoto", result.getNomeItemCardapio());
    }

    @Test
    void atualizarItemCardapioUseCase_deveLancarQuandoItemNaoExiste() {
        IItemCardapioGateway itemGateway = mock(IItemCardapioGateway.class);
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        AtualizarItemCardapioUseCase useCase = AtualizarItemCardapioUseCase.create(itemGateway, restauranteGateway);

        when(itemGateway.buscarPorId("i9")).thenReturn(null);

        assertThrows(ItemCardapioNaoEncontradoException.class, () -> useCase.run("i9", "Risoto", "Arroz", new BigDecimal("49.90"), false, "foto2.jpg", "r1"));
    }

    @Test
    void buscarItemCardapioPorIdUseCase_deveRetornarItem() {
        IItemCardapioGateway gateway = mock(IItemCardapioGateway.class);
        BuscarItemCardapioPorIdUseCase useCase = BuscarItemCardapioPorIdUseCase.create(gateway);
        Restaurante restaurante = restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")));
        ItemCardapio item = itemCardapio("i1", "Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", restaurante);

        when(gateway.buscarPorId("i1")).thenReturn(item);

        assertEquals("i1", useCase.run("i1").getIdItemCardapio());
    }

    @Test
    void buscarItemCardapioPorIdUseCase_deveLancarQuandoNaoExiste() {
        IItemCardapioGateway gateway = mock(IItemCardapioGateway.class);
        BuscarItemCardapioPorIdUseCase useCase = BuscarItemCardapioPorIdUseCase.create(gateway);

        when(gateway.buscarPorId("i9")).thenReturn(null);

        assertThrows(ItemCardapioNaoEncontradoException.class, () -> useCase.run("i9"));
    }

    @Test
    void listarItensCardapioUseCase_deveDelegarAoGateway() {
        IItemCardapioGateway gateway = mock(IItemCardapioGateway.class);
        ListarItensCardapioUseCase useCase = ListarItensCardapioUseCase.create(gateway);
        Restaurante restaurante = restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")));
        Page<ItemCardapio> page = new PageImpl<>(List.of(itemCardapio("i1", "Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", restaurante)));
        Pageable pageable = Pageable.unpaged();

        when(gateway.listarTodos("r1", pageable)).thenReturn(page);

        assertEquals(page, useCase.run("r1", pageable));
    }

    @Test
    void excluirItemCardapioUseCase_deveExcluirQuandoExiste() {
        IItemCardapioGateway gateway = mock(IItemCardapioGateway.class);
        ExcluirItemCardapioUseCase useCase = ExcluirItemCardapioUseCase.create(gateway);
        Restaurante restaurante = restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")));

        when(gateway.buscarPorId("i1")).thenReturn(itemCardapio("i1", "Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", restaurante));

        assertDoesNotThrow(() -> useCase.run("i1"));
        verify(gateway).excluir("i1");
    }

    @Test
    void excluirItemCardapioUseCase_deveLancarQuandoNaoExiste() {
        IItemCardapioGateway gateway = mock(IItemCardapioGateway.class);
        ExcluirItemCardapioUseCase useCase = ExcluirItemCardapioUseCase.create(gateway);

        when(gateway.buscarPorId("i9")).thenReturn(null);

        assertThrows(ItemCardapioNaoEncontradoException.class, () -> useCase.run("i9"));
    }

    private static TipoUsuario tipoUsuario(Long id, String tpUsuario, String descricao) {
        return TipoUsuario.builder().id(id).tpUsuario(tpUsuario).descricaoTipoUsuario(descricao).build();
    }

    private static Usuario usuario(String id, TipoUsuario tipoUsuario) {
        return Usuario.builder().id(id).login("login").nome("Nome").email("email@email.com").senha("123").tipoUsuario(tipoUsuario).build();
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

    private static ItemCardapio itemCardapio(String id, String nome, String descricao, BigDecimal preco, boolean apenasNoLocal, String caminhoFoto, Restaurante restaurante) {
        return ItemCardapio.builder()
                .idItemCardapio(id)
                .nomeItemCardapio(nome)
                .descricaoItem(descricao)
                .preco(preco)
                .apenasNoLocal(apenasNoLocal)
                .caminhoFoto(caminhoFoto)
                .restaurante(restaurante)
                .dataCriacao(LocalDateTime.now())
                .dataAlteracao(LocalDateTime.now())
                .build();
    }
}
