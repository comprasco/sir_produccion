package gov.sir.core.web.acciones.fotocopia;

import co.com.iridium.generalSIR.comun.modelo.Transaccion;
import co.com.iridium.generalSIR.transacciones.TransaccionSIR;
import gov.sir.core.eventos.comun.EvnRespLiquidacion;
import gov.sir.core.eventos.fotocopia.EvnFotocopia;
import gov.sir.core.eventos.fotocopia.EvnFotocopiaCrear;
import gov.sir.core.eventos.fotocopia.EvnLiquidarFotocopiaNegar;
import gov.sir.core.eventos.fotocopia.EvnRespFotocopia;
import gov.sir.core.eventos.fotocopia.EvnVerificarDocumentosFotocopia;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocumentoFotocopia;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoFotocopia;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDocumento;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CSolicitudFotocopia;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionLiquidacionFotocopiasException;
import gov.sir.core.web.acciones.excepciones.ValidacionReliquidacionFotocopiasException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;


/**
 * @author dlopez
 */
public class AWLiquidacionFotocopia extends SoporteAccionWeb {

     // accion propagada a la pagina
     public final static String PAGE_REGION__DOCUMENTOSASOCIADOSADD_ACTION = AW_FotocopiasConstants.DOCUMENTOSASOCIADOSADD_ACTION;

     // accion propagada a la pagina
     public final static String PAGE_REGION__DOCUMENTOSASOCIADOSDEL_ACTION = AW_FotocopiasConstants.DOCUMENTOSASOCIADOSDEL_ACTION;

	/**
	 * Constante que identifica que se desea liquidar la
	 * solicitud de fotocopias (En la parte inicial del proceso)
	 */
	public final static String LIQUIDAR = "LIQUIDAR";


	/**
	* Constante que identifica que se desea liquidar la
	* solicitud de fotocopias una vez determinado el tipo de fotocopia
	* y el número de hojas.
	*/
	public static final String RELIQUIDAR = AW_FotocopiasConstants.RELIQUIDAR_ACTION;




	public static final String NOEXPEDIR_SOLICITUDFOTOCOPIAS_ALLIQUIDAR_ACTION = AW_FotocopiasConstants.NOEXPEDIR_SOLICITUDFOTOCOPIAS_ALLIQUIDAR_ACTION;

	/**
	 * Variable donde se guarda la accion enviada en el request
	 */
	private String accion;

	/**
	 * Constructor de la clase AWLiquidacionFotocopia
	 */
	public AWLiquidacionFotocopia() {
		super();
	}


	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en
	* cualquier otro caso
	*/
	public Evento
	perform( HttpServletRequest request )
	throws AccionWebException {

		accion = request.getParameter(WebKeys.ACCION);
                /*AHERRENO
                 28/05/2012
                 REQ 076_151TRANSACCION*/
                request.getSession().setAttribute("TIEMPO_INICIO_TRANSACCION", new Date());        
		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}

