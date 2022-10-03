package gov.sir.core.negocio.modelo.imprimibles.util;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface AppletLogger extends java.io.Serializable{
  void info( String message );
  void debug( String message );
}
