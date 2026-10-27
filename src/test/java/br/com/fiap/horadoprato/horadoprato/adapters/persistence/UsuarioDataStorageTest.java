package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.TipoUsuarioEntity;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.UsuarioEntity;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioDataStorageTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Test
    void buscarPorIdDeveMapearUsuario() {
        UsuarioDataStorage storage = new UsuarioDataStorage(usuarioRepository, tipoUsuarioRepository);
        TipoUsuarioEntity tipoUsuario = TipoUsuarioEntity.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();
        UsuarioEntity entity = UsuarioEntity.builder()
                .id("u1")
                .login("carlos")
                .nome("Carlos")
                .email("carlos@email.com")
                .senha("123")
                .tipoUsuario(tipoUsuario)
                .build();

        when(usuarioRepository.findById("u1")).thenReturn(Optional.of(entity));
        when(usuarioRepository.findById("x")).thenReturn(Optional.empty());

        UsuarioDTO result = storage.buscarPorId("u1");

        assertEquals("u1", result.id());
        assertEquals("CLIENTE", result.tipoUsuario().tpUsuario());
        assertNull(storage.buscarPorId("x"));
    }

    @Test
    void listarTodosDeveRetornarPaginaMapeada() {
        UsuarioDataStorage storage = new UsuarioDataStorage(usuarioRepository, tipoUsuarioRepository);
        TipoUsuarioEntity tipoUsuario = TipoUsuarioEntity.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();
        UsuarioEntity entity = UsuarioEntity.builder().id("u1").login("carlos").nome("Carlos").email("carlos@email.com").senha("123").tipoUsuario(tipoUsuario).build();
        Pageable pageable = Pageable.unpaged();

        when(usuarioRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));

        Page<UsuarioDTO> page = storage.listarTodos(pageable);

        assertEquals(1, page.getContent().size());
        assertEquals("carlos", page.getContent().getFirst().login());
    }

    @Test
    void alterarTipoUsuarioDeveAtualizarTipo() {
        UsuarioDataStorage storage = new UsuarioDataStorage(usuarioRepository, tipoUsuarioRepository);
        TipoUsuarioEntity tipoAtual = TipoUsuarioEntity.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();
        TipoUsuarioEntity novoTipo = TipoUsuarioEntity.builder().id(2L).tpUsuario("DONO_RESTAURANTE").descricaoTipoUsuario("Dono").build();
        UsuarioEntity entity = UsuarioEntity.builder().id("u1").login("carlos").nome("Carlos").email("carlos@email.com").senha("123").tipoUsuario(tipoAtual).build();

        when(usuarioRepository.findById("u1")).thenReturn(Optional.of(entity));
        when(tipoUsuarioRepository.findById(2L)).thenReturn(Optional.of(novoTipo));
        when(usuarioRepository.save(entity)).thenReturn(entity);

        UsuarioDTO result = storage.alterarTipoUsuario("u1", 2L);

        assertEquals("DONO_RESTAURANTE", result.tipoUsuario().tpUsuario());
        verify(usuarioRepository).save(entity);
    }

    @Test
    void alterarTipoUsuarioDeveRetornarNullQuandoUsuarioNaoExiste() {
        UsuarioDataStorage storage = new UsuarioDataStorage(usuarioRepository, tipoUsuarioRepository);

        when(usuarioRepository.findById("x")).thenReturn(Optional.empty());

        assertNull(storage.alterarTipoUsuario("x", 2L));
    }

    @Test
    void existeUsuarioComTipoDeveDelegarAoRepository() {
        UsuarioDataStorage storage = new UsuarioDataStorage(usuarioRepository, tipoUsuarioRepository);

        when(usuarioRepository.existsByTipoUsuarioId(1L)).thenReturn(true);

        assertTrue(storage.existeUsuarioComTipo(1L));
    }
}
