package br.com.fiap.horadoprato.horadoprato.adapters.gateways.restaurante;

import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.RestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteDataStorageSource;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteGateway implements IRestauranteGateway {

    private final IRestauranteDataStorageSource dataStorageSource;

    @Override
    public Restaurante incluir(Restaurante restaurante) {
        final NovoRestauranteDTO novoRestaurante = new NovoRestauranteDTO(
                restaurante.getNomeRestaurante(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getDono() != null ? restaurante.getDono().getId() : null
        );

        final RestauranteDTO restauranteCriado = this.dataStorageSource.incluirRestaurante(novoRestaurante);

        return toEntity(restauranteCriado);
    }

    @Override
    public Restaurante buscarPorId(String id) {
        return toEntity(this.dataStorageSource.buscarPorId(id));
    }

    @Override
    public Page<Restaurante> listarTodos(Pageable pageable) {
        return this.dataStorageSource.listarTodos(pageable)
                .map(RestauranteGateway::toEntity);
    }

    @Override
    public boolean existeRestauranteComDono(String donoId) {
        return this.dataStorageSource.existeRestauranteComDono(donoId);
    }

    @Override
    public Restaurante atualizar(Restaurante restaurante) {
        final NovoRestauranteDTO dadosAtualizados = new NovoRestauranteDTO(
                restaurante.getNomeRestaurante(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getDono() != null ? restaurante.getDono().getId() : null
        );

        return toEntity(this.dataStorageSource.atualizarRestaurante(restaurante.getId(), dadosAtualizados));
    }

    @Override
    public void excluir(String id) {
        this.dataStorageSource.excluirRestaurante(id);
    }

    public static Restaurante toEntity(RestauranteDTO restauranteDTO) {
        if (restauranteDTO == null) {
            return null;
        }
        return Restaurante.builder()
                .id(restauranteDTO.id())
                .nomeRestaurante(restauranteDTO.nomeRestaurante())
                .endereco(restauranteDTO.endereco())
                .tipoCozinha(restauranteDTO.tipoCozinha())
                .horarioFuncionamento(restauranteDTO.horarioFuncionamento())
                .dono(br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario.builder()
                        .id(restauranteDTO.donoId()).build())
                .dataCriacao(restauranteDTO.dataCriacao())
                .dataAlteracao(restauranteDTO.dataAlteracao())
                .build();
    }
}
