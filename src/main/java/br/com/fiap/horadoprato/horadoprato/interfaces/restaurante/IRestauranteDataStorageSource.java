package br.com.fiap.horadoprato.horadoprato.interfaces.restaurante;

import br.com.fiap.horadoprato.horadoprato.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.RestauranteDTO;

import java.util.List;

public interface IRestauranteDataStorageSource {

    RestauranteDTO incluirRestaurante(NovoRestauranteDTO novoRestauranteDTO);

    RestauranteDTO buscarPorId(String id);

    List<RestauranteDTO> listarTodos();

    RestauranteDTO atualizarRestaurante(String id, NovoRestauranteDTO restauranteDTO);

    void excluirRestaurante(String id);
}
