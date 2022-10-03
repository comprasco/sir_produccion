package gov.sir.core.web.acciones.devolucion;

import co.com.iridium.generalSIR.comun.modelo.Transaccion;
import co.com.iridium.generalSIR.transacciones.TransaccionSIR;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.sir.core.eventos.comun.EvnLiquidacion;
import gov.sir.core.eventos.comun.EvnRespLiquidacion;
import gov.sir.core.eventos.comun.EvnRespPago;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionLiquidacionDevolucionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;
import gov.sir.core.eventos.devolucion.EvnDevolucion;
import gov.sir.core.eventos.devolucion.EvnRespDevolucion;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.CheckItemDev;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Consignacion;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolCheckedItemDev;
import gov.sir.core.negocio.modelo.Solicitante;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.hermod.gdocumental.integracion.SGD;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dsalas,mmunoz, jvelez
 *
 */
public class AWLiquidacionDevolucion extends SoporteAccionWeb {


    /**
     * Constante que identifica que se desea liquidar la
     * solicitud
     */
    public final static String LIQUIDAR = "LIQUIDAR";


    public static final String AGREGAR_TURNO_ANT = "AGREGAR_TURNO_ANT";
    
    public static final String AGREGAR_CONSIGNACION_CHEQUE = "AGREGAR_CONSIGNACION_CHEQUE";
    
    public static final String ELIMINAR_CONSIGNACION_CHEQUE = "ELIMINAR_CONSIGNACION_CHEQUE";
    
    public static final String AGREGAR_SOLICITANTE = "AGREGAR_SOLICITANTE";
    
    public static final String ELIMINAR_SOLICITANTE = "ELIMINAR_SOLICITANTE";
    
    public static final String TIPO_BUSQUEDA = "TIPO_BUSQUEDA";
    
    public static final String BUSQUEDA_CONSIGNACION = "BUSQUEDA_CONSIGNACION";
    
    public static final String BUSQUEDA_CHEQUE = "BUSQUEDA_CHEQUE";
    
    public static final String TIPO_CHECKED = "TIPO_CHECKED";
    
    public static final String TURNO = "TURNO";
    
    public static final String CONSIGNACION = "CONSIGNACION";
    
    public static final String CAMBIAR_TIPO_CHEQUED = "CAMBIAR_TIPO_CHEQUED";
    
    public static final String SOLICITANTE_AGREGADO = "SOLICITANTE_AGREGADO";
    
    public static final String DESCRIPCION_DOCUMENTO = "DESCRIPCION_DOCUMENTO";
    

    /**
     * Constante para eliminar turnos anteriores
     */
    public static final String ELIMINAR_TURNO_ANTERIOR ="ELIMINAR_TURNO_ANTERIOR";

    /**
     * Variable donde se guarda la accion enviada en el request
     */
    private String accion;

    private HttpSession session;
    public static final String TURNO_ANT_VAL = "TURNO_ANT_VAL";
    
	public static final String LISTA_ASOCIADOS = "LISTA_ASOCIADOS";
	
        public static final String LISTA_CANALES_RECAUDO = "LISTA_CANALES_RECAUDO";
	
	public static final String DOCUMENTO_PAGO = "DOCUMENTO_PAGO";
	
	public static final String FORMA_PAGO = "FORMA_PAGO";
	
	public static final String VALOR_DOCUMENTO = "VALOR_DOCUMENTO";
	
	public static final String BANCO = "BANCO";
	
	public static final String NUMERO = "NUMERO";
	
	public static final String SALDO_A_FAVOR = "SALDO_A_FAVOR";
	
	public static final String FECHA_CONSIGNACION = "FECHA_CONSIGNACION";
	
	public static final String LISTA_SOLICITANTES_DEVOLUCION = "LISTA_SOLICITANTES_DEVOLUCION";
	
	public static final String LISTA_CONSIGNACIONES_DEVOLUCION = "LISTA_CONSIGNACIONES_DEVOLUCION";
	
	public static final String LISTA_DOCUMENTOS_CHECKED = "LISTA_DOCUMENTOS_CHECKED";
	
	public static final int NUMERO_DOCUMENTOS_A_ENTREGAR = 6;
	
	public static final String NUMERO_FOLIOS = "NUMERO_FOLIOS";
	
	public static final String TURNO_VALIDADO = "TURNO_VALIDADO";
	
	private List solicitantes;
	
	private List consignaciones;
	
	private List checkedItems;
	
	/**
	 * Constante que indica que se desea cargar la lista de items de
	 * chequeo para el proceso de devoluciones.
	 */
	public static final String CONSULTAR_ITEMS_CHEQUEO = "CONSULTAR_ITEMS_CHEQUEO";

    /**
     * Constructor por defecto
     * */
    public AWLiquidacionDevolucion() {}
    
