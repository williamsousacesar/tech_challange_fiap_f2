package br.com.fiap.horadoprato.horadoprato.adapters.presenters;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;

public class TipoUsuarioPresenter {

    public static TipoUsuarioDTO ToDTO(TipoUsuario tipoUsuario) {

        final String tpUsuario = tipoUsuario.getTpUsuario();
        final String tpUsuarioOfuscado = tpUsuario.charAt(1) + "..." + tpUsuario.charAt(tpUsuario.length() - 1);

        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO(
                tpUsuarioOfuscado,
                tipoUsuario.getDescricaoTipoUsuario());

        return tipoUsuarioDTO;

    }

}
