package co.com.sofka.sbdcpc.api;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class RouterRestCausante {
    @Bean
    public WebProperties.Resources resources(){
        return new WebProperties.Resources();
    }
@Bean
public RouterFunction<ServerResponse> routerFunction(HandlerCausante handler) {
    return route(GET("/api/listarCausantes"), request -> handler.listarCausantes()).andRoute
            (GET("/api/buscarCausanteId/{id}"), handler::buscarCausantePorId).andRoute
            (GET("/api/validarCausante/{tipoDocumento}/{documentoCausante}"), handler::validarCausante).andRoute
            (GET("/api/renta/{tipoDocumento}/{documentoCausante}"), handler::datosRenta).andRoute
            (GET("/api/mesada/{tipoDocumento}/{documentoCausante}"), handler::obtenerMesada).andRoute
            (GET("/api/buscarPorDocumento/{tipoDocumento}/{documentoCausante}"), handler::buscarPorTipoDocumentoYNumeroDocumento).andRoute
            (DELETE("/api/eliminarCausante/{id}"), handler::eliminarCausantePorId).andRoute
            (POST("/api/crearCausante"), handler::guardarCausante).and
            (route(PUT("/api/ActualizarCausante"), handler::actualizarCausante));
    }
}
