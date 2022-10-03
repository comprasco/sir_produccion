package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoAnotacionPk;

/**
 * Application identity objectid-class.
 */
    public class TipoAnotacionEnhancedPk implements java.io.Serializable {
        public String idTipoAnotacion;

        public TipoAnotacionEnhancedPk() {
        }
        
		public TipoAnotacionEnhancedPk(TipoAnotacionPk id){
			idTipoAnotacion = id.idTipoAnotacion;        	
		}
        
		public TipoAnotacionPk getTipoAnotacionID(){
			TipoAnotacionPk id = new TipoAnotacionPk();
			id.idTipoAnotacion  = idTipoAnotacion;
			return id;
		}             

        public TipoAnotacionEnhancedPk(String s) {
            int i;
            int p = 0;
            idTipoAnotacion = s.substring(p);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof TipoAnotacionEnhancedPk)) {
                return false;
            }

            final TipoAnotacionEnhancedPk id = (TipoAnotacionEnhancedPk) o;

            if ((this.idTipoAnotacion != null)
                    ? (!idTipoAnotacion.equals(id.idTipoAnotacion))
                        : (id.idTipoAnotacion != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idTipoAnotacion != null) ? idTipoAnotacion.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idTipoAnotacion);

            return buffer.toString();
        }
    }