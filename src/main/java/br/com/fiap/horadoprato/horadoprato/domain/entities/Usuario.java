package br.com.fiap.horadoprato.horadoprato.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    private String id;

    private String login;

    private String nome;

    private String email;

    private String senha;

    private TipoUsuario tipoUsuario;
}
