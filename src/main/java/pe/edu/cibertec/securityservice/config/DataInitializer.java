package pe.edu.cibertec.securityservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pe.edu.cibertec.securityservice.model.Usuario;
import pe.edu.cibertec.securityservice.repository.IUsuarioRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (usuarioRepository.existsByEmail(adminEmail)) {
            return;
        }
        Usuario admin = new Usuario();
        admin.setNombre("Administrador");
        admin.setUsername("admin");
        admin.setEmail(adminEmail);
        admin.setRol("ADMIN");
        admin.setPassword(passwordEncoder.encode(adminPassword));
        usuarioRepository.save(admin);
        log.info("Usuario ADMIN creado: {}", adminEmail);
    }
}
