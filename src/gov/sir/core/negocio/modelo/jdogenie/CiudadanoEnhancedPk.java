package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CiudadanoPk;

/**
 * Application identity objectid-class.
 */
    public class CiudadanoEnhancedPk implements java.io.Serializable {
        public String idCiudadano;

        public CiudadanoEnhancedPk() {
        }
        
		public CiudadanoEnhancedPk(CiudadanoPk id){
			idCiudadano = id.idCiudadano;
		}
		
		public CiudadanoPk getCiudadanoID(){
			CiudadanoPk id = new CiudadanoPk();
			id.idCiudadano = idCiudadano;
			return id;
		}

        public CiudadanoEnhancedPk(String s) {
            int i;
            int p = 0;
            idCiudadano = s.substring(p);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof CiudadanoEnhancedPk)) {
                return false;
            }

            final CiudadanoEnhancedPk id = (CiudadanoEnhancedPk) o;

            if ((this.idCiudadano != null)
                    ? (!idCiudadano.equals(id.idCiudadano))
                        : (id.idCiudadano != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idCiudadano != null) ? idCiudadano.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idCiudadano);

            return buffer.toString();
        }
    }