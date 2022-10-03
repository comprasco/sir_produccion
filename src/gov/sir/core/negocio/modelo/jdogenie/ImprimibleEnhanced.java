package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Imprimible;

import java.util.Date;

/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public class ImprimibleEnhanced  extends Enhanced{

    private int idImprimible; // pk
    private byte[] datosImprimible;
    private Date fechaCreacion;
	private String UID;
    private String IP;
    private String turno;
    private int numeroBytes;
    private String tipoImprimible;
    private String folio;
    private String circulo;
    private int numeroImpresiones;
    private boolean imprimibleExtenso;
    
	public String getUID() {
		return UID;
	}

	public void setUID(String uid) {
		UID = uid;
	}    

    public String getCirculo() {
		return circulo;
	}

	public void setCirculo(String circulo) {
		this.circulo = circulo;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public int getNumeroImpresiones() {
		return numeroImpresiones;
	}

	public void setNumeroImpresiones(int numeroImpresiones) {
		this.numeroImpresiones = numeroImpresiones;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String ip) {
		IP = ip;
	}

	public int getNumeroBytes() {
		return numeroBytes;
	}

	public void setNumeroBytes(int numeroBytes) {
		this.numeroBytes = numeroBytes;
	}

	public String getTipoImprimible() {
		return tipoImprimible;
	}

	public void setTipoImprimible(String tipoImprimible) {
		this.tipoImprimible = tipoImprimible;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public ImprimibleEnhanced() {
    }

    public int getIdImprimible() {
        return idImprimible;
    }

    public void setIdImprimible(int idImprimible) {
        this.idImprimible = idImprimible;
    }

    public byte[] getDatosImprimible() {
        return datosImprimible;
    }

    public void setDatosImprimible(byte[] datosImprimible) {
        this.datosImprimible = datosImprimible;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }    

	public static ImprimibleEnhanced enhance(Imprimible x){
		return (ImprimibleEnhanced) Enhanced.enhance(x);
	}
	
	public boolean isImprimibleExtenso() {
		return imprimibleExtenso;
	}

	public void setImprimibleExtenso(boolean imprimibleExtenso) {
		this.imprimibleExtenso = imprimibleExtenso;
	}

}
