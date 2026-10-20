package pe.edu.cibertec.securityservice.restclient.movement.iclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.cibertec.securityservice.restclient.config.FeignConfig;
import pe.edu.cibertec.securityservice.restclient.movement.model.OrdenResponse;

import java.util.List;

@FeignClient(name = "movement-service",
        url = "${movement.service.url}",
        configuration = FeignConfig.class
)
public interface MovementClient {

    @GetMapping("/api/v1/movimientos/usuario/{usuarioId}")
    List<OrdenResponse> getOrdenesByUsuario(@PathVariable("usuarioId") Integer usuarioId);

}
