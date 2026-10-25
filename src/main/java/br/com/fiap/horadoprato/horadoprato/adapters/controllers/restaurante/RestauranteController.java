package br.com.fiap.horadoprato.horadoprato.adapters.controllers.restaurante;

import br.com.fiap.horadoprato.horadoprato.adapters.presenters.RestaurantePresenter;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.AtualizarRestauranteUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.BuscarRestaurantePorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.CadastrarRestauranteUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.ExcluirRestauranteUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.ListarRestaurantesUseCase;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.horadoprato.horadoprato.dto.restaurante.RestauranteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
@Tag(name = "Restaurante", description = "CRUD de restaurantes")
public class RestauranteController {

    private final CadastrarRestauranteUseCase cadastrarRestauranteUseCase;

    private final ListarRestaurantesUseCase listarRestaurantesUseCase;

    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    private final AtualizarRestauranteUseCase atualizarRestauranteUseCase;

    private final ExcluirRestauranteUseCase excluirRestauranteUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um restaurante")
    @ApiResponse(responseCode = "201", description = "Restaurante cadastrado")
    @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Usuario dono nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "409", description = "Usuario informado nao possui perfil de dono", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public RestauranteDTO cadastrar(@Valid @RequestBody NovoRestauranteDTO novoRestauranteDTO) {
        var restaurante = cadastrarRestauranteUseCase.run(
                novoRestauranteDTO.nomeRestaurante(),
                novoRestauranteDTO.endereco(),
                novoRestauranteDTO.tipoCozinha(),
                novoRestauranteDTO.horarioFuncionamento(),
                novoRestauranteDTO.donoId()
        );
        return RestaurantePresenter.ToDTO(restaurante);
    }

    @GetMapping
    @Operation(summary = "Lista todos os restaurantes")
    @ApiResponse(responseCode = "200", description = "Lista de restaurantes")
    public Page<RestauranteDTO> listar(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nomeRestaurante"));
        return listarRestaurantesUseCase.run(pageable)
                .map(RestaurantePresenter::ToDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um restaurante pelo id")
    @ApiResponse(responseCode = "200", description = "Restaurante encontrado")
    @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public RestauranteDTO buscarPorId(@PathVariable String id) {
        return RestaurantePresenter.ToDTO(buscarRestaurantePorIdUseCase.run(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um restaurante")
    @ApiResponse(responseCode = "200", description = "Restaurante atualizado")
    @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Restaurante ou usuario dono nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "409", description = "Usuario informado nao possui perfil de dono", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public RestauranteDTO atualizar(@PathVariable String id, @Valid @RequestBody NovoRestauranteDTO restauranteDTO) {
        var restaurante = atualizarRestauranteUseCase.run(
                id,
                restauranteDTO.nomeRestaurante(),
                restauranteDTO.endereco(),
                restauranteDTO.tipoCozinha(),
                restauranteDTO.horarioFuncionamento(),
                restauranteDTO.donoId()
        );
        return RestaurantePresenter.ToDTO(restaurante);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um restaurante")
    @ApiResponse(responseCode = "204", description = "Restaurante excluido")
    @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public void excluir(@PathVariable String id) {
        excluirRestauranteUseCase.run(id);
    }
}
