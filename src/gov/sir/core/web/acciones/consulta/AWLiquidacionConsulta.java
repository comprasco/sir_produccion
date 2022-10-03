package gov.sir.core.web.acciones.consulta;

import co.com.iridium.generalSIR.comun.modelo.Transaccion;
import co.com.iridium.generalSIR.transacciones.TransaccionSIR;
import gov.sir.core.eventos.comun.EvnLiquidacion;
import gov.sir.core.eventos.comun.EvnRespLiquidacion;
import gov.sir.core.eventos.comun.EvnRespPago;
import gov.sir.core.negocio.modelo.AlcanceGeografico;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CAlcanceGeografico;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CSolicitudConsulta;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.SeleccionConsultaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La accion web AWLiquidacionConsulta realiza liquidación de consultas
 *
 * @author jmendez
 */
public class AWLiquidacionConsulta extends SoporteAccionWeb {

	/** Constante que identifica la acción liquidar una consulta    **/
	public static final String LIQUIDAR_CONSULTA_SIMPLE = "LIQUIDAR_CONSULTA_SIMPLE";
	
	/** Constante que identifica la acción liquidar una consulta con la pantalla simplificada **/
	public static final String LIQUIDAR_CONSULTA_SIMPLE_SIMPLIFICADA = "LIQUIDAR_CONSULTA_SIMPLE_SIMPLIFICADA";

	/** Constante que identifica la acción liquidar una consulta compleja   **/
	public static final String LIQUIDAR_CONSULTA_COMPLEJA = "LIQUIDAR_CONSULTA_COMPLEJA";

	/** Constante que identifica la acción liquidar una consulta exenta   **/
	public static final String LIQUIDAR_CONSULTA_EXENTA = "LIQUIDAR_CONSULTA_EXENTA";

	/** Constante que identifica la acción liquidar una consulta compleja   **/
	public static final String LIQUIDAR_CONSULTA_OBSERVACION_FOLIO =
		"LIQUIDAR_CONSULTA_OBSERVACION_FOLIO";
	
	/** Constante que identifica la acción liquidar una consulta de constancia   **/
	public static final String LIQUIDAR_CONSULTA_CONSTANCIA = "LIQUIDAR_CONSULTA_CONSTANCIA";


	public static final String ROL_USUARIO_OPERATIVO = "SIR_ROL_USUARIO_OPERATIVO";
	public static final String ROL_USUARIO_OPERATIVO_CONSULTAS = CRoles.ROL_USUARIO_OPERATIVO_CONSULTAS;

	public static final String IMPRIMIR_RECIBO = "IMPRIMIR_RECIBO";


