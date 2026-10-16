package br.com.fiap.horadoprato.horadoprato.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoUsuario {

    private Long id;

    private String tpUsuario;

    private String descricaoTipoUsuario;
}
