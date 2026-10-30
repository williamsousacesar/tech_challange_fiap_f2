package br.com.fiap.horadoprato.horadoprato.adapters.controllers.usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UsuarioControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Sql(statements = {
            "DELETE FROM item_cardapio",
            "DELETE FROM restaurante",
            "DELETE FROM usuario",
            "DELETE FROM tipo_usuario",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (1, 'DONO_RESTAURANTE', 'Dono de Restaurante')",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (2, 'CLIENTE', 'Cliente')",
            "INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id) VALUES ('user-client', 'cliente_teste', 'Cliente Teste', 'cliente@teste.com', 'senha123', 2)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveListarUsuariosViaApi() throws Exception {
        mockMvc.perform(get("/api/usuarios")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].login").value("cliente_teste"))
                .andExpect(jsonPath("$.content[0].tipoUsuario.tpUsuario").value("CLIENTE"));
    }

    @Test
    @Sql(statements = {
            "DELETE FROM item_cardapio",
            "DELETE FROM restaurante",
            "DELETE FROM usuario",
            "DELETE FROM tipo_usuario",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (1, 'DONO_RESTAURANTE', 'Dono de Restaurante')",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (2, 'CLIENTE', 'Cliente')",
            "INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id) VALUES ('user-client', 'cliente_teste', 'Cliente Teste', 'cliente@teste.com', 'senha123', 2)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveAlterarTipoUsuarioViaApi() throws Exception {
        String payload = """
            {
              "tipoUsuarioId": 1
            }
            """;

        mockMvc.perform(put("/api/usuarios/user-client/tipo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("user-client"))
                .andExpect(jsonPath("$.tipoUsuario.tpUsuario").value("DONO_RESTAURANTE"));
    }

    @Test
    @Sql(statements = {
            "DELETE FROM item_cardapio",
            "DELETE FROM restaurante",
            "DELETE FROM usuario",
            "DELETE FROM tipo_usuario",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (1, 'DONO_RESTAURANTE', 'Dono de Restaurante')",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (2, 'CLIENTE', 'Cliente')",
            "INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id) VALUES ('user-owner', 'dono_teste', 'Dono Teste', 'dono@teste.com', 'senha123', 1)",
            "INSERT INTO restaurante (id, nome_restaurante, endereco, tipo_cozinha, horario_funcionamento, dono_id, data_criacao) VALUES ('rest-owner', 'Sabor da Casa', 'Rua Principal, 123', 'Italiana', 'Seg a Sex 10h-22h', 'user-owner', CURRENT_TIMESTAMP)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveRetornarConflitoAoAlterarTipoDeDonoComRestaurante() throws Exception {
        String payload = """
            {
              "tipoUsuarioId": 2
            }
            """;

        mockMvc.perform(put("/api/usuarios/user-owner/tipo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Conflito"))
                .andExpect(jsonPath("$.detail").value("Usuario com id user-owner possui restaurante e nao pode ter o tipo alterado"));

        mockMvc.perform(get("/api/usuarios/user-owner"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoUsuario.tpUsuario").value("DONO_RESTAURANTE"));
    }

    @Test
    @Sql(statements = {
            "DELETE FROM item_cardapio",
            "DELETE FROM restaurante",
            "DELETE FROM usuario",
            "DELETE FROM tipo_usuario",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (1, 'DONO_RESTAURANTE', 'Dono de Restaurante')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveRetornarNotFoundAoAlterarTipoDeUsuarioInexistente() throws Exception {
        String payload = """
            {
              "tipoUsuarioId": 1
            }
            """;

        mockMvc.perform(put("/api/usuarios/nao-existe/tipo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Recurso nao encontrado"))
                .andExpect(jsonPath("$.detail").value("Usuario com id nao-existe nao encontrado"));
    }

    @Test
    @Sql(statements = {
            "DELETE FROM item_cardapio",
            "DELETE FROM restaurante",
            "DELETE FROM usuario",
            "DELETE FROM tipo_usuario",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (2, 'CLIENTE', 'Cliente')",
            "INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id) VALUES ('user-client', 'cliente_teste', 'Cliente Teste', 'cliente@teste.com', 'senha123', 2)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveRetornarNotFoundAoAssociarTipoUsuarioInexistente() throws Exception {
        String payload = """
            {
              "tipoUsuarioId": 99
            }
            """;

        mockMvc.perform(put("/api/usuarios/user-client/tipo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Tipo de usuario com id 99 nao encontrado"));
    }

    @Test
    void deveRetornarBadRequestAoAlterarTipoSemInformarTipo() throws Exception {
        mockMvc.perform(put("/api/usuarios/user-client/tipo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Dados invalidos"))
                .andExpect(jsonPath("$.erros.tipoUsuarioId").value("O id do tipo de usuario e obrigatorio"));
    }
}
