package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocumentoPk;

/**
 * Application identity objectid-class.
 */
    public class DocumentoEnhancedPk implements java.io.Serializable {
        public String idDocumento;

        public DocumentoEnhancedPk() {
        }
		public DocumentoEnhancedPk(DocumentoPk id){
			idDocumento = id.idDocumento;	
		}
		
		public DocumentoPk getDocumentoID(){
			DocumentoPk id = new DocumentoPk();
			id.idDocumento = idDocumento;
			return id;
		}
		
        public DocumentoEnhancedPk(String s) {
            int i;
            int p = 0;
            idDocumento = s.substring(p);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof DocumentoEnhancedPk)) {
                return false;
            }

            final DocumentoEnhancedPk id = (DocumentoEnhancedPk) o;

            if ((this.idDocumento != null)
                    ? (!idDocumento.equals(id.idDocumento))
                        : (id.idDocumento != null)) {
                return false;
            }

            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idDocumento != null) ? idDocumento.hashCode() : 0);

            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idDocumento);

            return buffer.toString();
        }
    }