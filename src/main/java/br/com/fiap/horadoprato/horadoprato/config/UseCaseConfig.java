package br.com.fiap.horadoprato.horadoprato.config;

import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.AtualizarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.CadastrarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.ExcluirTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.ListarTiposUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.AlterarTipoDoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.BuscarUsuarioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.ListarUsuariosUseCase;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registra os use cases como beans do Spring. Os use cases continuam sem
 * dependencia do framework; apenas esta classe conhece o Spring.
 */
@Configuration
public class UseCaseConfig {

    @Bean
    public CadastrarTipoUsuarioUseCase cadastrarTipoUsuarioUseCase(ITipoUsuarioGateway tipoUsuarioGateway) {
        return CadastrarTipoUsuarioUseCase.create(tipoUsuarioGateway);
    }

    @Bean
    public ListarTiposUsuarioUseCase listarTiposUsuarioUseCase(ITipoUsuarioGateway tipoUsuarioGateway) {
        return ListarTiposUsuarioUseCase.create(tipoUsuarioGateway);
    }

    @Bean
    public BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase(ITipoUsuarioGateway tipoUsuarioGateway) {
        return BuscarTipoUsuarioPorIdUseCase.create(tipoUsuarioGateway);
    }

    @Bean
    public AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase(ITipoUsuarioGateway tipoUsuarioGateway) {
        return AtualizarTipoUsuarioUseCase.create(tipoUsuarioGateway);
    }

    @Bean
    public ExcluirTipoUsuarioUseCase excluirTipoUsuarioUseCase(ITipoUsuarioGateway tipoUsuarioGateway,
                                                               IUsuarioGateway usuarioGateway) {
        return ExcluirTipoUsuarioUseCase.create(tipoUsuarioGateway, usuarioGateway);
    }

    @Bean
    public ListarUsuariosUseCase listarUsuariosUseCase(IUsuarioGateway usuarioGateway) {
        return ListarUsuariosUseCase.create(usuarioGateway);
    }

    @Bean
    public BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase(IUsuarioGateway usuarioGateway) {
        return BuscarUsuarioPorIdUseCase.create(usuarioGateway);
    }

    @Bean
    public AlterarTipoDoUsuarioUseCase alterarTipoDoUsuarioUseCase(IUsuarioGateway usuarioGateway,
                                                                   ITipoUsuarioGateway tipoUsuarioGateway) {
        return AlterarTipoDoUsuarioUseCase.create(usuarioGateway, tipoUsuarioGateway);
    }
}
