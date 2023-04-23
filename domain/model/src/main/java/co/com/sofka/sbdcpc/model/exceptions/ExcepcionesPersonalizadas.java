package co.com.sofka.sbdcpc.model.exceptions;

import java.util.function.Supplier;

public class ExcepcionesPersonalizadas extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public enum Type {

        CAUSANTE_NO_EXISTE("El causante o su iD no existe"),
        DOCUMENTO_NO_EXISTE("El tipo de documento o su numero no coincide con ningun Causante"),
        LISTA_CAUSANTES_NO_EXISTE("No hay ningun registro de causantes"),
        CAUSANTES_NO_ACTUALIZADO("El ID del causante no puede ser modificado"),
        CAUSANTE_YA_EXISTE("El causante ya existe");

        private final String message;

        public String getMessage() {
            return message;
        }

        public ExcepcionesPersonalizadas build() {
            return new ExcepcionesPersonalizadas(this);
        }

        public Supplier<Throwable> defer() {
            return () -> new ExcepcionesPersonalizadas(this);
        }

        Type(String message) {
            this.message = message;
        }

    }

    private final Type type;

    public ExcepcionesPersonalizadas(Type type){
        super(type.message);
        this.type = type;
    }

    public ExcepcionesPersonalizadas(Type type,String menssage){
        super(menssage);
        this.type = type;
    }

    @Override
    public String getCode(){
        return type.name();
    }


}
