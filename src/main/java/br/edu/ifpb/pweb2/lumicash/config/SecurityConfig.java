package br.edu.ifpb.pweb2.lumicash.config;

import br.edu.ifpb.pweb2.lumicash.repository.CorrentistaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final CorrentistaRepository correntistaRepo;

    public SecurityConfig(CorrentistaRepository correntistaRepo) {
        this.correntistaRepo = correntistaRepo;
    }

    // Configuração do SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/auth/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/correntistas/**", "/categorias/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/auth/signin")
                        .loginProcessingUrl("/auth/login") // URL que processa o login
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/auth/signin?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/signin?logout")
                        .permitAll())
                .authenticationProvider(authenticationProvider()); // conecta o authProvider

        return http.build();
    }

    // UserDetailsService customizado
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var userEntity = correntistaRepo.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            List<GrantedAuthority> authorities = new ArrayList<>();
            if (Boolean.TRUE.equals(userEntity.getIsAdmin())) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }

            return new User(userEntity.getEmail(), userEntity.getSenha(), authorities);
        };
    }

    // Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // DaoAuthenticationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // AuthenticationManager para usar com login manual se necessário
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
