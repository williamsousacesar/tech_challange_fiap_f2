package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.TipoUsuarioEntity;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.IDataStorageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoUsuarioDataStorage implements IDataStorageSource {

    private final TipoUsuarioRepository repository;

    @Override
    public TipoUsuarioDTO incluirTipoUsuario(NovoTipoUsuarioDTO novoTipoUsuarioDTO) {
        TipoUsuarioEntity entity = TipoUsuarioEntity.builder()
                .tpUsuario(novoTipoUsuarioDTO.tpUsuario())
                .descricaoTipoUsuario(novoTipoUsuarioDTO.descricaoTipoUsuario())
                .build();

        TipoUsuarioEntity saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    public TipoUsuarioDTO buscarPorTpUsuario(String tpUsuario) {
        TipoUsuarioEntity entity = repository.findByTpUsuario(tpUsuario);
        if (entity == null) {
            return null;
        }
        return toDTO(entity);
    }

    @Override
    public TipoUsuarioDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(TipoUsuarioDataStorage::toDTO)
                .orElse(null);
    }

    @Override
    public List<TipoUsuarioDTO> listarTodos() {
        return repository.findAll().stream()
                .map(TipoUsuarioDataStorage::toDTO)
                .toList();
    }

    @Override
    public TipoUsuarioDTO atualizarTipoUsuario(Long id, NovoTipoUsuarioDTO tipoUsuarioDTO) {
        TipoUsuarioEntity entity = repository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        entity.setTpUsuario(tipoUsuarioDTO.tpUsuario());
        entity.setDescricaoTipoUsuario(tipoUsuarioDTO.descricaoTipoUsuario());
        return toDTO(repository.save(entity));
    }

    @Override
    public void excluirTipoUsuario(Long id) {
        repository.deleteById(id);
    }

    static TipoUsuarioDTO toDTO(TipoUsuarioEntity entity) {
        return new TipoUsuarioDTO(entity.getId(), entity.getTpUsuario(), entity.getDescricaoTipoUsuario());
    }
}
