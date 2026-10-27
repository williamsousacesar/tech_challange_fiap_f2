package br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio;

import br.com.fiap.horadoprato.horadoprato.domain.entities.ItemCardapio;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.exceptions.ItemCardapioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemCardapioUseCaseTest {

    @Mock
    private IItemCardapioGateway itemCardapioGateway;

    @Mock
    private IRestauranteGateway restauranteGateway;

    @Test
    void cadastrarDeveCadastrarQuandoRestauranteExiste() {
        CadastrarItemCardapioUseCase useCase = CadastrarItemCardapioUseCase.create(itemCardapioGateway, restauranteGateway);
        Restaurante restaurante = restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")));
        ItemCardapio item = itemCardapio("i1", "Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", restaurante);

        when(restauranteGateway.buscarPorId("r1")).thenReturn(restaurante);
        when(itemCardapioGateway.incluir(any(ItemCardapio.class))).thenReturn(item);

        ItemCardapio result = useCase.run("Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", "r1");

        assertEquals("Pasta", result.getNomeItemCardapio());
    }

    @Test
    void cadastrarDeveLancarQuandoRestauranteNaoExiste() {
        CadastrarItemCardapioUseCase useCase = CadastrarItemCardapioUseCase.create(itemCardapioGateway, restauranteGateway);

        when(restauranteGateway.buscarPorId("r9")).thenReturn(null);

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.run("Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", "r9"));
    }

    @Test
    void atualizarDeveAtualizarQuandoValido() {
        AtualizarItemCardapioUseCase useCase = AtualizarItemCardapioUseCase.create(itemCardapioGateway, restauranteGateway);
        Restaurante restaurante = restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")));
        ItemCardapio item = itemCardapio("i1", "Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", restaurante);

        when(itemCardapioGateway.buscarPorId("i1")).thenReturn(item);
        when(restauranteGateway.buscarPorId("r1")).thenReturn(restaurante);
        when(itemCardapioGateway.atualizar(item)).thenReturn(item);

        ItemCardapio result = useCase.run("i1", "Risoto", "Arroz", new BigDecimal("49.90"), false, "foto2.jpg", "r1");

        assertEquals("Risoto", result.getNomeItemCardapio());
    }

    @Test
    void atualizarDeveLancarQuandoItemNaoExiste() {
        AtualizarItemCardapioUseCase useCase = AtualizarItemCardapioUseCase.create(itemCardapioGateway, restauranteGateway);

        when(itemCardapioGateway.buscarPorId("i9")).thenReturn(null);

        assertThrows(ItemCardapioNaoEncontradoException.class, () -> useCase.run("i9", "Risoto", "Arroz", new BigDecimal("49.90"), false, "foto2.jpg", "r1"));
    }

    @Test
    void buscarPorIdDeveRetornarItem() {
        BuscarItemCardapioPorIdUseCase useCase = BuscarItemCardapioPorIdUseCase.create(itemCardapioGateway);
        Restaurante restaurante = restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")));
        ItemCardapio item = itemCardapio("i1", "Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", restaurante);

        when(itemCardapioGateway.buscarPorId("i1")).thenReturn(item);

        assertEquals("i1", useCase.run("i1").getIdItemCardapio());
    }

    @Test
    void buscarPorIdDeveLancarQuandoNaoExiste() {
        BuscarItemCardapioPorIdUseCase useCase = BuscarItemCardapioPorIdUseCase.create(itemCardapioGateway);

        when(itemCardapioGateway.buscarPorId("i9")).thenReturn(null);

        assertThrows(ItemCardapioNaoEncontradoException.class, () -> useCase.run("i9"));
    }

    @Test
    void listarDeveDelegarAoGateway() {
        ListarItensCardapioUseCase useCase = ListarItensCardapioUseCase.create(itemCardapioGateway);
        Restaurante restaurante = restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")));
        Page<ItemCardapio> page = new PageImpl<>(List.of(itemCardapio("i1", "Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", restaurante)));
        Pageable pageable = Pageable.unpaged();

        when(itemCardapioGateway.listarTodos("r1", pageable)).thenReturn(page);

        assertEquals(page, useCase.run("r1", pageable));
    }

    @Test
    void excluirDeveExcluirQuandoExiste() {
        ExcluirItemCardapioUseCase useCase = ExcluirItemCardapioUseCase.create(itemCardapioGateway);
        Restaurante restaurante = restaurante("r1", usuario("u1", tipoUsuario(1L, "DONO_RESTAURANTE", "Dono")));

        when(itemCardapioGateway.buscarPorId("i1")).thenReturn(itemCardapio("i1", "Pasta", "Massa", new BigDecimal("39.90"), true, "foto.jpg", restaurante));

        assertDoesNotThrow(() -> useCase.run("i1"));
        verify(itemCardapioGateway).excluir("i1");
    }

    @Test
    void excluirDeveLancarQuandoNaoExiste() {
        ExcluirItemCardapioUseCase useCase = ExcluirItemCardapioUseCase.create(itemCardapioGateway);

        when(itemCardapioGateway.buscarPorId("i9")).thenReturn(null);

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
