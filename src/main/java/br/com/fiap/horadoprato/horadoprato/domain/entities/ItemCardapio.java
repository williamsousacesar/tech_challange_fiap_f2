package br.com.fiap.horadoprato.horadoprato.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCardapio {

    private String idItemCardapio;

    private String nomeItemCardapio;

    private String descricaoItem;

    private BigDecimal preco;

    private boolean apenasNoLocal;

    private String caminhoFoto;

    private Restaurante restaurante;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAlteracao;
}
