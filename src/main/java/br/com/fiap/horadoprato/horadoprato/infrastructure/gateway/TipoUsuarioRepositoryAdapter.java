package br.com.fiap.horadoprato.horadoprato.infrastructure.gateway;

import br.com.fiap.horadoprato.horadoprato.domain.entity.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.domain.gateway.tipoUsuario.TipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.infrastructure.mapper.TipoUsuarioMapper;
import br.com.fiap.horadoprato.horadoprato.infrastructure.persistence.repository.TipoUsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class TipoUsuarioRepositoryAdapter implements TipoUsuarioGateway {
    
    private final TipoUsuarioRepository repository;
    private final TipoUsuarioMapper mapper;
    
    public TipoUsuarioRepositoryAdapter(TipoUsuarioRepository repository, TipoUsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TipoUsuario salvar(TipoUsuario tipoUsuario) {
        var jpaEntity = mapper.toJpaEntity(tipoUsuario);
        var saved = repository.save(jpaEntity);
        return mapper.toDomain(saved);
    }
}
