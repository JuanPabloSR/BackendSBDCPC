package co.com.sofka.sbdcpc.mongo.causante;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "SBDCPC")
public class CausanteData {

    @Id
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
