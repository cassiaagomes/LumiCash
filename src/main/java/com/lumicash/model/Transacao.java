package com.lumicash.model;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    private Long id;
    private LocalDate data;
    private String descricao;
    private double valor;
    private String movimento;

    private Categoria categoria; 
    private List<Conta> contas; 
    private Comentario comentario; 
}