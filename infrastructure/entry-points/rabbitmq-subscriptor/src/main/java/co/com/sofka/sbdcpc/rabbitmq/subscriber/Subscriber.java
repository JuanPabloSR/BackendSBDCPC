package co.com.sofka.sbdcpc.rabbitmq.subscriber;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import co.com.sofka.sbdcpc.usecase.causante.ActualizarCausanteUseCase;
import co.com.sofka.sbdcpc.usecase.subscriber.ActualizarFondoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.async.api.HandlerRegistry;
import org.reactivecommons.async.impl.config.annotations.EnableCommandListeners;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Log
@Configuration
@EnableCommandListeners
@RequiredArgsConstructor

public class Subscriber {
    static final String CAUSANTE_PUBLICADO = "causante_publicado";

    private final ActualizarFondoUseCase actualizarFondoUseCase;
    @Bean
    public HandlerRegistry comandoSuscriptor(){
        return HandlerRegistry.register().handleCommand(CAUSANTE_PUBLICADO,
                p->{
                    Causante causante = p.getData();
                    actualizarFondoUseCase.actualizarFondoPensiones(causante.getTipoDocumento(),causante.getDocumentoCausante(),causante.getFondoPensiones(),causante.isAfiliado()).subscribe();
                    return Mono.empty();

                }
                ,Causante.class);
    }


}
