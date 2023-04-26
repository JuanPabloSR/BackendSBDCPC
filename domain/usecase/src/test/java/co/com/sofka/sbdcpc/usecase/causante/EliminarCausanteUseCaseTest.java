package co.com.sofka.sbdcpc.usecase.causante;

import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import co.com.sofka.sbdcpc.model.exceptions.ExcepcionesPersonalizadas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EliminarCausanteUseCaseTest {
    private CausanteRepository causanteRepository;
    private EliminarCausanteUseCase eliminarCausanteUseCase;

    @BeforeEach
    void setUp() {
        causanteRepository = mock(CausanteRepository.class);
        eliminarCausanteUseCase = new EliminarCausanteUseCase(causanteRepository);
    }

    @Test
    void eliminarCausanteExistente() {
        // Arrange
        String id = "1";

        // Act
        when(causanteRepository.eliminarCausante(id)).thenReturn(Mono.empty());

        // Assert
        StepVerifier.create(eliminarCausanteUseCase.eliminarCausante(id))
                .expectError(ExcepcionesPersonalizadas.class)
                .verify();
    }

    @Test
    void eliminarCausanteNoExistente() {
        // Arrange
        String id = "1";

        // Act
        when(causanteRepository.eliminarCausante(id)).thenReturn(Mono.empty());

        // Assert
        StepVerifier.create(eliminarCausanteUseCase.eliminarCausante(id))
                .expectError(ExcepcionesPersonalizadas.class)
                .verify();
    }
}
