package co.com.sofka.sbdcpc.usecase.causante;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class ListarCausantesUseCase {
    private final CausanteRepository causanteRepository;
    public Flux<Causante> listarCausantes() {
        return causanteRepository.listarCausantes();
    }
}
