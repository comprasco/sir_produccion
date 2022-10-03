package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.LlaveBloqueoPk;

/**
 * Application identity objectid-class.
 */
    public class LlaveBloqueoEnhancedPk implements java.io.Serializable {
        public String idLlave;

        /**
         * Crea una nueva llave de bloqueo id
         */        
        public LlaveBloqueoEnhancedPk() {
        }

        /**
         * Crea una nueva llave de bloqueo a partir de un string
         * @param s llave
         */        
        public LlaveBloqueoEnhancedPk(String s) {
            int i;
            int p = 0;
            idLlave = s.substring(p);
        }
        
        /**
         * Crea una llave de bloqueo a partir de una llave no enhanced.
         * @param id llave no enhanced
         */        
        public LlaveBloqueoEnhancedPk(LlaveBloqueoPk id){
            idLlave = id.idLlave;
        }
        
        public LlaveBloqueoPk getLlaveBloqueoID(){
            LlaveBloqueoPk id = new LlaveBloqueoPk(idLlave);
            return id;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof LlaveBloqueoEnhancedPk)) {
                return false;
            }

            final LlaveBloqueoEnhancedPk id = (LlaveBloqueoEnhancedPk) o;

            if ((this.idLlave != null) ? (!idLlave.equals(id.idLlave))
                                           : (id.idLlave != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idLlave != null) ? idLlave.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idLlave);

            return buffer.toString();
        }
    }