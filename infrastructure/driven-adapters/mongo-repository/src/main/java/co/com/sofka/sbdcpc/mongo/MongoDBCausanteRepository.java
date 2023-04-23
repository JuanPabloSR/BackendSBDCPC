package co.com.sofka.sbdcpc.mongo;

import co.com.sofka.sbdcpc.mongo.causante.CausanteData;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;


public interface MongoDBCausanteRepository extends ReactiveMongoRepository<CausanteData, String>, ReactiveQueryByExampleExecutor<CausanteData> {

    @Query("{'tipoDocumento': ?0, 'documentoCausante': ?1}")
    Mono<CausanteData> buscarCausante(String tipoDocumento, Integer documentoCausante);

}
