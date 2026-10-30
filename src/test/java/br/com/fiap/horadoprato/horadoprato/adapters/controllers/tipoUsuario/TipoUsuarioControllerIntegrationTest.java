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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
