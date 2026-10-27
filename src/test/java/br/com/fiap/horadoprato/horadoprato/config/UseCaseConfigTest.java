package br.com.fiap.horadoprato.horadoprato.config;

import br.com.fiap.horadoprato.horadoprato.domain.usecase.itemCardapio.*;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.restaurante.*;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.*;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.usuario.*;
import br.com.fiap.horadoprato.horadoprato.interfaces.itemCardapio.IItemCardapioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.restaurante.IRestauranteGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.tipoUsuario.ITipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class UseCaseConfigTest {

    @Test
    void deveCriarTodosOsBeans() {
        UseCaseConfig config = new UseCaseConfig();
        ITipoUsuarioGateway tipoUsuarioGateway = mock(ITipoUsuarioGateway.class);
        IUsuarioGateway usuarioGateway = mock(IUsuarioGateway.class);
        IRestauranteGateway restauranteGateway = mock(IRestauranteGateway.class);
        IItemCardapioGateway itemCardapioGateway = mock(IItemCardapioGateway.class);

        assertNotNull(config.cadastrarTipoUsuarioUseCase(tipoUsuarioGateway));
        assertNotNull(config.listarTiposUsuarioUseCase(tipoUsuarioGateway));
        assertNotNull(config.buscarTipoUsuarioPorIdUseCase(tipoUsuarioGateway));
        assertNotNull(config.atualizarTipoUsuarioUseCase(tipoUsuarioGateway));
        assertNotNull(config.excluirTipoUsuarioUseCase(tipoUsuarioGateway, usuarioGateway));

        assertNotNull(config.listarUsuariosUseCase(usuarioGateway));
        assertNotNull(config.buscarUsuarioPorIdUseCase(usuarioGateway));
        assertNotNull(config.alterarTipoDoUsuarioUseCase(usuarioGateway, tipoUsuarioGateway, restauranteGateway));

        assertNotNull(config.cadastrarRestauranteUseCase(restauranteGateway, usuarioGateway));
        assertNotNull(config.listarRestaurantesUseCase(restauranteGateway));
        assertNotNull(config.buscarRestaurantePorIdUseCase(restauranteGateway));
        assertNotNull(config.atualizarRestauranteUseCase(restauranteGateway, usuarioGateway));
        assertNotNull(config.excluirRestauranteUseCase(restauranteGateway));

        assertNotNull(config.cadastrarItemCardapioUseCase(itemCardapioGateway, restauranteGateway));
        assertNotNull(config.listarItensCardapioUseCase(itemCardapioGateway));
        assertNotNull(config.buscarItemCardapioPorIdUseCase(itemCardapioGateway));
        assertNotNull(config.atualizarItemCardapioUseCase(itemCardapioGateway, restauranteGateway));
        assertNotNull(config.excluirItemCardapioUseCase(itemCardapioGateway));
    }
}
