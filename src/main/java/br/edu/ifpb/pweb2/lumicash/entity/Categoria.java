package br.edu.ifpb.pweb2.lumicash.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"transacoes"})
@AllArgsConstructor
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

    public void setAtivo(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
