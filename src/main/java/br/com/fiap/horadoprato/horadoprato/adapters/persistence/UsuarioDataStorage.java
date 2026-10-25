package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.UsuarioEntity;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioDataStorageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioDataStorage implements IUsuarioDataStorageSource {

    private final UsuarioRepository repository;

    private final TipoUsuarioRepository tipoUsuarioRepository;

    @Override
    public UsuarioDTO buscarPorId(String id) {
        return repository.findById(id)
                .map(UsuarioDataStorage::toDTO)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> listarTodos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(UsuarioDataStorage::toDTO);
    }

    @Override
    @Transactional
    public UsuarioDTO alterarTipoUsuario(String usuarioId, Long tipoUsuarioId) {
        UsuarioEntity usuario = repository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            return null;
        }
        usuario.setTipoUsuario(tipoUsuarioRepository.findById(tipoUsuarioId).orElseThrow());
        return toDTO(repository.save(usuario));
    }

    @Override
    public boolean existeUsuarioComTipo(Long tipoUsuarioId) {
        return repository.existsByTipoUsuarioId(tipoUsuarioId);
    }

    private static UsuarioDTO toDTO(UsuarioEntity entity) {
        return new UsuarioDTO(
                entity.getId(),
                entity.getLogin(),
                entity.getNome(),
                entity.getEmail(),
                TipoUsuarioDataStorage.toDTO(entity.getTipoUsuario()));
    }
}
