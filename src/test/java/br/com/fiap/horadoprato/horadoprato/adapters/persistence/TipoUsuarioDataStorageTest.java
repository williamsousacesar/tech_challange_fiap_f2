package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.TipoUsuarioEntity;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioDataStorageTest {

    @Mock
    private TipoUsuarioRepository repository;

    @Test
    void incluirTipoUsuarioDeveSalvarEMapear() {
        TipoUsuarioDataStorage storage = new TipoUsuarioDataStorage(repository);
        NovoTipoUsuarioDTO dto = new NovoTipoUsuarioDTO("CLIENTE", "Cliente");

        when(repository.save(any(TipoUsuarioEntity.class))).thenAnswer(invocation -> {
            TipoUsuarioEntity entity = invocation.getArgument(0);
            entity.setId(1L);
            return entity;
        });

        TipoUsuarioDTO result = storage.incluirTipoUsuario(dto);

        assertEquals(1L, result.id());
        assertEquals("CLIENTE", result.tpUsuario());
        verify(repository).save(any(TipoUsuarioEntity.class));
    }

    @Test
    void buscarPorTpUsuarioDeveRetornarDtoOuNull() {
        TipoUsuarioDataStorage storage = new TipoUsuarioDataStorage(repository);
        TipoUsuarioEntity entity = TipoUsuarioEntity.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();

        when(repository.findByTpUsuario("CLIENTE")).thenReturn(entity);
        when(repository.findByTpUsuario("INEXISTENTE")).thenReturn(null);

        assertEquals("CLIENTE", storage.buscarPorTpUsuario("CLIENTE").tpUsuario());
        assertNull(storage.buscarPorTpUsuario("INEXISTENTE"));
    }

    @Test
    void buscarPorIdDeveRetornarDtoOuNull() {
        TipoUsuarioDataStorage storage = new TipoUsuarioDataStorage(repository);
        TipoUsuarioEntity entity = TipoUsuarioEntity.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertEquals("CLIENTE", storage.buscarPorId(1L).tpUsuario());
        assertNull(storage.buscarPorId(99L));
    }

    @Test
    void listarTodosDeveRetornarPaginaMapeada() {
        TipoUsuarioDataStorage storage = new TipoUsuarioDataStorage(repository);
        Pageable pageable = Pageable.unpaged();
        TipoUsuarioEntity entity = TipoUsuarioEntity.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();

        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity)));

        Page<TipoUsuarioDTO> page = storage.listarTodos(pageable);

        assertEquals(1, page.getContent().size());
        assertEquals("CLIENTE", page.getContent().getFirst().tpUsuario());
    }

    @Test
    void atualizarTipoUsuarioDeveAtualizarQuandoExiste() {
        TipoUsuarioDataStorage storage = new TipoUsuarioDataStorage(repository);
        TipoUsuarioEntity entity = TipoUsuarioEntity.builder().id(1L).tpUsuario("CLIENTE").descricaoTipoUsuario("Cliente").build();
        NovoTipoUsuarioDTO dto = new NovoTipoUsuarioDTO("DONO_RESTAURANTE", "Dono");

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        TipoUsuarioDTO result = storage.atualizarTipoUsuario(1L, dto);

        assertEquals("DONO_RESTAURANTE", result.tpUsuario());
        assertEquals("Dono", result.descricaoTipoUsuario());
    }

    @Test
    void atualizarTipoUsuarioDeveRetornarNullQuandoNaoExiste() {
        TipoUsuarioDataStorage storage = new TipoUsuarioDataStorage(repository);

        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertNull(storage.atualizarTipoUsuario(99L, new NovoTipoUsuarioDTO("CLIENTE", "Cliente")));
    }

    @Test
    void excluirTipoUsuarioDeveDelegarAoRepository() {
        TipoUsuarioDataStorage storage = new TipoUsuarioDataStorage(repository);

        storage.excluirTipoUsuario(1L);

        verify(repository).deleteById(1L);
    }
}
