package co.com.sofka.sbdcpc.usecase.causante;


import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import co.com.sofka.sbdcpc.model.exceptions.ExcepcionesPersonalizadas;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class EliminarCausanteUseCase {
    private final CausanteRepository causanteRepository;

    public Mono<Void> eliminarCausante(String id) {
        return causanteRepository.eliminarCausante(id)
                .switchIfEmpty(Mono.error(new ExcepcionesPersonalizadas(ExcepcionesPersonalizadas.Type.CAUSANTE_NO_EXISTE)));

    }

}
