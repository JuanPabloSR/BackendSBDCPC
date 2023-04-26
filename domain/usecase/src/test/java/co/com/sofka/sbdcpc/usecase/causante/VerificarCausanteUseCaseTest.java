package co.com.sofka.sbdcpc.usecase.causante;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import co.com.sofka.sbdcpc.model.exceptions.ExcepcionesPersonalizadas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificarCausanteUseCaseTest {
    @Mock
    CausanteRepository causanteRepository;
    @InjectMocks
    VerificarCausanteUseCase verificarCausanteUseCase;

    @BeforeEach
    public void setUp() {
        causanteRepository = mock(CausanteRepository.class);
        verificarCausanteUseCase = new VerificarCausanteUseCase(causanteRepository);
    }

    @Test
    public void buscarCausante_Existente_True() {
        // Arrange
        String tipoDocumento = "CC";
        Integer documentoCausante = 123456;
        Causante causante = new Causante();
        when(causanteRepository.buscarPorDocumentoCausante(tipoDocumento, documentoCausante))
                .thenReturn(Mono.just(causante));

        // Act
        Mono<Boolean> resultado = verificarCausanteUseCase.buscarCausante(tipoDocumento, documentoCausante);

        // Assert
        StepVerifier.create(resultado)
                .expectNext(true)
                .verifyComplete();
        verify(causanteRepository, times(1)).buscarPorDocumentoCausante(tipoDocumento, documentoCausante);
    }

    @Test
    public void buscarCausante_NoExistente_Error() {
        // Arrange
        String tipoDocumento = "CC";
        Integer documentoCausante = 123456;
        when(causanteRepository.buscarPorDocumentoCausante(tipoDocumento, documentoCausante))
                .thenReturn(Mono.empty());

        // Act
        Mono<Boolean> resultado = verificarCausanteUseCase.buscarCausante(tipoDocumento, documentoCausante);

        // Assert
        StepVerifier.create(resultado)
                .expectError(ExcepcionesPersonalizadas.class)
                .verify();
        verify(causanteRepository, times(1)).buscarPorDocumentoCausante(tipoDocumento, documentoCausante);
    }

    @Test
    void verificarCausanteExistente() {
        // Arrange
        String tipoDocumento = "CC";
        Integer documentoCausante = 123456789;
        Causante causante = new Causante("1", "CC", 123456789, "Juan", "Perez", "MASCULINO", new Date(), "", 70, 1700000.0, false);

        Mockito.when(causanteRepository.buscarPorDocumentoCausante(tipoDocumento, documentoCausante)).thenReturn(Mono.just(causante));

        // Act
        Mono<Causante> result = verificarCausanteUseCase.verificarCausante(tipoDocumento, documentoCausante);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(causante1 -> causante1.getTipoDocumento().equals(tipoDocumento) && causante1.getDocumentoCausante().equals(documentoCausante))
                .verifyComplete();
    }

    @Test
    void verificarCausanteNoExistente() {
        // Arrange
        String tipoDocumento = "CC";
        Integer documentoCausante = 123456789;

        Mockito.when(causanteRepository.buscarPorDocumentoCausante(tipoDocumento, documentoCausante)).thenReturn(Mono.empty());

        // Act
        Mono<Causante> result = verificarCausanteUseCase.verificarCausante(tipoDocumento, documentoCausante);

        // Assert
        StepVerifier.create(result)
                .expectError(ExcepcionesPersonalizadas.class)
                .verify();
    }


}
