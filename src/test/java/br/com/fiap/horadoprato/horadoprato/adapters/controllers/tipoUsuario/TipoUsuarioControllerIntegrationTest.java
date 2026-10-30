package br.com.fiap.horadoprato.horadoprato.adapters.controllers.tipoUsuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TipoUsuarioControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void deveCadastrarTipoUsuarioViaApi() throws Exception {
        String payload = """
            {
              "tpUsuario": "ADMIN",
              "descricaoTipoUsuario": "Administrador do sistema"
            }
            """;

        mockMvc.perform(post("/api/tipo-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tpUsuario").value("ADMIN"))
                .andExpect(jsonPath("$.descricaoTipoUsuario").value("Administrador do sistema"));
    }

    @Test
    @Sql(statements = {
            "DELETE FROM item_cardapio",
            "DELETE FROM restaurante",
            "DELETE FROM usuario",
            "DELETE FROM tipo_usuario",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (1, 'CLIENTE', 'Cliente')",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (2, 'DONO_RESTAURANTE', 'Dono de Restaurante')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveListarTiposUsuarioViaApi() throws Exception {
        mockMvc.perform(get("/api/tipo-usuario")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].tpUsuario").value("CLIENTE"))
                .andExpect(jsonPath("$.content[0].descricaoTipoUsuario").value("Cliente"));
    }

    @Test
    @Sql(statements = {
            "DELETE FROM item_cardapio",
            "DELETE FROM restaurante",
            "DELETE FROM usuario",
            "DELETE FROM tipo_usuario",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (1, 'CLIENTE', 'Cliente')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveRetornarConflitoAoCadastrarTipoUsuarioDuplicado() throws Exception {
        String payload = """
            {
              "tpUsuario": "CLIENTE",
              "descricaoTipoUsuario": "Cliente duplicado"
            }
            """;

        mockMvc.perform(post("/api/tipo-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Conflito"))
                .andExpect(jsonPath("$.detail").value("Tipo de usuario 'CLIENTE' ja existe"));
    }

    @Test
    void deveRetornarBadRequestAoCadastrarTipoUsuarioSemNome() throws Exception {
        mockMvc.perform(post("/api/tipo-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Dados invalidos"))
                .andExpect(jsonPath("$.erros.tpUsuario").value("O tipo de usuario e obrigatorio"));
    }

    @Test
    @Sql(statements = {
            "DELETE FROM item_cardapio",
            "DELETE FROM restaurante",
            "DELETE FROM usuario",
            "DELETE FROM tipo_usuario"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveRetornarNotFoundAoBuscarTipoUsuarioInexistente() throws Exception {
        mockMvc.perform(get("/api/tipo-usuario/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Recurso nao encontrado"))
                .andExpect(jsonPath("$.detail").value("Tipo de usuario com id 99 nao encontrado"));
    }

    @Test
    @Sql(statements = {
            "DELETE FROM item_cardapio",
            "DELETE FROM restaurante",
            "DELETE FROM usuario",
            "DELETE FROM tipo_usuario",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (1, 'CLIENTE', 'Cliente')",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (2, 'DONO_RESTAURANTE', 'Dono de Restaurante')"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveRetornarConflitoAoAtualizarTipoUsuarioComNomeDeOutroTipo() throws Exception {
        String payload = """
            {
              "tpUsuario": "DONO_RESTAURANTE",
              "descricaoTipoUsuario": "Tentativa de duplicar"
            }
            """;

        mockMvc.perform(put("/api/tipo-usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("Tipo de usuario 'DONO_RESTAURANTE' ja existe"));
    }

    @Test
    @Sql(statements = {
            "DELETE FROM item_cardapio",
            "DELETE FROM restaurante",
            "DELETE FROM usuario",
            "DELETE FROM tipo_usuario",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (1, 'CLIENTE', 'Cliente')",
            "INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id) VALUES ('user-client', 'cliente_teste', 'Cliente Teste', 'cliente@teste.com', 'senha123', 1)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveRetornarConflitoAoExcluirTipoUsuarioComUsuariosVinculados() throws Exception {
        mockMvc.perform(delete("/api/tipo-usuario/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("Tipo de usuario com id 1 possui usuarios vinculados e nao pode ser excluido"));

        mockMvc.perform(get("/api/tipo-usuario/1"))
                .andExpect(status().isOk());
    }
}
