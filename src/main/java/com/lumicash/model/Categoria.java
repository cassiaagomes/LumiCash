package com.lumicash.model;

import lombok.Data;
@Data

public class Categoria {
    private Long id;
    private String nome;
    private boolean ativo;
    private String natureza; // Receita ou despesa
    private int ordem;
}