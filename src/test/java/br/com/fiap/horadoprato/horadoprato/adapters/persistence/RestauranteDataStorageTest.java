package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.RestauranteEntity;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.UsuarioEntity;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.RestauranteDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteDataStorageTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void incluirRestauranteDeveSalvarQuandoDonoExiste() {
        RestauranteDataStorage storage = new RestauranteDataStorage(restauranteRepository, usuarioRepository);
        UsuarioEntity dono = UsuarioEntity.builder().id("u1").login("carlos").nome("Carlos").email("carlos@email.com").senha("123").build();
        NovoRestauranteDTO dto = new NovoRestauranteDTO("Sabor", "Rua A", "Italiana", "Seg a Dom", "u1");

        when(usuarioRepository.findById("u1")).thenReturn(Optional.of(dono));
        when(restauranteRepository.save(any(RestauranteEntity.class))).thenAnswer(invocation -> {
            RestauranteEntity entity = invocation.getArgument(0);
            entity.setId("r1");
            entity.setDataCriacao(LocalDateTime.now());
            entity.setDataAlteracao(LocalDateTime.now());
            return entity;
        });

        RestauranteDTO result = storage.incluirRestaurante(dto);

        assertEquals("r1", result.id());
        assertEquals("Sabor", result.nomeRestaurante());
    }

    @Test
    void incluirRestauranteDeveRetornarNullQuandoDonoNaoExiste() {
        RestauranteDataStorage storage = new RestauranteDataStorage(restauranteRepository, usuarioRepository);

        when(usuarioRepository.findById("u9")).thenReturn(Optional.empty());

        assertNull(storage.incluirRestaurante(new NovoRestauranteDTO("Sabor", "Rua A", "Italiana", "Seg a Dom", "u9")));
    }

    @Test
    void buscarPorIdDeveRetornarDtoOuNull() {
        RestauranteDataStorage storage = new RestauranteDataStorage(restauranteRepository, usuarioRepository);
        UsuarioEntity dono = UsuarioEntity.builder().id("u1").login("carlos").nome("Carlos").email("carlos@email.com").senha("123").build();
        RestauranteEntity entity = RestauranteEntity.builder()
                .id("r1")
                .nomeRestaurante("Sabor")
                .endereco("Rua A")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("Seg a Dom")
                .dono(dono)
                .dataCriacao(LocalDateTime.now())
                .dataAlteracao(LocalDateTime.now())
                .build();

        when(restauranteRepository.findById("r1")).thenReturn(Optional.of(entity));
        when(restauranteRepository.findById("r9")).thenReturn(Optional.empty());

        assertEquals("Sabor", storage.buscarPorId("r1").nomeRestaurante());
        assertNull(storage.buscarPorId("r9"));
    }

    @Test
    void listarTodosDeveRetornarPaginaMapeada() {
        RestauranteDataStorage storage = new RestauranteDataStorage(restauranteRepository, usuarioRepository);
        UsuarioEntity dono = UsuarioEntity.builder().id("u1").login("carlos").nome("Carlos").email("carlos@email.com").senha("123").build();
        Pageable pageable = Pageable.unpaged();
        RestauranteEntity entity = RestauranteEntity.builder()
                .id("r1")
                .nomeRestaurante("Sabor")
                .endereco("Rua A")
                .tipoCozinha("Italiana")
                .horarioFuncionamento("Seg a Dom")
                .dono(dono)
                .dataCriacao(LocalDateTime.now())
                .dataAlteracao(LocalDateTime.now())
                .build();

        when(restauranteRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));

        Page<RestauranteDTO> page = storage.listarTodos(pageable);

        assertEquals(1, page.getContent().size());
        assertEquals("Sabor", page.getContent().getFirst().nomeRestaurante());
    }

    @Test
    void existeRestauranteComDonoDeveDelegarAoRepository() {
        RestauranteDataStorage storage = new RestauranteDataStorage(restauranteRepository, usuarioRepository);

        when(restauranteRepository.existsByDonoId("u1")).thenReturn(true);
        when(restauranteRepository.existsByDonoId("u9")).thenReturn(false);

        assertTrue(storage.existeRestauranteComDono("u1"));
        assertFalse(storage.existeRestauranteComDono("u9"));
    }

    @Test
    void atualizarRestauranteDeveAtualizarQuandoExiste() {
        RestauranteDataStorage storage = new RestauranteDataStorage(restauranteRepository, usuarioRepository);
        UsuarioEntity donoAtual = UsuarioEntity.builder().id("u1").login("carlos").nome("Carlos").email("carlos@email.com").senha("123").build();
        UsuarioEntity novoDono = UsuarioEntity.builder().id("u2").login("ana").nome("Ana").email("ana@email.com").senha("123").build();
        RestauranteEntity entity = RestauranteEntity.builder()
                .id("r1")
                .nomeRestaurante("Velho")
                .endereco("Rua X")
                .tipoCozinha("Brasileira")
                .horarioFuncionamento("Seg")
                .dono(donoAtual)
                .dataCriacao(LocalDateTime.now())
                .dataAlteracao(LocalDateTime.now())
                .build();
        NovoRestauranteDTO dto = new NovoRestauranteDTO("Novo", "Rua Z", "Japonesa", "Todos", "u2");

        when(restauranteRepository.findById("r1")).thenReturn(Optional.of(entity));
        when(usuarioRepository.findById("u2")).thenReturn(Optional.of(novoDono));
        when(restauranteRepository.save(entity)).thenReturn(entity);

        RestauranteDTO result = storage.atualizarRestaurante("r1", dto);

        assertEquals("Novo", result.nomeRestaurante());
        assertEquals("u2", result.donoId());
    }

    @Test
    void atualizarRestauranteDeveRetornarNullQuandoRestauranteOuDonoNaoExistem() {
        RestauranteDataStorage storage = new RestauranteDataStorage(restauranteRepository, usuarioRepository);
        NovoRestauranteDTO dto = new NovoRestauranteDTO("Novo", "Rua Z", "Japonesa", "Todos", "u2");

        when(restauranteRepository.findById("r9")).thenReturn(Optional.empty());
        when(restauranteRepository.findById("r1")).thenReturn(Optional.of(RestauranteEntity.builder().id("r1").build()));
        when(usuarioRepository.findById("u2")).thenReturn(Optional.empty());

        assertNull(storage.atualizarRestaurante("r9", dto));
        assertNull(storage.atualizarRestaurante("r1", dto));
    }

    @Test
    void excluirRestauranteDeveDelegarAoRepository() {
        RestauranteDataStorage storage = new RestauranteDataStorage(restauranteRepository, usuarioRepository);

        storage.excluirRestaurante("r1");

        verify(restauranteRepository).deleteById("r1");
    }
}
