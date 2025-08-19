package br.edu.ifpb.pweb2.lumicash.dtos;

import java.math.BigDecimal;
import java.util.List;

public class OrcamentoCategoriaDTO {

    private String nome;
    private List<BigDecimal> valoresMensais; // Jan, Fev, ..., Dez
    private BigDecimal total;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<BigDecimal> getValoresMensais() { return valoresMensais; }
    public void setValoresMensais(List<BigDecimal> valoresMensais) { this.valoresMensais = valoresMensais; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}

