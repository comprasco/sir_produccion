package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DocumentoFotocopiaPk;

/**
 * Application identity objectid-class.
 */
public class DocumentoFotocopiaEnhancedPk implements java.io.Serializable {

    public String idDocFotocopia;
    public String idSolicitud;

    public DocumentoFotocopiaEnhancedPk() {
    }
    
	public DocumentoFotocopiaEnhancedPk(DocumentoFotocopiaPk id){
		idDocFotocopia = id.idDocFotocopia;	
		idSolicitud = id.idSolicitud;
	}
	
	public DocumentoFotocopiaPk getDocumentoFotocopiaID(){
		DocumentoFotocopiaPk id = new DocumentoFotocopiaPk();
		id.idDocFotocopia = idDocFotocopia;
		id.idSolicitud = idSolicitud;
		return id;
	}

    public DocumentoFotocopiaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idDocFotocopia = s.substring(p, i);
        p = i + 1;
        idSolicitud = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentoFotocopiaEnhancedPk)) return false;

        final DocumentoFotocopiaEnhancedPk id = (DocumentoFotocopiaEnhancedPk) o;

        if (this.idDocFotocopia != null ? !idDocFotocopia.equals(id.idDocFotocopia) : id.idDocFotocopia != null) return false;
        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idDocFotocopia != null ? idDocFotocopia.hashCode() : 0);
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDocFotocopia);
        buffer.append('-');
        buffer.append(idSolicitud);
        return buffer.toString();
    }
}