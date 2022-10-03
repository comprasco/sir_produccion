/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.constantes;

import gov.sir.core.negocio.modelo.NotificacionNota;
import gov.sir.core.util.DateFormatUtil;

/**
 *
 * @author Cristian Garcia PS
 */
public final class CQueries {
    
    public static String getRespFromCalificacion(String turnoWF){
        String query = " SELECT TRHS_RESPUESTA FROM SIR_OP_TURNO_HISTORIA " +
                        " WHERE TRHS_ID_WORKFLOW = '"+turnoWF+"' " +
                        " AND TRHS_STACK_POS = (SELECT MAX(TRHS_STACK_POS) FROM SIR_OP_TURNO_HISTORIA " +
                        " WHERE TRHS_ID_WORKFLOW = '"+turnoWF+"' AND TRHS_ID_FASE = 'CAL_CALIFICACION')";
        return query;
    }
    
    public static String getBloqueoNotificadorNota(String idMatricula){
        String query = " SELECT ID_MATRICULA FROM SIR_OP_SOLICITUD_FOLIO S " +
                        " INNER JOIN SIR_OP_TURNO T ON S.ID_SOLICITUD = T.ID_SOLICITUD " +
                        " WHERE T.TRNO_ID_FASE IN ('NOT_NOTA_NOTIFICADA','NOT_NOTA_DEVOLUTIVA','NOT_RECURSOS_NOTA') AND ID_MATRICULA = '"+idMatricula+"'";
        return query;
    }
    
    public static String updateNotificacionNota(NotificacionNota notify){
        String query = " UPDATE SIR_OP_NOT_NOTA_DEV "
                + "SET ";
        int count = 0;
        if(notify.getDestino() != null){
            query += " NOT_DESTINO = '" + notify.getDestino() + "'";
            count++;
        }
        if(notify.getTipo() != null){
            query += (count==0?"":", ") + " NOT_TIPO = '" + notify.getTipo() + "'";
            count++;
        }

            query += (count==0?"":", ") + " NOT_APODERADO = '" + notify.getApoderado() + "'";
            count++;
        
        if(notify.getNombres() != null){
            query += (count==0?"":", ") + " NOT_NOMBRES = '" + notify.getNombres() + "'";
            count++;
        }
        if(notify.getApellidos() != null){
            query += (count==0?"":", ") + " NOT_APELLIDOS = '" + notify.getApellidos() + "'";
            count++;
        }
        if(notify.getTipoDocumento() != null){
            query += (count==0?"":", ") + " NOT_TIPO_DOCUMENTO = '" + notify.getTipoDocumento() + "'"; 
            count++;
        }
        if(notify.getDireccion() != null){
            query += (count==0?"":", ") + " NOT_DIRECCION = '" + notify.getDireccion() + "'"; 
            count++;
        }
        if(notify.getTelefono() != null){
           query += (count==0?"":", ") + " NOT_TELEFONO = '" + notify.getTelefono() + "'";  
           count++;
        }
        if(notify.getFechaNotificacion() != null){
           query += (count==0?"":", ") + " FECHA_NOTIFICACION = TO_DATE('" + DateFormatUtil.format(notify.getFechaNotificacion()) + "','DD/MM/YY')";
           count++;
        }
        if(notify.getCorreo() != null){
           query += (count==0?"":", ") + " NOT_CORREO = '" + notify.getCorreo() + "'"; 
           count++;
        }
        if(notify.getDocumento() != null){
           query += (count==0?"":", ") + " NOT_DOCUMENTO = '" + notify.getDocumento() + "'";
           count++;
        }
        
        query += " WHERE ID_NOTIFICACION = '" + notify.getIdNotificacion() + "'" ;

        return query;
    }
    
    public static String deleteNotificacionNota(NotificacionNota notify){
        String query = "DELETE FROM SIR_OP_NOT_NOTA_DEV WHERE ID_NOTIFICACION = '" + notify.getIdNotificacion() + "'";
        return query;
    }
    
    public static String deleteEveryNotifications(String turnoWF){
        String query = "DELETE FROM SIR_OP_NOT_NOTA_DEV WHERE NOT_ID_WORKFLOW = '" + turnoWF + "'";
        return query;
    }
    
}
