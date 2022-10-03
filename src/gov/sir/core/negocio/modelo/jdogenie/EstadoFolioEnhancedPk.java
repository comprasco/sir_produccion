package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.EstadoFolioPk;

/**
 * Application identity objectid-class.
 */
    public class EstadoFolioEnhancedPk implements java.io.Serializable {
        public String idEstado;

        public EstadoFolioEnhancedPk() {
        }

        public EstadoFolioEnhancedPk(String s) {
            int i;
            int p = 0;
            idEstado = s.substring(p);
        }

		public EstadoFolioEnhancedPk(EstadoFolioPk id){
			idEstado = id.idEstado;
		}
		
		public EstadoFolioPk getEstadoFolioID(){
			EstadoFolioPk id = new EstadoFolioPk();
			id.idEstado = idEstado;
			return id;
		}

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof EstadoFolioEnhancedPk)) {
                return false;
            }

            final EstadoFolioEnhancedPk id = (EstadoFolioEnhancedPk) o;

            if ((this.idEstado != null) ? (!idEstado.equals(id.idEstado))
                                            : (id.idEstado != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idEstado != null) ? idEstado.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idEstado);

            return buffer.toString();
        }
    }