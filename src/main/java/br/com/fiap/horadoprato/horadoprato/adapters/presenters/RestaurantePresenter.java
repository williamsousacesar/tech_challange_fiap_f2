package br.com.fiap.horadoprato.horadoprato.adapters.presenters;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.RestauranteDTO;

import java.util.List;

public class RestaurantePresenter {

    public static RestauranteDTO ToDTO(Restaurante restaurante) {

        return new RestauranteDTO(
                restaurante.getId(),
                restaurante.getNomeRestaurante(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getDono() != null ? restaurante.getDono().getId() : null,
                restaurante.getDataCriacao(),
                restaurante.getDataAlteracao()
        );

    }

    public static List<RestauranteDTO> ToDTO(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(RestaurantePresenter::ToDTO)
                .toList();
    }

}
