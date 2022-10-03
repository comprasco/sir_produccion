package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.BloqueoFolioPk;

/**
 * Application identity objectid-class.
 */
    public class BloqueoFolioEnhancedPk implements java.io.Serializable {
        public String idLlave;
        public String idMatricula;

        public BloqueoFolioEnhancedPk() {
        }

		public BloqueoFolioEnhancedPk(BloqueoFolioPk id){
			idLlave = id.idLlave;
			idMatricula = id.idMatricula;
		}
		
		public BloqueoFolioPk getBloqueoFolioID(){
			BloqueoFolioPk id = new BloqueoFolioPk();
			id.idLlave= idLlave;
			id.idMatricula = idMatricula;
			return id;
		}

        public BloqueoFolioEnhancedPk(String s) {
            int i;
            int p = 0;
            i = s.indexOf('-', p);
            idLlave = s.substring(p, i);
            p = i + 1;
            i = s.indexOf('-', p);
            idMatricula = s.substring(p, i);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof BloqueoFolioEnhancedPk)) {
                return false;
            }

            final BloqueoFolioEnhancedPk id = (BloqueoFolioEnhancedPk) o;

            if ((this.idLlave != null) ? (!idLlave.equals(id.idLlave))
                                           : (id.idLlave != null)) {
                return false;
            }

            if ((this.idMatricula != null)
                    ? (!idMatricula.equals(id.idMatricula))
                        : (id.idMatricula != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idLlave != null) ? idLlave.hashCode() : 0);
            result = (29 * result) +
                ((idMatricula != null) ? idMatricula.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idLlave);
            buffer.append('-');
            buffer.append(idMatricula);

            return buffer.toString();
        }
    }