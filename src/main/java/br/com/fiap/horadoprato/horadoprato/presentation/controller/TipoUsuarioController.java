package br.com.fiap.horadoprato.horadoprato.presentation.controller;

import br.com.fiap.horadoprato.horadoprato.domain.entity.TipoUsuario;
import br.com.fiap.horadoprato.horadoprato.domain.usecase.tipoUsuario.CriarTipoUsuarioUseCase;
import br.com.fiap.horadoprato.horadoprato.presentation.dto.request.CriarTipoUsuarioRequest;
import br.com.fiap.horadoprato.horadoprato.presentation.dto.response.TipoUsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-usuario")
public class TipoUsuarioController {

    private final CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;

    public TipoUsuarioController(CriarTipoUsuarioUseCase criarTipoUsuarioUseCase) {
        this.criarTipoUsuarioUseCase = criarTipoUsuarioUseCase;
    }

    @PostMapping
    public ResponseEntity<TipoUsuarioResponse> criar(@Valid @RequestBody CriarTipoUsuarioRequest request) {
        TipoUsuario tipoUsuario = criarTipoUsuarioUseCase.executar(
                request.getTpUsuario(),
                request.getDescricaoTipoUsuario()
        );

        TipoUsuarioResponse response = TipoUsuarioResponse.builder()
                .tipoUsuario(tipoUsuario.getTpUsuario())
                .descricaoTipoUsuario(tipoUsuario.getDescricaoTipoUsuario())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
