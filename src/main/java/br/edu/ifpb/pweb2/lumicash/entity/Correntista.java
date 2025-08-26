package br.edu.ifpb.pweb2.lumicash.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Correntista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 64, message = "O nome deve ter entre 3 e 64 caracteres")
    private String nome;

    @Column(nullable = false)
    @NotNull(message = "O campo isAdmin é obrigatório")
    private Boolean isAdmin = false;

    @Column(nullable = false, length = 512)
    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 8, max = 64, message = "A senha deve ter entre 8 e 64 caracteres")
    private String senha;

    @Column(nullable = false, unique = true, length = 512)
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail deve ser válido")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "O campo ativo é obrigatório")
    private Boolean ativo = true;

    @OneToMany(mappedBy = "correntista", cascade = CascadeType.ALL)
    private List<Conta> contas = new ArrayList<>();

    // Construtor sem argumentos (requerido pelo JPA)
    public Correntista() {
    }

    // Construtor para facilitar a criação de novos correntistas
    public Correntista(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = false;
        this.ativo = true;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    // Métodos de negócio para manipulação segura da lista de contas
    public void addConta(Conta conta) {
        this.contas.add(conta);
        conta.setCorrentista(this);
    }

    public void removeConta(Conta conta) {
        this.contas.remove(conta);
        conta.setCorrentista(null);
    }
    
    // Métodos utilitários para verificação de status
    public boolean isAtivo() {
        return this.ativo != null && this.ativo;
    }
    
    public boolean isAdmin() {
        return this.isAdmin != null && this.isAdmin;
    }

    // toString, equals e hashCode
    @Override
    public String toString() {
        return "Correntista{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", isAdmin=" + isAdmin +
                ", email='" + email + '\'' +
                ", ativo=" + ativo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Correntista that = (Correntista) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}