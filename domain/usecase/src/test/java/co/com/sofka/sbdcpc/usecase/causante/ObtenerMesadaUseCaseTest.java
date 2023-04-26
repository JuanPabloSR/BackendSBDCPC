package co.com.sofka.sbdcpc.usecase.causante;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObtenerMesadaUseCaseTest {
    @Mock
    CausanteRepository causanteRepository;
    @InjectMocks
    ObtenerMesadaUseCase obtenerMesadaUseCase;

    @BeforeEach
    public void setUp(){
        causanteRepository = mock(CausanteRepository.class);
        obtenerMesadaUseCase = new ObtenerMesadaUseCase();
    }

    @Test
    void obtenerMesadaTest() {
        Double salario = 2000000.0;
        Integer meses = 45;
        Double mesadaEsperada = 1500000.0;

        Double mesadaCalculada = obtenerMesadaUseCase.obtenerMesada(salario, meses);

        assertEquals(mesadaEsperada, mesadaCalculada);

        // Caso 1: meses cotizados <= 36
        Double salario1 = 1000000.0;
        Integer meses1 = 30;
        Double resultadoEsperado1 = salario1 * 0.5;
        Double resultadoObtenido1 = obtenerMesadaUseCase.obtenerMesada(salario1, meses1);
        assertEquals(resultadoEsperado1, resultadoObtenido1);

        // Caso 2: 37 <= meses cotizados <= 60
        Double salario2 = 800000.0;
        Integer meses2 = 50;
        Double resultadoEsperado2 = salario2 * 0.75;
        Double resultadoObtenido2 = obtenerMesadaUseCase.obtenerMesada(salario2, meses2);
        assertEquals(resultadoEsperado2, resultadoObtenido2);

        // Caso 3: salario actual <= 1,500,000
        Double salario3 = 1200000.0;
        Integer meses3 = 70;
        Double resultadoEsperado3 = salario3 * 0.75 + (meses3 * 2000);
        Double resultadoObtenido3 = obtenerMesadaUseCase.obtenerMesada(salario3, meses3);
        assertEquals(resultadoEsperado3, resultadoObtenido3);

        // Caso 4: salario actual > 1,500,000
        Double salario4 = 2000000.0;
        Integer meses4 = 100;
        Double resultadoEsperado4 = salario4 * 0.8 + (meses4 * 1000);
        Double resultadoObtenido4 = obtenerMesadaUseCase.obtenerMesada(salario4, meses4);
        assertEquals(resultadoEsperado4, resultadoObtenido4);
    }


}
