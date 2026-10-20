package pe.edu.cibertec.securityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {
    private String token;
    private Integer id;
    private String rol;
}
