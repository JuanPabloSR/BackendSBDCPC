package co.com.sofka.sbdcpc.usecase.causante;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import co.com.sofka.sbdcpc.model.exceptions.ExcepcionesPersonalizadas;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ActualizarCausanteUseCase {
    private final CausanteRepository causanteRepository;
    public Mono<Causante> actualizarCausante(Causante causante){
        return causanteRepository.actualizarCausante(causante)
                .switchIfEmpty(Mono.error(new ExcepcionesPersonalizadas(ExcepcionesPersonalizadas.Type.CAUSANTE_NO_EXISTE)));

    }
}
