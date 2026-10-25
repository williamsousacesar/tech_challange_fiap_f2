package br.com.fiap.horadoprato.horadoprato.adapters.persistence;

import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.RestauranteEntity;
import br.com.fiap.horadoprato.horadoprato.adapters.persistence.entities.UsuarioEntity;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.RestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteDataStorageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestauranteDataStorage implements IRestauranteDataStorageSource {

    private final RestauranteRepository repository;

    private final UsuarioRepository usuarioRepository;

    @Override
    public RestauranteDTO incluirRestaurante(NovoRestauranteDTO novoRestauranteDTO) {
        Optional<UsuarioEntity> dono = usuarioRepository.findById(novoRestauranteDTO.donoId());
        if (dono.isEmpty()) {
            return null;
        }

        RestauranteEntity entity = RestauranteEntity.builder()
                .nomeRestaurante(novoRestauranteDTO.nomeRestaurante())
                .endereco(novoRestauranteDTO.endereco())
                .tipoCozinha(novoRestauranteDTO.tipoCozinha())
                .horarioFuncionamento(novoRestauranteDTO.horarioFuncionamento())
                .dono(dono.get())
                .build();

        RestauranteEntity saved = repository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public RestauranteDTO buscarPorId(String id) {
        return repository.findById(id)
                .map(RestauranteDataStorage::toDTO)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RestauranteDTO> listarTodos(Pageable pageable) {
        return repository.findAll(pageable)
                .map(RestauranteDataStorage::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeRestauranteComDono(String donoId) {
        return repository.existsByDonoId(donoId);
    }

    @Override
    @Transactional
    public RestauranteDTO atualizarRestaurante(String id, NovoRestauranteDTO restauranteDTO) {
        Optional<RestauranteEntity> existente = repository.findById(id);
        if (existente.isEmpty()) {
            return null;
        }

        Optional<UsuarioEntity> dono = usuarioRepository.findById(restauranteDTO.donoId());
        if (dono.isEmpty()) {
            return null;
        }

        RestauranteEntity entity = existente.get();
        entity.setNomeRestaurante(restauranteDTO.nomeRestaurante());
        entity.setEndereco(restauranteDTO.endereco());
        entity.setTipoCozinha(restauranteDTO.tipoCozinha());
        entity.setHorarioFuncionamento(restauranteDTO.horarioFuncionamento());
        entity.setDono(dono.get());

        return toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public void excluirRestaurante(String id) {
        repository.deleteById(id);
    }

    private static RestauranteDTO toDTO(RestauranteEntity entity) {
        return new RestauranteDTO(
                entity.getId(),
                entity.getNomeRestaurante(),
                entity.getEndereco(),
                entity.getTipoCozinha(),
                entity.getHorarioFuncionamento(),
                entity.getDono() != null ? entity.getDono().getId() : null,
                entity.getDataCriacao(),
                entity.getDataAlteracao()
        );
    }
}
