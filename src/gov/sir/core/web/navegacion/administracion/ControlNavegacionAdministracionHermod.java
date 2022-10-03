package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.negocio.modelo.constantes.CCausalRestitucion;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWAdministracionHermod;
import static gov.sir.core.web.acciones.administracion.AWAdministracionHermod.ACT_INACT_CTA_BANCARIA;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUpload;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para AWAdministracionHermod
 * 
 * @author jmendez
 * @author dsalas
 *
 */
public class ControlNavegacionAdministracionHermod implements ControlNavegacion {

	public static final String ADICIONA_BANCO_OK = "ADICIONA_BANCO_OK";
	

	/**
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest arg0) {

	}

	/**
	 * Método que procesa la siguiente acción de navegación dentro del flujo de pantallas
	 *  
	 * @param request
	 * @return nombre de la acción siguiente 
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		HttpSession sesion = request.getSession();

		boolean isMultipart = FileUpload.isMultipartContent(request);
		if (isMultipart) {
			String accion = (String) sesion.getAttribute(WebKeys.ACCION);
			sesion.removeAttribute(WebKeys.ACCION);

			if (accion != null) {
				if (accion.equals(AWAdministracionHermod.ADICIONA_FIRMA_REGISTRADOR)
					|| accion.equals(AWAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR)) {
					return AWAdministracionHermod.ADICIONA_FIRMA_REGISTRADOR;
				} else if (accion.equals(AWAdministracionHermod.TERMINA)) {
					return AWAdministracionHermod.TERMINA;
				}
				return null;
			} else {
				return null;
			}
		} else {
			String accion = (String) request.getParameter(WebKeys.ACCION);

			if ((accion == null) || accion.equals("")) {
				throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
			}
			if (accion.equals(AWAdministracionHermod.ADICIONA_BANCO)
				|| accion.equals(AWAdministracionHermod.ELIMINA_BANCO)
				|| accion.equals(AWAdministracionHermod.TERMINA_SUCURSAL)) {
				return ControlNavegacionAdministracionHermod.ADICIONA_BANCO_OK;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_ALCANCE_GEOGRAFICO)
					|| accion.equals(AWAdministracionHermod.ELIMINA_ALCANCE_GEOGRAFICO)) {
				return AWAdministracionHermod.ADICIONA_ALCANCE_GEOGRAFICO;
			} else if (
				accion.equals(AWAdministracionHermod.SELECCIONA_BANCO)
					|| accion.equals(AWAdministracionHermod.ADICIONA_SUCURSAL)
					|| accion.equals(AWAdministracionHermod.ELIMINA_SUCURSAL)) {
				return AWAdministracionHermod.SELECCIONA_BANCO;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_TIPO_FOTOCOPIA)
					|| accion.equals(AWAdministracionHermod.ELIMINA_TIPO_FOTOCOPIA)) {
				return AWAdministracionHermod.ADICIONA_TIPO_FOTOCOPIA;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_TARIFA)
					|| accion.equals(AWAdministracionHermod.ELIMINA_TARIFA)
					|| accion.equals(AWAdministracionHermod.SELECCIONA_TIPO_TARIFA)) {
				return AWAdministracionHermod.ADICIONA_TARIFA;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_TIPO_TARIFA)
					|| accion.equals(AWAdministracionHermod.ELIMINA_TIPO_TARIFA)
					|| accion.equals(AWAdministracionHermod.TERMINA_TARIFA)) {
				return AWAdministracionHermod.ADICIONA_TIPO_TARIFA;
			} else if (accion.equals(AWAdministracionHermod.TERMINA)) {
				request.getSession().removeAttribute(CCirculo.ID_CIRCULO);
				return AWAdministracionHermod.TERMINA;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_SUBTIPO_SOLICITUD)
					|| accion.equals(AWAdministracionHermod.ELIMINA_SUBTIPO_SOLICITUD)
					|| accion.equals(AWAdministracionHermod.EDITA_SUBTIPO_SOLICITUD)
					|| accion.equals(AWAdministracionHermod.EDITAR_SUBTIPO_SOLICITUD)) {
				return AWAdministracionHermod.ADICIONA_SUBTIPO_SOLICITUD;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_OPLOOKUP_CODE)
					|| accion.equals(AWAdministracionHermod.ELIMINA_OPLOOKUP_CODE)
					|| accion.equals(AWAdministracionHermod.SELECCIONA_OPLOOKUP_TYPE)) {
				return AWAdministracionHermod.ADICIONA_OPLOOKUP_CODE;
			}else if (
						accion.equals(AWAdministracionHermod.UPDATE_OPLOOKUP_CODE)) {
						return AWAdministracionHermod.UPDATE_OPLOOKUP_CODE;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_OPLOOKUP_TYPE)
					|| accion.equals(AWAdministracionHermod.ELIMINA_OPLOOKUP_TYPE)
					|| accion.equals(AWAdministracionHermod.TERMINA_OPLOOKUP_CODE)
					|| accion.equals(AWAdministracionHermod.CONSULTA_OPLOOKUP_TYPE)) {
				return AWAdministracionHermod.ADICIONA_OPLOOKUP_TYPE;
			} else if (
					accion.equals(AWAdministracionHermod.UPDATE_OPLOOKUP_TYPE)) {
					return AWAdministracionHermod.UPDATE_OPLOOKUP_TYPE;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_TIPO_CALCULO)
					|| accion.equals(AWAdministracionHermod.ELIMINA_TIPO_CALCULO)) {
				return AWAdministracionHermod.ADICIONA_TIPO_CALCULO;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_TIPO_IMPUESTO)
					|| accion.equals(AWAdministracionHermod.ELIMINA_TIPO_IMPUESTO)) {
				return AWAdministracionHermod.ADICIONA_TIPO_IMPUESTO;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_TIPO_ACTO)
					|| accion.equals(AWAdministracionHermod.ELIMINA_TIPO_ACTO)
					|| accion.equals(AWAdministracionHermod.EDITAR_TIPO_ACTO)
					|| accion.equals(AWAdministracionHermod.EDITA_TIPO_ACTO)) {
				return AWAdministracionHermod.ADICIONA_TIPO_ACTO;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_SUBTIPO_ATENCION)
					|| accion.equals(AWAdministracionHermod.ELIMINA_SUBTIPO_ATENCION)
					|| accion.equals(AWAdministracionHermod.EDITAR_SUBTIPO_ATENCION)
					|| accion.equals(AWAdministracionHermod.EDITA_SUBTIPO_ATENCION)
					|| accion.equals(AWAdministracionHermod.CARGAR_SUBTIPO_ATENCION)) {
				return AWAdministracionHermod.ADICIONA_SUBTIPO_ATENCION;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_TIPO_DERECHO_REGISTRAL)
					|| accion.equals(AWAdministracionHermod.ELIMINA_TIPO_DERECHO_REGISTRAL)) {
				return AWAdministracionHermod.ADICIONA_TIPO_DERECHO_REGISTRAL;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_ACCION_NOTARIAL)
					|| accion.equals(AWAdministracionHermod.ELIMINA_ACCION_NOTARIAL)
					|| accion.equals(AWAdministracionHermod.EDITA_ACCION_NOTARIAL)
					|| accion.equals(AWAdministracionHermod.HABILITA_EDICION_ACCION_NOTARIAL)
					|| accion.equals(AWAdministracionHermod.DESHABILITA_EDICION_ACCION_NOTARIAL)) {
				return AWAdministracionHermod.ADICIONA_ACCION_NOTARIAL;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_CATEGORIA) 
					|| accion.equals(AWAdministracionHermod.EDITA_CATEGORIA)
					|| accion.equals(AWAdministracionHermod.ELIMINA_CATEGORIA)) {
				return AWAdministracionHermod.ADICIONA_CATEGORIA;
			} else if (accion.equals(AWAdministracionHermod.ELIMINA_ESTACION_RECIBO)
					|| accion.equals(AWAdministracionHermod.CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO)) {
				return AWAdministracionHermod.SET_ESTACION_RECIBO;
			}else if (
				accion.equals(AWAdministracionHermod.RESET_ESTACION_RECIBO_TRASPASO)
					|| accion.equals(AWAdministracionHermod.CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO_TRASPASO)
					|| accion.equals(AWAdministracionHermod.TRASPASO_ESTACION_RECIBO)){
				return AWAdministracionHermod.TRASPASO_ESTACION_RECIBO;
			}else if (
				accion.equals(AWAdministracionHermod.CONSULTA_ESTACIONES_CIRCULO)){
				return AWAdministracionHermod.ADMIN_ESTACION_CONSULTA_ESTACIONES_CIRCULO;
			} else if (
				accion.equals(AWAdministracionHermod.RESET_ULTIMO_VALOR_ESTACION_RECIBO)
					|| accion.equals(AWAdministracionHermod.CONSULTA_ESTACION_RECIBO)) {
				return AWAdministracionHermod.RESET_ULTIMO_VALOR_ESTACION_RECIBO;
			} else if (accion.equals(AWAdministracionHermod.CONSULTA_MINUTA)) {
				return AWAdministracionHermod.EDITA_MINUTA;
			} else if (accion.equals(AWAdministracionHermod.EDITA_MINUTA)) {
				return AWAdministracionHermod.CONSULTA_MINUTA;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_CIRCULO_TIPO_PAGO)
					|| accion.equals(AWAdministracionHermod.ELIMINA_CIRCULO_TIPO_PAGO)
					|| accion.equals(AWAdministracionHermod.SELECCIONA_CIRCULO_TIPO_PAGO)
                                        || accion.equals(AWAdministracionHermod.ACT_INACT_CTP)) {
				return AWAdministracionHermod.ADICIONA_CIRCULO_TIPO_PAGO;
			} else if (
				accion.equals(AWAdministracionHermod.ACTIVA_FIRMA_REGISTRADOR)
					|| accion.equals(AWAdministracionHermod.INACTIVA_FIRMA_REGISTRADOR)
					|| accion.equals(AWAdministracionHermod.INACTIVA_DEFINITIVAMENTE_FIRMA_REGISTRADOR)
					|| accion.equals(AWAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR)) {
				return AWAdministracionHermod.ADICIONA_FIRMA_REGISTRADOR;
			} else if (
					accion.equals(AWAdministracionHermod.BUSCAR_FIRMA_REGISTRADOR_CIRCULO)
					|| accion.equals(AWAdministracionHermod.PREVIEW_FIRMA_REGISTRADOR)
					|| accion.equals(AWAdministracionHermod.GUARDAR_FIRMA_REGISTRADOR)) {
					return AWAdministracionHermod.BUSCAR_FIRMA_REGISTRADOR_CIRCULO;
			} else if (
				accion.equals(AWAdministracionHermod.CONSULTA_ABOGADOS_POR_CIRCULO)
					|| accion.equals(AWAdministracionHermod.ACTUALIZA_SUBTIPOS_ATENCION_ABOGADOS)) {
				return AWAdministracionHermod.CONSULTA_ABOGADOS_POR_CIRCULO;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_VALIDACION_NOTA)
					|| accion.equals(AWAdministracionHermod.ELIMINA_VALIDACION_NOTA)
					|| accion.equals(AWAdministracionHermod.CONSULTA_FASE_PROCESO)) {
				return AWAdministracionHermod.ADICIONA_VALIDACION_NOTA;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_CAUSAL_RESTITUCION)
					|| accion.equals(AWAdministracionHermod.ELIMINA_CAUSAL_RESTITUCION)) {
				return AWAdministracionHermod.ADICIONA_CAUSAL_RESTITUCION;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_TIPO_CERTIFICADO_VALIDACION)
					|| accion.equals(AWAdministracionHermod.ELIMINA_TIPO_CERTIFICADO_VALIDACION)
					|| accion.equals(AWAdministracionHermod.CONSULTA_VALIDACIONES_DE_TIPO_CERTIFICADO)) {
				return AWAdministracionHermod.ADICIONA_TIPO_CERTIFICADO_VALIDACION;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_TIPO_NOTA)
					|| accion.equals(AWAdministracionHermod.ELIMINA_TIPO_NOTA)
					|| accion.equals(AWAdministracionHermod.CONSULTA_TIPO_NOTA_POR_PROCESO)
					|| accion.equals(AWAdministracionHermod.MODIFICA_TIPO_NOTA)) {
				return AWAdministracionHermod.ADICIONA_TIPO_NOTA;
			} else if (
				accion.equals(AWAdministracionHermod.CONSULTA_TIPO_NOTA_INF_POR_PROCESO)
				|| accion.equals(AWAdministracionHermod.ADICIONA_TIPO_NOTA_INF)
				|| accion.equals(AWAdministracionHermod.MODIFICA_TIPO_NOTA_INF)
				|| accion.equals(AWAdministracionHermod.ELIMINA_TIPO_NOTA_INF)){
				return AWAdministracionHermod.ADICIONA_TIPO_NOTA_INF;
			} else if (
				accion.equals(AWAdministracionHermod.CONSULTA_TIPO_NOTA_DEV_POR_PROCESO)
				|| accion.equals(AWAdministracionHermod.ADICIONA_TIPO_NOTA_DEV)
				|| accion.equals(AWAdministracionHermod.MODIFICA_TIPO_NOTA_DEV)
				|| accion.equals(AWAdministracionHermod.ELIMINA_TIPO_NOTA_DEV)){
				return AWAdministracionHermod.ADICIONA_TIPO_NOTA_DEV;
			} else if (
				accion.equals(AWAdministracionHermod.ADICIONA_CHECK_ITEM)
					|| accion.equals(AWAdministracionHermod.ELIMINA_CHECK_ITEM)
					|| accion.equals(AWAdministracionHermod.EDITAR_CHECK_ITEM)
					|| accion.equals(AWAdministracionHermod.EDITA_CHECK_ITEM)
					|| accion.equals(AWAdministracionHermod.CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD)) {
				return AWAdministracionHermod.ADICIONA_CHECK_ITEM;
			} else if (
				accion.equals(AWAdministracionHermod.ENTREGA_MASIVA_CERTIFICADOS)
					|| accion.equals(AWAdministracionHermod.CONSULTA_TURNOS_CER_ENTREGA_POR_FECHA)) {
				return AWAdministracionHermod.ENTREGA_MASIVA_CERTIFICADOS;
			} else if (
				accion.equals(AWAdministracionHermod.ENTREGA_MASIVA_CONSULTAS)
					|| accion.equals(AWAdministracionHermod.CONSULTA_TURNOS_CON_ENTREGA_POR_FECHA)) {
				return AWAdministracionHermod.ENTREGA_MASIVA_CONSULTAS;
			}else if (accion.equals(AWAdministracionHermod.CONSULTAR_PROB_REV_CALIFICACION)) {
				return AWAdministracionHermod.CONSULTAR_PROB_REV_CALIFICACION;
			} else if (accion.equals(AWAdministracionHermod.CONSULTA_SOLICITUDES_NO_PAGADAS)) {
				return AWAdministracionHermod.CONSULTA_SOLICITUDES_NO_PAGADAS;
			}  else if (accion.equals(AWAdministracionHermod.ELIMINAR_SOLICITUDES_NO_PAGADAS) ||
					accion.equals(AWAdministracionHermod.ELIMINAR_TODAS_LAS_SOLICITUDES_NO_PAGADAS)) {
				return AWAdministracionHermod.ELIMINAR_SOLICITUDES_NO_PAGADAS;
			} else if (accion.equals(AWAdministracionHermod.MODIFICAR_PROB_REV_CALIFICACION)) {
				return AWAdministracionHermod.TERMINA;
			} else if (accion.equals(AWAdministracionHermod.CONSULTA_TURNOS_CALIFICACION) || 
					accion.equals(AWAdministracionHermod.COLOCAR_DESCRIPCION_NOTA_INF)) {
				return AWAdministracionHermod.CONSULTA_TURNOS_CALIFICACION;
			}  else if (accion.equals(AWAdministracionHermod.SOLICITAR_CORRECCION_CALIFICACION)) {
				return AWAdministracionHermod.CONSULTA_TURNOS_CALIFICACION;
			} else if (accion.equals(AWAdministracionHermod.CONSULTA_TIPOS_TARIFA_POR_CIRCULO)) {
				return AWAdministracionHermod.CONSULTA_TIPOS_TARIFA_POR_CIRCULO;
			} else if (accion.equals(AWAdministracionHermod.ADICIONA_TARIFA_POR_CIRCULO)) {
				return AWAdministracionHermod.ADICIONA_TARIFA_POR_CIRCULO;
			} else if (accion.equals(AWAdministracionHermod.ADMIN_UNA_ESTACION)) {
				return AWAdministracionHermod.ADMIN_UNA_ESTACION;
			} else if (accion.equals(AWAdministracionHermod.SET_ESTACION_RECIBO)) {
				return AWAdministracionHermod.SET_ESTACION_RECIBO;
			}	
			  //Solicitar Edición de un Causal de Restitución.
			  else if (accion.equals(AWAdministracionHermod.EDITAR_CAUSAL_RESTITUCION)){
			  	return AWAdministracionHermod.EDITAR_CAUSAL_RESTITUCION;
			
			} 
			
			//Seleccionar turno para modificar valor de la devolucion
			else if (accion.equals(AWAdministracionHermod.SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION))
			{
				return AWAdministracionHermod.SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION;
			}
			else if (accion.equals(AWAdministracionHermod.VOLVER_SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION))
			{
				return AWAdministracionHermod.VOLVER_SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION;
			}
			else if (accion.equals(AWAdministracionHermod.MODIFICAR_VALOR_LIQUIDACION_DEVOLUCION_SELECCIONADA))
			{
				sesion.removeAttribute(WebKeys.TURNO);
				sesion.removeAttribute(AWAdministracionHermod.LIQUIDACION_NEGATIVA);
				return AWAdministracionHermod.TERMINA;
			}
			
			
			
			
			else if (accion.equals(AWAdministracionHermod.CONSULTA_ESTACIONES_POR_ID)) {
				return AWAdministracionHermod.SET_ESTACION_RECIBO;
			} else if (accion.equals(AWAdministracionHermod.SET_AGREGAR_ESTACION_RECIBO)) {
				return (String) sesion.getAttribute(SMARTKeys.VISTA_ACTUAL);
			} else if (accion.equals(AWAdministracionHermod.ADD_ROLES_ESTACION)) {
				return AWAdministracionHermod.ADMIN_UNA_ESTACION;
			} else if (accion.equals(AWAdministracionHermod.REMOVE_ROLES_ESTACION)) {
				return AWAdministracionHermod.ADMIN_UNA_ESTACION;
			} else if (accion.equals(AWAdministracionHermod.ADD_USUARIOS_ESTACION)) {
				return AWAdministracionHermod.ADMIN_UNA_ESTACION;
			} else if (accion.equals(AWAdministracionHermod.REMOVE_USUARIOS_ESTACION)) {
				return AWAdministracionHermod.ADMIN_UNA_ESTACION;
			} else if (accion.equals(AWAdministracionHermod.ADICIONA_NAT_JURIDICA_ENTIDAD)) {
				return AWAdministracionHermod.ADICIONA_NAT_JURIDICA_ENTIDAD;
			}  else if (accion.equals(AWAdministracionHermod.CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO)) {
				return AWAdministracionHermod.CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO;
			}  else if (accion.equals(AWAdministracionHermod.IMPRESION_FORMULARIOS_CALIFICACION)) {
				return AWAdministracionHermod.IMPRESION_FORMULARIOS_CALIFICACION;
			} else if (accion.equals(AWAdministracionHermod.IMPRESION_FORMULARIOS_CORRECCIONES)) {
				return AWAdministracionHermod.IMPRESION_FORMULARIOS_CORRECCIONES;
			} else if (accion.equals(AWAdministracionHermod.ADICIONA_ENTIDAD_PUBLICA)) {
				return AWAdministracionHermod.ADICIONA_ENTIDAD_PUBLICA;
			} else if (accion.equals(AWAdministracionHermod.EDITA_ENTIDAD_PUBLICA)) {
				return AWAdministracionHermod.ADICIONA_ENTIDAD_PUBLICA;
			} else if (accion.equals(AWAdministracionHermod.HABILITA_EDICION_ENTIDAD_PUBLICA)
						|| accion.equals(AWAdministracionHermod.DESHABILITA_EDICION_ENTIDAD_PUBLICA)) {
				return AWAdministracionHermod.ADICIONA_ENTIDAD_PUBLICA;
			} else if (accion.equals(AWAdministracionHermod.CONSULTA_ENTIDAD_PUBLICA_BY_NATURALEZA)) {
				return AWAdministracionHermod.ADICIONA_ENTIDAD_PUBLICA;
			}  else if (accion.equals(AWAdministracionHermod.CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO_ENTIDAD))  {
				return AWAdministracionHermod.CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO_ENTIDAD;
			}   else if (accion.equals(AWAdministracionHermod.ELIMINA_ENTIDAD_PUBLICA)) {
				return AWAdministracionHermod.ELIMINA_ENTIDAD_PUBLICA;
			} else if (accion.equals(AWAdministracionHermod.EDITAR_NAT_JURIDICA_ENTIDAD)) {
				return AWAdministracionHermod.CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO;
			} else if (accion.equals(AWAdministracionHermod.HABILITA_EDICION_NAT_JURIDICA_ENTIDAD)
						|| accion.equals(AWAdministracionHermod.DESHABILITA_EDICION_NAT_JURIDICA_ENTIDAD)) {
				return AWAdministracionHermod.CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO;
			}else if (accion.equals(AWAdministracionHermod.ELIMINA_NAT_JURIDICA_ENTIDAD)) {
				return AWAdministracionHermod.CONSULTAR_NATURALEZAS_JURIDICAS_REPARTO;
			} else if (accion.equals(AWAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO_CALIFICACION)) {
				return AWAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO_CALIFICACION;
			} else if (accion.equals(AWAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO_CORRECCIONES)) {
				return AWAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO_CORRECCIONES;
			} else if (accion.equals(AWAdministracionHermod.OBTENER_VENCIDOS_PAGO_MAYOR_VALOR)) {
			   return AWAdministracionHermod.OBTENER_VENCIDOS_PAGO_MAYOR_VALOR;
		   } else if (accion.equals(AWAdministracionHermod.DEVOLVER_VENCIDOS_PAGO_MAYOR_VALOR)) {
		       return AWAdministracionHermod.DEVOLVER_VENCIDOS_PAGO_MAYOR_VALOR;
	       } else if (accion.equals(AWAdministracionHermod.ADICIONA_TIPO_VALIDACION)){
	    	   return AWAdministracionHermod.ADICIONA_TIPO_VALIDACION;
	       } else if (accion.equals(AWAdministracionHermod.MOSTRAR_CIRCULO_ESTACION_RECIBO)){
	    	   return AWAdministracionHermod.MOSTRAR_CIRCULO_ESTACION_RECIBO;
	       } else if (accion.equals(AWAdministracionHermod.MOSTRAR_CIRCULO_PASO_ESTACION_RECIBO)){
	    	   return AWAdministracionHermod.MOSTRAR_CIRCULO_PASO_ESTACION_RECIBO;
	       } else if (accion.equals(AWAdministracionHermod.OBTENER_PENDIENTES_PAGO_MAYOR_VALOR)) {
	    	   return AWAdministracionHermod.OBTENER_PENDIENTES_PAGO_MAYOR_VALOR;
	       } else if (accion.equals(AWAdministracionHermod.COLOCAR_DESCRIPCION_NOTA_INF_PMY)) {
	    	   return AWAdministracionHermod.OBTENER_PENDIENTES_PAGO_MAYOR_VALOR;
	       } else if (accion.equals(AWAdministracionHermod.ELIMINAR_PENDIENTES_PAGO_MAYOR_VALOR)) {
	    	   return AWAdministracionHermod.ELIMINAR_PENDIENTES_PAGO_MAYOR_VALOR;
	       } else if (accion.equals(AWAdministracionHermod.CARGAR_CALIFICADORES_ORDEN_SUBTIPOATENCION)
	    		   || accion.equals(AWAdministracionHermod.CARGAR_LISTADO_CALIFICADORES_SUBTIPOATENCION)
	    		   || accion.equals(AWAdministracionHermod.ADICIONA_ORDEN_SUBTIPOATENCION)
	    		   || accion.equals(AWAdministracionHermod.ELIMINA_ORDEN_SUBTIPOATENCION)) {
	    	   return AWAdministracionHermod.CARGAR_CALIFICADORES_ORDEN_SUBTIPOATENCION;
	       }
	       

	       
	       else if (accion.equals(AWAdministracionHermod.VOLVER_SELECCIONAR_CAUSAL))
	       {
	       	  sesion.removeAttribute(CCausalRestitucion.ID_CAUSAL_RESTITUCION);
			  sesion.removeAttribute(CCausalRestitucion.CAUSAL_RESTITUCION_NOMBRE);
			  sesion.removeAttribute(CCausalRestitucion.CAUSAL_RESTITUCION_DESCRIPCION);
	       	  
	       	  return AWAdministracionHermod.VOLVER_SELECCIONAR_CAUSAL;
	       }
	       
		   else if (accion.equals(AWAdministracionHermod.EDITAR_DETALLES_CAUSAL_RESTITUCION))
		   {
			  sesion.removeAttribute(CCausalRestitucion.ID_CAUSAL_RESTITUCION);
			  sesion.removeAttribute(CCausalRestitucion.CAUSAL_RESTITUCION_NOMBRE);
			  sesion.removeAttribute(CCausalRestitucion.CAUSAL_RESTITUCION_DESCRIPCION);
	       	  
			  return AWAdministracionHermod.EDITAR_DETALLES_CAUSAL_RESTITUCION;
		   }else if (accion.equals(AWAdministracionHermod.CARGAR_CIRCULOS_NOTARIALES)||
		   		accion.equals(AWAdministracionHermod.CARGAR_CIRCULOS_NOTARIALES)||
		   		accion.equals(AWAdministracionHermod.ELIMINA_CIRCULO_NOTARIAL)||
		   		accion.equals(AWAdministracionHermod.EJECUTAR_EDICION_CIRCULO_NOTARIAL)||
		   		accion.equals(AWAdministracionHermod.TERMINA_EDICION)||
		   		accion.equals(AWAdministracionHermod.EDITAR_CIRCULO_NOTARIAL)||
				accion.equals(AWAdministracionHermod.ADICIONA_CIRCULO_NOTARIAL)){
		   		return AWAdministracionHermod.ADICIONA_CIRCULO_NOTARIAL;  
		   }else if (accion.equals(AWAdministracionHermod.CARGAR_CIRCULOS_NOTARIALES_ZONA_NOTARIAL)||
		   		accion.equals(AWAdministracionHermod.CARGAR_ZONAS_NOTARIALES)||
		   		accion.equals(AWAdministracionHermod.ADICIONA_ZONA_NOTARIAL)||
		   		accion.equals(AWAdministracionHermod.ELIMINA_ZONA_NOTARIAL)||
		   		accion.equals(AWAdministracionHermod.SELECCIONA_ZONA_NOTARIAL_DEPTO)||
	   			accion.equals(AWAdministracionHermod.SELECCIONA_ZONA_NOTARIAL_MUNICIPIO)){
		   		return AWAdministracionHermod.ADICIONA_ZONA_NOTARIAL;
		   }else if (accion.equals(AWAdministracionHermod.CARGAR_ROLES_PANTALLAS)
				   || accion.equals(AWAdministracionHermod.ELIMINAR_ROL_PANTALLA)
					 || accion.equals(AWAdministracionHermod.AGREGAR_ROL_PANTALLA)) {
			   return AWAdministracionHermod.CARGAR_ROLES_PANTALLAS;
		   }
			/** Pablo Quintana Junio 18 2008
			 * 	Navegación de reimpresión de constancias de testamento*/
		   else if (accion.equals(AWAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO_TESTAMENTOS)) {
				return AWAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO_TESTAMENTOS;
			}
		   else if (accion.equals(AWAdministracionHermod.IMPRESION_FORMULARIOS_TESTAMENTOS)) {
			   return AWAdministracionHermod.IMPRESION_FORMULARIOS_CORRECCIONES;
			}
		   else if(accion.equals(AWAdministracionHermod.ADICIONA_BANCO_CIRCULO)
				   || accion.equals(AWAdministracionHermod.SELECCIONA_BANCOS_X_CIRCULO)
				   || accion.equals(AWAdministracionHermod.ELIMINA_BANCO_CIRCULO)
				   || accion.equals(AWAdministracionHermod.ACTIVAR_BANCO_PRINCIPAL)){
			   return AWAdministracionHermod.BANCOS_X_CIRCULO;
		   }
                   else if(accion.equals(AWAdministracionHermod.LISTADO_BANCOS_X_CIRCULO) 
                           || accion.equals(AWAdministracionHermod.ADICIONA_CUENTA_BANCO_CIRCULO)
                           || accion.equals(AWAdministracionHermod.CUENTAS_X_CIRCULO_BANCO)
                           || accion.equals(AWAdministracionHermod.ACT_INACT_CTA_BANCARIA)){
			   return AWAdministracionHermod.CUENTAS_BANCARIAS;
		   }
                   else if(accion.equals(AWAdministracionHermod.ADICIONA_CANAL_RECAUDO)
                           || accion.equals(AWAdministracionHermod.ACT_INACT_CANAL_RECAUDO)){
			   return AWAdministracionHermod.CANALES_RECAUDO;
		   }
                   else if(accion.equals(AWAdministracionHermod.SELECCIONA_CUENTA_BANCARIA)){
			   return AWAdministracionHermod.ADICIONA_CIRCULO_TIPO_PAGO;
		   }
			return null;
		}
	}

	/**
	 * Finalización de la navegación
	 * 
	 * @param request
	 * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
	 */
	public void doEnd(HttpServletRequest arg0) {

	}

}
