package br.edu.ifpb.pweb2.lumicash.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    private String descricao;
    private double valor;
    private String movimento;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria; 

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    @OneToOne(mappedBy = "transacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Comentario comentario; 
}