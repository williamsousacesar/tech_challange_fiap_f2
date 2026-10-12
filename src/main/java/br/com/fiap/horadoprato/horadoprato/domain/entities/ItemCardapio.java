package br.com.fiap.horadoprato.horadoprato.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "item_cardapio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_item_cardapio")
    private String idItemCardapio;

    @Column(name = "nome_item_cardapio", nullable = false)
    private String nomeItemCardapio;

    @Column(name = "descricao_item", nullable = false)
    private String descricaoItem;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "apenas_no_local", nullable = false)
    private boolean apenasNoLocal;

    @Column(name = "caminho_foto")
    private String caminhoFoto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_alteracao")
    private LocalDateTime dataAlteracao;

    @PrePersist
    public void aoCriar() {
        this.dataCriacao = agora();
    }

    @PreUpdate
    public void atualizarDataAlteracao() {
        this.dataAlteracao = agora();
    }

    private static LocalDateTime agora() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
