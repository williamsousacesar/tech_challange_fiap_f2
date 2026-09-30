package br.com.fiap.horadoprato.horadoprato.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoUsuarioResponse {

    private String tipoUsuario;
    private String descricaoTipoUsuario;
}
