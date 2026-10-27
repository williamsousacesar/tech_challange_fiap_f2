package br.com.fiap.horadoprato.horadoprato.adapters.controllers;

import br.com.fiap.horadoprato.horadoprato.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void deveTratarNaoEncontrado() {
        ProblemDetail detail = handler.tratarNaoEncontrado(new UsuarioNaoEncontradoException("u9"));

        assertEquals(404, detail.getStatus());
        assertEquals("Recurso nao encontrado", detail.getTitle());
    }

    @Test
    void deveTratarConflito() {
        ProblemDetail detail = handler.tratarConflito(new TipoUsuarioJaExisteException("CLIENTE"));

        assertEquals(409, detail.getStatus());
        assertEquals("Conflito", detail.getTitle());
    }

    @Test
    void deveTratarValidacao() {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new DummyForm(), "dummy");
        bindingResult.addError(new FieldError("dummy", "nome", "obrigatorio"));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ProblemDetail detail = handler.tratarValidacao(exception);

        assertEquals(400, detail.getStatus());
        assertNotNull(detail.getProperties().get("erros"));
    }

    private static class DummyForm {
        private String nome;
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }
}
