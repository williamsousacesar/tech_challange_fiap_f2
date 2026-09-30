package br.com.fiap.horadoprato.horadoprato.infrastructure.persistence.repository;

import br.com.fiap.horadoprato.horadoprato.infrastructure.persistence.model.TipoUsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioJpaEntity, String> {
}
