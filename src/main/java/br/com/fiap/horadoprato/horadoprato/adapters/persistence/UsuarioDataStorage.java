package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioDataStorageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioDataStorage implements IUsuarioDataStorageSource {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Override
    public UsuarioDTO buscarPorId(String id) {
        return repository.findById(id)
                .map(UsuarioDataStorage::toDTO)
                .orElse(null);
    }

    @Override
    public List<UsuarioDTO> listarTodos() {
        return repository.findAll().stream()
                .map(UsuarioDataStorage::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public UsuarioDTO alterarTipoUsuario(String usuarioId, Long tipoUsuarioId) {
        Usuario usuario = repository.findById(usuarioId).orElse(null);
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

    private static UsuarioDTO toDTO(Usuario entity) {
        return new UsuarioDTO(
                entity.getId(),
                entity.getLogin(),
                entity.getNome(),
                entity.getEmail(),
                TipoUsuarioDataStorage.toDTO(entity.getTipoUsuario()));
    }
}
