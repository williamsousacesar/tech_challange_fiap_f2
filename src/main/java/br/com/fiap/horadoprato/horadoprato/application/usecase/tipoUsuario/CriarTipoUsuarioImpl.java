package br.com.fiap.horadoprato.horadoprato.application.usecase.tipoUsuario;

import br.com.fiap.horadoprato.horadoprato.domain.entity.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.domain.gateway.tipoUsuario.TipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.CriarTipoUsuarioUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CriarTipoUsuarioImpl implements CriarTipoUsuarioUseCase {

    private final TipoUsuarioGateway gateway;

    public CriarTipoUsuarioImpl(TipoUsuarioGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public TipoUsuario executar(String tipoUsuario, String descricaoTipoUsuario) {
        if (tipoUsuario == null || tipoUsuario.isBlank()) {
            throw new IllegalArgumentException("Tipo de usuário não pode ser vazio");
        }
        if (descricaoTipoUsuario == null || descricaoTipoUsuario.isBlank()) {
            throw new IllegalArgumentException("Descrição do tipo de usuário não pode ser vazia");
        }

        TipoUsuario novoTipo = TipoUsuario.builder()
                .tpUsuario(tipoUsuario)
                .descricaoTipoUsuario(descricaoTipoUsuario)
                .build();

        return gateway.salvar(novoTipo);
    }
}