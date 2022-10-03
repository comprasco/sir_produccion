/**
 * 
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocumentoPagoCorreccionPk;

public class DocumentoPagoCorreccionEnhancedPk implements java.io.Serializable {
    public String idDocumentoPagoCorreccion;

    public DocumentoPagoCorreccionEnhancedPk() {
    }
	
	public DocumentoPagoCorreccionEnhancedPk(DocumentoPagoCorreccionPk id){
		idDocumentoPagoCorreccion = id.idDocumentoPagoCorreccion;
	}
	
	public DocumentoPagoCorreccionPk getDocumentoPagoID(){
		DocumentoPagoCorreccionPk id = new DocumentoPagoCorreccionPk();
		id.idDocumentoPagoCorreccion = idDocumentoPagoCorreccion;
		return id;
	}
	
    public DocumentoPagoCorreccionEnhancedPk(String s) {
        int i;
        int p = 0;
        idDocumentoPagoCorreccion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DocumentoPagoEnhancedPk)) {
            return false;
        }

        final DocumentoPagoEnhancedPk id = (DocumentoPagoEnhancedPk) o;

        if ((this.idDocumentoPagoCorreccion != null)
                ? (!idDocumentoPagoCorreccion.equals(id.idDocumentoPago))
                    : (id.idDocumentoPago != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idDocumentoPagoCorreccion != null) ? idDocumentoPagoCorreccion.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDocumentoPagoCorreccion);

        return buffer.toString();
    }
}