	/**
	* Decide a partir de la informacion recibida en el request, el tipo de evento
	* de negocio que debe generar.
        * @param request Solicitud proveniente del servidor web
        * @throws AccionWebException Si el parámetro ACCION no es válido
        * @return Objeto <code>Evento</code> a enviar a capa de negocio, con parámetros provenientes de web
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {

		String accion = request.getParameter(WebKeys.ACCION).trim();
                /*AHERRENO
                 24/05/2012
                 REQ 076_151 TRANSACCION*/
                request.getSession().setAttribute("TIEMPO_INICIO_TRANSACCION", new Date());
		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una acción válida");
		}

		if (accion.equals(AWLiquidacionConsulta.LIQUIDAR_CONSULTA_SIMPLE)) {
			return liquidarConsultaSimple(request);
		} else if (accion.equals(AWLiquidacionConsulta.LIQUIDAR_CONSULTA_CONSTANCIA)) {
			return liquidarConsultaConstancia(request);
		} if (accion.equals(AWLiquidacionConsulta.LIQUIDAR_CONSULTA_SIMPLE_SIMPLIFICADA)) {
			return liquidarConsultaSimpleSimplificada(request);
		} else if (accion.equals(AWLiquidacionConsulta.LIQUIDAR_CONSULTA_COMPLEJA)) {
			return liquidarConsultaCompleja(request);
		} else if (accion.equals(AWLiquidacionConsulta.LIQUIDAR_CONSULTA_EXENTA)) {
			return liquidarConsultaExenta(request);
		} else if (accion.equals(AWLiquidacionConsulta.LIQUIDAR_CONSULTA_OBSERVACION_FOLIO)) {
			return liquidarConsultaObservacionFolio(request);
		} else {
			throw new AccionInvalidaException("Debe indicar un tipo de consulta");
		}
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de liquidación de una consulta simple
	 * @param request Solicitud proveniente del servidor web
	 * @return evento <code>EvnAdministracionCiudadano</code>
	 * @throws AccionWebException
	 */
	private EvnLiquidacion liquidarConsultaSimple(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();

                limpiarDatosSolicitante(session);
                limpiarConsulta(session);

                SeleccionConsultaException excepcion = new SeleccionConsultaException();

                AWConsulta aWConsulta = new AWConsulta();

                String alcanceConsulta = request.getParameter(CAlcanceGeografico.ALCANCE_CONSULTA);
                session.setAttribute(CAlcanceGeografico.ALCANCE_CONSULTA, alcanceConsulta);

                String tipoConsulta = request.getParameter(AWConsulta.TIPO_CONSULTA);
                session.setAttribute(AWConsulta.TIPO_CONSULTA, tipoConsulta);

                String numMax = request.getParameter(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS);
                session.setAttribute(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS, numMax);
                
    			this.poblarSesionCiudadano(request,request.getSession());                

                SolicitudConsulta solicitud = aWConsulta.getValidadorConsulta(request, true, new SeleccionConsultaException()).getSolicitudConsulta();

                if (numMax != null && !numMax.equals("")) {
                        try {
                                int cantidad = Integer.parseInt(numMax);
                                if (cantidad < 1 || cantidad > 3) {
                                        excepcion.addError(numMax + " No es un valor numérico válido. La consulta simple debe tener de 1 a 3 consultas");
                                }
                                solicitud.setNumeroMaximoBusquedas(cantidad);
                        } catch (NumberFormatException e) {
                                excepcion.addError(numMax + " No es un valor numérico válido.");
                        }
                } else {
                        excepcion.addError("Debe proporcionar el número máximo de búsquedas a realizar");
                }

              if (excepcion.getErrores().size() > 0) {
                      throw excepcion;
              }

		EvnLiquidacion evento = getEvento(request, solicitud);
                evento.setAsignarEstacion(true);
		return evento;
	}
	
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de liquidación de una consulta de Constancia de no propiedad
	 * @param request Solicitud proveniente del servidor web
	 * @return evento <code>EvnAdministracionCiudadano</code>
	 * @throws AccionWebException
	 */
	private EvnLiquidacion liquidarConsultaConstancia(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();

                limpiarDatosSolicitante(session);
                limpiarConsulta(session);

                AWConsulta aWConsulta = new AWConsulta();

                String alcanceConsulta = request.getParameter(CAlcanceGeografico.ALCANCE_CONSULTA);
                session.setAttribute(CAlcanceGeografico.ALCANCE_CONSULTA, alcanceConsulta);

                String tipoConsulta = request.getParameter(AWConsulta.TIPO_CONSULTA);
                session.setAttribute(AWConsulta.TIPO_CONSULTA, tipoConsulta);
                
    			this.poblarSesionCiudadano(request,request.getSession());                

                SolicitudConsulta solicitud = aWConsulta.getValidadorConsulta(request, true, new SeleccionConsultaException()).getSolicitudConsulta();

                solicitud.setNumeroMaximoBusquedas(1);

		EvnLiquidacion evento = getEvento(request, solicitud);
                evento.setAsignarEstacion(true);
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de liquidación de una consulta simple. Aplica solo para pantalla
	 * simplificada. El usuario solo ingresa el apellido o razon social. Se toma por defecto
	 * el lcance como local, tipo de consulta simple y numero de buquedas 1.
	 * @param request Solicitud proveniente del servidor web
	 * @return evento <code>EvnAdministracionCiudadano</code>
	 * @throws AccionWebException
	 */
	private EvnLiquidacion liquidarConsultaSimpleSimplificada(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();

        limpiarDatosSolicitante(session);
        limpiarConsulta(session);
        session.removeAttribute(WebKeys.SIMPLIFICADO);
        SeleccionConsultaException excepcion = new SeleccionConsultaException();

        AWConsulta aWConsulta = new AWConsulta();

        String alcanceConsulta = request.getParameter(CAlcanceGeografico.ALCANCE_CONSULTA);
        session.setAttribute(CAlcanceGeografico.ALCANCE_CONSULTA, alcanceConsulta);

        String tipoConsulta = request.getParameter(AWConsulta.TIPO_CONSULTA);
        session.setAttribute(AWConsulta.TIPO_CONSULTA, tipoConsulta);

        String numMax = request.getParameter(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS);
        session.setAttribute(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS, numMax);
                
    	/**POBLAR SESION CIUDADANO*/
        String tipoDocSolicitante = COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD;
        String primerApellidoSolicitante = request.getParameter(CCiudadano.APELLIDO1);
        if ((primerApellidoSolicitante == null)
           			|| primerApellidoSolicitante.trim().equals("")) {
           			excepcion.addError("Debe escribir el Solicitante");
        }  			
    	Ciudadano ciudadano =  new Ciudadano();
    	ciudadano.setApellido1(primerApellidoSolicitante);
    	ciudadano.setTipoDoc(tipoDocSolicitante);
    	//Se setea el circulo del ciudadano
        Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
        ciudadano.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);
               
        session.setAttribute(CCiudadano.TIPODOC, tipoDocSolicitante);
        session.setAttribute(CCiudadano.APELLIDO1, primerApellidoSolicitante);
        session.setAttribute(CAlcanceGeografico.ALCANCE_CONSULTA, alcanceConsulta);
                
        session.setAttribute(AWConsulta.CIUDADANO_SOLICITANTE, ciudadano);

        SolicitudConsulta solicitud = aWConsulta.getValidadorConsulta(request, false, new SeleccionConsultaException()).getSolicitudConsulta();
        solicitud.setCiudadano(ciudadano);
        if (numMax != null && !numMax.equals("")) {
            try {
                    int cantidad = Integer.parseInt(numMax);
                    if (cantidad < 1 || cantidad > 3) {
                            excepcion.addError(numMax + " No es un valor numérico válido. La consulta simple debe tener de 1 a 3 consultas");
                    }
                    solicitud.setNumeroMaximoBusquedas(cantidad);
            } catch (NumberFormatException e) {
                    excepcion.addError(numMax + " No es un valor numérico válido.");
            }
	    } else {
	            excepcion.addError("Debe proporcionar el número máximo de búsquedas a realizar");
	    }

        if (excepcion.getErrores().size() > 0) {
        	throw excepcion;
        }

        EvnLiquidacion evento = getEvento(request, solicitud);
        evento.setTipoEvento(evento.SOLICITAR_LIQUIDAR_SIMPLIFICADO);
        evento.setAsignarEstacion(true);
        Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
		evento.setRol(rol);
        return evento;
	}
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de liquidación de una consulta compleja
	 * @param request Solicitud proveniente del servidor web
	 * @return evento <code>EvnAdministracionCiudadano</code>
	 * @throws AccionWebException
	 */
	private EvnLiquidacion liquidarConsultaCompleja(HttpServletRequest request)
		throws AccionWebException {

              HttpSession session = request.getSession();

              limpiarDatosSolicitante(session);
              limpiarConsulta(session);

              SeleccionConsultaException excepcion = new SeleccionConsultaException();

              AWConsulta aWConsulta = new AWConsulta();

              String tipoConsulta = request.getParameter(AWConsulta.TIPO_CONSULTA);
              session.setAttribute(AWConsulta.TIPO_CONSULTA, tipoConsulta);

              String alcanceConsulta = request.getParameter(CAlcanceGeografico.ALCANCE_CONSULTA);
              session.setAttribute(CAlcanceGeografico.ALCANCE_CONSULTA, alcanceConsulta);

              String numMax = request.getParameter(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS);
              session.setAttribute(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS, numMax);
              
              this.poblarSesionCiudadano(request,request.getSession());                    

              SolicitudConsulta solicitud = aWConsulta.getValidadorConsulta(request, true, new SeleccionConsultaException()).getSolicitudConsulta();

              if (numMax != null && !numMax.equals("")) {
                      try {
                              int cantidad = Integer.parseInt(numMax);
                              if (cantidad < 4) {
                                      excepcion.addError(numMax + " No es un valor numérico válido. La consulta compleja debe tener al menos 3 consultas");
                              }
                              solicitud.setNumeroMaximoBusquedas(cantidad);
                      } catch (NumberFormatException e) {
                              excepcion.addError(numMax + " No es un valor numérico válido.");
                      }
              } else {
                      excepcion.addError("Debe proporcionar el número máximo de búsquedas a realizar");
              }

              if (excepcion.getErrores().size() > 0) {
                      throw excepcion;
              }


		EvnLiquidacion evento = getEvento(request, solicitud);
		return evento;
	}


	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de liquidación de una consulta exenta
	 * @param request Solicitud proveniente del servidor web
	 * @return evento <code>EvnAdministracionCiudadano</code>
	 * @throws AccionWebException
	 */
	private EvnLiquidacion liquidarConsultaExenta(HttpServletRequest request)
		throws AccionWebException {

			  HttpSession session = request.getSession();

			  limpiarDatosSolicitante(session);
			  limpiarConsulta(session);

			  SeleccionConsultaException excepcion = new SeleccionConsultaException();

			  AWConsulta aWConsulta = new AWConsulta();

			  String tipoConsulta = request.getParameter(AWConsulta.TIPO_CONSULTA);
			  session.setAttribute(AWConsulta.TIPO_CONSULTA, tipoConsulta);

			  String alcanceConsulta = request.getParameter(CAlcanceGeografico.ALCANCE_CONSULTA);
			  session.setAttribute(CAlcanceGeografico.ALCANCE_CONSULTA, alcanceConsulta);

			  String numMax = request.getParameter(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS);
			  session.setAttribute(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS, numMax);

			  this.poblarSesionCiudadano(request,request.getSession());      			  
			  
			  SolicitudConsulta solicitud = aWConsulta.getValidadorConsulta(request, true, new SeleccionConsultaException()).getSolicitudConsulta();

/*			  if (numMax != null && !numMax.equals("")) {
					  try {
							  int cantidad = Integer.parseInt(numMax);
							  solicitud.setNumeroMaximoBusquedas(cantidad);
					  } catch (NumberFormatException e) {
							  excepcion.addError(numMax + " No es un valor numérico válido.");
					  }
			  } else {
					  excepcion.addError("Debe proporcionar el número máximo de búsquedas a realizar");
			  }
*/

			  ////////////////////////////////////

			boolean isInternacional = false;


			String TOficina= request.getParameter(WebKeys.TIPO_OFICINA_I_N);
			if(TOficina.equalsIgnoreCase(WebKeys.TIPO_OFICINA_INTERNACIONAL)){
				isInternacional=true;
			}

			String tipo_encabezado = request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO);
			String id_encabezado = request.getParameter(CSolicitudRegistro.ID_ENCABEZADO);
			String fecha = request.getParameter(CSolicitudRegistro.CALENDAR);

			session.setAttribute(CSolicitudRegistro.TIPO_ENCABEZADO, tipo_encabezado);
			session.setAttribute(CSolicitudRegistro.ID_ENCABEZADO, id_encabezado);
			session.setAttribute(CSolicitudRegistro.CALENDAR, fecha);

			String nomOficinaInternacional="";
			String nomDepto="";
			String idDepto="";
			String nomMunic="";
			String idMunic="";
			String nomVereda="";
			String idVereda="";
			String tipo_oficina="";
			String numero_oficina="";
			String id_oficina="";
                        /*
                        *  @author Carlos Torres
                        *  @chage   se agrega validacion de version diferente
                        *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                        */
                        String version="";
			if(isInternacional){
				nomOficinaInternacional = request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL);
				session.setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL, nomOficinaInternacional);
			}else{

				nomDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
				idDepto = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
				session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, nomDepto);
				session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, idDepto);

				nomMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
				idMunic = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
				session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, nomMunic);
				session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, idMunic);


				nomVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
				idVereda = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
				session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, nomVereda);
				session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, idVereda);

				if ( idVereda == null || nomDepto.equals("") || nomMunic.equals("") ) {
					idVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), idDepto, idMunic);
				}

				tipo_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
				numero_oficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);
				id_oficina =	request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
				session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO, tipo_oficina);
				session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM, numero_oficina);
				session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, id_oficina);
                                /*
                                *  @author Carlos Torres
                                *  @chage   se agrega validacion de version diferente
                                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                */
                                session.setAttribute(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, version);

			}

			if ((tipo_encabezado == null) || tipo_encabezado.equals("SIN_SELECCIONAR")) {
				excepcion.addError("El tipo de encabezado es inválido");
			}

			Calendar fechaDocumento = darFecha(fecha);

			if (fechaDocumento == null) {
				excepcion.addError("La fecha del documento es invalida");
			}

			if ((fecha == null) || fecha.trim().equals("")) {
				excepcion.addError("La fecha del encabezado es inválida");
			}

			if((nomOficinaInternacional!=null && nomOficinaInternacional.length()>0 ) ||
			(idVereda!=null && idVereda.length()>0)||
			(idDepto!=null && idDepto.length()>0)||
			(idMunic!=null && idMunic.length()>0)||
			(id_oficina!=null&&id_oficina.length()>0)){

				if(isInternacional){
					if((nomOficinaInternacional == null) || nomOficinaInternacional.trim().equals("")) {
						excepcion.addError("El nombre de la oficina internacional es inválido");
					}
				}else{

					if ((idVereda == null) || idVereda.trim().equals("")) {
						excepcion.addError("La vereda o ciudad es inválida");
					}

					if ((idDepto == null) || idDepto.trim().equals("")) {
						excepcion.addError("El departamento es inválido");
					}

					if ((idMunic == null) || idMunic.trim().equals("")) {
						excepcion.addError("El municipio es inválido");
					}

					if ((id_oficina == null) || id_oficina.trim().equals("")) {
						excepcion.addError("El número de oficina es inválido");
					}
				}
			}

			if (excepcion.getErrores().size() > 0) {
				throw excepcion;
			}

			String valorDepartamento =
				request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
			String valorMunicipio =
				request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
			String valorVereda =
				request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
			String idOficina =
				request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
                        /*
                         *  @author Carlos Torres
                         *  @chage   se agrega validacion de version diferente
                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                         */
                        String oficinaVersion = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);

			String nomOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_TIPO);
			String numOficina = request.getParameter(CSolicitudRegistro.ANOTACION_OFICINA_DOCUMENTO_NUM);

			Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
			Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);

			//solicitud.setCirculo(circulo);
			//solicitud.setComentario("");
			//solicitud.setFecha(new Date(System.currentTimeMillis()));
			//solicitud.setProceso(proceso);

			OficinaOrigen oficinaOrigen = new OficinaOrigen();

			if(!isInternacional){

				if (nomOficina != null) {
					oficinaOrigen.setNombre(nomOficina);
				}

				if (numOficina != null) {
					oficinaOrigen.setNumero(numOficina);
				}

				oficinaOrigen.setIdOficinaOrigen(idOficina);
                                /*
                                 *  @author Carlos Torres
                                 *  @chage   se agrega validacion de version diferente
                                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                 */
                                if(oficinaVersion != null && !oficinaVersion.equalsIgnoreCase("")){
                                    oficinaOrigen.setVersion(Long.parseLong(oficinaVersion));
                                }
                                
				Vereda vereda = new Vereda();

				if (nomVereda != null) {
					vereda.setNombre(nomVereda);
				}

				vereda.setIdVereda(valorVereda);
				vereda.setIdDepartamento(valorDepartamento);
				vereda.setIdMunicipio(valorMunicipio);

				oficinaOrigen.setVereda(vereda);
			}

			Documento documento = new Documento();
			documento.setFecha(fechaDocumento.getTime());

			if((nomOficinaInternacional!=null && nomOficinaInternacional.length()>0 ) ||
			(idVereda!=null && idVereda.length()>0)||
			(idDepto!=null && idDepto.length()>0)||
			(idMunic!=null && idMunic.length()>0)||
			(id_oficina!=null&&id_oficina.length()>0)){

				if(!isInternacional){
					documento.setOficinaInternacional(null);
					documento.setOficinaOrigen(oficinaOrigen);
				}else{
					documento.setOficinaOrigen(null);
					documento.setOficinaInternacional(nomOficinaInternacional);
				}

			}

			TipoDocumento tipoDoc = new TipoDocumento();
			tipoDoc.setIdTipoDocumento(tipo_encabezado);

			List tiposDocs = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
			Iterator itTiposDocs = tiposDocs.iterator();

			while (itTiposDocs.hasNext()) {
				ElementoLista elemento = (ElementoLista) itTiposDocs.next();

				if (elemento.getId().equals(tipo_encabezado)) {
					tipoDoc.setNombre(elemento.getValor());
				}
			}

			documento.setTipoDocumento(tipoDoc);
			documento.setNumero(id_encabezado);
			solicitud.setDocumento(documento);
			  //////////////////////////////////////


		String impRecibo = request.getParameter(IMPRIMIR_RECIBO);
		boolean omitirRecibo = false;
		if (impRecibo.equals("NO")){
			omitirRecibo = true;
		}

		EvnLiquidacion evento = getEvento(request, solicitud);
		evento.setOmitirRecibo(omitirRecibo);
		return evento;
	}

	/**
	 * @param fechaInterfaz
	 * @return
	 */
	private Calendar darFecha(String fechaInterfaz) {
		java.util.Date date = null;

		try {
			date = DateFormatUtil.parse(fechaInterfaz);
			if(fechaInterfaz.indexOf("-")!=-1){
				return null;
			}
		} catch (ParseException e) {
			return null;
		} catch (Throwable t) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		String[] partido = fechaInterfaz.split("/");
		if (partido.length == 3) {
			int dia = Integer.parseInt(partido[0]);
			int mes = Integer.parseInt(partido[1]) - 1;
			int año = Integer.parseInt(partido[2]);
			calendar.set(año, mes, dia);

			if ((calendar.get(Calendar.YEAR) == año) && (calendar.get(Calendar.MONTH) == mes) && (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
				return calendar;
			}
		}
		return null;
	}



	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de liquidación de una consulta de observación de folio
	 * @param request Solicitud proveniente del servidor web
	 * @return evento <code>EvnAdministracionCiudadano</code>
	 * @throws AccionWebException
	 */
	private EvnLiquidacion liquidarConsultaObservacionFolio(HttpServletRequest request)
		throws AccionWebException {

                HttpSession session = request.getSession();

                limpiarDatosSolicitante(session);
                limpiarConsulta(session);
                
    			this.poblarSesionCiudadano(request,request.getSession());                

                SeleccionConsultaException excepcion = new SeleccionConsultaException();

                AWConsulta aWConsulta = new AWConsulta();

                String tipoConsulta = request.getParameter(AWConsulta.TIPO_CONSULTA);
                session.setAttribute(AWConsulta.TIPO_CONSULTA, tipoConsulta);

                String alcanceConsulta = request.getParameter(CAlcanceGeografico.ALCANCE_CONSULTA);
                session.setAttribute(CAlcanceGeografico.ALCANCE_CONSULTA, alcanceConsulta);

                
                SolicitudConsulta solicitud = aWConsulta.getValidadorConsulta(request, true, new SeleccionConsultaException()).getSolicitudConsulta();

		EvnLiquidacion evento = getEvento(request, solicitud);
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de generar un evento de liquidación consulta
	 * @param request Solicitud proveniente del servidor web
         * @param solicitud Solicitud de nueva consulta
	 * @return evento <code>EvnAdministracionCiudadano</code>
	 * @throws AccionWebException
	 */
	private EvnLiquidacion getEvento(HttpServletRequest request, SolicitudConsulta solicitud) throws AccionWebException {

                HttpSession session = request.getSession();

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
			(org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(
				SMARTKeys.USUARIO_EN_SESION);

		Usuario usuario = (Usuario)session.getAttribute(WebKeys.USUARIO);

		LiquidacionTurnoConsulta liquidacionTurnoConsulta = new LiquidacionTurnoConsulta();
		liquidacionTurnoConsulta.setSolicitud(solicitud);
		liquidacionTurnoConsulta.setAlcanceGeografico(getAlcanceGeografico(request));

		EvnLiquidacion evento =
			new EvnLiquidacion(
				usuarioAuriga,
				liquidacionTurnoConsulta,
				(Proceso) session.getAttribute(WebKeys.PROCESO),
				(Estacion) session.getAttribute(WebKeys.ESTACION),
				true, usuario, EvnLiquidacion.SOLICITAR_LIQUIDAR);

				evento.setCirculo((Circulo) session.getAttribute(WebKeys.CIRCULO));

		evento.setUID(session.getId());
		return evento;
	}

	/**
	 * Este método se encarga de tomar los datos de una solicitud a partir de la sesión del usuario
	 * @param session Objeto <code>Session</code> del servidor de servlets
	 * @return evento <code>EvnAdministracionCiudadano</code>
	 * @throws AccionWebException
	 */
	private SolicitudConsulta getSolicitud(HttpSession session) {
		session.removeAttribute(WebKeys.PAGO);
		session.removeAttribute(WebKeys.LIQUIDACION);
		session.removeAttribute(WebKeys.VALOR_LIQUIDACION);

		SolicitudConsultaValidator validadorSolicitudConsulta =
			(SolicitudConsultaValidator) session.getAttribute(AWConsulta.SOLICITUD_CONSULTA_VALIDATOR);
		SolicitudConsulta solicitud = validadorSolicitudConsulta.getSolicitudConsulta();
		return solicitud;
	}

	/**
	 * Este método se encarga de tomar los datos de una alcance geográfico a partir de la sesión del usuario
	 * @param request Solicitud proveniente del servidor web
	 * @return evento <code>EvnAdministracionCiudadano</code>
	 * @throws AccionWebException
	 */
	private AlcanceGeografico getAlcanceGeografico(HttpServletRequest request) throws AccionWebException {
                String alcance = (String) request.getParameter(CAlcanceGeografico.ALCANCE_CONSULTA);
                if (alcance == null) {
                    ValidacionParametrosException vpe = new ValidacionParametrosException();
                    vpe.addError("Debe indicar un alcance geográfico para la consulta");
                    throw vpe;
                }
		AlcanceGeografico alcanceGeografico = new AlcanceGeografico();
		alcanceGeografico.setIdAlcanceGeografico(alcance);

		if (alcance.equals(AlcanceGeografico.TIPO_LOCAL)) {
			alcanceGeografico.setNombre(CAlcanceGeografico.LOCAL);
		} else if (alcance.equals(AlcanceGeografico.TIPO_NACIONAL)) {
			alcanceGeografico.setNombre(CAlcanceGeografico.NACIONAL);
		}

		return alcanceGeografico;
	}

	/**
	 * Este método se encarga de manejar el evento de respuesta proveniente
	 * de la acción de negocio.
	 * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el evento
         * @param request Solicitud proveniente del servidor web
         * @param evento Evento obtenido como respuesta de la capa de negocio
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		HttpSession session = request.getSession();

		if (evento instanceof EvnRespLiquidacion) {
			EvnRespLiquidacion respuesta = (EvnRespLiquidacion) evento;

			if (respuesta != null) {
				if (respuesta.getTipoEvento().equals(EvnRespLiquidacion.LIQUIDACION)) {
					session.removeAttribute(WebKeys.SIMPLIFICADO);
					Liquidacion liquidacion = respuesta.getLiquidacion();
					session.setAttribute(WebKeys.LIQUIDACION, liquidacion);
					NumberFormat nf = NumberFormat.getInstance();
					session.setAttribute(
						WebKeys.VALOR_LIQUIDACION,
						String.valueOf(liquidacion.getValor()));

                                        // BUG 5679
                                        DocumentoPago documentoEfectivo = new DocPagoEfectivo(liquidacion.getValor());
                                        AplicacionPago aplicacionEfectivo =
                                                new AplicacionPago(documentoEfectivo, liquidacion.getValor());
                                        session.setAttribute(WebKeys.APLICACION_EFECTIVO, aplicacionEfectivo);
                                        session.setAttribute(WebKeys.SOLICITUD, liquidacion.getSolicitud());

				}
				if (respuesta.getTipoEvento().equals(EvnRespLiquidacion.LIQUIDACION_SIMPLIFICADO)) {
					Liquidacion liquidacion = respuesta.getLiquidacion();
					session.setAttribute(WebKeys.LIQUIDACION, liquidacion);
					session.setAttribute(WebKeys.SOLICITUD, liquidacion.getSolicitud());
					session.setAttribute(WebKeys.SIMPLIFICADO, EvnRespLiquidacion.LIQUIDACION_SIMPLIFICADO);
					Turno turno = respuesta.getTurno();
	                
	                request.getSession().setAttribute(WebKeys.TURNO, turno);
	                request.getSession().removeAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);
	                if(respuesta.getIdImprimible()!=0){
						request.getSession().setAttribute(WebKeys.ID_IMPRIMIBLE, new Integer(respuesta.getIdImprimible()));	
					}
	            }
			}
		} else if (evento instanceof EvnRespPago) {
			EvnRespPago respuesta = (EvnRespPago) evento;
			if (respuesta != null) {
				if (respuesta.getTipoEvento().equals(EvnRespPago.PROCESAMIENTO_PAGO)) {
					Turno turno = respuesta.getTurno();
					session.setAttribute(WebKeys.TURNO, turno);
				}
			}
		}
                /*AHERRENO
                 24/05/2012
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
            Logger.getLogger(AWLiquidacionConsulta.class.getName()).log(Level.SEVERE, null, ex);
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
                
                if(request.getParameter("ACCION").equals("LIQUIDAR_CONSULTA_SIMPLE_SIMPLIFICADA")){
                EvnRespLiquidacion turno = (EvnRespLiquidacion) evento;
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

        /**
         * Este método permite poblar la sesión con los parámetros que ha digitado el usuario
         * @param request Objeto <code>HttpServletRequest</code> que contiene los parámetros
         * @param session Objeto <code>HttpSession</code> en donde deben quedar los parámetros
         */
        private void poblarSesion(HttpServletRequest request, HttpSession session) {
            String tipoDocSolicitante = request.getParameter(CCiudadano.TIPODOC);
            String numIdentSolicitante = request.getParameter(CCiudadano.IDCIUDADANO);
            String primerApellidoSolicitante = request.getParameter(CCiudadano.APELLIDO1);
            String segundoApellidoSolicitante = request.getParameter(CCiudadano.APELLIDO2);
            String nombreSolicitante = request.getParameter(CCiudadano.NOMBRE);
            String alcanceConsulta = request.getParameter(CAlcanceGeografico.ALCANCE_CONSULTA);
            String numMax = request.getParameter(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS);

			String tipoConsulta = (String)request.getSession().getAttribute(AWConsulta.TIPO_CONSULTA);

            session.setAttribute(CCiudadano.TIPODOC, tipoDocSolicitante);
            session.setAttribute(CCiudadano.IDCIUDADANO, numIdentSolicitante);
            session.setAttribute(CCiudadano.APELLIDO1, primerApellidoSolicitante);
            session.setAttribute(CCiudadano.APELLIDO2, segundoApellidoSolicitante);
            session.setAttribute(CCiudadano.NOMBRE, nombreSolicitante);
            session.setAttribute(CAlcanceGeografico.ALCANCE_CONSULTA, alcanceConsulta);
            session.setAttribute(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS, numMax);

			session.setAttribute(AWConsulta.TIPO_CONSULTA, tipoConsulta);

			// Salvar información primera pantalla de registro
			session.setAttribute(WebKeys.TIPO_OFICINA_I_N,
				request.getParameter(WebKeys.TIPO_OFICINA_I_N));
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));

			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));

			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO));
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));

			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));

			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
                        /*
                                *  @author Carlos Torres
                                *  @chage   se agrega validacion de version diferente
                                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                */
                        session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION));
                        
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));

			session.setAttribute(WebKeys.TEXT_OFICINA_INTERNACIONAL,
				request.getParameter(WebKeys.TEXT_OFICINA_INTERNACIONAL));

			session.setAttribute(WebKeys.TIPO_OFICINA_INTERNACIONAL,
				request.getParameter(WebKeys.TIPO_OFICINA_INTERNACIONAL));

			session.setAttribute(CSolicitudRegistro.TIPO_ENCABEZADO,
				request.getParameter(CSolicitudRegistro.TIPO_ENCABEZADO));

			session.setAttribute(CSolicitudRegistro.ID_ENCABEZADO,
				request.getParameter(CSolicitudRegistro.ID_ENCABEZADO));

			session.setAttribute(CSolicitudRegistro.CALENDAR,
				request.getParameter(CSolicitudRegistro.CALENDAR));
        }
        
        
        /**
         * Método que permite guardar la información del ciudadano en la sesión.
         * @param request
         * @param session
         */
        private void poblarSesionCiudadano(HttpServletRequest request, HttpSession session) {
            String tipoDocSolicitante = request.getParameter(CCiudadano.TIPODOC);
            String numIdentSolicitante = request.getParameter(CCiudadano.IDCIUDADANO);
            String primerApellidoSolicitante = request.getParameter(CCiudadano.APELLIDO1);
            String segundoApellidoSolicitante = request.getParameter(CCiudadano.APELLIDO2);
            String nombreSolicitante = request.getParameter(CCiudadano.NOMBRE);
            String alcanceConsulta = request.getParameter(CAlcanceGeografico.ALCANCE_CONSULTA);
            String numMax = request.getParameter(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS);

			String tipoConsulta = (String)request.getSession().getAttribute(AWConsulta.TIPO_CONSULTA);

            session.setAttribute(CCiudadano.TIPODOC, tipoDocSolicitante);
            session.setAttribute(CCiudadano.IDCIUDADANO, numIdentSolicitante);
            session.setAttribute(CCiudadano.APELLIDO1, primerApellidoSolicitante);
            session.setAttribute(CCiudadano.APELLIDO2, segundoApellidoSolicitante);
            session.setAttribute(CCiudadano.NOMBRE, nombreSolicitante);
            session.setAttribute(CAlcanceGeografico.ALCANCE_CONSULTA, alcanceConsulta);
            session.setAttribute(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS, tipoConsulta.equals(TipoConsulta.TIPO_CONSTANCIA)?"1":numMax);
        }

        private void limpiarConsulta(HttpSession session) {
                session.removeAttribute(CAlcanceGeografico.ALCANCE_CONSULTA);
                session.removeAttribute(CSolicitudConsulta.NUMERO_MAX_BUSQUEDAS);
                session.removeAttribute(AWConsulta.RESULTADOS_CONSULTA);
                session.removeAttribute(AWConsulta.RESULTADOS_SELECCIONADOS_CONSULTA);
                session.removeAttribute(AWConsulta.PAGINADOR_RESULTADOS);
                session.removeAttribute(AWConsulta.PAGINADOR_RESULTADOS_SELECCIONADOS);
                session.removeAttribute(AWConsulta.SOLICITUD_CONSULTA_VALIDATOR);
                session.removeAttribute(AWConsulta.ACCION_SIGUIENTE);
        }

        private void limpiarDatosSolicitante(HttpSession session) {
                session.removeAttribute(CCiudadano.TIPODOC);
                session.removeAttribute(CCiudadano.IDCIUDADANO);
                session.removeAttribute(CCiudadano.APELLIDO1);
                session.removeAttribute(CCiudadano.APELLIDO2);
                session.removeAttribute(CCiudadano.NOMBRE);
        }
}
