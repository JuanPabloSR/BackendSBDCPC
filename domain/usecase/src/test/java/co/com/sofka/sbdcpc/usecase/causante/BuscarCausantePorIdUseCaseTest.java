package co.com.sofka.sbdcpc.usecase.causante;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuscarCausantePorIdUseCaseTest {
    private CausanteRepository causanteRepository;
    private BuscarCausantePorIdUseCase buscarCausantePorIdUseCase;

    @BeforeEach
    void setUp() {
        causanteRepository = mock(CausanteRepository.class);
        buscarCausantePorIdUseCase = new BuscarCausantePorIdUseCase(causanteRepository);
    }

    @Test
    void buscarCausantePorIdExistente() {
        // Arrange
        String id = "1";
        Date fecha = new Date(1999, 3, 30);
        Causante causante= new Causante("1", "CC", 123456, "Juan", "Perez", "MASCULINO", new Date(), "", 70, 1700000.0, false);

        // Act
        when(causanteRepository.buscarCausantePorId(id)).thenReturn(Mono.just(causante));

        // Assert
        StepVerifier.create(buscarCausantePorIdUseCase.buscarCausantePorId(id))
                .expectNextMatches(causante1 -> causante1.equals(causante))
                .verifyComplete();
    }

    @Test
    void buscarCausantePorIdNoExistente() {
        // Arrange
        String id = "1";
        // Act
        when(causanteRepository.buscarCausantePorId(id)).thenReturn(Mono.empty());
        // Assert
        StepVerifier.create(buscarCausantePorIdUseCase.buscarCausantePorId(id))
                .expectComplete()
                .verify();
    }
}