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
    @Size(min = 3, max = 64)
    private String nome;

    @Column(nullable = false, length = 512)
    private Boolean isAdmin;

    @Column(nullable = false, length = 512)
    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 8, max = 64)
    private String senha;

    @Column(nullable = false, length = 512)
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail deve ser válido")
    private String email;

    @Column(nullable = false)
    private Boolean ativo = true;

    // SOLUÇÃO 1: Remover orphanRemoval temporariamente
    @OneToMany(mappedBy = "correntista", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE) // Mantenha esta linha para evitar o bug de criação
    private List<Conta> contas = new ArrayList<>();

    public void addConta(Conta conta) {
        this.contas.add(conta);
        conta.setCorrentista(this);
    }

    public void removeConta(Conta conta) {
        this.contas.remove(conta);
        conta.setCorrentista(null);
    }
}