		if ( LIQUIDAR.equals( accion ) ) {
			return doCrearSolicitud( request );
		}
		else if( RELIQUIDAR.equals( accion ) ) {
			return doVerificarDocumentosAsociados( request );
		}
		else if( NOEXPEDIR_SOLICITUDFOTOCOPIAS_ALLIQUIDAR_ACTION.equals( accion ) ) {
			return doNoExpedirSolicitudFotocopiasAlLiquidar( request );
		}
		else if( PAGE_REGION__DOCUMENTOSASOCIADOSADD_ACTION.equals( accion ) ) {
			return pageRegionProcessor_OnAdd( request );
		}
		else if( PAGE_REGION__DOCUMENTOSASOCIADOSDEL_ACTION.equals( accion ) ) {
			return pageRegionProcessor_OnDel( request );
		}
		else {
			throw new AccionInvalidaException("La accion " + accion + " no es válida.");
		}
	}

  /**
   * doNoExpedirSolicitudFotocopiasAlLiquidar
   *
   * @param request HttpServletRequest
   * @return Evento
   */
	private Evento
	doNoExpedirSolicitudFotocopiasAlLiquidar( HttpServletRequest request )
	throws AccionWebException {

	   // get the request
	   HttpSession session = request.getSession();
	   
	   this.saveState(request, session);

	   // event reference variables
		gov.sir.core.negocio.modelo.Usuario              usuarioSIR;
		org.auriga.core.modelo.transferObjects.Usuario   usuario;
		String                                           tipoAccion;
		String                                           respuestaWf;
		Turno  														 turno;
		Fase  														 fase;

		// poblar valores para construir el evento, poblar valores para poder validar
		usuario     = (org.auriga.core.modelo.transferObjects.Usuario)session.getAttribute( SMARTKeys.USUARIO_EN_SESION );
		usuarioSIR  = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute( WebKeys.USUARIO );
		tipoAccion  = EvnFotocopia.NOEXPEDIRSOLICITUDFOTOCOPIAS_ALLIQUIDAR_EVENT;
		respuestaWf = AW_FotocopiasConstants.WF_LINK_NOEXPEDIR_SOLICITUDFOTOCOPIAS_ALLIQUIDAR;
		fase        = (Fase)session.getAttribute( WebKeys.FASE );
		turno       = (Turno)session.getAttribute( WebKeys.TURNO );


	   // ErrorCollector declaration (acumular los errores)
		ValidacionReliquidacionFotocopiasException exception
			= new ValidacionReliquidacionFotocopiasException();

		// validators ----------------------------------------------

		// solo se exige que existan notas informativas
		List temporalNotasList = turno.getNotas();
		if( ( null == temporalNotasList )
		|| ( temporalNotasList.size() <= 0 ) ) {
                        /** @author : HGOMEZ
                        *** @change : Se corrige mensaje "... al respecto ...", en lugar de ""... la respecto ..."".
                        *** Caso Mantis : 12288
                        */
			StringBuffer descripcion = new StringBuffer();
			descripcion.append( "Cuando no se va a expedir solicitud de fotocopias al liquidar " );
			descripcion.append( "se deben colocar notas informativas al respecto. " );
			exception.addError( descripcion.toString()  );

		}

		// raise application error
		if( exception.getErrores().size() > 0 ) {
			throw exception;
		}
		// ----------------------------------------------------------

	   // parametros adicionales para construir el evento (adicionados por necesidad de impresion)
		gov.sir.core.negocio.modelo.Circulo circulo;
		String                              sessionId;

		circulo   = (gov.sir.core.negocio.modelo.Circulo)session.getAttribute( WebKeys.CIRCULO );
		sessionId = session.getId();


      // valores necesarios para propagar al business-layer dentro del evento.
		EvnLiquidarFotocopiaNegar event;
		event = new EvnLiquidarFotocopiaNegar( usuario, usuarioSIR, turno, fase, tipoAccion, respuestaWf );

                event.setCirculo( circulo );
		event.setSessionId( sessionId );

		return event;
	}
	// ----------------------------------------------------------------------------------------------


  // PAGE_EVENT: elimina uno de los docuemtos
        // asociados que se encuentran en sesion

        private EvnFotocopia pageRegionProcessor_OnDel( HttpServletRequest request )
        throws AccionWebException {
        	
        	this.saveState(request, request.getSession());

           List documentosAsociados = (List) request.getSession().getAttribute( AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS );

           if( null == documentosAsociados ){
             documentosAsociados = new ArrayList();
             request.getSession().setAttribute( AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS, documentosAsociados );
           }

           // guarda un indice del elemento a eliminar
           String[] documentosAsociadosView_SelectedItems = request.getParameterValues( AW_FotocopiasConstants.PARAMETER_SELECTEDITEMS ); //AW_FotocopiasConstants.PARAMETER_TIPODOCUMENTO

           if( documentosAsociadosView_SelectedItems != null ) {
             DocumentoAsociado_Item[] tempDocAsociados = new DocumentoAsociado_Item[documentosAsociados.size()];
             tempDocAsociados = (DocumentoAsociado_Item[])documentosAsociados.toArray( tempDocAsociados );

             // foreach selected item ...
             for( int j=0; j < documentosAsociadosView_SelectedItems.length; j++ ) {

               // ... find in the array and delete
               // for (int i = 0; i < tempDocAsociados.length; i++) {

                 String tempElement = documentosAsociadosView_SelectedItems[j];
                 int index = -1;
                 try {
                   index = Integer.parseInt(tempElement);
                   documentosAsociados.remove(tempDocAsociados[index]);
                 }
                 catch (Exception e) {
                	  Log.getInstance().error(AWLiquidacionFotocopia.class,e);
                	  Log.getInstance().warn(AWLiquidacionFotocopia.class,"Error en indice de repeater");
                 }
               //}
             }

           }

           org.auriga.core.modelo.transferObjects.Usuario usuario
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

            // NOT PROPAGATE TO BUSINESS-LAYER
            return null ;
        }

        // PAGE_EVENT: añade y verifica los datos de un documento asociado;
        // los deja temporalmente en sesion
        // TODO: borrar luego estosw datos de sesion.

        private EvnFotocopia pageRegionProcessor_OnAdd( HttpServletRequest request )
        throws AccionWebException {
           List documentosAsociados = (List) request.getSession().getAttribute( AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS );

           if( null == documentosAsociados ){
             documentosAsociados = new ArrayList();
             request.getSession().setAttribute( AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS, documentosAsociados );
           }

           String documentosAsociadosView_TipoDocumento = request.getParameter( CDocumento.TIPO_DOCUMENTO ); //AW_FotocopiasConstants.PARAMETER_TIPODOCUMENTO
           String documentosAsociadosView_NumCopias     = request.getParameter( CSolicitudFotocopia.NUMCOPIAS  ); // AW_FotocopiasConstants.PARAMETER_NUMCOPIAS
           String documentosAsociadosView_Descripcion   = request.getParameter( CTurno.DESCRIPCION  ); // AW_FotocopiasConstants.PARAMETER_DESCRIPCION


           //EXCEPCION QUE ACUMULA LOS ERRORES
           ValidacionLiquidacionFotocopiasException exception = new ValidacionLiquidacionFotocopiasException();



             //TIPO DE DOCUMENTO
             documentosAsociadosView_TipoDocumento = ( documentosAsociadosView_TipoDocumento == null )?(null):documentosAsociadosView_TipoDocumento.trim();
             if( ( documentosAsociadosView_TipoDocumento == null)
                 || documentosAsociadosView_TipoDocumento.equals("SIN_SELECCIONAR")) {
                 exception.addError("El tipo de documento es inválido");
             }

             //DESCRIPCION
             documentosAsociadosView_Descripcion = ( documentosAsociadosView_Descripcion == null )?(null):documentosAsociadosView_Descripcion.trim();
             if( ( null == documentosAsociadosView_Descripcion ) || "".equals( documentosAsociadosView_Descripcion.trim() )  ) {
                 exception.addError("La descripción es inválida");
             }

             //NUMERO DE COPIAS
             int numCopias=0;
             try {
               documentosAsociadosView_NumCopias = (documentosAsociadosView_NumCopias==null)?("0"):(documentosAsociadosView_NumCopias.trim());
               numCopias = Integer.parseInt( documentosAsociadosView_NumCopias );
             }
             catch(NumberFormatException e) {
               exception.addError("El número de copias es inválido.");
 	     }

             this.saveState(request, request.getSession());
             
              if (exception.getErrores().size() > 0) {
                      throw exception;
              }

              DocumentoAsociado_Item documentoFotocopia;
              documentoFotocopia = new DocumentoAsociado_Item();

              documentoFotocopia.setTipoDocumento_Id( documentosAsociadosView_TipoDocumento );
              documentoFotocopia.setDescripcion( documentosAsociadosView_Descripcion );
              documentoFotocopia.setNumCopias( "" + numCopias );

            org.auriga.core.modelo.transferObjects.Usuario usuario
                = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
            //usuario = usuarioAuriga;

            // add here
            // request.getSession().setAttribute("temp",documentoFotocopia);

            // NOT PROPAGATE TO BUSINESS-LAYER
            return new EvnFotocopia(usuario, documentoFotocopia, EvnFotocopia.DOCUMENTOSASOCIADOSADD_EVENT );

            // return null ;
        }

	/**
	 * @param request
	 * @return
	 */

        //TODO: cambiar nombre;
        // esta funcion solo inserta la solicutud con el set de documentos asociados.

	private EvnFotocopia doCrearSolicitud( HttpServletRequest request ) throws AccionWebException {

		//Se recibe la información que viene del formulario.

		//EXCEPCION QUE ACUMULA LOS ERRORES
		ValidacionLiquidacionFotocopiasException exception = new ValidacionLiquidacionFotocopiasException();

                // Datos MRU:

                List documentosAsociados_Visual = (List) request.getSession().getAttribute( AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS );

                if( null == documentosAsociados_Visual || documentosAsociados_Visual.size() <= 0 )   {
                  exception.addError( "Debe adicionar por lo menos un documento" );
                }

                /*

                if(descripcion.equals("")) {
                        exception.addError("Debe introducir una descripcion");
                }
                if(tipoDocumento.equals("")) {
                        exception.addError("Debe seleccionar un tipo de documento");
                }

                if(numCopias < 0) {
                        exception.addError("Debe escribir un número de copias válido");
                }

              */

		//DATOS SOLICITANTE

        //Número de identificación.
        String numIdentSolicitante        = request.getParameter(CCiudadano.DOCUMENTO).trim();
                
                
		//Tipo de Documento.
		String tipoDocSolicitante = request.getParameter(CCiudadano.TIPODOC).trim();
		if ( ( null==tipoDocSolicitante )
                   || WebKeys.SIN_SELECCIONAR.equals( tipoDocSolicitante) ) {
			exception.addError("Debe seleccionar un tipo de identificacion para el ciudadano");
		}
		else if( !tipoDocSolicitante.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA) 
				&& (numIdentSolicitante==null || numIdentSolicitante.equals(""))){
			exception.addError("Debe ingresar un numero de identificacion para el ciudadano");
		}

		
		String primerApellidoSolicitante  = request.getParameter(CCiudadano.APELLIDO1).trim();
		String segundoApellidoSolicitante = request.getParameter(CCiudadano.APELLIDO2).trim();
		String nombreSolicitante          = request.getParameter(CCiudadano.NOMBRE).trim();
                String contactTelefSolicitante    = ( ( null == request.getParameter(CCiudadano.TELEFONO) )?(""):( request.getParameter(CCiudadano.TELEFONO) ) ).trim();


        //OTRAS VALIDACIONES


                //CC
		if(  ( COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD.equals( tipoDocSolicitante ) )
                        ||( COPLookupCodes.CC.equals( tipoDocSolicitante ) ) ) {
                  // validar nombre + apellido1
			if( ( null == primerApellidoSolicitante )
                           || ( "".equals( primerApellidoSolicitante.trim() ) ) ){
                           exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
                        if( ( null == nombreSolicitante )
                           || ( "".equals( nombreSolicitante.trim() ) ) ){
                           exception.addError("Debe ingresar el nombre del Ciudadano");
                        }
		}

		else if(tipoDocSolicitante.equals(COPLookupCodes.NIT)) {
			if(primerApellidoSolicitante == null || primerApellidoSolicitante.trim().equals("")){
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		}

		else {
			if( ( numIdentSolicitante == null )
                         || ( "".equals( numIdentSolicitante.trim() ) ) ){
				exception.addError("Debe ingresar el numero de identificacion del Ciudadano");
			}
                        else {
                            double valorId = 0.0d;
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
						exception.addError(
							"El valor del documento no puede ser negativo o cero");
					}
				}
                                catch (NumberFormatException e) {
					exception.addError(
						"El numero de identificacion de la persona en la solicitud es inválido");
				}
                            }
			} // end if
		} // end if

                // unwrap the visual component


                List documentosAsociados_Model = new ArrayList();

                // solo para propagar las validaciones
                if( null==documentosAsociados_Visual ) {
                  documentosAsociados_Visual = new ArrayList();
                }
                Iterator iterator;
                iterator= documentosAsociados_Visual.iterator();
                int index = 0;
                for( ;iterator.hasNext();index++){
                  DocumentoAsociado_Item item = (DocumentoAsociado_Item)iterator.next();

                  // validators

                  if( null == item.getDescripcion() || "".equals( item.getDescripcion() ) ) {
                          exception.addError(" Item " + index +": Debe introducir una descripcion");
                  }
                  if( null == item.getTipoDocumento_Id() || "".equals( item.getTipoDocumento_Id() ) ) {
                          exception.addError(" Item " + index +": Debe seleccionar un tipo de documento");
                  }
                  int numCopias = Integer.parseInt( item.getNumCopias() );
                  if( numCopias < 0) {
                          exception.addError( " Item " + index +": Debe escribir un número de copias válido");
                  }

                  // model set

                  // NOT BUILD THE OBJECTS YET
                  /*
                  gov.sir.core.negocio.modelo.DocumentoFotocopia documentoFotocopia ;
                  documentoFotocopia = new gov.sir.core.negocio.modelo.DocumentoFotocopia();

                  TipoFotocopia tipoDocumentoFotocopia;
                  tipoDocumentoFotocopia = new TipoFotocopia();
                  tipoDocumentoFotocopia.setIdTipoFotocopia( item.getTipoDocumentoId() );

                  documentoFotocopia.setDescripcion( item.getDescripcion() );
                  documentoFotocopia.setNumCopias( numCopias );
                  documentoFotocopia.setTipoFotocopia( tipoDocumentoFotocopia );
                  */

                }

                //Subir a sesión los elementos del formulario, para que en el caso en el que
                //se presente un error, estos no sean borrados.
                saveState( request, request.getSession() );

                //LANZAR ERRORES
                if (exception.getErrores().size() > 0) {
                        throw exception;
                }


                //OBTENER USUARIO
                Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
                org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
                        (org.auriga.core.modelo.transferObjects.Usuario)
                        request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

                //CIRCULO
                Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
                Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);


		//CREAR OBJETO SOLICITANTE
		Ciudadano solicitante = new Ciudadano();
		solicitante.setTipoDoc(tipoDocSolicitante);
		solicitante.setDocumento(numIdentSolicitante);
		solicitante.setApellido1(primerApellidoSolicitante);
		solicitante.setApellido2(segundoApellidoSolicitante);
		solicitante.setNombre(nombreSolicitante);
        solicitante.setTelefono( contactTelefSolicitante );

   		//Se setea el circulo del ciudadano
        solicitante.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);

		//CREAR OBJETO SOLICITUD
		SolicitudFotocopia solicitud = new SolicitudFotocopia();
		solicitud.setCirculo(circulo);
		solicitud.setProceso(proceso);
		solicitud.setUsuario(usuario);
                solicitud.setCiudadano(solicitante);




                // añadir los documentos relacionados a la solicitud
                iterator = documentosAsociados_Visual.iterator();

               index = 0;
               for( ;iterator.hasNext();index++){
                    DocumentoAsociado_Item item = (DocumentoAsociado_Item)iterator.next();

                    // model set

                    gov.sir.core.negocio.modelo.DocumentoFotocopia documentoFotocopia ;
                    documentoFotocopia = new gov.sir.core.negocio.modelo.DocumentoFotocopia();

                    // tipo documento: {ACTA,AUTO,DEMANDA, DEPURACION...}
                    // tipo fotocopia:{MECANICO, MANUAL}

                    TipoDocumento tipoDocumentoFotocopia;
                    tipoDocumentoFotocopia = new TipoDocumento();
                    tipoDocumentoFotocopia.setIdTipoDocumento( item.getTipoDocumento_Id() );

                    int numCopias = Integer.parseInt( item.getNumCopias() );

                    documentoFotocopia.setDescripcion( item.getDescripcion() );
                    documentoFotocopia.setNumCopias( numCopias );
                    documentoFotocopia.setTipoDocumento( tipoDocumentoFotocopia );

                    solicitud.addDocumentoFotocopia(documentoFotocopia );
                }

                HttpSession session = request.getSession();

                gov.sir.core.negocio.modelo.Usuario usuarioSIR =  (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
                String sessionId = session.getId();

                EvnFotocopiaCrear result = null;
                result = new EvnFotocopiaCrear( usuarioAuriga, usuarioSIR, solicitud, EvnFotocopia.CREAR_SOLICITUD );
                result.setSessionId( sessionId ); // usado para impresion
                return result;
          }

          private void saveState( HttpServletRequest request, HttpSession session ) {

            String numIdentSolicitante   = request.getParameter(CCiudadano.DOCUMENTO);
            
            if (numIdentSolicitante != null) {
            	numIdentSolicitante = numIdentSolicitante.trim();
            } 
           
            String primerApellidoSolicitante  = request.getParameter(CCiudadano.APELLIDO1);
            
            if (primerApellidoSolicitante != null) {
            	primerApellidoSolicitante = primerApellidoSolicitante.trim();
            } 
            
            String segundoApellidoSolicitante =	request.getParameter(CCiudadano.APELLIDO2);
            
            if (segundoApellidoSolicitante != null) {
            	segundoApellidoSolicitante = segundoApellidoSolicitante.trim();
            } 
            
            String nombreSolicitante          = request.getParameter(CCiudadano.NOMBRE);
            
            if (nombreSolicitante != null) {
            	nombreSolicitante = nombreSolicitante.trim();
            } 
            
            String tipoDocSolicitante         = request.getParameter(CCiudadano.TIPODOC);
            
            if (tipoDocSolicitante != null) {
            	tipoDocSolicitante = tipoDocSolicitante.trim();
            } 
            
            String contactTelefSolicitante    = request.getParameter(CCiudadano.TELEFONO);

            if (contactTelefSolicitante != null) {
            	contactTelefSolicitante = contactTelefSolicitante.trim();
            } 
            
            session.setAttribute(CCiudadano.DOCUMENTO,numIdentSolicitante);
            session.setAttribute(CCiudadano.APELLIDO1,primerApellidoSolicitante);
            session.setAttribute(CCiudadano.APELLIDO2,segundoApellidoSolicitante);
            session.setAttribute(CCiudadano.NOMBRE,nombreSolicitante);
            session.setAttribute(CCiudadano.TIPODOC, tipoDocSolicitante );
            session.setAttribute(CCiudadano.TELEFONO, contactTelefSolicitante );
            
            
            String documentosAsociadosView_TipoDocumento = request.getParameter( CDocumento.TIPO_DOCUMENTO ); 
            String documentosAsociadosView_NumCopias     = request.getParameter( CSolicitudFotocopia.NUMCOPIAS  ); 
            String documentosAsociadosView_Descripcion   = request.getParameter( CTurno.DESCRIPCION  );
            
            session.setAttribute(CDocumento.TIPO_DOCUMENTO, documentosAsociadosView_TipoDocumento );
            session.setAttribute(CSolicitudFotocopia.NUMCOPIAS, documentosAsociadosView_NumCopias );
            session.setAttribute(CTurno.DESCRIPCION, documentosAsociadosView_Descripcion );
            
          }


	/**
	 * @param request
	 * @return
	 */

        // el operador verifica la cantidad de hojas
        // y el tipo de medio en el cual esta la informacion

	private EvnFotocopia doVerificarDocumentosAsociados( HttpServletRequest request ) throws AccionWebException {

		//Se recibe la información que viene del formulario.

		//EXCEPCION QUE ACUMULA LOS ERRORES
		ValidacionReliquidacionFotocopiasException exception = new ValidacionReliquidacionFotocopiasException();


                String[] documentosAsociadosView_TipoFotocopia = request.getParameterValues( CSolicitudFotocopia.TIPO_FOTOCOPIA ); //AW_FotocopiasConstants.PARAMETER_TIPODOCUMENTO
                String[] documentosAsociadosView_NumHojas      = request.getParameterValues( CSolicitudFotocopia.NUMHOJAS       ); // AW_FotocopiasConstants.PARAMETER_NUMCOPIAS


                boolean validate;

                validate_MRU_TipoFotocopia : {

                  if ( !( validate = ( null != documentosAsociadosView_TipoFotocopia ) ) ) {
                    exception.addError("No se encuentran escritos los tipos de fotocopia.");
                  }

                  if( validate ){
                    for( int i=0; i < documentosAsociadosView_TipoFotocopia.length; i++ ) {

                      //TIPO DE FOTOCOPIA
                      String tipoFotocopia = documentosAsociadosView_TipoFotocopia[i].trim();
                      if ( ( tipoFotocopia == null )
                         ||( tipoFotocopia.equals( "SIN_SELECCIONAR" ) )
                         ||( tipoFotocopia.equals( "" ) )
                        ) {
                              exception.addError( "Item" + i + ": El tipo de fotocopia es inválido; asegúrese de haber seleccionado algún tipo");
                      }

                    }
                  }

                } // end validate_MRU_TipoFotocopia

                // si el # de hojas totales es 0 debe añadir notas informativas
                Turno turnoFotocopias = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
                Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);


                validate_MRU_NumHojas : {

                  if ( !( validate = ( null != documentosAsociadosView_NumHojas ) ) ) {
                    exception.addError("No se encuentran escritos los numeros de hojas.");
                  }

                  if( validate ){
                    int sumNumHojas = 0;
                    for( int i=0; i < documentosAsociadosView_NumHojas.length; i++ ) {

                      //NUMERO DE HOJAS
                      int numHojas=0;

                      String numHojasString = documentosAsociadosView_NumHojas[i].trim();
                      if ( ( numHojasString == null )
                         ||( numHojasString.equals( "" ) ) ) {
                              // exception.addError( "Item" + i + ": El numero de hojas es requerido");
                          numHojas = 0;
                      }
                      else {
                        try {
                          numHojas = Integer.parseInt(numHojasString.trim());

                        }
                        catch (NumberFormatException e) {
                          exception.addError( "Item" + i + ":  El numero de hojas debe ser numerico");
                          continue;
                        }
                      }
                      //OTRAS VALIDACIONES

                      if(numHojas < 0) {
                         exception.addError( "Item" + i + ":   Debe escribir un número válido de hojas para el documento" );
                         continue;
                      }
                      sumNumHojas += numHojas;
                    }

                    // si las hojas registradas dan 0 se debe observar que existan notas informativas
                    if( sumNumHojas <= 0 ) {
                      List temporalNotasList = turnoFotocopias.getNotas();
                      if( ( null == temporalNotasList )
                        || ( temporalNotasList.size() <= 0 ) ) {

                        exception.addError( "Cuando el numero de hojas a tomar es 0, se deben colocar notas informativas; <br /> Ello debido a que pudo no haberse encontrado el documento." );

                      }
                    }

                  }

                } // :validate_MRU_NumHojas

                // Comprobar las longitudes de los arreglos ?:
                //   no deberia pasar
                //    (documentosAsociadosView_TipoFotocopia.length==null)?(-1):(documentosAsociadosView_TipoFotocopia.length)
                // == (documentosAsociadosView_NumHojas.length==null)?(-2):(documentosAsociadosView_NumHojas.length)

                this.saveState(request, request.getSession());
                
                //LANZAR ERRORES
                if (exception.getErrores().size() > 0) {
                        throw exception;
                }


                //OBTENER USUARIO
                Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
                org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
                                  (org.auriga.core.modelo.transferObjects.Usuario)
                                  request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);


                //SOLICITUD


                // assert: es una solicitud fotocopia ?
                SolicitudFotocopia solicitud = (SolicitudFotocopia)turnoFotocopias.getSolicitud();

                // obtener la lista de documentos asociados
                List documentosAsociados = solicitud.getDocumentoFotocopia();

                // Construir los objetos,
                // Obtener la solicitud y modeificarla

                for( int i=0; i < documentosAsociadosView_TipoFotocopia.length ; i++ ) {

                    //CREAR OBJETO TIPO DE FOTOCOPIA
                    TipoFotocopia tipoFot = new TipoFotocopia ();
                    tipoFot.setIdTipoFotocopia( documentosAsociadosView_TipoFotocopia[i] );

                    long numHojas = Long.parseLong( documentosAsociadosView_NumHojas[i] );

                    DocumentoFotocopia documento = (DocumentoFotocopia)documentosAsociados.get( i );
                    documento.setTipoFotocopia( tipoFot );
                    documento.setNumHojas( numHojas );
                }

                // # Obtener la impresora Para las solicitudes de fotocopias
                // String impresora = (String) request.getSession().getAttribute(WebKeys.IMPRESORA);

                HttpSession session = request.getSession();

                gov.sir.core.negocio.modelo.Usuario usuarioSIR =  (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
                String sessionId = session.getId();

                EvnFotocopia result = null;
                result = new EvnVerificarDocumentosFotocopia( usuarioAuriga, usuarioSIR, turnoFotocopias, fase, EvnFotocopia.VERIFICAR_DOCUMENTOS_ASOCIADOS, sessionId , AW_FotocopiasConstants.WF_LINK_VERIFICAR2CONFIRMAR );
                /** @author : HGOMEZ
                *** @change : Se valida que se haya seleccionado la opción fotocopias
                *** exentas.
                *** Caso Mantis : 12288
                */
                if(request.getParameter("esExento") != null && request.getParameter("esExento").compareTo("on") == 0){
                    result.setEsExento("EXENTO");
                }
                else
                {
                    result.setEsExento("");
                }
                
                   
                //result  = new EvnLiquidacion(usuarioAuriga, turnoFotocopias,(Proceso)request.getSession().getAttribute(WebKeys.PROCESO),
		//		(Estacion)request.getSession().getAttribute(WebKeys.ESTACION),false, usuario);
                //result.setUID( request.getSession().getId() );
                //result.setTurno( turnoFotocopias );

		// result.setImpresora( impresora );
                return result;

          }


	/*
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		Log.getInstance().debug(AWLiquidacionFotocopia.class,"doEndMethod");

              // ADDED
              //auxEvento.getTipoEvento().equalsIgnoreCase()

		if (evento != null)
		{


			//Se obtuvo como respuesta un evento de fotocopias.
			if (evento instanceof EvnRespFotocopia)
			{

                          EvnRespFotocopia auxEvento = (EvnRespFotocopia)evento;

                          // maneja acciones a nivel de pagina
                          // todo: despues manejarlo con el evento;
                          if( EvnRespFotocopia.DOCUMENTOSASOCIADOSADD_EVENTRESP_OK.equals( auxEvento.getTipoEvento() ) ) {
                                List documentosAsociados = (List) request.getSession().getAttribute( AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS );
                                if( null == documentosAsociados ) {
                                  documentosAsociados = new ArrayList();
                                  request.getSession().setAttribute(AW_FotocopiasConstants.DOCUMENTOS_ASOCIADOS, documentosAsociados );
                                }
                                DocumentoAsociado_Item documentoFotocopia = auxEvento.getDocumentoFotocopia();
                                if( documentoFotocopia!= null ) {
                                  documentosAsociados.add(documentoFotocopia);
                                  // request.getSession().removeAttribute( "temp" );
                                }
                          }


				if (auxEvento.getTurno() != null) {
					request.getSession().setAttribute(WebKeys.TURNO, auxEvento.getTurno());
				}

			}


            //Se obtuvo como respuesta un evento de liquidacion.
			if(evento instanceof EvnRespLiquidacion)
			{
				EvnRespLiquidacion eventoLiq = (EvnRespLiquidacion) evento;
				if (eventoLiq.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.VALIDACION)) {
					SolicitudFotocopia solicitud = (SolicitudFotocopia) eventoLiq.getPayload();

					if (solicitud != null) {
					}
				}

				else if (eventoLiq.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.LIQUIDACION))
				{
					Liquidacion liquidacion = eventoLiq.getLiquidacion();
				 	request.getSession().setAttribute(WebKeys.LIQUIDACION, liquidacion);
					NumberFormat nf = NumberFormat.getInstance();
					request.getSession().setAttribute(WebKeys.VALOR_LIQUIDACION,String.valueOf(liquidacion.getValor()));

					DocumentoPago documentoEfectivo = new DocPagoEfectivo(liquidacion.getValor());
					AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo, liquidacion.getValor());

					request.getSession().setAttribute(WebKeys.APLICACION_EFECTIVO,aplicacionEfectivo);
					request.getSession().setAttribute(CActo.NUM_ACTOS, new Integer(0));

				}

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
            Logger.getLogger(AWLiquidacionFotocopia.class.getName()).log(Level.SEVERE, null, ex);
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
                EvnRespFotocopia  turno = (EvnRespFotocopia) evento;
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
