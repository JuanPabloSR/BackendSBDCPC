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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GuardarCausanteUseCaseTest {

    @Mock
    CausanteRepository causanteRepository;
    @InjectMocks
    GuardarCausanteUseCase guardarCausanteUseCase;


    @BeforeEach
    public void setUp() {
        causanteRepository = mock(CausanteRepository.class);
        guardarCausanteUseCase = new GuardarCausanteUseCase(causanteRepository);
    }


    @Test
    public void validar() {
        // Arrange
        Causante causante =  new Causante("1", "CC", 123456, "Juan", "Perez", "MASCULINO", new Date(), "", 70, 1700000.0, false);

        when(causanteRepository.buscarPorDocumentoCausante(eq("CC"), eq(123456)))
                .thenReturn(Mono.empty());
        when(causanteRepository.guardarCausante(eq(causante)))
                .thenReturn(Mono.just(causante));

        // Act
        Mono<Causante> result = guardarCausanteUseCase.guardarCausante(causante);

        // Assert
        StepVerifier.create(result)
                .expectNext(causante)
                .verifyComplete();
    }

    @Test
    public void guardarCausanteTest() {
        // Arrange
        Causante causante =  new Causante("1", "CC", 123456, "Juan", "Perez", "MASCULINO", new Date(), "", 70, 1700000.0, false);

        when(causanteRepository.buscarPorDocumentoCausante("CC", 123456)).thenReturn(Mono.empty());
        when(causanteRepository.guardarCausante(causante)).thenReturn(Mono.just(causante));

        // Act
        Mono<Causante> result = guardarCausanteUseCase.guardarCausante(causante);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(savedCausante -> savedCausante.getNombres().equals("Juan"))
                .expectComplete()
                .verify();

        verify(causanteRepository, times(1)).buscarPorDocumentoCausante("CC", 123456);
        verify(causanteRepository, times(1)).guardarCausante(causante);
    }

    @Test
    public void guardarCausanteExistenteTest() {
        // Arrange
        Causante causante = new Causante();
        causante.setTipoDocumento("CC");
        causante.setDocumentoCausante(123);

        Causante causanteExistente = new Causante();
        causanteExistente.setTipoDocumento("CC");
        causanteExistente.setDocumentoCausante(123);
        causanteExistente.setNombres("Juan");

        when(causanteRepository.buscarPorDocumentoCausante("CC", 123)).thenReturn(Mono.just(causanteExistente));

        // Act
        Mono<Causante> result = guardarCausanteUseCase.guardarCausante(causante);

        // Assert
        StepVerifier.create(result)
                .expectError(ExcepcionesPersonalizadas.class)
                .verify();

        verify(causanteRepository, times(1)).buscarPorDocumentoCausante("CC", 123);
        verify(causanteRepository, never()).guardarCausante(any(Causante.class));
    }

}
