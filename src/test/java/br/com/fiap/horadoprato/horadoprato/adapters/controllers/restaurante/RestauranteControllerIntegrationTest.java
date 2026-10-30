package br.com.fiap.horadoprato.horadoprato.adapters.controllers.restaurante;

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
class RestauranteControllerIntegrationTest {

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
            "INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id) VALUES ('user-restaurant', 'dono_restaurante', 'Dono Restaurante', 'dono@restaurante.com', 'senha123', 1)",
            "INSERT INTO restaurante (id, nome_restaurante, endereco, tipo_cozinha, horario_funcionamento, dono_id, data_criacao) VALUES ('rest-1', 'Sabor da Casa', 'Rua Principal, 123', 'Italiana', 'Seg a Sex 10h-22h', 'user-restaurant', CURRENT_TIMESTAMP)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveCadastrarRestauranteViaApi() throws Exception {
        String payload = """
            {
              "nomeRestaurante": "Bistro do Bairro",
              "endereco": "Avenida Brasil, 456",
              "tipoCozinha": "Japonesa",
              "horarioFuncionamento": "Todos os dias 18h-23h",
              "donoId": "user-restaurant"
            }
            """;

        mockMvc.perform(post("/api/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeRestaurante").value("Bistro do Bairro"))
                .andExpect(jsonPath("$.donoId").value("user-restaurant"));
    }

    @Test
    @Sql(statements = {
            "DELETE FROM item_cardapio",
            "DELETE FROM restaurante",
            "DELETE FROM usuario",
            "DELETE FROM tipo_usuario",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (1, 'DONO_RESTAURANTE', 'Dono de Restaurante')",
            "INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id) VALUES ('user-restaurant', 'dono_restaurante', 'Dono Restaurante', 'dono@restaurante.com', 'senha123', 1)",
            "INSERT INTO restaurante (id, nome_restaurante, endereco, tipo_cozinha, horario_funcionamento, dono_id, data_criacao) VALUES ('rest-1', 'Sabor da Casa', 'Rua Principal, 123', 'Italiana', 'Seg a Sex 10h-22h', 'user-restaurant', CURRENT_TIMESTAMP)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveListarRestaurantesViaApi() throws Exception {
        mockMvc.perform(get("/api/restaurantes")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nomeRestaurante").value("Sabor da Casa"))
                .andExpect(jsonPath("$.content[0].donoId").value("user-restaurant"));
    }
}
