package br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entity.TipoUsuario;

public interface CriarTipoUsuarioUseCase {
    TipoUsuario executar(String tipoUsuario, String descricaoTipoUsuario);
}
