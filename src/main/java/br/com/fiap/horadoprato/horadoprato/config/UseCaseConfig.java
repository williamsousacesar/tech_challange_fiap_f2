package br.com.fiap.horadoprato.horadoprato.config;

import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.AtualizarRestauranteUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.BuscarRestaurantePorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.CadastrarRestauranteUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.ExcluirRestauranteUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.ListarRestaurantesUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.AtualizarItemCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.BuscarItemCardapioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.CadastrarItemCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.ExcluirItemCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.ListarItensCardapioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.AtualizarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.CadastrarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.ExcluirTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.ListarTiposUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.AlterarTipoDoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.BuscarUsuarioPorIdUseCase;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.ListarUsuariosUseCase;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;
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
                                                                    ITipoUsuarioGateway tipoUsuarioGateway,
                                                                    IRestauranteGateway restauranteGateway) {
        return AlterarTipoDoUsuarioUseCase.create(usuarioGateway, tipoUsuarioGateway, restauranteGateway);
    }

    @Bean
    public CadastrarRestauranteUseCase cadastrarRestauranteUseCase(IRestauranteGateway restauranteGateway,
                                                                   IUsuarioGateway usuarioGateway) {
        return CadastrarRestauranteUseCase.create(restauranteGateway, usuarioGateway);
    }

    @Bean
    public ListarRestaurantesUseCase listarRestaurantesUseCase(IRestauranteGateway restauranteGateway) {
        return ListarRestaurantesUseCase.create(restauranteGateway);
    }

    @Bean
    public BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase(IRestauranteGateway restauranteGateway) {
        return BuscarRestaurantePorIdUseCase.create(restauranteGateway);
    }

    @Bean
    public AtualizarRestauranteUseCase atualizarRestauranteUseCase(IRestauranteGateway restauranteGateway,
                                                                   IUsuarioGateway usuarioGateway) {
        return AtualizarRestauranteUseCase.create(restauranteGateway, usuarioGateway);
    }

    @Bean
    public ExcluirRestauranteUseCase excluirRestauranteUseCase(IRestauranteGateway restauranteGateway,
                                                               IItemCardapioGateway itemCardapioGateway) {
        return ExcluirRestauranteUseCase.create(restauranteGateway, itemCardapioGateway);
    }

    @Bean
    public CadastrarItemCardapioUseCase cadastrarItemCardapioUseCase(IItemCardapioGateway itemCardapioGateway,
                                                                      IRestauranteGateway restauranteGateway) {
        return CadastrarItemCardapioUseCase.create(itemCardapioGateway, restauranteGateway);
    }

    @Bean
    public ListarItensCardapioUseCase listarItensCardapioUseCase(IItemCardapioGateway itemCardapioGateway) {
        return ListarItensCardapioUseCase.create(itemCardapioGateway);
    }

    @Bean
    public BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase(IItemCardapioGateway itemCardapioGateway) {
        return BuscarItemCardapioPorIdUseCase.create(itemCardapioGateway);
    }

    @Bean
    public AtualizarItemCardapioUseCase atualizarItemCardapioUseCase(IItemCardapioGateway itemCardapioGateway,
                                                                      IRestauranteGateway restauranteGateway) {
        return AtualizarItemCardapioUseCase.create(itemCardapioGateway, restauranteGateway);
    }

    @Bean
    public ExcluirItemCardapioUseCase excluirItemCardapioUseCase(IItemCardapioGateway itemCardapioGateway) {
        return ExcluirItemCardapioUseCase.create(itemCardapioGateway);
    }
}
