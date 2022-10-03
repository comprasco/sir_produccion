package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIR;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Municipio;

import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.core.modelo.transferObjects.Rol;

import java.util.List;

/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas
 * con la administración de objetos de Fenrir.
 * @author jmendez
 */
public class EvnReportes extends EvnSIR {

  private String relacionId;
  private String faseId;
  private gov.sir.core.negocio.modelo.Municipio municipioSeleccionado;

  public static final String CONSULTA_CIRCULOS_BY_USUARIO =
      "CONSULTA_CIRCULOS_BY_USUARIO";

  public static final String DO_FINDRELATEDFASESBYRELACIONID_EVENT
      = "DO_FINDRELATEDFASESBYRELACIONID_EVENT";

  public static final String DO_GENERATERELACIONREPORT_EVENT
      = "DO_GENERATERELACIONREPORT_EVENT";

  public static final String CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO
      = "CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO";
  
  public static final String VALIDAR_NUMERO_REPARTO = "VALIDAR_NUMERO_REPARTO";
  



    /**Esta constante se utiliza  para identificar el evento de consulta de usuarios */
    public static final String CONSULTAR_USUARIOS = "CONSULTAR_USUARIOS";

    /**Esta constante se utiliza  para identificar el evento de consulta de usuarios*/
    public static final String USUARIOS_CONSULTAR_POR_CIRCULO = "USUARIOS_CONSULTAR_POR_CIRCULO";
    
    /**Esta constante se utiliza  para identificar el evento de consulta de usuarios por circulo y rol*/
    public static final String USUARIOS_CONSULTAR_POR_CIRCULO_ROL = "USUARIOS_CONSULTAR_POR_CIRCULO_ROL";

    public static final String USUARIOS_CONSULTAR_POR_CIRCULO_REL02 = "USUARIOS_CONSULTAR_POR_CIRCULO_REL02";
    
    /**Esta constante se utiliza  para identificar el evento de consulta de usuarios por circulo y roles*/
    public static final String USUARIOS_CONSULTAR_POR_CIRCULO_ROLES = "USUARIOS_CONSULTAR_POR_CIRCULO_ROLES";
    
    public static final String USUARIOS_CONSULTAR_POR_CIRCULO_ROL_CALIFICADOR = "USUARIOS_CONSULTAR_POR_CIRCULO_ROL_CALIFICADOR";

    /** Nombre del usuario */
    private String nombreUsuario;
    private Rol rol;
    private gov.sir.core.negocio.modelo.Usuario usuarioNegocio;
    private List roles;
    private Circulo circulo;
    private String idRol;
    private String numeroRepartoNotarial;

    /**
     * elora - 24/01/2012, declaracion de nuevo evento para filtro de usuarios de correcciones.
     */
    public static final String USUARIOS_CONSULTAR_POR_CIRCULO_PROCESO = "USUARIOS_CONSULTAR_POR_CIRCULO_PROCESO";


    /**
     * @param usuario
     */
    public EvnReportes(Usuario usuario) {
        super(usuario);
    }

    /**
     * @param usuario
     * @param tipoEvento
     */
    public EvnReportes(Usuario usuario, String tipoEvento) {
        super(usuario, tipoEvento);
    }

    public EvnReportes( String tipoEvento ) {
        super( null, tipoEvento );
    }


    /**
     * Obtener el atributo circulo
     *
     * @return Retorna el atributo circulo.
     */
    public Circulo getCirculo() {
        return circulo;
    }

    /**
     * Actualizar el valor del atributo circulo
     * @param circulo El nuevo valor del atributo circulo.
     */
    public void setCirculo(Circulo circulo) {
        this.circulo = circulo;
    }

    /**
     * Obtener el atributo nombreUsuario
     *
     * @return Retorna el atributo nombreUsuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Actualizar el valor del atributo nombreUsuario
     * @param nombreUsuario El nuevo valor del atributo nombreUsuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtener el atributo rol
     *
     * @return Retorna el atributo rol.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Actualizar el valor del atributo rol
     * @param rol El nuevo valor del atributo rol.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtener el atributo usuarioNegocio
     *
     * @return Retorna el atributo usuarioNegocio.
     */
    public gov.sir.core.negocio.modelo.Usuario getUsuarioNegocio() {
        return usuarioNegocio;
    }

    /**
     * Actualizar el valor del atributo usuarioNegocio (usuarioSIR)
     * @param usuarioNegocio El nuevo valor del atributo usuarioNegocio.
     */
    public void setUsuarioNegocio(
        gov.sir.core.negocio.modelo.Usuario usuarioNegocio) {
        this.usuarioNegocio = usuarioNegocio;
    }




    org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga;
    gov.sir.core.negocio.modelo.Usuario usuarioSir;


  public void setUsuarioAuriga( org.auriga.core.modelo.transferObjects.Usuario newUsuarioAuriga ) {
       super.setUsuario( newUsuarioAuriga );
     }

     public void setUsuarioSir( gov.sir.core.negocio.modelo.Usuario newUsuarioSir ) {
       setUsuarioNegocio( newUsuarioSir );
     }

     public gov.sir.core.negocio.modelo.Usuario getUsuarioSir() {
             return getUsuarioNegocio();
     }

     public org.auriga.core.modelo.transferObjects.Usuario getUsuarioAuriga() {
      return super.getUsuario( );
     }









	public String getIdRol() {
		return idRol;
	}











  public String getRelacionId() {
    return relacionId;
  }

  public String getFaseId() {
    return faseId;
  }

  public Municipio getMunicipioSeleccionado() {
    return municipioSeleccionado;
  }

  public void setIdRol(String idRol) {
		this.idRol = idRol;
	}

  public void setRelacionId(String relacionId) {
    this.relacionId = relacionId;
  }

  public void setFaseId(String faseId) {
    this.faseId = faseId;
  }

  public void setMunicipioSeleccionado(Municipio municipioSeleccionado) {
    this.municipioSeleccionado = municipioSeleccionado;
  }

public String getNumeroRepartoNotarial() {
	return numeroRepartoNotarial;
}

public void setNumeroRepartoNotarial(String numeroRepartoNotarial) {
	this.numeroRepartoNotarial = numeroRepartoNotarial;
}

	
	public List getRoles() {
		return roles;
	}
	
	public void setRoles(List roles) {
		this.roles = roles;
	}



}
