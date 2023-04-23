package co.com.sofka.sbdcpc.model.causante.gateways;

import co.com.sofka.sbdcpc.model.causante.Causante;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CausanteRepository {

    Flux<Causante> listarCausantes();
    Mono<Causante> guardarCausante(Causante causante);
    Mono<Causante> buscarCausantePorId(String id);
    Mono<Causante> actualizarCausante(Causante causante);
    Mono<Causante> buscarPorDocumentoCausante(String tipoDocumento, Integer documentoCausante);
    Mono<Void> eliminarCausante(String id);
}
