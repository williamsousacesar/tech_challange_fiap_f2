package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.IDataStorageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return new TipoUsuarioDTO(saved.getTpUsuario(), saved.getDescricaoTipoUsuario());
    }

    @Override
    public TipoUsuarioDTO buscarPorTpUsuario(String tpUsuario) {
        TipoUsuario entity = repository.findByTpUsuario(tpUsuario);
        if (entity == null) {
            return null;
        }
        return new TipoUsuarioDTO(entity.getTpUsuario(), entity.getDescricaoTipoUsuario());
    }
}
