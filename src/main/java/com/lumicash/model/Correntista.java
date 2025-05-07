package com.lumicash.model;

import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Correntista {
    private Long id;
    private String nome;
    private Boolean isAdmin;
    private String senha;
    private String user;

    private List<Conta> contas; // Lista associada a um correntista
}