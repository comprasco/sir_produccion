/*
 * Created on 25/10/2004
 *
 */
package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWAdministracionForseti;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se habilita el uso de las clases
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
import org.apache.commons.fileupload.FileUpload;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para <CODE>AWAdministracionForseti</CODE>
 * 
 * @author jmendez
 *
 */
public class ControlNavegacionAdministracionForseti implements ControlNavegacion {

	public static final String ADICIONA_DEPARTAMENTO_OK = "ADICIONA_DEPARTAMENTO_OK";

	public static final String SELECCIONA_DEPARTAMENTO_OK = "SELECCIONA_DEPARTAMENTO_OK";

	public static final String SELECCIONA_MUNICIPIO_OK = "SELECCIONA_MUNICIPIO_OK";
	public static final String LISTA_DEPARTAMENTO_CIRCULO_OK="LISTA_DEPARTAMENTO_CIRCULO_OK";
	public static final String INHABILITAR_CIRCULO="INHABILITAR_CIRCULO";
	public static final String ASOCIAR_ZONA_CIRCULO_INHABILITADO="ASOCIAR_ZONA_CIRCULO_INHABILITADO";
	public static final String ASOCIAR_USUARIOS_CIRCULO_INHABILITADO="ASOCIAR_USUARIOS_CIRCULO_INHABILITADO";
	public static final String ASOCIAR_ROL_USUARIOS_CIRCULO_INHABILITADO="ASOCIAR_ROL_USUARIOS_CIRCULO_INHABILITADO";
	public static final String TRASLADO_MASIVO_FOLIOS="TRASLADO_MASIVO_FOLIOS";
        /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se habilita el uso de las clases
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */	
        public static final String VALIDAR_MATRICULA_MASIVO_OK = "VALIDAR_MATRICULA_MASIVO_OK";
	/**
	 *  Constructor por Default de <CODE>ControlNavegacionAdministracionForseti</CODE>
	 */
	public ControlNavegacionAdministracionForseti() {
		super();
	}

	/**
	 * Prepara el procesamiento de la navegación.
	 * @param request
	 */
	public void doStart(HttpServletRequest request) {

	}

	/**
	 * Método que procesa la siguiente acción de navegación dentro del flujo de pantallas
	 *  
	 * @param request
	 * @return nombre de la acción siguiente 
	 * @throws ControlNavegacionException
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		/*
         * @author      :   Carlos Mario Torres Urina
         * @change       : Se modifica par 
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */	

            boolean isMultipart = FileUpload.isMultipartContent(request);
                String accion = "";
                
                if(isMultipart)
                {    
                    return ControlNavegacionAdministracionForseti.VALIDAR_MATRICULA_MASIVO_OK;
                }
               
