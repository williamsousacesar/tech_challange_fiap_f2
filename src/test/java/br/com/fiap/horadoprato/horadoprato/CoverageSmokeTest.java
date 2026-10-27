package br.com.fiap.horadoprato.horadoprato;

import br.com.fiap.horadoprato.horadoprato.adapters.controllers.GlobalExceptionHandler;
import br.com.fiap.horadoprato.horadoprato.adapters.controllers.itemCardapio.ItemCardapioController;
import br.com.fiap.horadoprato.horadoprato.adapters.controllers.restaurante.RestauranteController;
import br.com.fiap.horadoprato.horadoprato.adapters.controllers.tipoUsuario.TipoUsuarioController;
import br.com.fiap.horadoprato.horadoprato.adapters.controllers.usuario.UsuarioController;
import br.com.fiap.horadoprato.horadoprato.adapters.gateways.itemCardapio.ItemCardapioGateway;
import br.com.fiap.horadoprato.horadoprato.adapters.gateways.restaurante.RestauranteGateway;
import br.com.fiap.horadoprato.horadoprato.adapters.gateways.tipoUsuario.TipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.adapters.gateways.usuario.UsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.ItemCardapioEntity;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.RestauranteEntity;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.TipoUsuarioEntity;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.UsuarioEntity;
import br.com.fiap.horadoprato.horadoprato.adapters.presenters.ItemCardapioPresenter;
import br.com.fiap.horadoprato.horadoprato.adapters.presenters.RestaurantePresenter;
import br.com.fiap.horadoprato.horadoprato.adapters.presenters.TipoUsuarioPresenter;
import br.com.fiap.horadoprato.horadoprato.adapters.presenters.UsuarioPresenter;
import br.com.fiap.horadoprato.horadoprato.config.UseCaseConfig;
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
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.ItemCardapioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.NovoItemCardapioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.RestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.AlterarTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.exceptions.ItemCardapioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioEmUsoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioJaExisteException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioComRestauranteNaoPodeAlterarTipoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEhDonoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioDataStorageSource;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteDataStorageSource;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.IDataStorageSource;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioDataStorageSource;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoverageSmokeTest {

    @Test
    void shouldInstantiateEntitiesPresentersDtosExceptionsAndConfig() {
        TipoUsuario tipoUsuario = TipoUsuario.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();
        Usuario usuario = Usuario.builder().id("u1").login("carlos").nome("Carlos").email("carlos@email.com").senha("123").tipoUsuario(tipoUsuario).build();
        Restaurante restaurante = Restaurante.builder().id("r1").nomeRestaurante("Sabor").endereco("Rua A").tipoCozinha("Italiana").horarioFuncionamento("Seg a Dom").dono(usuario).dataCriacao(LocalDateTime.now()).dataAlteracao(LocalDateTime.now()).build();
        ItemCardapio itemCardapio = ItemCardapio.builder().idItemCardapio("i1").nomeItemCardapio("Pizza").descricaoItem("Mussarela").preco(new BigDecimal("49.90")).apenasNoLocal(true).caminhoFoto("/pizza.jpg").restaurante(restaurante).dataCriacao(LocalDateTime.now()).dataAlteracao(LocalDateTime.now()).build();

        assertEquals("CLIENTE", tipoUsuario.getTpUsuario());
        assertEquals("u1", usuario.getId());
        assertEquals("Sabor", restaurante.getNomeRestaurante());
        assertEquals("Pizza", itemCardapio.getNomeItemCardapio());

        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO(1L, "DONO_RESTAURANTE", "Dono");
        NovoTipoUsuarioDTO novoTipoUsuarioDTO = new NovoTipoUsuarioDTO("CLIENTE", "Cliente");
        UsuarioDTO usuarioDTO = new UsuarioDTO("u1", "carlos", "Carlos", "carlos@email.com", tipoUsuarioDTO);
        RestauranteDTO restauranteDTO = new RestauranteDTO("r1", "Sabor", "Rua A", "Italiana", "Seg a Dom", "u1", LocalDateTime.now(), LocalDateTime.now());
        NovoRestauranteDTO novoRestauranteDTO = new NovoRestauranteDTO("Sabor", "Rua A", "Italiana", "Seg a Dom", "u1");
        ItemCardapioDTO itemCardapioDTO = new ItemCardapioDTO("i1", "Pizza", "Mussarela", new BigDecimal("49.90"), true, "/pizza.jpg", "r1", LocalDateTime.now(), LocalDateTime.now());
        NovoItemCardapioDTO novoItemCardapioDTO = new NovoItemCardapioDTO("Pizza", "Mussarela", new BigDecimal("49.90"), true, "/pizza.jpg", "r1");
        AlterarTipoUsuarioDTO alterarTipoUsuarioDTO = new AlterarTipoUsuarioDTO(2L);

        assertEquals("DONO_RESTAURANTE", tipoUsuarioDTO.tpUsuario());
        assertEquals("Sabor", restauranteDTO.nomeRestaurante());
        assertEquals("u1", usuarioDTO.id());
        assertEquals("Pizza", novoItemCardapioDTO.nomeItemCardapio());
        assertEquals(2L, alterarTipoUsuarioDTO.tipoUsuarioId());

        assertEquals("Tipo de usuario 'CLIENTE' ja existe", new TipoUsuarioJaExisteException("Tipo de usuario 'CLIENTE' ja existe").getMessage());
        assertEquals("Usuario com id u1 nao encontrado", new UsuarioNaoEncontradoException("Usuario com id u1 nao encontrado").getMessage());
        assertEquals("Item nao encontrado", new ItemCardapioNaoEncontradoException("Item nao encontrado").getMessage());
        assertEquals("Restaurante nao encontrado", new RestauranteNaoEncontradoException("Restaurante nao encontrado").getMessage());
        assertEquals("Tipo nao encontrado", new TipoUsuarioNaoEncontradoException("Tipo nao encontrado").getMessage());
        assertEquals("Conflito", new UsuarioComRestauranteNaoPodeAlterarTipoException("Conflito").getMessage());
        assertEquals("Tipo em uso", new TipoUsuarioEmUsoException("Tipo em uso").getMessage());
        assertEquals("Nao eh dono", new UsuarioNaoEhDonoException("Nao eh dono").getMessage());

        assertEquals(1L, TipoUsuarioPresenter.ToDTO(tipoUsuario).id());
        assertEquals("u1", UsuarioPresenter.ToDTO(usuario).id());
        assertEquals("r1", RestaurantePresenter.ToDTO(restaurante).id());
        assertEquals("i1", ItemCardapioPresenter.ToDTO(itemCardapio).idItemCardapio());

        assertEquals(1, TipoUsuarioPresenter.ToDTO(List.of(tipoUsuario)).size());
        assertEquals(1, UsuarioPresenter.ToDTO(List.of(usuario)).size());
        assertEquals(1, RestaurantePresenter.ToDTO(List.of(restaurante)).size());
        assertEquals(1, ItemCardapioPresenter.ToDTO(List.of(itemCardapio)).size());

        UseCaseConfig config = new UseCaseConfig();
        ITipoUsuarioGateway tipoGateway = mock(ITipoUsuarioGateway.class);
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        IItemCardapioGateway itemGateway = mock(IItemCardapioGateway.class);

        assertNotNull(config.cadastrarTipoUsuarioUseCase(tipoGateway));
        assertNotNull(config.listarTiposUsuarioUseCase(tipoGateway));
        assertNotNull(config.buscarTipoUsuarioPorIdUseCase(tipoGateway));
        assertNotNull(config.atualizarTipoUsuarioUseCase(tipoGateway));
        assertNotNull(config.excluirTipoUsuarioUseCase(tipoGateway, usuarioGateway));
        assertNotNull(config.listarUsuariosUseCase(usuarioGateway));
        assertNotNull(config.buscarUsuarioPorIdUseCase(usuarioGateway));
        assertNotNull(config.alterarTipoDoUsuarioUseCase(usuarioGateway, tipoGateway, restauranteGateway));
        assertNotNull(config.cadastrarRestauranteUseCase(restauranteGateway, usuarioGateway));
        assertNotNull(config.listarRestaurantesUseCase(restauranteGateway));
        assertNotNull(config.buscarRestaurantePorIdUseCase(restauranteGateway));
        assertNotNull(config.atualizarRestauranteUseCase(restauranteGateway, usuarioGateway));
        assertNotNull(config.excluirRestauranteUseCase(restauranteGateway, itemGateway));

        assertNotNull(config.cadastrarItemCardapioUseCase(itemGateway, restauranteGateway));        assertNotNull(config.listarItensCardapioUseCase(itemGateway));
        assertNotNull(config.buscarItemCardapioPorIdUseCase(itemGateway));
        assertNotNull(config.atualizarItemCardapioUseCase(itemGateway, restauranteGateway));
        assertNotNull(config.excluirItemCardapioUseCase(itemGateway));

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ProblemDetail notFound = handler.tratarNaoEncontrado(new UsuarioNaoEncontradoException("not found"));
        ProblemDetail conflict = handler.tratarConflito(new TipoUsuarioJaExisteException("ja existe"));
        assertEquals("not found", notFound.getDetail());
        assertEquals("ja existe", conflict.getDetail());

        BindingResult bindingResult = new BeanPropertyBindingResult(new NovoItemCardapioDTO("Pizza", "Mussarela", new BigDecimal("49.90"), true, "/pizza.jpg", "r1"), "novoItemCardapioDTO");
        bindingResult.rejectValue("nomeItemCardapio", "error", "campo obrigatorio");
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        ProblemDetail validation = handler.tratarValidacao(ex);
        assertEquals("Dados invalidos", validation.getTitle());
        assertTrue(((Map<?, ?>) validation.getProperties().get("erros")).containsKey("nomeItemCardapio"));
    }

    @Test
    void shouldExerciseGatewayConversionCode() {
        IDataStorageSource tipoDataStorageSource = mock(IDataStorageSource.class);
        TipoUsuarioGateway tipoUsuarioGateway = new TipoUsuarioGateway(tipoDataStorageSource);
        when(tipoDataStorageSource.incluirTipoUsuario(any(NovoTipoUsuarioDTO.class))).thenReturn(new TipoUsuarioDTO(1L, "CLIENTE", "Cliente"));
        when(tipoDataStorageSource.buscarPorTpUsuario("CLIENTE")).thenReturn(new TipoUsuarioDTO(1L, "CLIENTE", "Cliente"));
        when(tipoDataStorageSource.buscarPorId(1L)).thenReturn(new TipoUsuarioDTO(1L, "CLIENTE", "Cliente"));
        when(tipoDataStorageSource.listarTodos(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(new TipoUsuarioDTO(1L, "CLIENTE", "Cliente"))));
        when(tipoDataStorageSource.atualizarTipoUsuario(eq(1L), any(NovoTipoUsuarioDTO.class))).thenReturn(new TipoUsuarioDTO(1L, "DONO_RESTAURANTE", "Dono"));
        assertEquals("CLIENTE", tipoUsuarioGateway.incluir(TipoUsuario.builder().tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build()).getTpUsuario());
        assertEquals("CLIENTE", tipoUsuarioGateway.buscarPorTpUsuario("CLIENTE").getTpUsuario());
        assertEquals(1L, tipoUsuarioGateway.buscarPorId(1L).getId());
        assertEquals(1, tipoUsuarioGateway.listarTodos(PageRequest.of(0, 10)).getContent().size());
        assertEquals("DONO_RESTAURANTE", tipoUsuarioGateway.atualizar(TipoUsuario.builder().id(1L).tpUsuario("DONO_RESTAURANTE").descricaoTipoUsuario("Dono").build()).getTpUsuario());

        IUsuarioDataStorageSource usuarioDataStorageSource = mock(IUsuarioDataStorageSource.class);
        UsuarioGateway usuarioGateway = new UsuarioGateway(usuarioDataStorageSource);
        when(usuarioDataStorageSource.buscarPorId("u1")).thenReturn(new UsuarioDTO("u1", "carlos", "Carlos", "carlos@email.com", new TipoUsuarioDTO(1L, "CLIENTE", "Cliente")));
        when(usuarioDataStorageSource.listarTodos(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(new UsuarioDTO("u1", "carlos", "Carlos", "carlos@email.com", new TipoUsuarioDTO(1L, "CLIENTE", "Cliente")))));
        when(usuarioDataStorageSource.alterarTipoUsuario("u1", 2L)).thenReturn(new UsuarioDTO("u1", "carlos", "Carlos", "carlos@email.com", new TipoUsuarioDTO(2L, "DONO_RESTAURANTE", "Dono")));
        when(usuarioDataStorageSource.existeUsuarioComTipo(1L)).thenReturn(true);
        assertEquals("u1", usuarioGateway.buscarPorId("u1").getId());
        assertEquals("carlos", usuarioGateway.listarTodos(PageRequest.of(0, 10)).getContent().getFirst().getLogin());
        assertEquals("DONO_RESTAURANTE", usuarioGateway.alterarTipoUsuario("u1", 2L).getTipoUsuario().getTpUsuario());
        assertTrue(usuarioGateway.existeUsuarioComTipo(1L));
        IRestauranteDataStorageSource restauranteDataStorageSource = mock(IRestauranteDataStorageSource.class);
        RestauranteGateway restauranteGateway = new RestauranteGateway(restauranteDataStorageSource);
        when(restauranteDataStorageSource.incluirRestaurante(any(NovoRestauranteDTO.class))).thenReturn(new RestauranteDTO("r1", "Sabor", "Rua A", "Italiana", "Seg a Dom", "u1", LocalDateTime.now(), LocalDateTime.now()));
        when(restauranteDataStorageSource.buscarPorId("r1")).thenReturn(new RestauranteDTO("r1", "Sabor", "Rua A", "Italiana", "Seg a Dom", "u1", LocalDateTime.now(), LocalDateTime.now()));
        when(restauranteDataStorageSource.listarTodos(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(new RestauranteDTO("r1", "Sabor", "Rua A", "Italiana", "Seg a Dom", "u1", LocalDateTime.now(), LocalDateTime.now()))));
        when(restauranteDataStorageSource.atualizarRestaurante(eq("r1"), any(NovoRestauranteDTO.class))).thenReturn(new RestauranteDTO("r1", "Bistro", "Rua B", "Japonesa", "Todos", "u1", LocalDateTime.now(), LocalDateTime.now()));
        assertEquals("Sabor", restauranteGateway.incluir(Restaurante.builder().nomeRestaurante("Sabor").endereco("Rua A").tipoCozinha("Italiana").horarioFuncionamento("Seg a Dom").dono(Usuario.builder().id("u1").build()).build()).getNomeRestaurante());
        assertEquals("Sabor", restauranteGateway.buscarPorId("r1").getNomeRestaurante());
        assertEquals("Sabor", restauranteGateway.listarTodos(PageRequest.of(0, 10)).getContent().getFirst().getNomeRestaurante());
        assertEquals("Bistro", restauranteGateway.atualizar(Restaurante.builder().id("r1").nomeRestaurante("Bistro").endereco("Rua B").tipoCozinha("Japonesa").horarioFuncionamento("Todos").dono(Usuario.builder().id("u1").build()).build()).getNomeRestaurante());
        assertNull(RestauranteGateway.toEntity(null));

        IItemCardapioDataStorageSource itemDataStorageSource = mock(IItemCardapioDataStorageSource.class);
        ItemCardapioGateway itemCardapioGateway = new ItemCardapioGateway(itemDataStorageSource);
        when(itemDataStorageSource.incluirItemCardapio(any(NovoItemCardapioDTO.class))).thenReturn(new ItemCardapioDTO("i1", "Pizza", "Mussarela", new BigDecimal("49.90"), true, "/pizza.jpg", "r1", LocalDateTime.now(), LocalDateTime.now()));
        when(itemDataStorageSource.buscarPorId("i1")).thenReturn(new ItemCardapioDTO("i1", "Pizza", "Mussarela", new BigDecimal("49.90"), true, "/pizza.jpg", "r1", LocalDateTime.now(), LocalDateTime.now()));
        when(itemDataStorageSource.listarTodos(eq("r1"), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(new ItemCardapioDTO("i1", "Pizza", "Mussarela", new BigDecimal("49.90"), true, "/pizza.jpg", "r1", LocalDateTime.now(), LocalDateTime.now()))));
        when(itemDataStorageSource.atualizarItemCardapio(eq("i1"), any(NovoItemCardapioDTO.class))).thenReturn(new ItemCardapioDTO("i1", "Risoto", "Arroz", new BigDecimal("39.90"), false, "/risoto.jpg", "r1", LocalDateTime.now(), LocalDateTime.now()));
        assertEquals("Pizza", itemCardapioGateway.incluir(ItemCardapio.builder().nomeItemCardapio("Pizza").descricaoItem("Mussarela").preco(new BigDecimal("49.90")).apenasNoLocal(true).caminhoFoto("/pizza.jpg").restaurante(Restaurante.builder().id("r1").build()).build()).getNomeItemCardapio());
        assertEquals("Pizza", itemCardapioGateway.buscarPorId("i1").getNomeItemCardapio());
        assertEquals("Pizza", itemCardapioGateway.listarTodos("r1", PageRequest.of(0, 10)).getContent().getFirst().getNomeItemCardapio());
        assertEquals("Risoto", itemCardapioGateway.atualizar(ItemCardapio.builder().idItemCardapio("i1").nomeItemCardapio("Risoto").descricaoItem("Arroz").preco(new BigDecimal("39.90")).apenasNoLocal(false).caminhoFoto("/risoto.jpg").restaurante(Restaurante.builder().id("r1").build()).build()).getNomeItemCardapio());
        assertNull(ItemCardapioGateway.toEntity(null));

        IItemCardapioGateway itemGateway = mock(IItemCardapioGateway.class);
        IRestauranteGateway restGateway = mock(IRestauranteGateway.class);
        IUsuarioGateway userGateway = mock(IUsuarioGateway.class);
        ITipoUsuarioGateway tipoUserGateway = mock(ITipoUsuarioGateway.class);
        assertNotNull(new ItemCardapioController(mock(CadastrarItemCardapioUseCase.class), mock(ListarItensCardapioUseCase.class), mock(BuscarItemCardapioPorIdUseCase.class), mock(AtualizarItemCardapioUseCase.class), mock(ExcluirItemCardapioUseCase.class)));
        assertNotNull(new RestauranteController(mock(CadastrarRestauranteUseCase.class), mock(ListarRestaurantesUseCase.class), mock(BuscarRestaurantePorIdUseCase.class), mock(AtualizarRestauranteUseCase.class), mock(ExcluirRestauranteUseCase.class)));
        assertNotNull(new TipoUsuarioController(mock(CadastrarTipoUsuarioUseCase.class), mock(ListarTiposUsuarioUseCase.class), mock(BuscarTipoUsuarioPorIdUseCase.class), mock(AtualizarTipoUsuarioUseCase.class), mock(ExcluirTipoUsuarioUseCase.class)));
        assertNotNull(new UsuarioController(mock(ListarUsuariosUseCase.class), mock(BuscarUsuarioPorIdUseCase.class), mock(AlterarTipoDoUsuarioUseCase.class)));
        assertNotNull(new GlobalExceptionHandler());
        assertNotNull(itemGateway);
        assertNotNull(restGateway);
        assertNotNull(userGateway);
        assertNotNull(tipoUserGateway);
    }

    @Test
    void shouldTriggerPersistenceEntityLifecycleHooks() {
        TipoUsuarioEntity tipoUsuarioEntity = TipoUsuarioEntity.builder().tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();
        UsuarioEntity usuarioEntity = UsuarioEntity.builder().id("u1").login("carlos").nome("Carlos").email("carlos@email.com").senha("123").tipoUsuario(tipoUsuarioEntity).build();
        RestauranteEntity restauranteEntity = RestauranteEntity.builder().id("r1").nomeRestaurante("Sabor").endereco("Rua A").tipoCozinha("Italiana").horarioFuncionamento("Seg a Dom").dono(usuarioEntity).build();
        ItemCardapioEntity itemCardapioEntity = ItemCardapioEntity.builder().idItemCardapio("i1").nomeItemCardapio("Pizza").descricaoItem("Mussarela").preco(new BigDecimal("49.90")).apenasNoLocal(true).caminhoFoto("/pizza.jpg").restaurante(restauranteEntity).build();

        tipoUsuarioEntity.setId(1L);
        usuarioEntity.setTipoUsuario(tipoUsuarioEntity);
        restauranteEntity.setDono(usuarioEntity);
        itemCardapioEntity.setRestaurante(restauranteEntity);

        restauranteEntity.aoCriar();
        restauranteEntity.atualizarDataAlteracao();
        itemCardapioEntity.aoCriar();
        itemCardapioEntity.atualizarDataAlteracao();

        assertNotNull(tipoUsuarioEntity.getTpUsuario());
        assertNotNull(usuarioEntity.getLogin());
        assertNotNull(restauranteEntity.getDataCriacao());
        assertNotNull(restauranteEntity.getDataAlteracao());
        assertNotNull(itemCardapioEntity.getDataCriacao());
        assertNotNull(itemCardapioEntity.getDataAlteracao());
    }
}
