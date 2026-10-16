package br.com.fiap.horadoprato.horadoprato.adapters.gateways.usuario;

import br.com.fiap.horadoprato.horadoprato.adapters.gateways.tipoUsuario.TipoUsuarioGateway;
import br.com.fiap.horadoprato.horadoprato.domain.entities.Usuario;
import br.com.fiap.horadoprato.horadoprato.dto.usuario.UsuarioDTO;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioDataStorageSource;
import br.com.fiap.horadoprato.horadoprato.interfaces.usuario.IUsuarioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioGateway implements IUsuarioGateway {

    private final IUsuarioDataStorageSource dataStorageSource;

    @Override
    public Usuario buscarPorId(String id) {
        return toEntity(this.dataStorageSource.buscarPorId(id));
    }

    @Override
    public List<Usuario> listarTodos() {
        return this.dataStorageSource.listarTodos().stream()
                .map(UsuarioGateway::toEntity)
                .toList();
    }

    @Override
    public Usuario alterarTipoUsuario(String usuarioId, Long tipoUsuarioId) {
        return toEntity(this.dataStorageSource.alterarTipoUsuario(usuarioId, tipoUsuarioId));
    }

    @Override
    public boolean existeUsuarioComTipo(Long tipoUsuarioId) {
        return this.dataStorageSource.existeUsuarioComTipo(tipoUsuarioId);
    }

    private static Usuario toEntity(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) {
            return null;
        }
        return Usuario.builder()
                .id(usuarioDTO.id())
                .login(usuarioDTO.login())
                .nome(usuarioDTO.nome())
                .email(usuarioDTO.email())
                .tipoUsuario(TipoUsuarioGateway.toEntity(usuarioDTO.tipoUsuario()))
                .build();
    }
}
