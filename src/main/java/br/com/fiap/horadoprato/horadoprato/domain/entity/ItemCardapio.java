package br.com.fiap.horadoprato.horadoprato.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "preco", nullable = false)
    private Double preco;

    @Column(name = "disponibilidade_consumo", nullable = false)
    private String disponibilidadeConsumo;

    @Column(name = "foto_prato", nullable = true)
    private byte[] fotoPrato;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_alteracao")
    private LocalDateTime dataAlteracao;

    @PreUpdate
    public void atualizarDataAlteracao() {
        this.dataAlteracao = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

}