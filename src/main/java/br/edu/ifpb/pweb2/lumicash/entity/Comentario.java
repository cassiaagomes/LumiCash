package br.edu.ifpb.pweb2.lumicash.entity;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {
    private Long id;
    private String texto;

    @OneToOne
    @JoinColumn(name = "transacao_id")
    private Transacao transacao;
}