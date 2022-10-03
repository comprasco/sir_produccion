package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ZonaRegistralPk;

/**
 * Application identity objectid-class.
 */
    public class ZonaRegistralEnhancedPk implements java.io.Serializable {
        public String idZonaRegistral;

        public ZonaRegistralEnhancedPk() {
        }
        
  
		public ZonaRegistralEnhancedPk(ZonaRegistralPk id){
			idZonaRegistral = id.idZonaRegistral;
		}
                
		public ZonaRegistralPk getZonaRegistralID(){
			ZonaRegistralPk id = new ZonaRegistralPk();
			id.idZonaRegistral = idZonaRegistral;
			return id;
		}

        public ZonaRegistralEnhancedPk(String s) {
            int i;
            int p = 0;
            idZonaRegistral = s.substring(p);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof ZonaRegistralEnhancedPk)) {
                return false;
            }

            final ZonaRegistralEnhancedPk id = (ZonaRegistralEnhancedPk) o;

            if ((this.idZonaRegistral != null)
                    ? (!idZonaRegistral.equals(id.idZonaRegistral))
                        : (id.idZonaRegistral != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idZonaRegistral != null) ? idZonaRegistral.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idZonaRegistral);

            return buffer.toString();
        }
    }