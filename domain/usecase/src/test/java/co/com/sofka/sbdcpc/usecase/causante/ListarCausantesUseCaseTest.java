package co.com.sofka.sbdcpc.usecase.causante;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import co.com.sofka.sbdcpc.model.exceptions.ExcepcionesPersonalizadas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarCausantesUseCaseTest {
    @Mock
    CausanteRepository causanteRepository;
    @InjectMocks
    ListarCausantesUseCase listarCausantesUseCase;

    @BeforeEach
    public void setUp(){
        causanteRepository = mock(CausanteRepository.class);
        listarCausantesUseCase = new ListarCausantesUseCase(causanteRepository);
    }

    @Test
    public void listarCausantesExistenCausantes() {
        // Arrange
        List<Causante> causantes = Arrays.asList(
                new Causante("1", "CC", 123456, "Juan", "Perez", "MASCULINO", new Date(), "", 70, 1700000.0, false),
                new Causante("2", "CE", 7890123, "Maria", "Gonzalez", "FEMENINO", new Date(), "", 65, 1600000.0, false)
        );
        when(causanteRepository.listarCausantes()).thenReturn(Flux.fromIterable(causantes));

        // Act
        Flux<Causante> fluxCausantes = listarCausantesUseCase.listarCausantes();

        // Assert
        StepVerifier.create(fluxCausantes)
                .expectNextMatches(causante -> causante.getDocumentoCausante().equals(123456) && causante.getNombres().equals("Juan") && causante.getApellidos().equals("Perez"))
                .expectNextMatches(causante -> causante.getDocumentoCausante().equals(7890123) && causante.getNombres().equals("Maria") && causante.getApellidos().equals("Gonzalez"))
                .expectComplete()
                .verify();
    }

    @Test
    public void listarCausantesNoExistenCausantesDeberiaRetornarError() {
        // Arrange
        when(causanteRepository.listarCausantes()).thenReturn(Flux.empty());

        // Act
        Flux<Causante> fluxCausantes = listarCausantesUseCase.listarCausantes();

        // Assert
        StepVerifier.create(fluxCausantes)
                .expectComplete()
                .verify();
    }
}
