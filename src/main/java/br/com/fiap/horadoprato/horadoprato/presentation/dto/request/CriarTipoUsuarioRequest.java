package br.com.fiap.horadoprato.horadoprato.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CriarTipoUsuarioRequest {

    @NotBlank(message = "Tipo de usuário não pode ser vazio")
    private String tpUsuario;

    @NotBlank(message = "Descrição do tipo de usuário não pode ser vazia")
    private String descricaoTipoUsuario;
}
