/* Decompiler 2ms, total 109ms, lines 56 */
package gov.sir.core.negocio.modelo.constantes;

public final class CDevoluciones {
   public static final String MODIFICACION_VALOR_DEVOLVER = "NUEVO VALOR A DEVOLVER: ";
   public static final String START_NOTA_NOTIFICADA = "START_NOTA_NOTIFICADA";
   public static final String JUZGADO_NOTA_NOTIFICADA = "JUZGADO_NOTA_NOTIFICADA";
   public static final String RECURSOS_NOTA_NOTIFICADA = "RECURSOS_NOTA_NOTIFICADA";
   public static final String USUARIO = "USUARIO";
   public static final String OFICINA_ORIGEN = "OFICINA_ORIGEN";
   public static final String CORREO_ELECTRONICO = "CORREO_ELECTRONICO";
   public static final String NOT_USUARIO = "NOT_USUARIO";
   public static final String NOT_OFICINA = "NOT_OFICINA";
   public static final String DEFAULT = "DEFAULT";
   public static final String PLAZO_NOTIFICACION_NOTA_DEV = "PLAZO_NOTIFICACION_NOTA_DEV";
   public static final String PLAZO_NOTIFICADA = "PLAZO_NOTIFICADA";
   public static final String PLAZO_NOTIFICAR = "PLAZO_NOTIFICAR";
   public static final String PLAZO_NOTIFICADA_JUZGADO = "PLAZO_NOTIFICADA_JUZGADO";
   public static final String PLAZO_RECURSO = "PLAZO_RECURSO";
   public static final String ID_NOTIFICACION = "ID_NOTIFICACION";
   public static final String NOTIFICATION_ALERT = "NOTIFICATION_ALERT";
   public static final String ALERTA_VENCIMIENTO = "ALERTA_VENCIMIENTO";
   public static final String ALERTA_VENCIMIENTO_RECURSOS = "ALERTA_VENCIMIENTO_RECURSOS";
   public static final String REC_REPO = "REC_REPO";
   public static final String REC_APEL = "REC_APEL";
   public static final String EDITAR_RECURSO = "EDITAR_RECURSO";
   public static final String NO_PROCEDE_RECURSO = "NO_PROCEDE_RECURSO";
   public static final String EXPIRES = "EXPIRES";
   public static final String DAYS_LEFT = "DAYS_LEFT";

   public static String getMessageDays(String days, String fase, String type) {
      String msg = "";
      if (type.equals(DAYS_LEFT)) {
         msg = "Quedan " + days + " días para ";
      } else {
         if (!type.equals(EXPIRES)) {
            return null;
         }

         msg = "Se han vencido los " + days + " días para ";
      }

      if (fase.equals(CFase.NOT_NOTA_DEVOLUTIVA)) {
         msg = msg + "la gestión de notificar nota devolutiva.";
      } else if (fase.equals(CFase.NOT_NOTA_NOTIFICADA)) {
         msg = msg + "interponer el recurso.";
      } else {
         if (!fase.equals(CFase.NOT_RECURSOS_NOTA)) {
            return null;
         }

         msg = msg + "resolver el recurso.";
      }

      return msg;
   }
}