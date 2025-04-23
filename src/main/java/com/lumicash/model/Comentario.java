package com.lumicash.model;

import lombok.Data;

@Data

public class Comentario {
    private Long id;
    private String texto;
    private Transacao transacao;
}