package co.com.sofka.sbdcpc.mongo;

import co.com.sofka.sbdcpc.model.causante.Causante;
import co.com.sofka.sbdcpc.model.causante.gateways.CausanteRepository;
import co.com.sofka.sbdcpc.model.exceptions.ExcepcionesPersonalizadas;
import co.com.sofka.sbdcpc.mongo.causante.CausanteData;
import co.com.sofka.sbdcpc.mongo.convertidor.Convertidor;
import co.com.sofka.sbdcpc.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static co.com.sofka.sbdcpc.mongo.convertidor.Convertidor.*;

@Repository
public class MongoCausanteRepositoryAdapter extends AdapterOperations<Causante, CausanteData, String, MongoDBCausanteRepository> implements CausanteRepository{
    public MongoCausanteRepositoryAdapter(MongoDBCausanteRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Causante.class));
    }

    @Override
    public Flux<Causante> listarCausantes() {
        return repository.findAll()
                .collectList()
                .flatMapMany(causantes -> {
                    if (!causantes.isEmpty()) {
                        return Flux.fromIterable(convertirCausantesDataACausantes(causantes));
                    } else {
                        return Flux.error(new ExcepcionesPersonalizadas(ExcepcionesPersonalizadas.Type.LISTA_CAUSANTES_NO_EXISTE));
                    }
                });
    }


    @Override
    public Mono<Causante> guardarCausante(Causante causante) {
        if(causante != null){
            CausanteData nuevoCausanteData = convertirCausanteACausanteData(causante);
            return repository.save(nuevoCausanteData)
                    .map(Convertidor::convertirCausanteDataACausante);
        } else {
            return Mono.just(causante);
        }
    }


    @Override
    public Mono<Causante> buscarCausantePorId(String id) {
        return repository.findById(id)
                .map(Convertidor::convertirCausanteDataACausante)
                .switchIfEmpty(Mono.error(new ExcepcionesPersonalizadas(ExcepcionesPersonalizadas.Type.CAUSANTE_NO_EXISTE)));
    }


//    @Override
//    public Mono<Causante> actualizarCausante(Causante causante) {
//        if (causante != null && causante.getId() != null) {
//            String id = causante.getId();
//            CausanteData causanteData = convertirCausanteACausanteData(causante);
//            causanteData.setId(id);
//            return repository.findById(id)
//                    .flatMap(existingData -> {
//                        if(!causanteData.getId().equals(existingData.getId())) {
//                            return Mono.error(new ExcepcionesPersonalizadas(ExcepcionesPersonalizadas.Type.CAUSANTES_NO_ACTUALIZADO));
//                        }
//                        causanteData.setId(id);
//                        return repository.save(causanteData)
//                                .map(Convertidor::convertirCausanteDataACausante);
//                    });
//        } else {
//            return Mono.error((new ExcepcionesPersonalizadas(ExcepcionesPersonalizadas.Type.CAUSANTE_NO_EXISTE)));
//        }
//    }

    @Override
    public Mono<Causante> actualizarCausante(Causante causante) {
        String id = causante.getId();
        CausanteData causanteData = convertirCausanteACausanteData(causante);
        causanteData.setId(id);
        return repository.findById(id)
                .flatMap(existingData -> {
                    if(!causanteData.getId().equals(existingData.getId())) {
                        return Mono.error(new ExcepcionesPersonalizadas(ExcepcionesPersonalizadas.Type.CAUSANTES_NO_ACTUALIZADO));
                    }
                    causanteData.setId(id);
                    return repository.save(causanteData)
                            .map(Convertidor::convertirCausanteDataACausante);
                });
    }




    @Override
    public Mono<Causante> buscarPorDocumentoCausante(String tipoDocumento, Integer documentoCausante) {
        return repository.buscarCausante(tipoDocumento, documentoCausante)
                .map(Convertidor::convertirCausanteDataACausante);
    }

    @Override
    public Mono<Void> eliminarCausante(String id) {
        return repository.deleteById(id)
                .then();
    }

}
