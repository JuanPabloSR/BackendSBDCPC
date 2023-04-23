package co.com.sofka.sbdcpc.usecase.causante;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import co.com.sofka.sbdcpc.model.exceptions.ExcepcionesPersonalizadas;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GuardarCausanteUseCase {
    private final CausanteRepository causanteRepository;
    public Mono<Causante> guardarCausante(Causante causante) {
        return causanteRepository.buscarPorDocumentoCausante(causante.getTipoDocumento(), causante.getDocumentoCausante())
                .flatMap(error -> Mono.error(new ExcepcionesPersonalizadas(ExcepcionesPersonalizadas.Type.CAUSANTE_YA_EXISTE)))
                .switchIfEmpty(causanteRepository.guardarCausante(causante)).thenReturn(causante);
    }


}
