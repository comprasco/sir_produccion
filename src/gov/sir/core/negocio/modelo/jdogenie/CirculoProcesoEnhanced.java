package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CirculoProceso;




/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class CirculoProcesoEnhanced extends Enhanced{

    private String anio; // pk 
    private String idCirculo; // pk 
    private long idProceso; // pk 
    private long lastIdTurno;
    private CirculoEnhanced circulo;
    private ProcesoEnhanced proceso;
    private long lastIdSolicitud;
	private long lastIdRepartoNotarial;
	
	/**
	* Secuencial para el manejo de constancias de pago de devolución. 
	*/
	private long secuencialConstDevolucion;
	
	private long secuencialConstMasivos;

    public CirculoProcesoEnhanced() {
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getIdCirculo() {
        return idCirculo;
    }

    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    public long getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(long idProceso) {
        this.idProceso = idProceso;
    }

    public long getLastIdTurno() {
        return lastIdTurno;
    }

    public void setLastIdTurno(long lastIdTurno) {
        this.lastIdTurno = lastIdTurno;
    }

    public CirculoEnhanced getCirculo() {
        return circulo;
    }

    public void setCirculo(CirculoEnhanced circulo) {
        this.circulo = circulo;
        setIdCirculo(circulo.getIdCirculo());
    }

    public ProcesoEnhanced getProceso() {
        return proceso;
    }

    public void setProceso(ProcesoEnhanced proceso) {
        this.proceso = proceso;
        setIdProceso(proceso.getIdProceso());
    }
    
	public static CirculoProcesoEnhanced enhance(CirculoProceso circuloProceso){
	   return (CirculoProcesoEnhanced)Enhanced.enhance(circuloProceso);
	}


    /**
	 * @return
	 */
	public long getLastIdSolicitud() {
		return lastIdSolicitud;
	}

	/**
	 * @param l
	 */
	public void setLastIdSolicitud(long l) {
		lastIdSolicitud = l;
	}

	/**
	 * @return
	 */
	public long getLastIdRepartoNotarial() {
		return lastIdRepartoNotarial;
	}

	/**
	 * @param l
	 */
	public void setLastIdRepartoNotarial(long l) {
		lastIdRepartoNotarial = l;
	}
	

	/**
	 * @return Retorna el secuencial de constancia de devolución.
	 */
	public long getSecuencialConstDevolucion() {
		return secuencialConstDevolucion;
	}


	/**
	 * @param l Nuevo secuencial que va a ser asignado a la constancia de devolución. 
	 */
	public void setSecuencialConstDevolucion(long secuencial) {
		secuencialConstDevolucion = secuencial;
	}

	/**
	 * @return Returns the secuencialConstMasivos.
	 */
	public long getSecuencialConstMasivos() {
		return secuencialConstMasivos;
	}
	/**
	 * @param secuencialConstMasivos The secuencialConstMasivos to set.
	 */
	public void setSecuencialConstMasivos(long secuencialConstMasivos) {
		this.secuencialConstMasivos = secuencialConstMasivos;
	}
}