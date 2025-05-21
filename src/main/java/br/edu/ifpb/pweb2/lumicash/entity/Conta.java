package br.edu.ifpb.pweb2.lumicash.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conta {
    private Long id;
    private String numero;
    private String descricao;
    private String tipo;
    private Integer diaFechamento; 

    @ManyToOne
    @JoinColumn(name = "correntista_id")
    private Correntista correntista; 

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes; 
}