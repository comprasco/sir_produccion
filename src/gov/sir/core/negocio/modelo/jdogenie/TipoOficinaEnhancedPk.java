package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoOficinaPk;

/**
 * Application identity objectid-class.
 */
    public class TipoOficinaEnhancedPk implements java.io.Serializable {
        public String idTipoOficina;

        public TipoOficinaEnhancedPk() {
        }
        
        public TipoOficinaEnhancedPk (TipoOficinaPk id)
        {
        	idTipoOficina = id.idTipoOficina;
        }

        public TipoOficinaEnhancedPk(String s) {
            int i;
            int p = 0;
            idTipoOficina = s.substring(p);
        }
        
        public TipoOficinaPk getTipoOficinaID()
        {
			TipoOficinaPk id = new TipoOficinaPk();
			id.idTipoOficina = idTipoOficina;
			return id;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof TipoOficinaEnhancedPk)) {
                return false;
            }

            final TipoOficinaEnhancedPk id = (TipoOficinaEnhancedPk) o;

            if ((this.idTipoOficina != null)
                    ? (!idTipoOficina.equals(id.idTipoOficina))
                        : (id.idTipoOficina != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idTipoOficina != null) ? idTipoOficina.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idTipoOficina);

            return buffer.toString();
        }
    }