package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


public class Consignacion implements TransferObject{

    private String idSolicitud; // pk
    private String idDocPago; // pk
    private SolicitudDevolucion solicitud; // inverse SolicitudDevolucionEnhanced.consignaciones
    private DocumentoPago docPago; 
    private static final long serialVersionUID = 1L;
    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    
    public String getIdDocPago() {
        return idDocPago;
    }

    public void setIdDocPago(String idDocPago) {
        this.idDocPago = idDocPago;
    }
    
    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudDevolucion solicitud) {
        this.solicitud = solicitud;
        setIdSolicitud(solicitud.getIdSolicitud());
    }
    
    public DocumentoPago getDocPago() {
        return docPago;
    }

    public void setDocPago(DocumentoPago docPago) {
        this.docPago = docPago;
        setIdDocPago(docPago.getIdDocumentoPago());
    }
    
}
