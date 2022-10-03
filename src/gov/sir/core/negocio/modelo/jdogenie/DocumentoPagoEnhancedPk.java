/**
 * 
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocumentoPagoPk;

public class DocumentoPagoEnhancedPk implements java.io.Serializable {
    public String idDocumentoPago;

    public DocumentoPagoEnhancedPk() {
    }
	
	public DocumentoPagoEnhancedPk(DocumentoPagoPk id){
		idDocumentoPago = id.idDocumentoPago;
	}
	
	public DocumentoPagoPk getDocumentoPagoID(){
		DocumentoPagoPk id = new DocumentoPagoPk();
		id.idDocumentoPago = idDocumentoPago;
		return id;
	}
	
    public DocumentoPagoEnhancedPk(String s) {
        int i;
        int p = 0;
        idDocumentoPago = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DocumentoPagoEnhancedPk)) {
            return false;
        }

        final DocumentoPagoEnhancedPk id = (DocumentoPagoEnhancedPk) o;

        if ((this.idDocumentoPago != null)
                ? (!idDocumentoPago.equals(id.idDocumentoPago))
                    : (id.idDocumentoPago != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idDocumentoPago != null) ? idDocumentoPago.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDocumentoPago);

        return buffer.toString();
    }
}