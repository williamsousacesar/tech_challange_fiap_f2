package br.com.fiap.horadoprato.horadoprato.adapters.controllers.itemCardapio;

import br.com.fiap.horadoprato.horadoprato.adapters.presenters.ItemCardapioPresenter;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.AtualizarItemCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.BuscarItemCardapioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.CadastrarItemCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.ExcluirItemCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.ListarItensCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.ItemCardapioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.NovoItemCardapioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/itens-cardapio")
@RequiredArgsConstructor
@Tag(name = "Item de Cardapio", description = "CRUD de itens de cardapio")
public class ItemCardapioController {

    private final CadastrarItemCardapioUseCase cadastrarItemCardapioUseCase;

    private final ListarItensCardapioUseCase listarItensCardapioUseCase;

    private final BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;

    private final AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;

    private final ExcluirItemCardapioUseCase excluirItemCardapioUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um item de cardapio")
    @ApiResponse(responseCode = "201", description = "Item de cardapio cadastrado")
    @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public ItemCardapioDTO cadastrar(@Valid @RequestBody NovoItemCardapioDTO novoItemCardapioDTO) {
        var itemCardapio = cadastrarItemCardapioUseCase.run(
                novoItemCardapioDTO.nomeItemCardapio(),
                novoItemCardapioDTO.descricaoItem(),
                novoItemCardapioDTO.preco(),
                novoItemCardapioDTO.apenasNoLocal(),
                novoItemCardapioDTO.caminhoFoto(),
                novoItemCardapioDTO.restauranteId()
        );
        return ItemCardapioPresenter.ToDTO(itemCardapio);
    }

    @GetMapping("/restaurante/{restauranteId}")
    @Operation(summary = "Lista todos os itens de cardapio")
    @ApiResponse(responseCode = "200", description = "Lista de itens de cardapio")
    public Page<ItemCardapioDTO> listar(@PathVariable String restauranteId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nomeItemCardapio"));
        return listarItensCardapioUseCase.run(restauranteId, pageable)
                .map(ItemCardapioPresenter::ToDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um item de cardapio pelo id")
    @ApiResponse(responseCode = "200", description = "Item de cardapio encontrado")
    @ApiResponse(responseCode = "404", description = "Item de cardapio nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public ItemCardapioDTO buscarPorId(@PathVariable String id) {
        return ItemCardapioPresenter.ToDTO(buscarItemCardapioPorIdUseCase.run(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um item de cardapio")
    @ApiResponse(responseCode = "200", description = "Item de cardapio atualizado")
    @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Item de cardapio ou restaurante nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public ItemCardapioDTO atualizar(@PathVariable String id, @Valid @RequestBody NovoItemCardapioDTO itemCardapioDTO) {
        var itemCardapio = atualizarItemCardapioUseCase.run(
                id,
                itemCardapioDTO.nomeItemCardapio(),
                itemCardapioDTO.descricaoItem(),
                itemCardapioDTO.preco(),
                itemCardapioDTO.apenasNoLocal(),
                itemCardapioDTO.caminhoFoto(),
                itemCardapioDTO.restauranteId()
        );
        return ItemCardapioPresenter.ToDTO(itemCardapio);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um item de cardapio")
    @ApiResponse(responseCode = "204", description = "Item de cardapio excluido")
    @ApiResponse(responseCode = "404", description = "Item de cardapio nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public void excluir(@PathVariable String id) {
        excluirItemCardapioUseCase.run(id);
    }
}
