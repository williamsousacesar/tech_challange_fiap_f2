package br.com.fiap.horadoprato.horadoprato.dto.itemCardapio;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ItemCardapioDTO(

        @Schema(description = "Id do item do cardapio", example = "550e8400-e29b-41d4-a716-446655440000")
        String idItemCardapio,

        @Schema(description = "Nome do item do cardapio", example = "Pizza Margherita")
        String nomeItemCardapio,

        @Schema(description = "Descricao do item", example = "Pizza com molho de tomate, mussarela e manjericao")
        String descricaoItem,

        @Schema(description = "Preco do item", example = "59.90")
        BigDecimal preco,

        @Schema(description = "Indica se o item e apenas no local", example = "false")
        boolean apenasNoLocal,

        @Schema(description = "Caminho da foto do item", example = "/imagens/pizza-margherita.jpg")
        String caminhoFoto,

        @Schema(description = "Id do restaurante dono do item", example = "550e8400-e29b-41d4-a716-446655440000")
        String restauranteId,

        @Schema(description = "Data de criacao")
        LocalDateTime dataCriacao,

        @Schema(description = "Data de ultima alteracao")
        LocalDateTime dataAlteracao
) {
}
