package co.com.sofka.sbdcpc.mongo.convertidor;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.mongo.causante.CausanteData;

import java.util.List;
import java.util.stream.Collectors;

public class Convertidor {
    private Convertidor() {
        throw new IllegalStateException("Utility class");
    }

    public static CausanteData convertirCausanteACausanteData(Causante causante){
        CausanteData causanteData = new CausanteData();
        causanteData.setId(causante.getId());
        causanteData.setNombres(causante.getNombres());
        causanteData.setApellidos(causante.getApellidos());
        causanteData.setTipoDocumento(causante.getTipoDocumento());
        causanteData.setDocumentoCausante(causante.getDocumentoCausante());
        causanteData.setGenero(causante.getGenero());
        causanteData.setFechaNacimiento(causante.getFechaNacimiento());
        causanteData.setFondoPensiones(causante.getFondoPensiones());
        causanteData.setMesesCotizados(causante.getMesesCotizados());
        causanteData.setSalarioActual(causante.getSalarioActual());
        causanteData.setAfiliado(causante.isAfiliado());

        return causanteData;
    }

    public static Causante convertirCausanteDataACausante(CausanteData causanteData) {
        return Causante.builder()
                .id(causanteData.getId())
                .tipoDocumento(causanteData.getTipoDocumento())
                .documentoCausante(causanteData.getDocumentoCausante())
                .nombres(causanteData.getNombres())
                .apellidos(causanteData.getApellidos())
                .genero(causanteData.getGenero())
                .fechaNacimiento(causanteData.getFechaNacimiento())
                .fondoPensiones(causanteData.getFondoPensiones())
                .mesesCotizados(causanteData.getMesesCotizados())
                .salarioActual(causanteData.getSalarioActual())
                .afiliado(causanteData.isAfiliado())
                .build();
    }



    public static List<Causante> convertirCausantesDataACausantes(List<CausanteData> causantes) {
        return causantes.stream()
                .map(Convertidor::convertirCausanteDataACausante)
                .collect(Collectors.toList());
    }
}
