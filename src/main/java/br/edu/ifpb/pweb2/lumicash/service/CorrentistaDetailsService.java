package br.edu.ifpb.pweb2.lumicash.service;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.repository.CorrentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CorrentistaDetailsService implements UserDetailsService {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Correntista correntista = correntistaRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + username));

        // 1. Define as permissões (roles) do usuário
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (correntista.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // 2. Verifica se a conta está ativa (esta é a lógica de bloqueio)
        boolean isEnabled = correntista.isAtivo();

        // 3. Retorna o objeto UserDetails completo para o Spring Security
        return new User(
                correntista.getEmail(),
                correntista.getSenha(),
                isEnabled, // <-- A MÁGICA ACONTECE AQUI
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                authorities
        );
    }
}