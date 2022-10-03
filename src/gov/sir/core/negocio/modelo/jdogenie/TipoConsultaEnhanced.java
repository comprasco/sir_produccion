package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoConsulta;

/*
 * @author dlopez
 */
   
public class TipoConsultaEnhanced extends Enhanced{

    private String idTipoConsulta; // pk
    
   //constantes para el manejo de los tipos de consultas
   public static final String TIPO_SIMPLE = "0";
   public static final String TIPO_FOLIOS = "1";
   public static final String TIPO_COMPLEJO="2";
   
    
    private String nombre;

    public TipoConsultaEnhanced() {
    }

    public String getIdTipoConsulta() {
        return idTipoConsulta;
    }

    public void setIdTipoConsulta(String idTipoConsulta) {
        this.idTipoConsulta = idTipoConsulta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
	public static TipoConsultaEnhanced enhance (
	TipoConsulta tipoConsulta)
	{
		return (TipoConsultaEnhanced)Enhanced.enhance(tipoConsulta);
	}
}