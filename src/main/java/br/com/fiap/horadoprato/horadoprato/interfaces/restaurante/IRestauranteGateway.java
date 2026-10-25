package br.com.fiap.horadoprato.horadoprato.interfaces.restaurante;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestauranteGateway {

    Restaurante incluir(Restaurante restaurante);

    Restaurante buscarPorId(String id);

    Page<Restaurante> listarTodos(Pageable pageable);

    boolean existeRestauranteComDono(String donoId);

    Restaurante atualizar(Restaurante restaurante);

    void excluir(String id);
}
