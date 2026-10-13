package br.com.fiap.horadoprato.horadoprato.adapters.controllers.usuario;

import br.com.fiap.horadoprato.horadoprato.adapters.presenters.UsuarioPresenter;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.AlterarTipoDoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.BuscarUsuarioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.ListarUsuariosUseCase;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.AlterarTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final ListarUsuariosUseCase listarUsuariosUseCase;

    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    private final AlterarTipoDoUsuarioUseCase alterarTipoDoUsuarioUseCase;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return UsuarioPresenter.ToDTO(listarUsuariosUseCase.run());
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscarPorId(@PathVariable String id) {
        return UsuarioPresenter.ToDTO(buscarUsuarioPorIdUseCase.run(id));
    }

    @PutMapping("/{id}/tipo")
    public UsuarioDTO alterarTipo(@PathVariable String id, @Valid @RequestBody AlterarTipoUsuarioDTO alterarTipoUsuarioDTO) {
        return UsuarioPresenter.ToDTO(alterarTipoDoUsuarioUseCase.run(id, alterarTipoUsuarioDTO.tipoUsuarioId()));
    }
}
