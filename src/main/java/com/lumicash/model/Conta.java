package com.lumicash.model;

import java.util.List;

public class Conta {
    private Long id;
    private String numero;
    private String descricao;
    private String tipo;
    private Integer diaFechamento; // Dia de fechamento da conta if tipo == CARTAO

    private Correntista correntista; 
    private List<Transacao> transacoes; 
}