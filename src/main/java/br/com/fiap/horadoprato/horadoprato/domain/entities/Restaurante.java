package br.com.fiap.horadoprato.horadoprato.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurante {

    private String id;

    private String nomeRestaurante;

    private String endereco;

    private String tipoCozinha;

    private String horarioFuncionamento;

    private Usuario dono;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAlteracao;
}
