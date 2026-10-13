package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.IDataStorageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoUsuarioDataStorage implements IDataStorageSource {

    @Autowired
    private TipoUsuarioRepository repository;

    @Override
    public TipoUsuarioDTO incluirTipoUsuario(NovoTipoUsuarioDTO novoTipoUsuarioDTO) {
        TipoUsuario entity = TipoUsuario.builder()
                .tpUsuario(novoTipoUsuarioDTO.tpUsuario())
                .descricaoTipoUsuario(novoTipoUsuarioDTO.descricaoTipoUsuario())
                .build();

        TipoUsuario saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    public TipoUsuarioDTO buscarPorTpUsuario(String tpUsuario) {
        TipoUsuario entity = repository.findByTpUsuario(tpUsuario);
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
        TipoUsuario entity = repository.findById(id).orElse(null);
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

    static TipoUsuarioDTO toDTO(TipoUsuario entity) {
        return new TipoUsuarioDTO(entity.getId(), entity.getTpUsuario(), entity.getDescricaoTipoUsuario());
    }
}
