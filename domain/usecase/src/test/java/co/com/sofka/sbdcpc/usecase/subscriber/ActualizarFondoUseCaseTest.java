package co.com.sofka.sbdcpc.usecase.subscriber;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import co.com.sofka.sbdcpc.usecase.causante.VerificarCausanteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActualizarFondoUseCaseTest {

    @Mock
    CausanteRepository causanteRepository;
    @InjectMocks
    ActualizarFondoUseCase actualizarFondoUseCase;
    @Captor
    private ArgumentCaptor<Causante> causanteCaptor;

    @BeforeEach
    public void setUp() {
        causanteRepository = mock(CausanteRepository.class);
        actualizarFondoUseCase = new ActualizarFondoUseCase(causanteRepository);
    }

    @Test
    public void actualizarFondoPensionesTest() {
        // Arrange
        String tipoDocumento = "CC";
        Integer documentoCausante = 123;
        String fondoPensiones = "Porvenir";
        Boolean afiliado = true;

        Causante causante = new Causante();
        causante.setTipoDocumento(tipoDocumento);
        causante.setDocumentoCausante(documentoCausante);
        causante.setFondoPensiones("Arus");
        causante.setAfiliado(false);

        when(causanteRepository.buscarPorDocumentoCausante(tipoDocumento, documentoCausante))
                .thenReturn(Mono.just(causante));
        when(causanteRepository.guardarCausante(any(Causante.class))).thenReturn(Mono.just(causante));

        // Act
        Mono<Causante> result = actualizarFondoUseCase.actualizarFondoPensiones(tipoDocumento, documentoCausante, fondoPensiones, afiliado);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(updatedCausante -> {
                    return updatedCausante.getFondoPensiones().equals(fondoPensiones) &&
                            updatedCausante.isAfiliado() == afiliado;
                })
                .expectComplete()
                .verify();

        verify(causanteRepository, times(1)).buscarPorDocumentoCausante(tipoDocumento, documentoCausante);
        verify(causanteRepository, times(1)).guardarCausante(causanteCaptor.capture());
        Causante capturedCausante = causanteCaptor.getValue();
        assertEquals(fondoPensiones, capturedCausante.getFondoPensiones());
        assertEquals(afiliado, capturedCausante.isAfiliado());
    }
}
