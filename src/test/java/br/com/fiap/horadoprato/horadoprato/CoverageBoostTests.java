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
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.ItemCardapioDataStorage;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.ItemCardapioRepository;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.RestauranteDataStorage;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.RestauranteRepository;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.TipoUsuarioDataStorage;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.TipoUsuarioRepository;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.UsuarioDataStorage;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.UsuarioRepository;
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
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CoverageBoostTests {

    @Test
    void presenters_shouldConvertEntitiesToDtos() {
        TipoUsuario tipo = TipoUsuario.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();
        Usuario usuario = Usuario.builder().id("u1").login("login").nome("Nome").email("email").tipoUsuario(tipo).build();
        Restaurante restaurante = Restaurante.builder().id("r1").nomeRestaurante("R").endereco("E").tipoCozinha("T").horarioFuncionamento("H").dono(usuario).dataCriacao(LocalDateTime.now()).dataAlteracao(LocalDateTime.now()).build();
        ItemCardapio item = ItemCardapio.builder().idItemCardapio("i1").nomeItemCardapio("Pizza").descricaoItem("Desc").preco(new BigDecimal("12.50")).apenasNoLocal(false).caminhoFoto("/img.png").restaurante(restaurante).dataCriacao(LocalDateTime.now()).dataAlteracao(LocalDateTime.now()).build();

        assertEquals("CLIENTE", TipoUsuarioPresenter.ToDTO(tipo).tpUsuario());
        assertEquals("u1", UsuarioPresenter.ToDTO(usuario).id());
        assertEquals("r1", RestaurantePresenter.ToDTO(restaurante).id());
        assertEquals("i1", ItemCardapioPresenter.ToDTO(item).idItemCardapio());

        assertEquals(1, TipoUsuarioPresenter.ToDTO(List.of(tipo)).size());
        assertEquals(1, UsuarioPresenter.ToDTO(List.of(usuario)).size());
        assertEquals(1, RestaurantePresenter.ToDTO(List.of(restaurante)).size());
        assertEquals(1, ItemCardapioPresenter.ToDTO(List.of(item)).size());
    }

    @Test
    void controllers_shouldExecuteCrudOperations() {
        CadastrarTipoUsuarioUseCase cadTipo = mock(CadastrarTipoUsuarioUseCase.class);
        ListarTiposUsuarioUseCase listTipo = mock(ListarTiposUsuarioUseCase.class);
        BuscarTipoUsuarioPorIdUseCase buscarTipo = mock(BuscarTipoUsuarioPorIdUseCase.class);
        AtualizarTipoUsuarioUseCase updTipo = mock(AtualizarTipoUsuarioUseCase.class);
        ExcluirTipoUsuarioUseCase exclTipo = mock(ExcluirTipoUsuarioUseCase.class);
        TipoUsuarioController tipoController = new TipoUsuarioController(cadTipo, listTipo, buscarTipo, updTipo, exclTipo);
        TipoUsuario tipo = TipoUsuario.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();
        when(cadTipo.run("CLIENTE", "Cliente")).thenReturn(tipo);
        when(listTipo.run(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(tipo)));
        when(buscarTipo.run(1L)).thenReturn(tipo);
        when(updTipo.run(1L, "DONO", "Dono")).thenReturn(tipo);

        assertEquals("CLIENTE", tipoController.cadastrar(new NovoTipoUsuarioDTO("CLIENTE", "Cliente")).tpUsuario());
        assertEquals(1, tipoController.listar(0, 10).getTotalElements());
        assertEquals("CLIENTE", tipoController.buscarPorId(1L).tpUsuario());
        assertEquals("CLIENTE", tipoController.atualizar(1L, new NovoTipoUsuarioDTO("DONO", "Dono")).tpUsuario());
        assertDoesNotThrow(() -> tipoController.excluir(1L));

        ListarUsuariosUseCase listUser = mock(ListarUsuariosUseCase.class);
        BuscarUsuarioPorIdUseCase buscarUser = mock(BuscarUsuarioPorIdUseCase.class);
        AlterarTipoDoUsuarioUseCase alterarUser = mock(AlterarTipoDoUsuarioUseCase.class);
        UsuarioController usuarioController = new UsuarioController(listUser, buscarUser, alterarUser);
        Usuario usuario = Usuario.builder().id("u1").login("login").nome("nome").email("e@e.com").tipoUsuario(tipo).build();
        when(listUser.run(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(usuario)));
        when(buscarUser.run("u1")).thenReturn(usuario);
        when(alterarUser.run("u1", 1L)).thenReturn(usuario);
        assertEquals(1, usuarioController.listar(0, 10).getTotalElements());
        assertEquals("u1", usuarioController.buscarPorId("u1").id());
        assertEquals("u1", usuarioController.alterarTipo("u1", new AlterarTipoUsuarioDTO(1L)).id());

        CadastrarRestauranteUseCase cadRest = mock(CadastrarRestauranteUseCase.class);
        ListarRestaurantesUseCase listRest = mock(ListarRestaurantesUseCase.class);
        BuscarRestaurantePorIdUseCase buscarRest = mock(BuscarRestaurantePorIdUseCase.class);
        AtualizarRestauranteUseCase updRest = mock(AtualizarRestauranteUseCase.class);
        ExcluirRestauranteUseCase exclRest = mock(ExcluirRestauranteUseCase.class);
        RestauranteController restauranteController = new RestauranteController(cadRest, listRest, buscarRest, updRest, exclRest);
        Restaurante restaurante = Restaurante.builder().id("r1").nomeRestaurante("N").endereco("E").tipoCozinha("T").horarioFuncionamento("H").dono(usuario).dataCriacao(LocalDateTime.now()).dataAlteracao(LocalDateTime.now()).build();
        when(cadRest.run("N", "E", "T", "H", "u1")).thenReturn(restaurante);
        when(listRest.run(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(restaurante)));
        when(buscarRest.run("r1")).thenReturn(restaurante);
        when(updRest.run("r1", "NN", "EE", "TT", "HH", "u1")).thenReturn(restaurante);
        assertEquals("r1", restauranteController.cadastrar(new NovoRestauranteDTO("N", "E", "T", "H", "u1")).id());
        assertEquals(1, restauranteController.listar(0, 10).getTotalElements());
        assertEquals("r1", restauranteController.buscarPorId("r1").id());
        assertEquals("r1", restauranteController.atualizar("r1", new NovoRestauranteDTO("NN", "EE", "TT", "HH", "u1")).id());
        assertDoesNotThrow(() -> restauranteController.excluir("r1"));

        CadastrarItemCardapioUseCase cadItem = mock(CadastrarItemCardapioUseCase.class);
        ListarItensCardapioUseCase listItem = mock(ListarItensCardapioUseCase.class);
        BuscarItemCardapioPorIdUseCase buscarItem = mock(BuscarItemCardapioPorIdUseCase.class);
        AtualizarItemCardapioUseCase updItem = mock(AtualizarItemCardapioUseCase.class);
        ExcluirItemCardapioUseCase exclItem = mock(ExcluirItemCardapioUseCase.class);
        ItemCardapioController itemController = new ItemCardapioController(cadItem, listItem, buscarItem, updItem, exclItem);
        ItemCardapio item = ItemCardapio.builder().idItemCardapio("i1").nomeItemCardapio("P").descricaoItem("Desc").preco(new BigDecimal("10.00")).apenasNoLocal(true).caminhoFoto("/p.png").restaurante(restaurante).dataCriacao(LocalDateTime.now()).dataAlteracao(LocalDateTime.now()).build();
        when(cadItem.run("P", "Desc", new BigDecimal("10.00"), true, "/p.png", "r1")).thenReturn(item);
        when(listItem.run(eq("r1"), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(item)));
        when(buscarItem.run("i1")).thenReturn(item);
        when(updItem.run("i1", "PP", "Desc2", new BigDecimal("20.00"), false, "/p2.png", "r1")).thenReturn(item);
        assertEquals("i1", itemController.cadastrar(new NovoItemCardapioDTO("P", "Desc", new BigDecimal("10.00"), true, "/p.png", "r1")).idItemCardapio());
        assertEquals(1, itemController.listar("r1", 0, 10).getTotalElements());
        assertEquals("i1", itemController.buscarPorId("i1").idItemCardapio());
        assertEquals("i1", itemController.atualizar("i1", new NovoItemCardapioDTO("PP", "Desc2", new BigDecimal("20.00"), false, "/p2.png", "r1")).idItemCardapio());
        assertDoesNotThrow(() -> itemController.excluir("i1"));
    }

    @Test
    void gateways_shouldMapDomainAndDto() {
        IDataStorageSource tipoStorage = mock(IDataStorageSource.class);
        TipoUsuarioGateway tipoGateway = new TipoUsuarioGateway(tipoStorage);
        TipoUsuarioDTO tipoDto = new TipoUsuarioDTO(9L, "CLIENTE", "Cliente");
        when(tipoStorage.incluirTipoUsuario(any())).thenReturn(tipoDto);
        when(tipoStorage.buscarPorTpUsuario("CLIENTE")).thenReturn(tipoDto);
        when(tipoStorage.buscarPorId(9L)).thenReturn(tipoDto);
        when(tipoStorage.listarTodos(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(tipoDto)));
        when(tipoStorage.atualizarTipoUsuario(eq(9L), any())).thenReturn(tipoDto);

        assertEquals(9L, tipoGateway.incluir(TipoUsuario.builder().tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build()).getId());
        assertEquals("CLIENTE", tipoGateway.buscarPorTpUsuario("CLIENTE").getTpUsuario());
        assertEquals(9L, tipoGateway.buscarPorId(9L).getId());
        assertEquals(1, tipoGateway.listarTodos(Pageable.unpaged()).getTotalElements());
        assertEquals("CLIENTE", tipoGateway.atualizar(TipoUsuario.builder().id(9L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build()).getTpUsuario());
        assertNull(TipoUsuarioGateway.toEntity(null));

        IUsuarioDataStorageSource usuarioStorage = mock(IUsuarioDataStorageSource.class);
        UsuarioGateway usuarioGateway = new UsuarioGateway(usuarioStorage);
        UsuarioDTO usuarioDto = new UsuarioDTO("u1", "login", "Nome", "e@e.com", tipoDto);
        when(usuarioStorage.buscarPorId("u1")).thenReturn(usuarioDto);
        when(usuarioStorage.listarTodos(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(usuarioDto)));
        when(usuarioStorage.alterarTipoUsuario("u1", 9L)).thenReturn(usuarioDto);
        assertEquals("u1", usuarioGateway.buscarPorId("u1").getId());
        assertEquals(1, usuarioGateway.listarTodos(Pageable.unpaged()).getTotalElements());
        assertEquals("u1", usuarioGateway.alterarTipoUsuario("u1", 9L).getId());

        IRestauranteDataStorageSource restauranteStorage = mock(IRestauranteDataStorageSource.class);
        RestauranteGateway restauranteGateway = new RestauranteGateway(restauranteStorage);
        RestauranteDTO restauranteDto = new RestauranteDTO("r1", "R", "E", "T", "H", "u1", LocalDateTime.now(), LocalDateTime.now());
        when(restauranteStorage.incluirRestaurante(any())).thenReturn(restauranteDto);
        when(restauranteStorage.buscarPorId("r1")).thenReturn(restauranteDto);
        when(restauranteStorage.listarTodos(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(restauranteDto)));
        when(restauranteStorage.atualizarRestaurante(eq("r1"), any())).thenReturn(restauranteDto);
        assertEquals("r1", restauranteGateway.incluir(Restaurante.builder().nomeRestaurante("R").endereco("E").tipoCozinha("T").horarioFuncionamento("H").dono(Usuario.builder().id("u1").build()).build()).getId());
        assertEquals("r1", restauranteGateway.buscarPorId("r1").getId());
        assertEquals(1, restauranteGateway.listarTodos(Pageable.unpaged()).getTotalElements());
        assertEquals("r1", restauranteGateway.atualizar(Restaurante.builder().id("r1").nomeRestaurante("R").endereco("E").tipoCozinha("T").horarioFuncionamento("H").dono(Usuario.builder().id("u1").build()).build()).getId());
        assertNull(RestauranteGateway.toEntity(null));

        IItemCardapioDataStorageSource itemStorage = mock(IItemCardapioDataStorageSource.class);
        ItemCardapioGateway itemGateway = new ItemCardapioGateway(itemStorage);
        ItemCardapioDTO itemDto = new ItemCardapioDTO("i1", "Pizza", "Desc", new BigDecimal("15.00"), false, "/img.png", "r1", LocalDateTime.now(), LocalDateTime.now());
        when(itemStorage.incluirItemCardapio(any())).thenReturn(itemDto);
        when(itemStorage.buscarPorId("i1")).thenReturn(itemDto);
        when(itemStorage.listarTodos(eq("r1"), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(itemDto)));
        when(itemStorage.atualizarItemCardapio(eq("i1"), any())).thenReturn(itemDto);
        when(itemStorage.existeItemComRestaurante("r1")).thenReturn(true);
        assertTrue(itemGateway.existeItemComRestaurante("r1"));
        assertEquals("i1", itemGateway.incluir(ItemCardapio.builder().nomeItemCardapio("Pizza").descricaoItem("Desc").preco(new BigDecimal("15.00")).apenasNoLocal(false).caminhoFoto("/img.png").restaurante(Restaurante.builder().id("r1").build()).build()).getIdItemCardapio());
        assertEquals("i1", itemGateway.buscarPorId("i1").getIdItemCardapio());
        assertEquals(1, itemGateway.listarTodos("r1", Pageable.unpaged()).getTotalElements());
        assertEquals("i1", itemGateway.atualizar(ItemCardapio.builder().idItemCardapio("i1").nomeItemCardapio("Pizza").descricaoItem("Desc").preco(new BigDecimal("15.00")).apenasNoLocal(false).caminhoFoto("/img.png").restaurante(Restaurante.builder().id("r1").build()).build()).getIdItemCardapio());
        assertNull(ItemCardapioGateway.toEntity(null));
    }

    @Test
    void dataStorages_shouldMapEntitiesAndRepositoryData() {
        TipoUsuarioRepository tipoRepository = mock(TipoUsuarioRepository.class);
        TipoUsuarioDataStorage tipoStorage = new TipoUsuarioDataStorage(tipoRepository);
        TipoUsuarioEntity tipoEntity = TipoUsuarioEntity.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();
        when(tipoRepository.save(any(TipoUsuarioEntity.class))).thenReturn(tipoEntity);
        when(tipoRepository.findByTpUsuario("CLIENTE")).thenReturn(tipoEntity);
        when(tipoRepository.findById(1L)).thenReturn(java.util.Optional.of(tipoEntity));
        when(tipoRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(tipoEntity)));

        assertEquals("CLIENTE", tipoStorage.incluirTipoUsuario(new NovoTipoUsuarioDTO("CLIENTE", "Cliente")).tpUsuario());
        assertEquals("CLIENTE", tipoStorage.buscarPorTpUsuario("CLIENTE").tpUsuario());
        assertEquals(1L, tipoStorage.buscarPorId(1L).id());
        assertEquals(1, tipoStorage.listarTodos(Pageable.unpaged()).getTotalElements());
        assertEquals("CLIENTE", tipoStorage.atualizarTipoUsuario(1L, new NovoTipoUsuarioDTO("CLIENTE", "Cliente")).tpUsuario());
        assertDoesNotThrow(() -> tipoStorage.excluirTipoUsuario(1L));

        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        TipoUsuarioRepository usuarioTipoRepository = mock(TipoUsuarioRepository.class);
        UsuarioDataStorage usuarioStorage = new UsuarioDataStorage(usuarioRepository, usuarioTipoRepository);
        TipoUsuarioEntity usuarioTipoEntity = TipoUsuarioEntity.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();
        UsuarioEntity usuarioEntity = UsuarioEntity.builder().id("u1").login("login").nome("Nome").email("a@a.com").senha("123").tipoUsuario(usuarioTipoEntity).build();
        when(usuarioRepository.findById("u1")).thenReturn(java.util.Optional.of(usuarioEntity));
        when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(usuarioEntity)));
        when(usuarioTipoRepository.findById(1L)).thenReturn(java.util.Optional.of(usuarioTipoEntity));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);
        when(usuarioRepository.existsByTipoUsuarioId(1L)).thenReturn(true);

        assertEquals("u1", usuarioStorage.buscarPorId("u1").id());
        assertEquals(1, usuarioStorage.listarTodos(Pageable.unpaged()).getTotalElements());
        assertEquals("u1", usuarioStorage.alterarTipoUsuario("u1", 1L).id());
        assertEquals(true, usuarioStorage.existeUsuarioComTipo(1L));

        RestauranteRepository restauranteRepository = mock(RestauranteRepository.class);
        UsuarioRepository restauranteUsuarioRepository = mock(UsuarioRepository.class);
        RestauranteDataStorage restauranteStorage = new RestauranteDataStorage(restauranteRepository, restauranteUsuarioRepository);
        UsuarioEntity dono = UsuarioEntity.builder().id("u1").login("login").nome("Nome").email("a@a.com").senha("123").tipoUsuario(usuarioTipoEntity).build();
        RestauranteEntity restauranteEntity = RestauranteEntity.builder().id("r1").nomeRestaurante("R").endereco("E").tipoCozinha("T").horarioFuncionamento("H").dono(dono).dataCriacao(LocalDateTime.now()).dataAlteracao(LocalDateTime.now()).build();
        when(restauranteUsuarioRepository.findById("u1")).thenReturn(java.util.Optional.of(dono));
        when(restauranteRepository.save(any(RestauranteEntity.class))).thenReturn(restauranteEntity);
        when(restauranteRepository.findById("r1")).thenReturn(java.util.Optional.of(restauranteEntity));
        when(restauranteRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(restauranteEntity)));
        when(restauranteRepository.existsByDonoId("u1")).thenReturn(true);
        assertEquals("r1", restauranteStorage.incluirRestaurante(new NovoRestauranteDTO("R", "E", "T", "H", "u1")).id());
        assertEquals("r1", restauranteStorage.buscarPorId("r1").id());
        assertEquals(1, restauranteStorage.listarTodos(Pageable.unpaged()).getTotalElements());
        assertEquals(true, restauranteStorage.existeRestauranteComDono("u1"));
        assertEquals("r1", restauranteStorage.atualizarRestaurante("r1", new NovoRestauranteDTO("R2", "E2", "T2", "H2", "u1")).id());
        assertDoesNotThrow(() -> restauranteStorage.excluirRestaurante("r1"));

        ItemCardapioRepository itemRepository = mock(ItemCardapioRepository.class);
        RestauranteRepository itemRestauranteRepository = mock(RestauranteRepository.class);
        ItemCardapioDataStorage itemStorage = new ItemCardapioDataStorage(itemRepository, itemRestauranteRepository);
        ItemCardapioEntity itemEntity = ItemCardapioEntity.builder().idItemCardapio("i1").nomeItemCardapio("P").descricaoItem("D").preco(new BigDecimal("10.00")).apenasNoLocal(true).caminhoFoto("/p.png").restaurante(restauranteEntity).dataCriacao(LocalDateTime.now()).dataAlteracao(LocalDateTime.now()).build();
        when(itemRestauranteRepository.findById("r1")).thenReturn(java.util.Optional.of(restauranteEntity));
        when(itemRepository.save(any(ItemCardapioEntity.class))).thenReturn(itemEntity);
        when(itemRepository.findById("i1")).thenReturn(java.util.Optional.of(itemEntity));
        when(itemRepository.findByRestauranteId(eq("r1"), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(itemEntity)));
        assertEquals("i1", itemStorage.incluirItemCardapio(new NovoItemCardapioDTO("P", "D", new BigDecimal("10.00"), true, "/p.png", "r1")).idItemCardapio());
        assertEquals("i1", itemStorage.buscarPorId("i1").idItemCardapio());
        assertEquals(1, itemStorage.listarTodos("r1", Pageable.unpaged()).getTotalElements());
        assertEquals("i1", itemStorage.atualizarItemCardapio("i1", new NovoItemCardapioDTO("P2", "D2", new BigDecimal("12.00"), false, "/p2.png", "r1")).idItemCardapio());
        assertDoesNotThrow(() -> itemStorage.excluirItemCardapio("i1"));
        when(itemRepository.existsByRestauranteId("r1")).thenReturn(true);
        assertTrue(itemStorage.existeItemComRestaurante("r1"));
    }

    @Test
    void exceptionHandler_shouldBuildProblemDetails() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ProblemDetail notFound = handler.tratarNaoEncontrado(new UsuarioNaoEncontradoException("x"));
        ProblemDetail conflict = handler.tratarConflito(new TipoUsuarioJaExisteException("x"));

        assertEquals(404, notFound.getStatus());
        assertEquals(409, conflict.getStatus());

        var object = new Object();
        BeanPropertyBindingResult binding = new BeanPropertyBindingResult(object, "object");
        binding.addError(new FieldError("object", "nome", "campo obrigatorio"));
        MethodParameter parameter = new MethodParameter(CoverageBoostTests.class.getDeclaredMethods()[0], -1);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, binding);
        ProblemDetail valida = handler.tratarValidacao(ex);
        assertEquals(400, valida.getStatus());
        assertNotNull(valida.getProperties().get("erros"));
    }

    @Test
    void useCaseConfig_shouldCreateAllBeans() {
        UseCaseConfig config = new UseCaseConfig();

        ITipoUsuarioGateway tipoGateway = mock(ITipoUsuarioGateway.class);
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        IItemCardapioGateway itemCardapioGateway = mock(IItemCardapioGateway.class);

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
        assertNotNull(config.excluirRestauranteUseCase(restauranteGateway, itemCardapioGateway));
        assertNotNull(config.cadastrarItemCardapioUseCase(itemCardapioGateway, restauranteGateway));
        assertNotNull(config.listarItensCardapioUseCase(itemCardapioGateway));
        assertNotNull(config.buscarItemCardapioPorIdUseCase(itemCardapioGateway));
        assertNotNull(config.atualizarItemCardapioUseCase(itemCardapioGateway, restauranteGateway));
        assertNotNull(config.excluirItemCardapioUseCase(itemCardapioGateway));
    }
}
