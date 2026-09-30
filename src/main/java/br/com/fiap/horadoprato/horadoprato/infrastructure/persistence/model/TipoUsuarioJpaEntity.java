package br.com.fiap.horadoprato.horadoprato.infrastructure.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoUsuarioJpaEntity {
    
    @Id
    @Column(name = "tipo_usuario", length = 50)
    private String tipoUsuario;
    
    @Column(name = "descricao", nullable = false)
    private String descricaoTipoUsuario;
}
