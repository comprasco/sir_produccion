package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.FaseAlerta;

import java.util.Date;

/*
 * Generated by Versant Open Access (version:3.2.6 (17 Nov 2004))
 */
public class FaseAlertaEnhanced extends Enhanced{

    private String idFase; // pk 
    private long idProceso; // pk 
    private Date fechaCreacion;
    private ProcesoEnhanced proceso;

    public FaseAlertaEnhanced() {
    }

    public String getIdFase() {
        return idFase;
    }

    public void setIdFase(String idFase) {
        this.idFase = idFase;
    }

    public long getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(long idProceso) {
        this.idProceso = idProceso;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public ProcesoEnhanced getProceso() {
        return proceso;
    }

    public void setProceso(ProcesoEnhanced proceso) {
        this.proceso = proceso;
        setIdProceso(proceso.getIdProceso());
    }
    

	public static FaseAlertaEnhanced enhance(FaseAlerta estado){
		return (FaseAlertaEnhanced) Enhanced.enhance(estado);
	}
}
