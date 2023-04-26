package co.com.sofka.sbdcpc.usecase.subscriber;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ActualizarFondoUseCase {
    private final CausanteRepository causanteRepository;
    public Mono<Causante> actualizarFondoPensiones(String tipoDocumento, Integer documentoCausante, String fondoPensiones, Boolean afiliado) {
        var nuevoFondo = fondoPensiones;
        var nuevaAfiliacion = afiliado;
        return causanteRepository.buscarPorDocumentoCausante(tipoDocumento, documentoCausante)
                .flatMap(causante -> {
                    causante.setFondoPensiones(nuevoFondo);
                    causante.setAfiliado(nuevaAfiliacion);
                    return causanteRepository.guardarCausante(causante);
                });
    }
}
