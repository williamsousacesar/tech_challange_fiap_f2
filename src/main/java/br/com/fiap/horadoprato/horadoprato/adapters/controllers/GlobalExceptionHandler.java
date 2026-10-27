package br.com.fiap.horadoprato.horadoprato.adapters.controllers;

import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteComItensCardapioException;
import br.com.fiap.horadoprato.horadoprato.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioComRestauranteNaoPodeAlterarTipoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEhDonoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.ItemCardapioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioEmUsoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioJaExisteException;
import br.com.fiap.horadoprato.horadoprato.exceptions.TipoUsuarioNaoEncontradoException;
import br.com.fiap.horadoprato.horadoprato.exceptions.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({TipoUsuarioNaoEncontradoException.class, UsuarioNaoEncontradoException.class, RestauranteNaoEncontradoException.class, ItemCardapioNaoEncontradoException.class})
    public ProblemDetail tratarNaoEncontrado(RuntimeException e) {
        return criarProblema(HttpStatus.NOT_FOUND, "Recurso nao encontrado", e.getMessage());
    }

    @ExceptionHandler({TipoUsuarioJaExisteException.class, TipoUsuarioEmUsoException.class, UsuarioNaoEhDonoException.class, UsuarioComRestauranteNaoPodeAlterarTipoException.class, RestauranteComItensCardapioException.class})
    public ProblemDetail tratarConflito(RuntimeException e) {
        return criarProblema(HttpStatus.CONFLICT, "Conflito", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail tratarValidacao(MethodArgumentNotValidException e) {
        ProblemDetail problema = criarProblema(HttpStatus.BAD_REQUEST, "Dados invalidos", "Um ou mais campos estao invalidos");

        Map<String, String> erros = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(erro -> erros.putIfAbsent(erro.getField(), erro.getDefaultMessage()));
        problema.setProperty("erros", erros);

        return problema;
    }

    private static ProblemDetail criarProblema(HttpStatus status, String titulo, String detalhe) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(status, detalhe);
        problema.setTitle(titulo);
        return problema;
    }
}
