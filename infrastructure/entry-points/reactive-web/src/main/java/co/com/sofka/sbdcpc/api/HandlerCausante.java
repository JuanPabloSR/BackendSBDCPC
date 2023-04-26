package co.com.sofka.sbdcpc.api;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.renta.Renta;
import co.com.sofka.sbdcpc.usecase.causante.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HandlerCausante {
    private final BuscarCausantePorIdUseCase buscarCausantePorIdUseCase;
    private final ListarCausantesUseCase listarCausantesUseCase;

    private final VerificarCausanteUseCase verificarCausanteUseCase;
    private final GuardarCausanteUseCase guardarCausanteUseCase;
    private final ActualizarCausanteUseCase actualizarCausanteUseCase;
    private final ObtenerMesadaUseCase obtenerMesadaUseCase;
    private final EliminarCausanteUseCase eliminarCausanteUseCase;
    public Mono<ServerResponse> buscarCausantePorId(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Causante> causanteMono = buscarCausantePorIdUseCase.buscarCausantePorId(id);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(causanteMono, Causante.class);
    }

    public Mono<ServerResponse> eliminarCausantePorId(ServerRequest request){
        String id =request.pathVariable("id");
        Mono<Void> causanteMono = eliminarCausanteUseCase.eliminarCausante(id);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(causanteMono, Causante.class);
    }


    public Mono<ServerResponse> listarCausantes() {
        Flux<Causante> causantesFlux = listarCausantesUseCase.listarCausantes();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(causantesFlux, Causante.class);
    }

    private Mono<Causante> buscarCausante(ServerRequest request) {
        String tipoDocumento = request.pathVariable("tipoDocumento");
        Integer documentoCausante = Integer.valueOf(request.pathVariable("documentoCausante"));
        return verificarCausanteUseCase.verificarCausante(tipoDocumento, documentoCausante);
    }
    public Mono<ServerResponse> buscarPorTipoDocumentoYNumeroDocumento(ServerRequest request) {
        Mono<Causante> causanteMono = buscarCausante(request);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(causanteMono, Causante.class);
    }

    public Mono<ServerResponse> datosRenta(ServerRequest request) {
        Mono<Causante> causanteMono = buscarCausante(request);
        Mono<Renta> rentaMono = causanteMono.map(causante -> new Renta(causante.getMesesCotizados(), causante.getSalarioActual()));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(rentaMono, Renta.class);
    }

    public Mono<ServerResponse> obtenerMesada(ServerRequest request) {
        Mono<Causante> causanteMono = buscarCausante(request);
        causanteMono.map(causante -> new Renta(causante.getMesesCotizados(), causante.getSalarioActual()));

        return causanteMono.flatMap(causante -> {
            Double salario = causante.getSalarioActual();
            Integer meses = causante.getMesesCotizados();
            Double mesadaBeneficiario = obtenerMesadaUseCase.obtenerMesada(salario, meses);

            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(mesadaBeneficiario), Double.class );
        });
    }


    public Mono<ServerResponse> validarCausante(ServerRequest request) {
        String tipoDocumento = request.pathVariable("tipoDocumento");
        Integer documentoCausante = Integer.valueOf(request.pathVariable("documentoCausante"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(verificarCausanteUseCase.buscarCausante(tipoDocumento, documentoCausante), Boolean.class);
    }


    public Mono<ServerResponse> guardarCausante(ServerRequest request) {
        Mono<Causante> causanteMono = request.bodyToMono(Causante.class);
        Mono<Causante> causanteGuardadoMono = causanteMono.flatMap(guardarCausanteUseCase::guardarCausante);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(causanteGuardadoMono, Causante.class);
    }

    public Mono<ServerResponse> actualizarCausante(ServerRequest request) {
        Mono<Causante> causanteMono = request.bodyToMono(Causante.class);
        Mono<Causante> causanteActualizadoMono = causanteMono.flatMap(actualizarCausanteUseCase::actualizarCausante);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(causanteActualizadoMono, Causante.class);
    }

}
