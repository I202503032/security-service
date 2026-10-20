package pe.edu.cibertec.securityservice.dto;

import lombok.Getter;
import lombok.Setter;
import pe.edu.cibertec.securityservice.model.Usuario;

// Lo que ven los demás microservicios (nunca se expone el password)
@Setter
@Getter
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String username;
    private String email;
    private String direccion;
    private String telefono;
    private String rol;

    public static UsuarioDTO from(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setDireccion(usuario.getDireccion());
        dto.setTelefono(usuario.getTelefono());
        dto.setRol(usuario.getRol());
        return dto;
    }
}
