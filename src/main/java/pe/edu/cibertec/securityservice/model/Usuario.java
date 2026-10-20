package pe.edu.cibertec.securityservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    private String direccion;
    private String telefono;
    // ADMIN o USER
    @Column(nullable = false)
    private String rol;
    // encriptado con BCrypt
    @Column(nullable = false)
    private String password;
}
