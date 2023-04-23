package co.com.sofka.sbdcpc.model.renta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Renta {
    private Integer mesesCotizados;
    private Double salarioActual;
}
