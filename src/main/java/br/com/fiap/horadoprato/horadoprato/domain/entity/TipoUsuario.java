package br.com.fiap.horadoprato.horadoprato.domain.entity;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Table(name = "tipo_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoUsuario {

    @Column(nullable = false)
    private String tpUsuario;

    @Column(nullable = false)
    private String descricaoTipoUsuario;

}