    public void doStart(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        solicitantes = (List)sesion.getAttribute(AWLiquidacionDevolucion.LISTA_SOLICITANTES_DEVOLUCION);
		
        if (solicitantes == null){
            solicitantes = new ArrayList();
            sesion.setAttribute(AWLiquidacionDevolucion.LISTA_SOLICITANTES_DEVOLUCION, solicitantes);
        }
        
        consignaciones = (List)sesion.getAttribute(AWLiquidacionDevolucion.LISTA_CONSIGNACIONES_DEVOLUCION);
		
        if (consignaciones == null){
        	consignaciones = new ArrayList();
            sesion.setAttribute(AWLiquidacionDevolucion.LISTA_CONSIGNACIONES_DEVOLUCION, consignaciones);
        }
        
        checkedItems = (List)sesion.getAttribute(AWLiquidacionDevolucion.LISTA_DOCUMENTOS_CHECKED);
        
        if (checkedItems == null){
        	checkedItems = new ArrayList();
        	for(int i=0;i<NUMERO_DOCUMENTOS_A_ENTREGAR;i++){
        		checkedItems.add("");
        	}
            sesion.setAttribute(AWLiquidacionDevolucion.LISTA_DOCUMENTOS_CHECKED, checkedItems);
        }
        
    }

    /**
     * Método principal de esta acción web. Aqui se realiza
     * toda la lógica requerida de validación y de generación
     * de eventos de negocio.
     * @param request trae la informacion del formulario
     * @throws AccionWebException cuando ocurre un error
     * @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);
        session = request.getSession();

        /*AHERRENO
         28/05/2012
         REQ 076_151 TRANSACCION*/
        request.getSession().setAttribute("TIEMPO_INICIO_TRANSACCION", new Date());        
        if ((accion == null) || (accion.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe indicar una accion valida");
        }

        if (accion.equals(LIQUIDAR)) {
            return liquidar(request);
        } else if (accion.equals(AGREGAR_TURNO_ANT)) {
            return validarTurnoAnt(request);

        } else if (accion.equals(AGREGAR_CONSIGNACION_CHEQUE)) {
            return validarConsignacionCheque(request);
        
        } else if (accion.equals(ELIMINAR_CONSIGNACION_CHEQUE)) {
            eliminarConsignacionCheque(request);
            return null;

        } else if (accion.equals(AGREGAR_SOLICITANTE)) {
            validarSolicitante(request);
            return null;
        
        } else if (accion.equals(ELIMINAR_SOLICITANTE)) {
            eliminarSolicitante(request);
            return null;
            
        } else if (accion.equals(ELIMINAR_TURNO_ANTERIOR)) {
		return null;
        } else if (accion.equals(CAMBIAR_TIPO_CHEQUED)) {
        	return null;

	} 
	else if (accion.equals(CONSULTAR_ITEMS_CHEQUEO)){
				return obtenerItemsChequeo(request);
			}

        
        
        else {
            throw new AccionInvalidaException("La accion " + accion +
                                              " no es valida.");
        }
    }

    private void eliminarConsignacionCheque(HttpServletRequest request) {
    	preservarInfo(request);
    	int pos = Integer.parseInt(request.getParameter(WebKeys.POSICION));
    	this.consignaciones.remove(pos);
	}

	/**
     * validarTurnoAnt
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento validarTurnoAnt(HttpServletRequest request)throws AccionWebException {

        preservarInfo(request);
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.
                auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.
                modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        String idWF = request.getParameter(CTurno.TURNO_ANTERIOR);

        ValidacionLiquidacionDevolucionException excepcion = new ValidacionLiquidacionDevolucionException();
        List notasInformativas = (List)session.getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        
        String conCh = request.getParameter("NUMERO");
        if((conCh != null && !conCh.equals(""))|| (this.consignaciones!=null && this.consignaciones.size()>0)){
        	excepcion.addError("No puede ingresar turno y consignacion/cheque en una misma solicitud");
        	throw excepcion;
        }
        
        if(idWF == null ||idWF.equals("")){
        	excepcion.addError("El codigo del turno no es completo");
        	throw excepcion;
        }
        /**Validacion del turno. Debe pertenecer al circulo que se encuentre el usuario*/
        String idWorkFlowTurno[] = idWF.split("-");
        if(idWorkFlowTurno.length!=4){
        	excepcion.addError("El formato del turno es inválido.");
        	throw excepcion;
        }
        try{
        	Integer aux = new Integer(idWorkFlowTurno[0]);
        	aux = new Integer(idWorkFlowTurno[2]);
        	aux = new Integer(idWorkFlowTurno[3]);
        }catch(Exception e){
        	excepcion.addError("El formato del turno es inválido.");
        	throw excepcion;
        }
        	
        
        if(!idWorkFlowTurno[1].equals(circulo.getIdCirculo())){
        	excepcion.addError("El turno no es del mismo circulo.");
        	throw excepcion;
        }
                                               
        
        EvnDevolucion evn = new EvnDevolucion(usuarioAuriga,
                                              EvnDevolucion.
                                              AGREGAR_TURNO_ANT,
                                              turno, fase, estacion,
                                              null, usuario);
        evn.setIdWfTurnoAnterior(idWF.toUpperCase());
        
        if(notasInformativas != null) {
			 evn.setNotasInformativas(notasInformativas);
		 }
        
