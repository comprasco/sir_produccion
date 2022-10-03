package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;


/**
 * @author jalvarez
 */
public class SiguienteConsultaSimpleException
    extends AccionWebException {
  public SiguienteConsultaSimpleException() {
    super("Se alcanzó el número máximo de consultas para el usuario. Si desea seguir consultando debe solicitar un nuevo turno de consulta");
  }

  public SiguienteConsultaSimpleException(String msg) {
    super(msg);
  }
}
