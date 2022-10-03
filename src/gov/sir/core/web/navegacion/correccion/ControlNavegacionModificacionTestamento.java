/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.web.WebKeys;
import javax.servlet.http.HttpServletRequest;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.web.acciones.correccion.AWModificarTestamento;
import gov.sir.core.web.acciones.registro.AWTestamentos;

/**
 *
 * @author Carlos Torres
 */
public class ControlNavegacionModificacionTestamento implements ControlNavegacion {

    public final static String GUARDAR_DATOS_TEMPORALES = "GUARDAR_DATOS_TEMPORALES";
    public final static String CARGAR_TESTAMENTO = "CARGAR_TESTAMENTO";
    public final static String REGRESAR_CARGAR_TESTAMENTO_OK = "REGRESAR_CARGAR_TESTAMENTO_OK";
    public final static String REGRESAR_MESACONTROL_TESTAMENTO_OK = "REGRESAR_MESACONTROL_TESTAMENTO_OK";
    public final static String REGRESAR_REVISAR_APROBAR_TESTAMENTO_OK = "REGRESAR_REVISAR_APROBAR_TESTAMENTO_OK";
    public static final String REFRESCAR_EDICION = "REFRESCAR_EDICION";
    public static final String VALIDAR_CIUDADANO = "VALIDAR_CIUDADANO";
    public static final String REGRESAR_REVISION_ANALISIS_TESTAMENTO_OK = "REGRESAR_REVISION_ANALISIS_TESTAMENTO_OK";
    
    @Override
    public void doStart(HttpServletRequest hsr) {
     
    }

    @Override
    public String procesarNavegacion(HttpServletRequest hsr) throws ControlNavegacionException {
        String accion = (String) hsr.getParameter(WebKeys.ACCION);
        if(accion.equals(AWModificarTestamento.CARGAR_TESTAMENTO))
        {
            return CARGAR_TESTAMENTO;
            
        }else if(accion.equals(AWModificarTestamento.AGREGAR_TESTADOR))
        {
            return AWModificarTestamento.AGREGAR_TESTADOR;
        }else if(accion.equals(AWModificarTestamento.ELIMINAR_TESTADOR))
        {
            return AWModificarTestamento.ELIMINAR_TESTADOR;
        }else if(accion.equals(AWModificarTestamento.REGRESAR_CARGAR_TESTAMENTO))
        {
            return REGRESAR_CARGAR_TESTAMENTO_OK;
        }else if(accion.equals(AWModificarTestamento.REGRESAR_MESACONTROL_TESTAMENTO))
        {
            return REGRESAR_MESACONTROL_TESTAMENTO_OK;
        }else if(accion.equals(AWModificarTestamento.REGRESAR_REVISAR_APROBAR_TESTAMENTO))
        {
            return REGRESAR_REVISAR_APROBAR_TESTAMENTO_OK;
        }else if(accion.equals(AWModificarTestamento.GUARDAR_DATOS_TEMPORALES))
        {
            return GUARDAR_DATOS_TEMPORALES;
        }else if(accion.equals(AWModificarTestamento.VALIDAR_CIUDADANO)){
	    return VALIDAR_CIUDADANO;
	}else if(accion.equals(AWModificarTestamento.REFRESCAR_EDICION)){
	    return REFRESCAR_EDICION;
	}else if(accion.equals(AWModificarTestamento.REGRESAR_REVISION_ANALISIS_TESTAMENTO)){
	    return REGRESAR_REVISION_ANALISIS_TESTAMENTO_OK;
	}
        return "";
        
    }

    @Override
    public void doEnd(HttpServletRequest hsr) {
    
    }
    
}
