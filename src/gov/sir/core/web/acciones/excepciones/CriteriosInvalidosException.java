package gov.sir.core.web.acciones.excepciones;

import org.auriga.smart.web.acciones.AccionWebException;


// BUG 6223
/**
 * Esta clase se utiliza para identificar las situaciones en las que los
 * criterios de búsqueda son incompatibles, es decir, cuando se ha utilizado
 * como criterio la matrícula y luego otro, o viceversa
 *
 * @author jalvarez
 * @version 1.0
 */
public class CriteriosInvalidosException
    extends AccionWebException {
  public CriteriosInvalidosException() {
    super("La combinación de criterios de búsqueda es inválida");
  }
  public CriteriosInvalidosException(String arg0) {
    super(arg0);
  }
}
