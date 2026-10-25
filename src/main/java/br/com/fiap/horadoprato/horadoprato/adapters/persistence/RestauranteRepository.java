package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.RestauranteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<RestauranteEntity, String> {

    @Override
    @EntityGraph(attributePaths = "dono")
    Page<RestauranteEntity> findAll(Pageable pageable);

    boolean existsByDonoId(String donoId);
}
