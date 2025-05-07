package com.lumicash.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    private Long id;
    private String nome;
    private boolean ativo;
    private String natureza; // Receita ou despesa
    private int ordem;
}