                accion = request.getParameter(WebKeys.ACCION);
		HttpSession sesion = request.getSession();

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
		}
		if (accion.equals(AWAdministracionForseti.ADICIONA_DEPARTAMENTO)
			|| accion.equals(AWAdministracionForseti.ELIMINA_DEPARTAMENTO)
			|| accion.equals(AWAdministracionForseti.TERMINA_MUNICIPIO)) {
			return ControlNavegacionAdministracionForseti.ADICIONA_DEPARTAMENTO_OK;
		} else if (
			accion.equals(AWAdministracionForseti.SELECCIONA_DEPARTAMENTO)
				|| accion.equals(AWAdministracionForseti.ADICIONA_MUNICIPIO)
				|| accion.equals(AWAdministracionForseti.ELIMINA_MUNICIPIO)
				|| accion.equals(AWAdministracionForseti.TERMINA_VEREDA)) {
			return ControlNavegacionAdministracionForseti.SELECCIONA_DEPARTAMENTO_OK;
		} else if (
			accion.equals(AWAdministracionForseti.SELECCIONA_MUNICIPIO)
				|| accion.equals(AWAdministracionForseti.ADICIONA_VEREDA)
				|| accion.equals(AWAdministracionForseti.ELIMINA_VEREDA)) {
			return ControlNavegacionAdministracionForseti.SELECCIONA_MUNICIPIO_OK;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_ESTADO_FOLIO)
				|| accion.equals(AWAdministracionForseti.ELIMINA_ESTADO_FOLIO)) {
			return AWAdministracionForseti.ADICIONA_ESTADO_FOLIO;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_TIPO_OFICINA)
				|| accion.equals(AWAdministracionForseti.ELIMINA_TIPO_OFICINA)) {
			return AWAdministracionForseti.ADICIONA_TIPO_OFICINA;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_TIPO_PREDIO)
				|| accion.equals(AWAdministracionForseti.ELIMINA_TIPO_PREDIO)) {
			return AWAdministracionForseti.ADICIONA_TIPO_PREDIO;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_TIPO_DOCUMENTO)
				|| accion.equals(AWAdministracionForseti.ELIMINA_TIPO_DOCUMENTO)) {
			return AWAdministracionForseti.ADICIONA_TIPO_DOCUMENTO;
		} else if (accion.equals(AWAdministracionForseti.TERMINA)) {
			return AWAdministracionForseti.TERMINA;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_GRUPO_NAT_JURIDICA_ANOTACIONES)
				|| accion.equals(AWAdministracionForseti.ELIMINA_GRUPO_NAT_JURIDICA_ANOTACIONES)
				|| accion.equals(AWAdministracionForseti.TERMINA_TIPO_NATURALEZA_JURIDICA)) {
			return AWAdministracionForseti.ADICIONA_GRUPO_NAT_JURIDICA_ANOTACIONES;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_TIPO_NATURALEZA_JURIDICA)
				|| accion.equals(AWAdministracionForseti.ELIMINA_TIPO_NATURALEZA_JURIDICA)
				|| accion.equals(AWAdministracionForseti.SELECCIONA_GRUPO_NAT_JURIDICA_ANOTACIONES)) {
			return AWAdministracionForseti.ADICIONA_TIPO_NATURALEZA_JURIDICA;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_CIRCULO_REGISTRAL)
				|| accion.equals(AWAdministracionForseti.ELIMINA_CIRCULO_REGISTRAL)) {
			return AWAdministracionForseti.ADICIONA_CIRCULO_REGISTRAL;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_ESTADO_ANOTACION)
				|| accion.equals(AWAdministracionForseti.ELIMINA_ESTADO_ANOTACION)) {
			return AWAdministracionForseti.ADICIONA_ESTADO_ANOTACION;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_DOMINIO_NAT_JURIDICA)
				|| accion.equals(AWAdministracionForseti.ELIMINA_DOMINIO_NAT_JURIDICA)) {
			return AWAdministracionForseti.ADICIONA_DOMINIO_NAT_JURIDICA;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_CIRCULO_FESTIVO)
				|| accion.equals(AWAdministracionForseti.ELIMINA_CIRCULO_FESTIVO)
				|| accion.equals(AWAdministracionForseti.SELECCIONA_CIRCULO_FESTIVO)) {
			return AWAdministracionForseti.ADICIONA_CIRCULO_FESTIVO;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_EJE)
				|| accion.equals(AWAdministracionForseti.ELIMINA_EJE)) {
			return AWAdministracionForseti.ADICIONA_EJE;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_ZONA_REGISTRAL)
				|| accion.equals(AWAdministracionForseti.ELIMINA_ZONA_REGISTRAL)
				|| accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL)
				|| accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_DEPTO)
				|| accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_MUNICIPIO)) {
			return AWAdministracionForseti.ADICIONA_ZONA_REGISTRAL;
		} else if (
			accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_TRASLADO)
				|| accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_TRASLADO_DEPTO)
				|| accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_TRASLADO_MUNICIPIO)) {
			return AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_TRASLADO;
		} else if (
			accion.equals(AWAdministracionForseti.ADICIONA_OFICINA_ORIGEN)
				|| accion.equals(AWAdministracionForseti.CONSULTA_OFICINA_ORIGEN_POR_VEREDA)
				|| accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO)
				|| accion.equals(
					AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MUNICIPIO)
				|| accion.equals(AWAdministracionForseti.CONSULTA_OFICINA_ORIGEN_POR_TIPO_OFICINA)
				|| accion.equals(AWAdministracionForseti.ELIMINAR_OFICINA_ORIGEN)) {
			return AWAdministracionForseti.ADICIONA_OFICINA_ORIGEN;
		} else if (accion.equals(AWAdministracionForseti.TRASLADAR)) {
			return AWAdministracionForseti.TRASLADAR; 
		} else if (accion.equals(AWAdministracionForseti.EDITA_CIRCULO_NIT)) {
			return AWAdministracionForseti.EDITA_CIRCULO_NIT;
		} else if (accion.equals(AWAdministracionForseti.EDITA_NATURALEZA_JURIDICA)) {
			return AWAdministracionForseti.EDITA_NATURALEZA_JURIDICA;
		} else if (accion.equals(AWAdministracionForseti.EDITA_CIRCULO)) {
			return AWAdministracionForseti.EDITA_CIRCULO;
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_NATURALEZA_JURIDICA)) {
			return AWAdministracionForseti.SELECCIONA_NATURALEZA_JURIDICA;
		} else if (accion.equals(AWAdministracionForseti.CARGAR_PLANTILLAS_CERTIFICADOS)) {
			return AWAdministracionForseti.CARGAR_PLANTILLAS_CERTIFICADOS;
		} else if (accion.equals(AWAdministracionForseti.CARGAR_PLANTILLA)) {
			return AWAdministracionForseti.CARGAR_PLANTILLA;
		} else if (accion.equals(AWAdministracionForseti.EDITAR_PLANTILLAS_CERTIFICADOS)) {
			return AWAdministracionForseti.EDITAR_PLANTILLAS_CERTIFICADOS;
		} else if (accion.equals(AWAdministracionForseti.PROCESO_CATASTRO)) {
            return AWAdministracionForseti.PROCESO_CATASTRO;
        } else if (accion.equals(AWAdministracionForseti.REABRIR_FOLIO)) {
			return AWAdministracionForseti.REABRIR_FOLIO;
		} else if (accion.equals(AWAdministracionForseti.EDITAR_OFICINA_ORIGEN)) {
			return AWAdministracionForseti.EDITAR_OFICINA_ORIGEN;
		} else if (accion.equals(AWAdministracionForseti.ENVIAR_OFICINA_EDICION)) {
		   return AWAdministracionForseti.ENVIAR_OFICINA_EDICION;
	   } else if (accion.equals(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO)){
		   return ControlNavegacionAdministracionForseti.LISTA_DEPARTAMENTO_CIRCULO_OK;
	   } 	   
	   //Edición de tipos de naturaleza jurídica.
	   else if (accion.equals(AWAdministracionForseti.EDITA_TIPO_NATURALEZA_JURIDICA)){
		   return AWAdministracionForseti.EDITA_TIPO_NATURALEZA_JURIDICA;
	   }
	   
       //Edición de detalles de tipos de naturaleza jurídica.
	   else if (accion.equals(AWAdministracionForseti.EDITA_DETALLES_NATURALEZA_JURIDICA)){
		   return AWAdministracionForseti.EDITA_DETALLES_NATURALEZA_JURIDICA;
	   }

	   //Termina edición de tipos de naturaleza jurídica.
	   else if (accion.equals(AWAdministracionForseti.TERMINA_EDICION_NATURALEZA)){
		   return AWAdministracionForseti.TERMINA_EDICION_NATURALEZA;
	   }else if (accion.equals(AWAdministracionForseti.ELIMINAR_OFICINA_ORIGEN_NOTARIA)||
	   		(accion.equals(AWAdministracionForseti.EDITAR_OFICINA_ORIGEN_NOTARIA))||
	   		(accion.equals(AWAdministracionForseti.CONSULTA_OFICINA_ORIGEN_POR_VEREDA_NOTARIA))||
	   		(accion.equals(AWAdministracionForseti.ENVIAR_OFICINA_EDICION_NOTARIA))||
	   		(accion.equals(AWAdministracionForseti.ADICIONA_OFICINA_ORIGEN_NOTARIA))||
			(accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO_NOTARIA))){
	   	
		   return AWAdministracionForseti.EDITAR_OFICINA_ORIGEN_NOTARIA;
	   }else if(accion.equals(AWAdministracionForseti.INHABILITAR_CIRCULO_REGISTRAL)){
		   return ControlNavegacionAdministracionForseti.INHABILITAR_CIRCULO;
	   }else if(accion.equals(AWAdministracionForseti.TERMINAR_CIRCULO_INHABILITAR)){
		   return ControlNavegacionAdministracionForseti.ASOCIAR_ZONA_CIRCULO_INHABILITADO;
	   }else if(accion.equals(AWAdministracionForseti.VOLVER_CIRCULO_INHABILITAR)){
		   return AWAdministracionForseti.ADICIONA_CIRCULO_REGISTRAL;
	   }else if(accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_DEPTO_INHAB)){
		   return ControlNavegacionAdministracionForseti.ASOCIAR_ZONA_CIRCULO_INHABILITADO;
	   }else if(accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_MUNICIPIO_INHAB)){
		   return ControlNavegacionAdministracionForseti.ASOCIAR_ZONA_CIRCULO_INHABILITADO;
	   }else if(accion.equals(AWAdministracionForseti.VOLVER_INHAB_CIRCULO_ZONAS)){
		   return ControlNavegacionAdministracionForseti.INHABILITAR_CIRCULO;
	   }else if(accion.equals(AWAdministracionForseti.TERMINA_ZONA_CIRC_INHAB)){
		   return ControlNavegacionAdministracionForseti.ASOCIAR_USUARIOS_CIRCULO_INHABILITADO;
	   }else if(accion.equals(AWAdministracionForseti.ADICIONA_ZONA_REGISTRAL_CIRC_INHAB)
			   ||accion.equals(AWAdministracionForseti.ELIMINA_ZONA_REGISTRAL_INHAB)){
		   return ControlNavegacionAdministracionForseti.ASOCIAR_ZONA_CIRCULO_INHABILITADO;
	   }else if(accion.equals(AWAdministracionForseti.TERMINAR_PROCESO_CIRCULO_INHAB)){
		   return AWAdministracionForseti.ADICIONA_CIRCULO_REGISTRAL;
	   }else if(accion.equals(AWAdministracionForseti.USUARIOS_MOVER)
			   ||accion.equals(AWAdministracionForseti.USUARIOS_CREAR_INHAB)
			   ||accion.equals(AWAdministracionForseti.ELIMINAR_USUARIOS_CREADOS)
			   ||accion.equals(AWAdministracionForseti.VOLVER_USUARIO_ROLCONSULTA)
			   ||accion.equals(AWAdministracionForseti.ELIMINAR_USUARIOS_TRASLADADOS)){
		   return ControlNavegacionAdministracionForseti.ASOCIAR_USUARIOS_CIRCULO_INHABILITADO;
	   }else if(accion.equals(AWAdministracionForseti.VOLVER_USUARIO_CIRCULO_INHAB)){
		   return ControlNavegacionAdministracionForseti.ASOCIAR_ZONA_CIRCULO_INHABILITADO;
	   }else if(accion.equals(AWAdministracionForseti.TERMINAR_USUARIO_CIRCULO_INHAB)){
		   return ControlNavegacionAdministracionForseti.ASOCIAR_ROL_USUARIOS_CIRCULO_INHABILITADO;
	   }else if (accion.equals(AWAdministracionForseti.CARGAR_CIRCULOS_INHABILITADOS)
	   		||accion.equals(AWAdministracionForseti.TRASLADAR_FOLIOS)){
	   		return ControlNavegacionAdministracionForseti.TRASLADO_MASIVO_FOLIOS;
	   }
           /*
            * @author      :   Carlos Mario Torres urina
            * @change      :   Se modifica la navegacion para el proceso traslado de folios masivo
            * Caso Mantis  :   
            */
           else if(accion.equals(AWAdministracionForseti.ONSELECT_CIRCULO_ORIGEN)){
		   return AWAdministracionForseti.ONSELECT_CIRCULO_ORIGEN;
	   }else if(accion.equals(AWAdministracionForseti.ONSELECT_DEPARTAMENTO_ORIGEN)){
		   return AWAdministracionForseti.ONSELECT_DEPARTAMENTO_ORIGEN;
	   }
           else if(accion.equals(AWAdministracionForseti.ONSELECT_MUNICIPIO_ORIGEN)){
		   return AWAdministracionForseti.ONSELECT_MUNICIPIO_ORIGEN;
	   }else if(accion.equals(AWAdministracionForseti.ONSELECT_VEREDA_ORIGEN)){
		   return AWAdministracionForseti.ONSELECT_VEREDA_ORIGEN;
	   }else if(accion.equals(AWAdministracionForseti.ONSELECT_CIRCULO_DESTINO)){
		   return AWAdministracionForseti.ONSELECT_CIRCULO_DESTINO;
	   }else if(accion.equals(AWAdministracionForseti.ONSELECT_DEPARTAMENTO_DESTINO)){
		   return AWAdministracionForseti.ONSELECT_DEPARTAMENTO_DESTINO;
	   }else if(accion.equals(AWAdministracionForseti.ONSELECT_MUNICIPIO_DESTINO)){
		   return AWAdministracionForseti.ONSELECT_MUNICIPIO_DESTINO;
	   }else if(accion.equals(AWAdministracionForseti.TRASLADAR_MASIVO)){
		   return AWAdministracionForseti.TRASLADAR_MASIVO;
	   }else if(accion.equals(AWAdministracionForseti.FOLIO_CONFIRMACION_MASIVO)){
		   return AWAdministracionForseti.FOLIO_CONFIRMACION_MASIVO;
	   }else if(accion.equals(AWAdministracionForseti.TRASLADO_CONFIRMACION_MASIVO)){
		   return AWAdministracionForseti.TRASLADO_CONFIRMACION_MASIVO;
	   }else if(accion.equals(AWAdministracionForseti.DESCARGAR_ARCHIVO)){
		   return AWAdministracionForseti.DESCARGAR_ARCHIVO;
	   }else if(accion.equals(AWAdministracionForseti.CONSULTAR_ARCHIVOS)){
		   return AWAdministracionForseti.CONSULTAR_ARCHIVOS;
	   }
            /*
            * @author      :   Carlos Mario Torres urina
            * @change      :   Se modifica la navegacion para el proceso traslado de folios masivo
            * Caso Mantis  :   14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
            */
           else if(accion.equals(AWAdministracionForseti.AGREGAR_FUNDAMENTO_MASIVO)||accion.equals(AWAdministracionForseti.AGREGAR_FUNDAMENTO_MASIVO+"_DESTINO")) {
               return AWAdministracionForseti.ONSELECT_CIRCULO_ORIGEN;
           } else if(accion.equals(AWAdministracionForseti.ELIMINAR_FUNDAMENTO_MASIVO) || accion.equals(AWAdministracionForseti.ELIMINAR_FUNDAMENTO_MASIVO+"_DESTINO")) {
               return AWAdministracionForseti.ONSELECT_CIRCULO_ORIGEN;
           }
           else if(accion.equals(AWAdministracionForseti.AGREGAR_FUNDAMENTO_CONFIRMACION)) {
               return AWAdministracionForseti.FOLIO_CONFIRMACION;
           } else if(accion.equals(AWAdministracionForseti.ELIMINAR_FUNDAMENTO_CONFIRMACION)) {
               return AWAdministracionForseti.FOLIO_CONFIRMACION;
           }
           
           /*
            * @author      :   Julio Alcázar Rivas
            * @change      :   Se modifica la navegacion en el proceso traslado folio
            * Caso Mantis  :   07123
            */
           else if(accion.equals(AWAdministracionForseti.AGREGAR)){
		   return AWAdministracionForseti.AGREGAR;
	   }else if(accion.equals(AWAdministracionForseti.ELIMINAR)){
		   return AWAdministracionForseti.ELIMINAR;
	   } else if(accion.equals(AWAdministracionForseti.AGREGAR_FUNDAMENTO)) {
               return AWAdministracionForseti.AGREGAR_FUNDAMENTO;
           } else if(accion.equals(AWAdministracionForseti.ELIMINAR_FUNDAMENTO)) {
               return AWAdministracionForseti.ELIMINAR_FUNDAMENTO;
           }
           /*
            * @author      : Julio Alcázar Rivas
            * @change      : Manejo de la navegacion
            * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
            */
           else if(accion.equals(AWAdministracionForseti.FOLIO_CONFIRMACION)){
		   return AWAdministracionForseti.FOLIO_CONFIRMACION;
	   }else if(accion.equals(AWAdministracionForseti.TRASLADO_CONFIRMACION)){
		   return AWAdministracionForseti.TRASLADO_CONFIRMACION;
	   }else if(accion.equals(AWAdministracionForseti.CANCELAR_SOLICITUD)){
                   return AWAdministracionForseti.TRASLADO_CONFIRMACION;
           }

		return null;
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
