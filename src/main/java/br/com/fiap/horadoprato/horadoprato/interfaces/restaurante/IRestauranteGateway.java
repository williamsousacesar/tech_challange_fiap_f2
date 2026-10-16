package br.com.fiap.horadoprato.horadoprato.interfaces.restaurante;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;

import java.util.List;

public interface IRestauranteGateway {

    Restaurante incluir(Restaurante restaurante);

    Restaurante buscarPorId(String id);

    List<Restaurante> listarTodos();

    Restaurante atualizar(Restaurante restaurante);

    void excluir(String id);
}
