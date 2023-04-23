package co.com.sofka.sbdcpc.usecase.causante;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import co.com.sofka.sbdcpc.model.exceptions.ExcepcionesPersonalizadas;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class VerificarCausanteUseCase {
    private final CausanteRepository causanteRepository;
    public Mono<Causante> verificarCausante(String tipoDocumento, Integer documentoCausante){
        return causanteRepository.buscarPorDocumentoCausante(tipoDocumento, documentoCausante)
                .switchIfEmpty(Mono.error(new ExcepcionesPersonalizadas(ExcepcionesPersonalizadas.Type.DOCUMENTO_NO_EXISTE)));
    }
    public Mono<Boolean> buscarCausante(String tipoDocumento, Integer documentoCausante){
        return causanteRepository.buscarPorDocumentoCausante(tipoDocumento, documentoCausante)
                .switchIfEmpty(Mono.error(new ExcepcionesPersonalizadas(ExcepcionesPersonalizadas.Type.DOCUMENTO_NO_EXISTE))).thenReturn(true);
    }
}
