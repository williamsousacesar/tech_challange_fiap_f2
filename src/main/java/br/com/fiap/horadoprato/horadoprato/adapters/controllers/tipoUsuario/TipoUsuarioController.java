package br.com.fiap.horadoprato.horadoprato.adapters.controllers.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.adapters.gateways.tipoUsuario.TipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.adapters.presenters.TipoUsuarioPresenter;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.CadastrarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioJaExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tipo-usuario")
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioGateway tipoUsuarioGateway;

    @PostMapping("/cadastrar")
    public TipoUsuarioDTO cadastrar(@RequestBody NovoTipoUsuarioDTO novoTipoUsuarioDTO) {
        var useCase = CadastrarTipoUsuarioUseCase.create(tipoUsuarioGateway);

        try {
            var tipousuario = useCase.run(novoTipoUsuarioDTO);
            return TipoUsuarioPresenter.ToDTO(tipousuario);
        } catch (TipoUsuarioJaExisteException e) {
            throw new RuntimeException("Tipo de Usuario ja existe!", e);
        }
    }
}
