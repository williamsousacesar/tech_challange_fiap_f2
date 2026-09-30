package br.com.fiap.horadoprato.horadoprato.infrastructure.mapper;

import br.com.fiap.horadoprato.horadoprato.domain.entity.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.infrastructure.persistence.model.TipoUsuarioJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class TipoUsuarioMapper {
    
    public TipoUsuarioJpaEntity toJpaEntity(TipoUsuario domain) {
        if (domain == null) return null;
        
        return TipoUsuarioJpaEntity.builder()
                .tipoUsuario(domain.getTpUsuario())
                .descricaoTipoUsuario(domain.getDescricaoTipoUsuario())
                .build();
    }
    
    public TipoUsuario toDomain(TipoUsuarioJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        return TipoUsuario.builder()
                .tpUsuario(jpaEntity.getTipoUsuario())
                .descricaoTipoUsuario(jpaEntity.getDescricaoTipoUsuario())
                .build();
    }
}
