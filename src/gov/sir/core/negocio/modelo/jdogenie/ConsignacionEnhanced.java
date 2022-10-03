package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Consignacion;

public class ConsignacionEnhanced extends Enhanced{

    private String idSolicitud; // pk
    private String idDocPago; // pk
    private SolicitudDevolucionEnhanced solicitud; // inverse SolicitudDevolucionEnhanced.consignaciones
    private DocumentoPagoEnhanced docPago; 
    
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

    public SolicitudDevolucionEnhanced getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudDevolucionEnhanced solicitud) {
        this.solicitud = solicitud;
        setIdSolicitud(solicitud.getIdSolicitud());
    }
    
    public DocumentoPagoEnhanced getDocPago() {
        return docPago;
    }

    public void setDocPago(DocumentoPagoEnhanced docPago) {
        this.docPago = docPago;
        setIdDocPago(docPago.getIdDocumentoPago());
    }
    
    public static ConsignacionEnhanced enhance(Consignacion x) {
		return (ConsignacionEnhanced) Enhanced.enhance(x);
	}

	
	
}
