package gov.sir.core.negocio.modelo.jdogenie;

import java.util.Date;

/**
 * Application identity objectid-class.
 * @author juan.rave
 */
public class ObsoletoEnhanced extends Enhanced {
	private String idMatricula; // pk
    private String idCirculo; // pk
    private Date fecha;
    
	public ObsoletoEnhanced() { }

	public ObsoletoEnhanced(ObsoletoEnhancedPk id) {
		idMatricula = id.idMatricula;
		idCirculo = id.idCirculo;
	}
	
    public String getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(String id) {
        this.idMatricula = id;
    }
    
    public String getIdCirculo() {
        return idCirculo;
    }

    public void setIdCirculo(String id) {
        this.idCirculo = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
 }

