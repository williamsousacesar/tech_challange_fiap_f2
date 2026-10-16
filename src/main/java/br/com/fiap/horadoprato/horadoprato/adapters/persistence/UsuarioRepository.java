package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, String> {

    @Override
    @EntityGraph(attributePaths = "tipoUsuario")
    List<UsuarioEntity> findAll();

    @Override
    @EntityGraph(attributePaths = "tipoUsuario")
    Optional<UsuarioEntity> findById(String id);

    boolean existsByTipoUsuarioId(Long tipoUsuarioId);
}
