package br.edu.ifpb.pweb2.lumicash.entity;

import java.util.ArrayList;
import java.util.List;

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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = { "contas" })
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

    // ✅ SOLUÇÃO ALTERNATIVA 1: Remover orphanRemoval temporariamente
    @OneToMany(mappedBy = "correntista", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    private List<Conta> contas = new ArrayList<>();

    // ✅ SOLUÇÃO ALTERNATIVA 2: Ou manter orphanRemoval mas usar @JoinColumn
    /*
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "correntista_id")
    @Setter(AccessLevel.NONE)
    private List<Conta> contas = new ArrayList<>();
    */

    public Correntista(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = false;
        this.ativo = true;
    }

    public void addConta(Conta conta) {
        this.contas.add(conta);
        conta.setCorrentista(this);
    }

    public void removeConta(Conta conta) {
        this.contas.remove(conta);
        conta.setCorrentista(null);
    }
    
    public boolean isAtivo() {
        return this.ativo != null && this.ativo;
    }
    
    public boolean isAdmin() {
        return this.isAdmin != null && this.isAdmin;
    }
}