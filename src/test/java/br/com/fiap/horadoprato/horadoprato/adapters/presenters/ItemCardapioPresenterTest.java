package br.com.fiap.horadoprato.horadoprato.adapters.presenters;

import br.com.fiap.horadoprato.horadoprato.domain.entities.ItemCardapio;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Restaurante;
import br.com.fiap.horadoprato.horadoprato.dto.itemCardapio.ItemCardapioDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemCardapioPresenterTest {

    @Test
    void deveConverterItemParaDto() {
        LocalDateTime agora = LocalDateTime.now();
        ItemCardapio item = ItemCardapio.builder()
                .idItemCardapio("i1")
                .nomeItemCardapio("Pizza")
                .descricaoItem("Mussarela")
                .preco(new BigDecimal("49.90"))
                .apenasNoLocal(true)
                .caminhoFoto("/pizza.jpg")
                .restaurante(Restaurante.builder().id("r1").build())
                .dataCriacao(agora)
                .dataAlteracao(agora)
                .build();

        ItemCardapioDTO dto = ItemCardapioPresenter.ToDTO(item);

        assertEquals("i1", dto.idItemCardapio());
        assertEquals("Pizza", dto.nomeItemCardapio());
        assertEquals("r1", dto.restauranteId());
    }

    @Test
    void deveConverterListaParaDto() {
        List<ItemCardapio> itens = List.of(
                ItemCardapio.builder().idItemCardapio("i1").nomeItemCardapio("Pizza").restaurante(Restaurante.builder().id("r1").build()).build(),
                ItemCardapio.builder().idItemCardapio("i2").nomeItemCardapio("Risoto").restaurante(Restaurante.builder().id("r2").build()).build()
        );

        List<ItemCardapioDTO> resultado = ItemCardapioPresenter.ToDTO(itens);

        assertEquals(2, resultado.size());
        assertEquals("Pizza", resultado.get(0).nomeItemCardapio());
        assertEquals("Risoto", resultado.get(1).nomeItemCardapio());
    }
}
