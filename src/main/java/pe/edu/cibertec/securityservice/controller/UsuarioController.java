package pe.edu.cibertec.securityservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.cibertec.securityservice.dto.UsuarioDTO;
import pe.edu.cibertec.securityservice.restclient.movement.model.OrdenResponse;
import pe.edu.cibertec.securityservice.service.UsuarioService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
@RestController
public class UsuarioController {
    private final UsuarioService usuarioService;

    //localhost:8081/api/v1/usuarios  (solo ADMIN)
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    //localhost:8081/api/v1/usuarios/1  (lo usan los demás servicios por Feign)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id, @AuthenticationPrincipal Jwt jwt) {
        validarAcceso(id, jwt);
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    //localhost:8081/api/v1/usuarios/1/rol?rol=ADMIN  (solo ADMIN)
    @PutMapping("/{id}/rol")
    public ResponseEntity<UsuarioDTO> cambiarRol(@PathVariable Integer id, @RequestParam String rol) {
        return ResponseEntity.ok(usuarioService.cambiarRol(id, rol));
    }

    //localhost:8081/api/v1/usuarios/1/compras  (Feign -> movement-service)
    @GetMapping("/{id}/compras")
    public ResponseEntity<List<OrdenResponse>> getCompras(@PathVariable Integer id, @AuthenticationPrincipal Jwt jwt) {
        validarAcceso(id, jwt);
        return ResponseEntity.ok(usuarioService.getCompras(id));
    }

    // Un USER solo puede ver sus propios datos; un ADMIN puede ver los de cualquiera
    private void validarAcceso(Integer id, Jwt jwt) {
        boolean esAdmin = "ADMIN".equals(jwt.getClaimAsString("rol"));
        if (!esAdmin && !String.valueOf(id).equals(jwt.getSubject())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puede acceder a datos de otro usuario");
        }
    }
}
