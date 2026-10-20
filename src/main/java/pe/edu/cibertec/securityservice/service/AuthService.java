package pe.edu.cibertec.securityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.cibertec.securityservice.dto.LoginRequest;
import pe.edu.cibertec.securityservice.dto.LoginResponse;
import pe.edu.cibertec.securityservice.dto.RegisterRequest;
import pe.edu.cibertec.securityservice.dto.UsuarioDTO;
import pe.edu.cibertec.securityservice.model.Usuario;
import pe.edu.cibertec.securityservice.repository.IUsuarioRepository;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioDTO register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setDireccion(request.getDireccion());
        usuario.setTelefono(request.getTelefono());
        usuario.setRol("USER");
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        return UsuarioDTO.from(usuarioRepository.save(usuario));
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Email o contraseña incorrectos"));
        return new LoginResponse(jwtService.generarToken(usuario), usuario.getId(), usuario.getRol());
    }
}
