package gov.sir.core.eventos.comun;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

import java.util.List;

/**
 * @author eacosta
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EvnLiquidacion extends EvnSIR {

        public static final String PRELIQUIDAR = "PRELIQUIDAR";
        public static final String LIQUIDAR = "LIQUIDAR";
        public static final String SOLICITAR_LIQUIDAR = "SOLICITAR_LIQUIDAR";
        public static final String SOLICITAR_LIQUIDAR_SIMPLIFICADO = "SOLICITAR_LIQUIDAR_SIMPLIFICADO";
        public static final String LIQUIDAR_PAGO = "LIQUIDAR_PAGO";
  private Turno turno;

  private Liquidacion liquidacion;

	private Proceso proceso;

	private Estacion estacion;

	private boolean habilitarPago;
        private boolean asignarEstacion;

	/** Identificador unico de usuario*/
	private String UID;

	/** Identificador del circulo registral*/
	private String idCirculo;

	/** Circulo Registral*/
	private Circulo circulo;


        /** Impresora seleccionada (ReciboFotocopiasLiquidacion) */
        // private String impresora;


	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	
	
	private boolean omitirRecibo;
	
	private List listaNotasInformativas;
	
	private String impresoraPredeterminada;
	
	private Rol rol;
	private Solicitud solicitud;
	private Fase fase;
	private String matricula;
	private String tipoCertificado;
        private boolean isEspecial;
	
	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	/**
	 * @param usuario
	 */
	public EvnLiquidacion(Usuario usuario) {
		super(usuario);
	}

        public EvnLiquidacion(
                Usuario usuario,
                Liquidacion liquidacion,
                Proceso proceso,
                Estacion estacion,
                boolean habilitarPago,
                gov.sir.core.negocio.modelo.Usuario usuarioSIR) {

                super(usuario, LIQUIDAR);
                this.liquidacion = liquidacion;
                this.proceso = proceso;
                this.estacion = estacion;
                this.habilitarPago = habilitarPago;
                this.usuarioSIR = usuarioSIR;
	}

	public EvnLiquidacion(
		Usuario usuario,
		Liquidacion liquidacion,
		Proceso proceso,
		Estacion estacion,
		boolean habilitarPago,
		gov.sir.core.negocio.modelo.Usuario usuarioSIR,
                String tipoEvento) {

		super(usuario, tipoEvento);
		this.liquidacion = liquidacion;
		this.proceso = proceso;
		this.estacion = estacion;
		this.habilitarPago = habilitarPago;
		this.usuarioSIR = usuarioSIR;
	}
            /**
             * @return 
             */
            public boolean isEspecial() {
                return isEspecial;
            }
            /**
             * @param isEspecial 
             */
            public void setEspecial(boolean isEspecial) {
                this.isEspecial = isEspecial;
            }
        
	/**
	 * @return
	 */
	public Liquidacion getLiquidacion() {
		return liquidacion;
	}
	/**
	 * @return
	 */
	public Estacion getEstacion() {
		return estacion;
	}

	/**
	 * @return
	 */
	public Proceso getProceso() {
		return proceso;
	}

	/**
	 * @return
	 */
	public boolean isHabilitarPago() {
		return habilitarPago;
	}

        /**
        * @return
        */
        public boolean isAsignarEstacion() {
            return asignarEstacion;
        }

        /**
         * @param boolean
         */
        public void setAsignarEstacion(boolean asignarEstacion) {
            this.asignarEstacion = asignarEstacion;
        }

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}

	/**
	 * @param usuario
	 */
	public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuario) {
		usuarioSIR = usuario;
	}

	/**
	 * @return
	 */
	public String getUID() {
		return UID;
	}

	/**
	 * @param string
	 */
	public void setUID(String string) {
		UID = string;
	}

	/**
	 * @return
	 */
	public String getIdCirculo() {
		return idCirculo;
	}

	/**
	 * @param string
	 */
	public void setIdCirculo(String string) {
		idCirculo = string;
	}

	/**
	 * @return
	 */
	public Circulo getCirculo() {
		return circulo;
	}


  /**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

        public Turno getTurno() {
          return turno;
        }

        public void setTurno(Turno turno) {
           this.turno = turno;
        }

  /*
        public void setImpresora( String impresora ) {
          this.impresora = impresora;
        }

        public String getImpresora() {
          return this.impresora;
        }
        */


	/**
	 * @return
	 */
	public boolean isOmitirRecibo() {
		return omitirRecibo;
	}

	/**
	 * @param b
	 */
	public void setOmitirRecibo(boolean b) {
		omitirRecibo = b;
	}

	public List getListaNotasInformativas() {
		return listaNotasInformativas;
	}

	public void setListaNotasInformativas(List listaNotasInformativas) {
		this.listaNotasInformativas = listaNotasInformativas;
	}

	public String getImpresoraPredeterminada() {
		return impresoraPredeterminada;
	}

	public void setImpresoraPredeterminada(String impresoraPredeterminada) {
		this.impresoraPredeterminada = impresoraPredeterminada;
	}

	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTipoCertificado() {
		return tipoCertificado;
	}

	public void setTipoCertificado(String tipoCertificado) {
		this.tipoCertificado = tipoCertificado;
	}

}
