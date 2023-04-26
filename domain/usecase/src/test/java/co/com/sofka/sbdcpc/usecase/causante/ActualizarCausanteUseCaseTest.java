package co.com.sofka.sbdcpc.usecase.causante;
import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import co.com.sofka.sbdcpc.model.exceptions.ExcepcionesPersonalizadas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActualizarCausanteUseCaseTest {


    @Mock
    CausanteRepository causanteRepository;
    @InjectMocks
    ActualizarCausanteUseCase actualizarCausanteUseCase;

    @BeforeEach
    public void setUp(){
        causanteRepository = mock(CausanteRepository.class);
        actualizarCausanteUseCase = new ActualizarCausanteUseCase(causanteRepository);
    }
    @Test
    void actualizarCausanteExistente() {
        // Arrange
        Causante causante = new Causante("1", "CC", 123456, "Juan", "Perez", "MASCULINO", new Date(), "", 70, 1700000.0, false);
        when(causanteRepository.actualizarCausante(causante)).thenReturn(Mono.just(causante));
         Mono<Causante> causanteMono = Mono.just(causante);

        when(causanteRepository.guardarCausante(Mockito.any(Causante.class))).thenReturn(causanteMono);

        // Act
        Mono<Causante> resultado = actualizarCausanteUseCase.actualizarCausante(causante);

        // Assert
        StepVerifier.create(resultado)
                .expectNextMatches(causante1 -> causante1.equals(causante))
                .verifyComplete();
    }

}
