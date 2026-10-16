package br.com.fiap.horadoprato.horadoprato.adapters.presenters;

import br.com.fiap.horadoprato.horadoprato.domain.entities.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.dto.tipoUsuario.TipoUsuarioDTO;

import java.util.List;

public class TipoUsuarioPresenter {

    public static TipoUsuarioDTO ToDTO(TipoUsuario tipoUsuario) {

        return new TipoUsuarioDTO(
                tipoUsuario.getId(),
                tipoUsuario.getTpUsuario(),
                tipoUsuario.getDescricaoTipoUsuario());

    }

    public static List<TipoUsuarioDTO> ToDTO(List<TipoUsuario> tiposUsuario) {
        return tiposUsuario.stream()
                .map(TipoUsuarioPresenter::ToDTO)
                .toList();
    }

}
