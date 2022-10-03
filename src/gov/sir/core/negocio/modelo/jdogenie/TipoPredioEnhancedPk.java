package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoPredioPk;

/**
 * Application identity objectid-class.
 */
    public class TipoPredioEnhancedPk implements java.io.Serializable {
        public String idPredio;

        public TipoPredioEnhancedPk() {
        }
        
        public TipoPredioEnhancedPk (TipoPredioPk id)
        {
        	idPredio = id.idPredio;
        }

        public TipoPredioEnhancedPk(String s) {
            int i;
            int p = 0;
            idPredio = s.substring(p);
        }
        
        public TipoPredioPk getTipoPredioID()
        {
			TipoPredioPk id = new TipoPredioPk();
			id.idPredio = idPredio;
			return id;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof TipoPredioEnhancedPk)) {
                return false;
            }

            final TipoPredioEnhancedPk id = (TipoPredioEnhancedPk) o;

            if ((this.idPredio != null) ? (!idPredio.equals(id.idPredio))
                                            : (id.idPredio != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idPredio != null) ? idPredio.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idPredio);

            return buffer.toString();
        }
    }