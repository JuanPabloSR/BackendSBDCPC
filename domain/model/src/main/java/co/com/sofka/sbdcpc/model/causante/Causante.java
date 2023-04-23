package co.com.sofka.sbdcpc.model.causante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Causante {
    private String id;
    private String tipoDocumento;
    private Integer documentoCausante;
    private String nombres;
    private String apellidos;
    private String genero;
    private Date fechaNacimiento;
    private String fondoPensiones;
    private Integer mesesCotizados;
    private Double salarioActual;
    private boolean afiliado;

}
