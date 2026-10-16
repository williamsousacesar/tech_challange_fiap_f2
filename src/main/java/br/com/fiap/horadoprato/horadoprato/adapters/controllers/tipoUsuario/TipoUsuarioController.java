package br.com.fiap.horadoprato.horadoprato.adapters.controllers.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.adapters.presenters.TipoUsuarioPresenter;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.AtualizarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.CadastrarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.ExcluirTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.ListarTiposUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@RestController
@RequestMapping("/api/tipo-usuario")
@RequiredArgsConstructor
@Tag(name = "Tipo de Usuario", description = "Cadastro dos tipos de usuario (ex.: Dono de Restaurante, Cliente)")
public class TipoUsuarioController {

    private final CadastrarTipoUsuarioUseCase cadastrarTipoUsuarioUseCase;

    private final ListarTiposUsuarioUseCase listarTiposUsuarioUseCase;

    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    private final AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;

    private final ExcluirTipoUsuarioUseCase excluirTipoUsuarioUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um tipo de usuario")
    @ApiResponse(responseCode = "201", description = "Tipo de usuario cadastrado")
    @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "409", description = "Ja existe um tipo com esse nome", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public TipoUsuarioDTO cadastrar(@Valid @RequestBody NovoTipoUsuarioDTO novoTipoUsuarioDTO) {
        var tipoUsuario = cadastrarTipoUsuarioUseCase.run(novoTipoUsuarioDTO.tpUsuario(), novoTipoUsuarioDTO.descricaoTipoUsuario());
        return TipoUsuarioPresenter.ToDTO(tipoUsuario);
    }

    @GetMapping
    @Operation(summary = "Lista todos os tipos de usuario")
    @ApiResponse(responseCode = "200", description = "Lista de tipos de usuario")
    public List<TipoUsuarioDTO> listar() {
        return TipoUsuarioPresenter.ToDTO(listarTiposUsuarioUseCase.run());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um tipo de usuario pelo id")
    @ApiResponse(responseCode = "200", description = "Tipo de usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Tipo de usuario nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public TipoUsuarioDTO buscarPorId(@PathVariable Long id) {
        return TipoUsuarioPresenter.ToDTO(buscarTipoUsuarioPorIdUseCase.run(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um tipo de usuario")
    @ApiResponse(responseCode = "200", description = "Tipo de usuario atualizado")
    @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Tipo de usuario nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "409", description = "Ja existe outro tipo com esse nome", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public TipoUsuarioDTO atualizar(@PathVariable Long id, @Valid @RequestBody NovoTipoUsuarioDTO tipoUsuarioDTO) {
        var tipoUsuario = atualizarTipoUsuarioUseCase.run(id, tipoUsuarioDTO.tpUsuario(), tipoUsuarioDTO.descricaoTipoUsuario());
        return TipoUsuarioPresenter.ToDTO(tipoUsuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um tipo de usuario")
    @ApiResponse(responseCode = "204", description = "Tipo de usuario excluido")
    @ApiResponse(responseCode = "404", description = "Tipo de usuario nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "409", description = "Existem usuarios vinculados a esse tipo", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public void excluir(@PathVariable Long id) {
        excluirTipoUsuarioUseCase.run(id);
    }
}
