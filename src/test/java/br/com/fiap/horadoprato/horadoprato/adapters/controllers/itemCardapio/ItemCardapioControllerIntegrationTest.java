package br.com.fiap.horadoprato.horadoprato.adapters.controllers.itemCardapio;

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
class ItemCardapioControllerIntegrationTest {

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
            "INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id) VALUES ('user-item', 'dono_item', 'Dono Item', 'dono.item@restaurante.com', 'senha123', 1)",
            "INSERT INTO restaurante (id, nome_restaurante, endereco, tipo_cozinha, horario_funcionamento, dono_id, data_criacao) VALUES ('rest-item', 'Casa do Sabor', 'Rua do Prato, 77', 'Brasileira', 'Seg a Dom 12h-23h', 'user-item', CURRENT_TIMESTAMP)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveCadastrarItemCardapioViaApi() throws Exception {
        String payload = """
            {
              "nomeItemCardapio": "Salada Caesar",
              "descricaoItem": "Salada crocante com frango e parmesao",
              "preco": 39.90,
              "apenasNoLocal": true,
              "caminhoFoto": "/imagens/salada-caesar.jpg",
              "restauranteId": "rest-item"
            }
            """;

        mockMvc.perform(post("/api/itens-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeItemCardapio").value("Salada Caesar"))
                .andExpect(jsonPath("$.restauranteId").value("rest-item"));
    }

    @Test
    @Sql(statements = {
            "DELETE FROM item_cardapio",
            "DELETE FROM restaurante",
            "DELETE FROM usuario",
            "DELETE FROM tipo_usuario",
            "INSERT INTO tipo_usuario (id, tp_usuario, descricao_tipo_usuario) VALUES (1, 'DONO_RESTAURANTE', 'Dono de Restaurante')",
            "INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id) VALUES ('user-item', 'dono_item', 'Dono Item', 'dono.item@restaurante.com', 'senha123', 1)",
            "INSERT INTO restaurante (id, nome_restaurante, endereco, tipo_cozinha, horario_funcionamento, dono_id, data_criacao) VALUES ('rest-item', 'Casa do Sabor', 'Rua do Prato, 77', 'Brasileira', 'Seg a Dom 12h-23h', 'user-item', CURRENT_TIMESTAMP)",
            "INSERT INTO item_cardapio (id_item_cardapio, nome_item_cardapio, descricao_item, preco, apenas_no_local, caminho_foto, restaurante_id, data_criacao) VALUES ('item-1', 'Pizza de Calabresa', 'Pizza com molho e calabresa', 48.90, false, '/imagens/pizza.jpg', 'rest-item', CURRENT_TIMESTAMP)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveListarItensCardapioViaApi() throws Exception {
        mockMvc.perform(get("/api/itens-cardapio/restaurante/rest-item")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nomeItemCardapio").value("Pizza de Calabresa"))
                .andExpect(jsonPath("$.content[0].restauranteId").value("rest-item"));
    }
}
