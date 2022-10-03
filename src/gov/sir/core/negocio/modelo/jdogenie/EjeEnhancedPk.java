package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.EjePk;

/**
 * Application identity objectid-class.
 */
    public class EjeEnhancedPk implements java.io.Serializable {
        public String idEje;

        public EjeEnhancedPk() {
        }

		public EjeEnhancedPk(EjePk id){
			idEje = id.idEje;
		}
		
		public EjePk getEjeID(){
			EjePk id = new EjePk();
			id.idEje = idEje;
			return id;
		}

        public EjeEnhancedPk(String s) {
            int i;
            int p = 0;
            idEje = s.substring(p);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof EjeEnhancedPk)) {
                return false;
            }

            final EjeEnhancedPk id = (EjeEnhancedPk) o;

            if ((this.idEje != null) ? (!idEje.equals(id.idEje))
                                         : (id.idEje != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) + ((idEje != null) ? idEje.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idEje);

            return buffer.toString();
        }
    }