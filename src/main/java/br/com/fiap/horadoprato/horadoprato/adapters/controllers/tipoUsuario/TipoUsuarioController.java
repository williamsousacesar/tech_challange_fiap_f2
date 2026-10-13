package br.com.fiap.horadoprato.horadoprato.adapters.controllers.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.adapters.presenters.TipoUsuarioPresenter;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.AtualizarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.CadastrarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.ExcluirTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.ListarTiposUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class TipoUsuarioController {

    private final CadastrarTipoUsuarioUseCase cadastrarTipoUsuarioUseCase;

    private final ListarTiposUsuarioUseCase listarTiposUsuarioUseCase;

    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    private final AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;

    private final ExcluirTipoUsuarioUseCase excluirTipoUsuarioUseCase;

    @PostMapping({"", "/cadastrar"})
    @ResponseStatus(HttpStatus.CREATED)
    public TipoUsuarioDTO cadastrar(@Valid @RequestBody NovoTipoUsuarioDTO novoTipoUsuarioDTO) {
        var tipoUsuario = cadastrarTipoUsuarioUseCase.run(novoTipoUsuarioDTO);
        return TipoUsuarioPresenter.ToDTO(tipoUsuario);
    }

    @GetMapping
    public List<TipoUsuarioDTO> listar() {
        return TipoUsuarioPresenter.ToDTO(listarTiposUsuarioUseCase.run());
    }

    @GetMapping("/{id}")
    public TipoUsuarioDTO buscarPorId(@PathVariable Long id) {
        return TipoUsuarioPresenter.ToDTO(buscarTipoUsuarioPorIdUseCase.run(id));
    }

    @PutMapping("/{id}")
    public TipoUsuarioDTO atualizar(@PathVariable Long id, @Valid @RequestBody NovoTipoUsuarioDTO tipoUsuarioDTO) {
        return TipoUsuarioPresenter.ToDTO(atualizarTipoUsuarioUseCase.run(id, tipoUsuarioDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        excluirTipoUsuarioUseCase.run(id);
    }
}