        return evn;

    }
    
    /**
     * validarConsignacionCheque
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento validarConsignacionCheque(HttpServletRequest request)throws AccionWebException {

        preservarInfo(request);
        HttpSession session = request.getSession();
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.
                auriga.core.modelo.transferObjects.Usuario) request.getSession()
                .getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.
                modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
        String conCh = request.getParameter("NUMERO");
        String banco = request.getParameter("BANCO");
        String fecha = request.getParameter(AWLiquidacionDevolucion.FECHA_CONSIGNACION);
        String strValor = request.getParameter("VALOR_DOCUMENTO");
        String strSaldo = request.getParameter("SALDO_A_FAVOR");
        
        boolean isConsignacion = false;
        isConsignacion = request.getParameter(TIPO_BUSQUEDA) != null && request.getParameter(TIPO_BUSQUEDA).equals(BUSQUEDA_CONSIGNACION);
        session.setAttribute(TIPO_BUSQUEDA, request.getParameter(TIPO_BUSQUEDA));

        ValidacionLiquidacionDevolucionException excepcion = new ValidacionLiquidacionDevolucionException();
        List notasInformativas = (List)session.getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        
        String idWF = request.getParameter(CTurno.TURNO_ANTERIOR);

        if(idWF != null && !idWF.equals("")){
        	excepcion.addError("No puede ingresar turno y consignacion/cheque en una misma solicitud");
        	throw excepcion;
        }
        
        if(conCh == null ||conCh.equals("")){
        	excepcion.addError("El codigo de la consignacion o cheque no esta completo");
        	throw excepcion;
        }
        
        Date date = null;
        if(( fecha == null) 
        		|| (fecha.equals(""))) {
        	excepcion.addError( "Debe ingresar la fecha de la consignacion" );

     	} else {
     		String send1 = null;

     		try {
     			date = DateFormatUtil.parse(fecha);
     		}
     		catch (Exception e) {
     			excepcion.addError("La fecha de consignacion es inválida" + e.getMessage() );
     		}
     	}
        
        if (banco == null || banco.length() <= 0 || banco.equals(WebKeys.SIN_SELECCIONAR) ) {
			excepcion.addError("Seleccione el banco.");
        	throw excepcion;
		}
                 
        double valor = 0;
        if (strValor == null ||
        		strValor.trim().equals("")) {
					throw new AccionInvalidaException("Debe ingresar el valor de la consignacion o cheque");
        } else {
        	
            try {
            	valor = Double.valueOf(strValor).doubleValue();
                if (valor < 0) {
					throw new AccionInvalidaException("El valor de la consignacion o cheque no puede ser negativo");
                }
            } catch (NumberFormatException e) {
				throw new AccionInvalidaException("El valor de la consignacion o cheque es inválido");
            }
        }
        
        double saldo = 0;
        if (strSaldo == null ||
        		strSaldo.trim().equals("")) {
					throw new AccionInvalidaException("Debe ingresar el saldo a favor");
        } else {
        	
            try {
            	saldo = Double.valueOf(strSaldo).doubleValue();
                if (saldo < 0) {
					throw new AccionInvalidaException("El valor del saldo a favor no puede ser negativo");
                }
            } catch (NumberFormatException e) {
				throw new AccionInvalidaException("El valor del saldo a favor es inválido");
            }
        }
        
        EvnDevolucion evn = new EvnDevolucion(usuarioAuriga,
                                              EvnDevolucion.
                                              AGREGAR_CONSIGNACION_CHEQUE,
                                              turno, fase, estacion,
        
                                              null, usuario);
        DocumentoPago docPago = null;
        if (isConsignacion){
        	DocPagoConsignacion docPagoCon = new DocPagoConsignacion();
        	docPagoCon.setNoConsignacion(conCh.toUpperCase());
        	Banco objBanco = new Banco();
        	objBanco.setIdBanco(banco.toUpperCase());
        	docPagoCon.setBanco(objBanco);
        	docPagoCon.setFecha(fecha);
        	docPagoCon.setValorDocumento(valor);
        	docPagoCon.setSaldoDocumento(saldo);
        	docPagoCon.setTipoPago("CONSIGNACION");
        	evn.setDocPago(docPagoCon);
        } else {
        	DocPagoCheque docPagoCheque = new DocPagoCheque();
        	docPagoCheque.setNoCheque(conCh.toUpperCase());
        	Banco objBanco = new Banco();
        	objBanco.setIdBanco(banco.toUpperCase());
        	docPagoCheque.setBanco (objBanco);
        	docPagoCheque.setFecha(fecha);
        	docPagoCheque.setValorDocumento(valor);
        	docPagoCheque.setSaldoDocumento(saldo);
        	docPagoCheque.setTipoPago("CHEQUE");
        	evn.setDocPago(docPagoCheque);
        }
                
        if(notasInformativas != null) {
			 evn.setNotasInformativas(notasInformativas);
		 }
        
        Rol rol = (Rol)request.getSession().getAttribute(WebKeys.ROL);
        evn.setRol(rol);
        
        return evn;

    }
    
    /**
     * Este metodo es el que se encarga de armar la liquidacion y enviarla a negocio
     * @param request HttpServletRequest
     * @return EvnLiquidacion
     * @throws AccionWebException
     */
    private void validarSolicitante(HttpServletRequest request) throws
            AccionWebException {

    	preservarInfo(request);
        HttpSession session = request.getSession();
        
        if (solicitantes.size() == 2){
        	throw new AccionInvalidaException("No se puede agregar mas de dos solicitantes ");
        }
        //DATOS SOLICITANTE
        String tipoDocSolicitante =
                request.getParameter(CCiudadano.TIPODOC).trim();
        if ((tipoDocSolicitante == null) ||
            tipoDocSolicitante.equals("SIN_SELECCIONAR")) {
				throw new AccionInvalidaException("El tipo de documento de identificación es inválido");
        }
        String numIdentSolicitante = request.getParameter(CCiudadano.DOCUMENTO).
                                     trim();
        String primerApellidoSolicitante = request.getParameter(CCiudadano.
                APELLIDO1).trim();
        String segundoApellidoSolicitante = request.getParameter(CCiudadano.
                APELLIDO2).trim();
        String nombreSolicitante = request.getParameter(CCiudadano.NOMBRE).trim();
        
        //VALIDAR DATOS
        if (tipoDocSolicitante.equals(WebKeys.SIN_SELECCIONAR)) {
			throw new AccionInvalidaException("Debe seleccionar un tipo de identificacion para el ciudadano");
        } else if (tipoDocSolicitante.equals(COPLookupCodes.
                                             SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
            if (primerApellidoSolicitante == null ||
                primerApellidoSolicitante.trim().equals("")) {
					throw new AccionInvalidaException("Debe ingresar el primer apellido del Ciudadano");
            }
        } else if (tipoDocSolicitante.equals(COPLookupCodes.NIT)) {
            if (primerApellidoSolicitante == null ||
                primerApellidoSolicitante.trim().equals("")) {
					throw new AccionInvalidaException("Debe ingresar el primer apellido del Ciudadano");
            }
        } else {
            if (numIdentSolicitante == null ||
                numIdentSolicitante.trim().equals("")) {
					throw new AccionInvalidaException("Debe ingresar el numero de identificacion del Ciudadano");
            }
            double valorId = 0.0d;
            if (numIdentSolicitante == null ||
                numIdentSolicitante.length() <= 0) {
					throw new AccionInvalidaException("El número de identificacion de la persona en la anotacion es invalido");
                }
                else
                {
            /* @author : CTORRES
             * @change : Se implemento la validación para solo permitir caracteres alfanuméricos en 
             *           numId cuando el tipo de identificación es PASAPORTE.
             * Caso Mantis : 11056
             * No Requerimiento: 073_151
             */
                if(tipoDocSolicitante.contains("PS"))
                {
                    String regexSL = "^[a-zA-Z]+$";
                    String regexSN = "^[0-9]+$";
                    String regexLN = "^[a-zA0-Z9]+$";
                    java.util.regex.Pattern patternSL = java.util.regex.Pattern.compile(regexSL);
                    java.util.regex.Pattern patternSN = java.util.regex.Pattern.compile(regexSN);
                    java.util.regex.Pattern patternLN = java.util.regex.Pattern.compile(regexLN);
                    boolean esC = false;
                    if(patternSL.matcher(numIdentSolicitante).matches()) esC = true;
                    else if(patternSN.matcher(numIdentSolicitante).matches()) esC = true;
                    else if(patternLN.matcher(numIdentSolicitante).matches()) esC = true;
                    else throw new AccionInvalidaException("El número de identificación de la persona es inválido. Debe ser alfanumérico");

                }
                else{                
                    try {
                        valorId = Double.parseDouble(numIdentSolicitante);
                        if (valorId <= 0) {
                                                    throw new AccionInvalidaException("El valor del documento no puede ser negativo o cero");
                        }
                    } catch (NumberFormatException e) {
                                            throw new AccionInvalidaException("El numero de identificacion de la persona en la anotacion es inválido");
                    }
                }
            }
        }
        
        //CREAR OBJETO SOLICITANTE
        Ciudadano solicitante = new Ciudadano();
        solicitante.setTipoDoc(tipoDocSolicitante);
        solicitante.setDocumento(numIdentSolicitante);
        solicitante.setApellido1(primerApellidoSolicitante);
        solicitante.setApellido2(segundoApellidoSolicitante);
        solicitante.setNombre(nombreSolicitante);
        
        //CIRCULO
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        //Se setea el circulo del ciudadano
		solicitante.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);
        
        session.removeAttribute(CCiudadano.TIPODOC);
        session.removeAttribute(CCiudadano.DOCUMENTO);
        session.removeAttribute(CCiudadano.APELLIDO1);
        session.removeAttribute(CCiudadano.APELLIDO2);
        session.removeAttribute(CCiudadano.NOMBRE);
        
        this.solicitantes.add(solicitante);
        session.setAttribute(this.SOLICITANTE_AGREGADO, solicitante);
    }
    
    private void eliminarSolicitante(HttpServletRequest request) throws AccionWebException {
    	preservarInfo(request);
    	int pos = Integer.parseInt(request.getParameter(WebKeys.POSICION));
    	this.solicitantes.remove(pos);
    }

	/**
	 * Genera un evento para consultar en capa de negocio el listado de
	 * items de chequeo para el proceso de devoluciones. 
	 * @param request HttpServletRequest
	 * @return Evento
	 */
	 private Evento obtenerItemsChequeo(HttpServletRequest request) {

		 preservarInfo(request);
		 HttpSession session = request.getSession();
		 org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.
				 auriga.core.modelo.transferObjects.Usuario) request.getSession()
				 .getAttribute(SMARTKeys.USUARIO_EN_SESION);
		 Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		 Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		 Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		 gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.
				 modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

		 EvnDevolucion evn = new EvnDevolucion(usuarioAuriga,
											   AWLiquidacionDevolucion.CONSULTAR_ITEMS_CHEQUEO,
											   turno, fase, estacion,
											   null, usuario);
		 return evn;

	 }



    /**
     * Este metodo es el que se encarga de armar la liquidacion y enviarla a negocio
     * @param request HttpServletRequest
     * @return EvnLiquidacion
     * @throws AccionWebException
     */
    private EvnLiquidacion liquidar(HttpServletRequest request) throws
            AccionWebException {

        preservarInfo(request);

        String turnoAntVal = (String) session.getAttribute(TURNO_ANT_VAL);
        String turnoAnt = (String) session.getAttribute(CTurno.TURNO_ANTERIOR);
        request.getSession().removeAttribute(this.SOLICITANTE_AGREGADO);
        
        if (turnoAnt == null || turnoAnt.trim().equals("")) {
        	if (consignaciones.size() == 0) {
        		throw new AccionInvalidaException("Debe ingresar el turno asociado o agregar al menos una consignacion o cheque");
        	}
        } else if (turnoAntVal != null && !turnoAntVal.equals(turnoAnt)) {
			throw new AccionInvalidaException("El turno asociado no ha sido agregado");
        } else if (turnoAnt != null && !turnoAnt.trim().equals("") && turnoAntVal == null) {
			throw new AccionInvalidaException("El turno asociado no ha sido agregado");
        }

        //LIMPIAR SESSION
        session.removeAttribute(WebKeys.PAGO);
        session.removeAttribute(WebKeys.LIQUIDACION);
        session.removeAttribute(WebKeys.LISTA_CHEQUES);
        session.removeAttribute(WebKeys.LISTA_CONSIGNACIONES);
        session.removeAttribute(WebKeys.APLICACION_EFECTIVO);

        //OBTENER USUARIO
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
                (org.auriga.core.modelo.transferObjects.Usuario)
                session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

        //CIRCULO
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);

        //DATOS SOLICITUD
        String turnoAnterior = request.getParameter(CTurno.TURNO_ANTERIOR).
                               toUpperCase();
        String descripcion = request.getParameter(CTurno.DESCRIPCION).trim();

        //DATOS SOLICITANTE
        
        if (solicitantes.size() == 0){
        	throw new AccionInvalidaException("Se requiere al menos un solicitante ");
        }
        
        if (descripcion.equals("")) {
			throw new AccionInvalidaException("Debe introducir una descripcion");
        }
        
        
        //CREAR OBJETO TURNO ANTERIOR
        Turno auxTurno = new Turno();
        auxTurno.setIdWorkflow(turnoAnterior);

        //CREAR OBJETO SOLICITUD
        SolicitudDevolucion solicitud = new SolicitudDevolucion();
        solicitud.setCirculo(circulo);
        solicitud.setProceso(proceso);
        solicitud.setUsuario(usuario);
        
        //asociar las consignaciones o cheuqes
        for (int y=0; y < consignaciones.size(); y++)
    	{
    		DocumentoPago docPago = (DocumentoPago) consignaciones.get(y);
        	Consignacion cons = new Consignacion();
        	if (docPago instanceof DocPagoCheque){
        		cons.setDocPago((DocPagoCheque)docPago);
        	} 
        	if (docPago instanceof DocPagoConsignacion){
        		cons.setDocPago((DocPagoConsignacion)docPago);
        	} 
    		solicitud.addConsignacion(cons);
    	}
        
        //asociar los solicitantes agregados a la solicitud
        for (int y=0; y < solicitantes.size(); y++)
    	{
    		Ciudadano ciu = (Ciudadano)solicitantes.get(y);
    		Solicitante solte = new Solicitante();
    		solte.setCiudadano(ciu);
    		solicitud.addSolicitante(solte);
    	}
        
        solicitud.setDescripcion(descripcion);
        if (turnoAnterior != null && !turnoAnterior.equals("")) {
            solicitud.setTurnoAnterior(auxTurno);
        }
        
        
        //ASOCIAR LOS ITEMS DE CHEQUEO OBTENIDOS DEL REQUEST.
        String [] docEntregados = request.getParameterValues("DOCUMENTOS_ENTREGADOS");
        if (docEntregados!= null)
        {
        	if((docEntregados[docEntregados.length - 1].equals("6"))
            		&& (docEntregados.length > 1)){
            	throw new AccionInvalidaException("Si selecciono documentos para entregar, no puede seleccionar la opcion NINGUNO");
            }
        	for (int y=0; y<docEntregados.length; y++)
        	{
        		String value = (String)docEntregados[y];
        		CheckItemDev newCheckItem = new CheckItemDev();
        		newCheckItem.setIdCheckItemDev(value);
        		SolCheckedItemDev solCheckItem = new SolCheckedItemDev();
        		solCheckItem.setCheckItem(newCheckItem);
        		solicitud.addCheckedItem(solCheckItem);
        		
        		
        	}
        } else {
        	throw new AccionInvalidaException("Debe seleccionar al menos una opción en documentos entregados");
        }
        
        String desDoc = request.getParameter(DESCRIPCION_DOCUMENTO);
        if(docEntregados[0].equals("1")){
        		if (desDoc == null) {
					desDoc = "";
        		}
        		solicitud.setComentario(desDoc);
        }
        
        String numFolios = request.getParameter("NUMERO_FOLIOS");
        if (numFolios == null ||
        		numFolios.trim().equals("")) {
					throw new AccionInvalidaException("Debe ingresar el numero de folios");
        } else {
        	int valorFolios = 0;
            try {
            	valorFolios = Integer.valueOf(numFolios).intValue();
                if (valorFolios < 0) {
					throw new AccionInvalidaException("El numero de folios no puede ser negativo");
                }
                if((docEntregados[docEntregados.length - 1].equals("6"))
                		&& (docEntregados.length == 1)){
                	if (valorFolios != 0){
                		throw new AccionInvalidaException("Si no va entregar documentos el numero de folios debe ser cero");
                	}
                }
                solicitud.setNumeroFolios(valorFolios);
            } catch (NumberFormatException e) {
				throw new AccionInvalidaException("El numero de folios es inválido");
            }
        }
        

        //CREAR OBJETO LIQUIDACION
        LiquidacionTurnoDevolucion liquidacion = new LiquidacionTurnoDevolucion();
        liquidacion.setSolicitud(solicitud);
        solicitud.addLiquidacion(liquidacion);

        EvnLiquidacion evn = new EvnLiquidacion(usuarioAuriga, liquidacion,
                                                (Proceso) session.getAttribute(
                WebKeys.PROCESO),
                                                (Estacion) session.getAttribute(
                WebKeys.ESTACION), true, usuario);
        
        List notasInf = (List)request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
        if (notasInf != null){
        	evn.setListaNotasInformativas(notasInf);
        }
        
        evn.setUID(session.getId());
        return evn;

    }

    private void preservarInfo(HttpServletRequest request) {
        session.setAttribute(CTurno.TURNO_ANTERIOR,
                             request.getParameter(CTurno.TURNO_ANTERIOR));
        session.setAttribute(TURNO_ANT_VAL,
                request.getParameter(CTurno.TURNO_ANTERIOR));
        session.setAttribute(CTurno.DESCRIPCION,
                             request.getParameter(CTurno.DESCRIPCION));
        session.setAttribute(CCiudadano.TIPODOC,
                             request.getParameter(CCiudadano.TIPODOC));
        session.setAttribute(CCiudadano.DOCUMENTO,
                             request.getParameter(CCiudadano.DOCUMENTO));
        session.setAttribute(CCiudadano.APELLIDO1,
                             request.getParameter(CCiudadano.APELLIDO1));
        session.setAttribute(CCiudadano.APELLIDO2,
                             request.getParameter(CCiudadano.APELLIDO2));
        session.setAttribute(CCiudadano.NOMBRE,
                             request.getParameter(CCiudadano.NOMBRE));
        session.setAttribute(VALOR_DOCUMENTO,request.getParameter(VALOR_DOCUMENTO));
        session.setAttribute(BANCO,request.getParameter(BANCO));
        session.setAttribute(NUMERO,request.getParameter(NUMERO));
        session.setAttribute(SALDO_A_FAVOR,request.getParameter(SALDO_A_FAVOR));
        
        String[] docEntregados = request.getParameterValues("DOCUMENTOS_ENTREGADOS");
        if(docEntregados!=null){
        	for(int i=0;i<checkedItems.size();i++){
            	for(int j=0;j<docEntregados.length;j++){
            		if(i==Integer.parseInt(docEntregados[j])-1){
                		checkedItems.remove(i);
                    	checkedItems.add(i,"checked");
                    	break;
                	}else{
                		checkedItems.remove(i);
                    	checkedItems.add(i,"");
                	}
            	}        		       		
            }
        	session.setAttribute(AWLiquidacionDevolucion.LISTA_DOCUMENTOS_CHECKED, checkedItems);
        }
        session.setAttribute(DESCRIPCION_DOCUMENTO, request.getParameter(DESCRIPCION_DOCUMENTO));
        
        session.setAttribute(NUMERO_FOLIOS,request.getParameter(NUMERO_FOLIOS));
        
    }

    /**
     * Este metodo borra de la sesion la informacion que se puso en los campos,
     * ademas de la informacion de las matriculas que se pone en sesion.
     * @param request HttpServletRequest
     * @return Evento nulo ya que no se requiere que viaje hasta el negocio
     */
    private void removerInfo(HttpServletRequest request) {
        session.removeAttribute(CTurno.TURNO_ANTERIOR);
        session.removeAttribute(CTurno.DESCRIPCION);
        session.removeAttribute(CCiudadano.TIPODOC);
        session.removeAttribute(CCiudadano.DOCUMENTO);
        session.removeAttribute(CCiudadano.APELLIDO1);
        session.removeAttribute(CCiudadano.APELLIDO2);
        session.removeAttribute(CCiudadano.NOMBRE);
        
    }

    /**
     * Este método permite procesar cualquier evento de respuesta de la capa
     * de negocio, en caso de recibir alguno.
     * @param request la información del formulario
     * @param eventoRespuesta el evento de respuesta de la capa de negocio, en caso
     * de existir alguno
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
        //session.removeAttribute(TURNO_ANT_VAL);

        if (evento instanceof EvnRespLiquidacion) {
            EvnRespLiquidacion respuesta = (EvnRespLiquidacion) evento;

            if (respuesta != null) {
                if (respuesta.getTipoEvento().equals(EvnRespLiquidacion.
                        LIQUIDACION)) {
                    Liquidacion liquidacion = respuesta.getLiquidacion();
                    session.setAttribute(WebKeys.LIQUIDACION, liquidacion);
                    NumberFormat nf = NumberFormat.getInstance();
                    session.setAttribute(WebKeys.VALOR_LIQUIDACION,
                                         String.valueOf(liquidacion.getValor()));

                    DocumentoPago documentoEfectivo = new DocPagoEfectivo(
                            liquidacion.getValor());
                    AplicacionPago aplicacionEfectivo = new AplicacionPago(
                            documentoEfectivo, liquidacion.getValor());
                    session.setAttribute(WebKeys.APLICACION_EFECTIVO,
                                         aplicacionEfectivo);

                }
            }
        } else if (evento instanceof EvnRespPago) {
            EvnRespPago respuesta = (EvnRespPago) evento;
            if (respuesta != null) {
                if (respuesta.getTipoEvento().equals(EvnRespPago.
                        PROCESAMIENTO_PAGO)) {
                    Turno turno = respuesta.getTurno();
                    session.setAttribute(WebKeys.TURNO, turno);
                    session.removeAttribute(DOCUMENTO_PAGO);
                    session.removeAttribute(VALOR_DOCUMENTO);
                    session.removeAttribute(FORMA_PAGO);
                    session.removeAttribute(BANCO);
                    session.removeAttribute(NUMERO);
                    session.removeAttribute(SALDO_A_FAVOR);
                    session.removeAttribute(AWLiquidacionDevolucion.LISTA_DOCUMENTOS_CHECKED);
                    session.removeAttribute(DESCRIPCION_DOCUMENTO);
                    session.removeAttribute(NUMERO_FOLIOS);
                }
				if(respuesta.getIdImprimible()!=0){
					request.getSession().setAttribute(WebKeys.ID_IMPRIMIBLE, new Integer(respuesta.getIdImprimible()));	
				}
                Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
                SGD sgd = new SGD(respuesta.getTurno(), usuario.getUsername());
                sgd.enviarTurnoDevolucion();
            }
        }

        else if (evento instanceof EvnRespDevolucion) {
            
            
			EvnRespDevolucion eventoAux = (EvnRespDevolucion)evento;
            
			if (eventoAux.getTipoEvento().equals(AWLiquidacionDevolucion.CONSULTAR_ITEMS_CHEQUEO))
			{
				session.setAttribute(WebKeys.ITEMS_CHEQUEO_DEVOLUCIONES,eventoAux.getListaItemsChequeo()); 
				session.setAttribute(TURNO_ANT_VAL, session.getAttribute(CTurno.TURNO_ANTERIOR));
				session.setAttribute(LISTA_ASOCIADOS,eventoAux.getListaCertificadosAsociados());
                                session.setAttribute(LISTA_CANALES_RECAUDO, eventoAux.getCanalRecaudoYcuentas());
			}
            
			else if (eventoAux.getTipoEvento().equals(AWLiquidacionDevolucion.AGREGAR_TURNO_ANT))
			{
				session.setAttribute(TURNO_ANT_VAL, session.getAttribute(CTurno.TURNO_ANTERIOR));
				session.setAttribute(LISTA_ASOCIADOS,eventoAux.getListaCertificadosAsociados());
				session.setAttribute(DOCUMENTO_PAGO,eventoAux.getDocumentoPago());
				session.setAttribute(TURNO_VALIDADO, eventoAux.getTurnoRespuestaAnterior());
                                session.setAttribute(LISTA_CANALES_RECAUDO, eventoAux.getCanalRecaudoYcuentas());
				/*session.setAttribute(FORMA_PAGO,eventoAux.getDocumentoPago().getTipoPago());
				session.setAttribute(VALOR_DOCUMENTO,""+eventoAux.getDocumentoPago().getValorDocumento());
				session.setAttribute(LISTA_SOLICITANTES_DEVOLUCION, solicitantes);
				if (eventoAux.getDocumentoPago() instanceof DocPagoCheque){
					DocPagoCheque dpch = (DocPagoCheque)eventoAux.getDocumentoPago();
					session.setAttribute(BANCO,dpch.getBanco().getNombre());
					session.setAttribute(NUMERO,dpch.getNoCheque());
					session.setAttribute(SALDO_A_FAVOR,""+dpch.getSaldoDocumento());
				}
				if (eventoAux.getDocumentoPago() instanceof DocPagoConsignacion) {
					DocPagoConsignacion dpc = (DocPagoConsignacion)eventoAux.getDocumentoPago();
					session.setAttribute(BANCO,dpc.getBanco().getNombre());
					session.setAttribute(NUMERO,dpc.getNoConsignacion());
					session.setAttribute(SALDO_A_FAVOR,""+dpc.getSaldoDocumento());
				}*/
				
			}
			
			else if (eventoAux.getTipoEvento().equals(AWLiquidacionDevolucion.AGREGAR_CONSIGNACION_CHEQUE))
			{
				session.setAttribute(TURNO_ANT_VAL, session.getAttribute(CTurno.TURNO_ANTERIOR));
				session.setAttribute(LISTA_ASOCIADOS,eventoAux.getListaCertificadosAsociados());
                                session.setAttribute(LISTA_CANALES_RECAUDO, eventoAux.getCanalRecaudoYcuentas());
				//CREAR OBJETO CONSIGNACION
		        DocumentoPago docPago = (DocumentoPago)eventoAux.getDocumentoPago().get(0);
		        consignaciones.add(docPago);
				session.setAttribute(LISTA_SOLICITANTES_DEVOLUCION, solicitantes);
				session.setAttribute(LISTA_CONSIGNACIONES_DEVOLUCION, consignaciones);
				session.removeAttribute(DOCUMENTO_PAGO);
                session.removeAttribute(VALOR_DOCUMENTO);
                session.removeAttribute(FORMA_PAGO);
                session.removeAttribute(BANCO);
                session.removeAttribute(NUMERO);
                session.removeAttribute(SALDO_A_FAVOR);
				
				
			}
			
			else if (eventoAux.getTipoEvento().equals(AWLiquidacionDevolucion.ELIMINAR_CONSIGNACION_CHEQUE))
			{
				session.setAttribute(TURNO_ANT_VAL, session.getAttribute(CTurno.TURNO_ANTERIOR));
				session.setAttribute(LISTA_ASOCIADOS,eventoAux.getListaCertificadosAsociados());
				session.setAttribute(LISTA_SOLICITANTES_DEVOLUCION, solicitantes);
				session.setAttribute(LISTA_CONSIGNACIONES_DEVOLUCION, consignaciones);
                                session.setAttribute(LISTA_CANALES_RECAUDO, eventoAux.getCanalRecaudoYcuentas());
			}
			
			
			
			else if (eventoAux.getTipoEvento().equals(AWLiquidacionDevolucion.AGREGAR_SOLICITANTE) 
					|| eventoAux.getTipoEvento().equals(AWLiquidacionDevolucion.ELIMINAR_SOLICITANTE))
			{
				session.setAttribute(TURNO_ANT_VAL, session.getAttribute(CTurno.TURNO_ANTERIOR));
				session.setAttribute(LISTA_ASOCIADOS,eventoAux.getListaCertificadosAsociados());
                                session.setAttribute(LISTA_CANALES_RECAUDO, eventoAux.getCanalRecaudoYcuentas());
			}
            
			else if (eventoAux.getTipoEvento().equals(AWLiquidacionDevolucion.ELIMINAR_TURNO_ANTERIOR))
			{
				session.setAttribute(TURNO_ANT_VAL, session.getAttribute(CTurno.TURNO_ANTERIOR));
				session.setAttribute(LISTA_ASOCIADOS,eventoAux.getListaCertificadosAsociados());
				session.removeAttribute(DOCUMENTO_PAGO);
                                session.setAttribute(LISTA_CANALES_RECAUDO, eventoAux.getCanalRecaudoYcuentas());
                session.removeAttribute(VALOR_DOCUMENTO);
                session.removeAttribute(FORMA_PAGO);
                session.removeAttribute(BANCO);
                session.removeAttribute(NUMERO);
                session.removeAttribute(SALDO_A_FAVOR);
			}

            
            
                      

        }
                /*AHERRENO
                 28/05/2012
                 REQ 076_151 TRANSACCION*/
                 Date fechaIni =  (Date) request.getSession().getAttribute("TIEMPO_INICIO_TRANSACCION");
                double tiempoSession =  (Double) request.getSession().getAttribute("TIEMPO_TRANSACCION");
                Date fechaFin =  new Date();
                TransaccionSIR transaccion = new TransaccionSIR();
                List <Transaccion> acciones = (List <Transaccion>) request.getSession().getAttribute("LISTA_TRANSACCION");
                long calTiempo = 0;
        try {
            calTiempo = transaccion.calculoTransaccion(fechaIni, fechaFin);
        } catch (Exception ex) {
            Logger.getLogger(AWLiquidacionDevolucion.class.getName()).log(Level.SEVERE, null, ex);
        }                
                DecimalFormat df = new DecimalFormat("0.000"); 
                double calculo = Double.valueOf(df.format(tiempoSession+((double)calTiempo/1000)).replace(',', '.'));
                System.out.println("El tiempo de la accion "+request.getParameter("ACCION")+" en milisegundos " +calTiempo );
                request.getSession().setAttribute("TIEMPO_TRANSACCION",calculo);
                Transaccion transaccionReg = new Transaccion();
                transaccionReg.setFechaTransaccion(fechaFin);
                transaccionReg.setAccionWeb(request.getParameter("ACCION"));
                transaccionReg.setTiempo(calTiempo);
                acciones.add(transaccionReg);
                request.getSession().setAttribute("LISTA_TRANSACCION",acciones);
                
                if(request.getParameter("ACCION").equals("LIQUIDAR")){
                EvnRespPago turno = (EvnRespPago) evento;
                    /*Se recorre la lista para almacenar la informacion del turno*/
                if(turno != null){                    
                        for (Transaccion transacion: acciones) {
                            transacion.setAnio(turno.getTurno().getAnio());
                            transacion.setIdCirculo(turno.getTurno().getIdCirculo());
                            transacion.setIdProceso(turno.getTurno().getIdProceso());
                            transacion.setIdTurno(turno.getTurno().getIdTurno());                    
                        }
                    transaccion.guardarTransaccion(acciones);
                    acciones.clear();
                    request.getSession().setAttribute("LISTA_TRANSACCION",acciones);
                    request.getSession().setAttribute("TIEMPO_TRANSACCION",Double.valueOf(0));                    
                    }  
                }
    }

}
