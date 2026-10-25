package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.ItemCardapioEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemCardapioEntity, String> {

    @Override
    @EntityGraph(attributePaths = "restaurante")
    Page<ItemCardapioEntity> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "restaurante")
    Optional<ItemCardapioEntity> findById(String id);

    @EntityGraph(attributePaths = "restaurante")
    Page<ItemCardapioEntity> findByRestauranteId(String restauranteId, Pageable pageable);
}
