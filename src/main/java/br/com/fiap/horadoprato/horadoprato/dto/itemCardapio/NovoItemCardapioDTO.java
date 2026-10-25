package br.com.fiap.horadoprato.horadoprato.dto.itemCardapio;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record NovoItemCardapioDTO(

        @Schema(description = "Nome do item do cardapio", example = "Pizza Margherita")
        @NotBlank(message = "O nome do item e obrigatorio")
        @Size(max = 120, message = "O nome deve ter no maximo 120 caracteres")
        String nomeItemCardapio,

        @Schema(description = "Descricao do item", example = "Pizza com molho de tomate, mussarela e manjericao")
        @NotBlank(message = "A descricao do item e obrigatoria")
        @Size(max = 255, message = "A descricao deve ter no maximo 255 caracteres")
        String descricaoItem,

        @Schema(description = "Preco do item", example = "59.90")
        @NotNull(message = "O preco e obrigatorio")
        @DecimalMin(value = "0.01", message = "O preco deve ser maior que zero")
        BigDecimal preco,

        @Schema(description = "Indica se o item e apenas no local", example = "false")
        boolean apenasNoLocal,

        @Schema(description = "Caminho da foto do item", example = "/imagens/pizza-margherita.jpg")
        @Size(max = 255, message = "O caminho da foto deve ter no maximo 255 caracteres")
        String caminhoFoto,

        @Schema(description = "Id do restaurante dono do item", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotBlank(message = "O id do restaurante e obrigatorio")
        String restauranteId
) {
}
