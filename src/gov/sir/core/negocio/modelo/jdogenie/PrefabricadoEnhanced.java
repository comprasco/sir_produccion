package gov.sir.core.negocio.modelo.jdogenie;
/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class PrefabricadoEnhanced extends Enhanced{
	private String idMatricula; // pk 
	private String idCirculo; // pk 
    
    private byte[] datosPrefabricado;

    
	public PrefabricadoEnhanced() {
    }

    public String getIdCirculo() {
        return idCirculo;
    }

    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    public String getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(String idMatricula) {
        this.idMatricula = idMatricula;
    }
    
	public byte[] getDatosPrefabricado() {
		return datosPrefabricado;
	}

	public void setDatosPrefabricado(byte[] datos) {
		this.datosPrefabricado = datos;
	}
}