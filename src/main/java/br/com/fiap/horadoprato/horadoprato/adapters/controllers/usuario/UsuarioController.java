package br.com.fiap.horadoprato.horadoprato.adapters.controllers.usuario;

import br.com.fiap.horadoprato.horadoprato.adapters.presenters.UsuarioPresenter;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.AlterarTipoDoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.BuscarUsuarioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.ListarUsuariosUseCase;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.AlterarTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;
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
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuario", description = "Consulta de usuarios e associacao com o tipo de usuario")
public class UsuarioController {

    private final ListarUsuariosUseCase listarUsuariosUseCase;

    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    private final AlterarTipoDoUsuarioUseCase alterarTipoDoUsuarioUseCase;

    @GetMapping
    @Operation(summary = "Lista todos os usuarios com seu tipo")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios")
    public Page<UsuarioDTO> listar(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "login"));
        return listarUsuariosUseCase.run(pageable)
                .map(UsuarioPresenter::ToDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuario pelo id")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public UsuarioDTO buscarPorId(@PathVariable String id) {
        return UsuarioPresenter.ToDTO(buscarUsuarioPorIdUseCase.run(id));
    }

    @PutMapping("/{id}/tipo")
    @Operation(summary = "Associa um tipo de usuario a um usuario existente")
    @ApiResponse(responseCode = "200", description = "Tipo do usuario alterado")
    @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Usuario ou tipo de usuario nao encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "409", description = "Usuario dono de restaurante nao pode ter o tipo alterado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    public UsuarioDTO alterarTipo(@PathVariable String id, @Valid @RequestBody AlterarTipoUsuarioDTO alterarTipoUsuarioDTO) {
        return UsuarioPresenter.ToDTO(alterarTipoDoUsuarioUseCase.run(id, alterarTipoUsuarioDTO.tipoUsuarioId()));
    }
}
