package br.com.fiap.horadoprato.horadoprato.interfaces.restaurante;

import br.com.fiap.horadoprato.horadoprato.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.RestauranteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestauranteDataStorageSource {

    RestauranteDTO incluirRestaurante(NovoRestauranteDTO novoRestauranteDTO);

    RestauranteDTO buscarPorId(String id);

    Page<RestauranteDTO> listarTodos(Pageable pageable);

    boolean existeRestauranteComDono(String donoId);

    RestauranteDTO atualizarRestaurante(String id, NovoRestauranteDTO restauranteDTO);

    void excluirRestaurante(String id);
}
