package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Eje;

/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class EjeEnhanced  extends Enhanced{
    private String idEje; // pk 
    private String nombre;
    private UsuarioEnhanced usuario;

    public EjeEnhanced() {
    }

    public String getIdEje() {
        return idEje;
    }

    public void setIdEje(String idEje) {
        this.idEje = idEje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	public UsuarioEnhanced getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEnhanced usuario) {
		this.usuario = usuario;
	}
	public static EjeEnhanced enhance(Eje eje){
		return (EjeEnhanced) Enhanced.enhance(eje);
	}
}