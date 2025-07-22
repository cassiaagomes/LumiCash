package br.edu.ifpb.pweb2.lumicash.entity;

import java.util.ArrayList; // Importe ArrayList
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel; // Importe AccessLevel
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"transacoes", "correntista"})
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    private String numero;

    @Column(nullable = false, length = 512)
    private String descricao;

    @Column(nullable = false, length = 512)
    private String tipo;

    @Column(nullable = true, length = 512)
    private Integer diaFechamento;

    @ManyToOne
    @JoinColumn(name = "correntista_id")
    private Correntista correntista;

    // ✅ CORREÇÕES APLICADAS AQUI
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes = new ArrayList<>();

    // ✅ MÉTODOS DE AJUDA PARA MANIPULAR A LISTA DE FORMA SEGURA
    public void addTransacao(Transacao transacao) {
        this.transacoes.add(transacao);
        transacao.setConta(this);
    }

    public void removeTransacao(Transacao transacao) {
        this.transacoes.remove(transacao);
        transacao.setConta(null);
    }

}