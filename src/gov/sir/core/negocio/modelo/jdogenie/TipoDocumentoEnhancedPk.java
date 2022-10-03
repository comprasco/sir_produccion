package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoDocumentoPk;

/**
 * Application identity objectid-class.
 */
    public class TipoDocumentoEnhancedPk implements java.io.Serializable {
        public String idTipoDocumento;

        public TipoDocumentoEnhancedPk() {
        }
        
        public TipoDocumentoEnhancedPk (TipoDocumentoPk id)
        {
        	idTipoDocumento = id.idTipoDocumento;
        }

        public TipoDocumentoEnhancedPk(String s) {
            int i;
            int p = 0;
            idTipoDocumento = s.substring(p);
        }
        
        public TipoDocumentoPk getTipoDocumentoID()
        {
			TipoDocumentoPk id = new TipoDocumentoPk();
			id.idTipoDocumento = idTipoDocumento;
			return id;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof TipoDocumentoEnhancedPk)) {
                return false;
            }

            final TipoDocumentoEnhancedPk id = (TipoDocumentoEnhancedPk) o;

            if ((this.idTipoDocumento != null)
                    ? (!idTipoDocumento.equals(id.idTipoDocumento))
                        : (id.idTipoDocumento != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idTipoDocumento != null) ? idTipoDocumento.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idTipoDocumento);

            return buffer.toString();
        }
    }