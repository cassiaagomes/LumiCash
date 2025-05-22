package br.edu.ifpb.pweb2.lumicash.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private boolean ativo;
    private String natureza; // Receita ou despesa
    private int ordem;

    @OneToMany(mappedBy = "categoria")
    private List<Transacao> transacoes;
}
