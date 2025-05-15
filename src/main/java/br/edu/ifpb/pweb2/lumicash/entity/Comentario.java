package br.edu.ifpb.pweb2.lumicash.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {
    private Long id;
    private String texto;
    private Transacao transacao;
}