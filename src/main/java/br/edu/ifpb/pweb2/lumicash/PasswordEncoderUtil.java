package br.edu.ifpb.pweb2.lumicash;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String senhaEmTextoPuro = "123456789"; 
        
        String senhaCriptografada = passwordEncoder.encode(senhaEmTextoPuro);

        System.out.println("====================================================================");
        System.out.println("Senha em texto puro: " + senhaEmTextoPuro);
        System.out.println("Senha criptografada (para colar no banco de dados):");
        System.out.println(senhaCriptografada);
        System.out.println("====================================================================");
    }
}