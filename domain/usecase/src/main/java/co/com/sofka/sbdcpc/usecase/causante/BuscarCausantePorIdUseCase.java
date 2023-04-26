package co.com.sofka.sbdcpc.usecase.causante;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BuscarCausantePorIdUseCase {
    private final CausanteRepository causanteRepository;
    public Mono<Causante> buscarCausantePorId(String id){
        return causanteRepository.buscarCausantePorId(id);
    }
}
