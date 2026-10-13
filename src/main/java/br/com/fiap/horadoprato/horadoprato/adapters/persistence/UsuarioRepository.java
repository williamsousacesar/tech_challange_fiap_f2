package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Override
    @EntityGraph(attributePaths = "tipoUsuario")
    List<Usuario> findAll();

    @Override
    @EntityGraph(attributePaths = "tipoUsuario")
    Optional<Usuario> findById(String id);

    boolean existsByTipoUsuarioId(Long tipoUsuarioId);
}
