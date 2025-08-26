package br.edu.ifpb.pweb2.lumicash.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    private String nome;

    @Column(nullable = false, length = 512)
    private Boolean ativo = true;

    @Column(nullable = false, length = 512)
    private String natureza;

    @Column(nullable = false, length = 512) // Receita ou despesa
    private int ordem;

    @OneToMany(mappedBy = "categoria")
    private List<Transacao> transacoes;

    // Construtores
    public Categoria() {
    }

    public Categoria(Long id, String nome, Boolean ativo, String natureza, int ordem, List<Transacao> transacoes) {
        this.id = id;
        this.nome = nome;
        this.ativo = ativo;
        this.natureza = natureza;
        this.ordem = ordem;
        this.transacoes = transacoes;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    // toString, equals e hashCode
    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", ativo=" + ativo +
                ", natureza='" + natureza + '\'' +
                ", ordem=" + ordem +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}