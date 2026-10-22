package pe.edu.cibertec.securityservice.restclient.movement.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class OrdenResponse {
    private Integer id;
    private String numero;
    private Date fechaCreacion;
    private Date fechaRecibida;
    private double total;
    private Integer usuarioId;
    private List<Detalle> detalle;

    @Setter
    @Getter
    public static class Detalle {
        private Integer id;
        private String nombre;
        private double cantidad;
        private double precio;
        private double total;
        private Integer productoId;
    }
}
