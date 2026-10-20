package pe.edu.cibertec.securityservice.service;

import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.cibertec.securityservice.dto.UsuarioDTO;
import pe.edu.cibertec.securityservice.model.Usuario;
import pe.edu.cibertec.securityservice.repository.IUsuarioRepository;
import pe.edu.cibertec.securityservice.restclient.movement.iclient.MovementClient;
import pe.edu.cibertec.securityservice.restclient.movement.model.OrdenResponse;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private static final Set<String> ROLES = Set.of("ADMIN", "USER");

    private final IUsuarioRepository usuarioRepository;
    private final MovementClient movementClient;

    public UsuarioDTO findById(Integer id) {
        return UsuarioDTO.from(getUsuario(id));
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioDTO::from)
                .toList();
    }

    public UsuarioDTO cambiarRol(Integer id, String rol) {
        String nuevoRol = rol == null ? "" : rol.toUpperCase();
        if (!ROLES.contains(nuevoRol)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol inválido, use ADMIN o USER");
        }
        Usuario usuario = getUsuario(id);
        usuario.setRol(nuevoRol);
        return UsuarioDTO.from(usuarioRepository.save(usuario));
    }

    public List<OrdenResponse> getCompras(Integer id) {
        getUsuario(id);
        try {
            return movementClient.getOrdenesByUsuario(id);
        } catch (RetryableException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "movement-service no disponible");
        }
    }

    private Usuario getUsuario(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }
}
