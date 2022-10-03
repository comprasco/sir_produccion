package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

import gov.sir.core.negocio.modelo.Relacion;

import java.util.List;


/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas
 * con la administración de objetos de Fenrir
 * @author jmendez
 */
public class EvnRespReportes extends EvnSIRRespuesta {


  Relacion relacion;

  // List< OficinaOrigen >
  private List oficinasOrigenByMunicipioList;

  public static final String DO_FINDRELATEDFASESBYRELACIONID_EVENTRESP_OK
      = "DO_FINDRELATEDFASESBYRELACIONID_EVENTRESP_OK";

  public static final String DO_GENERATERELACIONREPORT_EVENTRESP_OK
      = "DO_GENERATERELACIONREPORT_EVENTRESP_OK";

  public static final String CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO_EVENTRESPOK
      = "CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO_EVENTRESPOK";

  public static final String CONSULTA_CIRCULOS_BY_USUARIO_EVENTRESPOK
      = "CONSULTA_CIRCULOS_BY_USUARIO_EVENTRESPOK";
  
  public static final String VALIDAR_NUMERO_REPARTO_NOTARIAL = "VALIDAR_NUMERO_REPARTO_NOTARIAL";
  


	/**Esta constante se utiliza  para identificar el evento de consulta satisfactoria de los usuarios del sistema */
	public static final String CONSULTA_USUARIOS_OK = "CONSULTA_USUARIOS_OK";

	/**Esta constante se utiliza  para identificar el evento de consulta satisfactoria de los usuarios del sistema por círculo y rol */
	public static final String USUARIOS_CONSULTAR_POR_CIRCULO_ROL_OK = "USUARIOS_CONSULTAR_POR_CIRCULO_ROL_OK";

	/**
	 * @param payload
	 */
	public EvnRespReportes(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespReportes(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}
        public EvnRespReportes(String tipoEvento) {
                super(null, tipoEvento);
        }


        public java.util.List getRelatedFases() {
          return (List) super.getPayload();
        }

  public Relacion getRelacion() {
    return relacion;
  }

  public List getOficinasOrigenByMunicipioList() {
    return oficinasOrigenByMunicipioList;
  }

  public List getCirculosByUsuarioList() {
    return circulosByUsuarioList;
  }

  public void setRelacion(Relacion relacion) {
    this.relacion = relacion;
  }

  public void setOficinasOrigenByMunicipioList(List
                                               oficinasOrigenByMunicipioList) {
    this.oficinasOrigenByMunicipioList = oficinasOrigenByMunicipioList;
  }
  List circulosByUsuarioList;
  /**
   * setCirculosByUsuarioList
   *
   * @param circulosByUsuarioList List
   */
  public void setCirculosByUsuarioList(List circulosByUsuarioList) {
    this.circulosByUsuarioList = circulosByUsuarioList;
  }

}
