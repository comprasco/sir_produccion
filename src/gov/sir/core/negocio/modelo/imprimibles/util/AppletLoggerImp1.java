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
 * @author ahurtado
 * @version 1.0
 *
 * - type = Singleton
 */
public class AppletLoggerImp1
    implements AppletLogger {

  /* TODO: extender de org.apache.log4j.Logger ? */
  protected static AppletLoggerImp1 instance;

  public static
  AppletLoggerImp1 getAppletLogger() {
     if( null == instance )
       instance = new AppletLoggerImp1();
     return instance;
  }

  private AppletLoggerImp1() {};

  protected static boolean debugEnabled = true;


  private static String INFO_STRING = "INFO";
  private static String DEBUG_STRING = "DEBUG";


  protected void printConsoleMessage( String type, String message, boolean printDateActive ) {
    if( printDateActive ){
      java.util.Date clientDate = new java.util.Date();
      System.out.println( clientDate + " ");
    }
    System.out.println( type + ": " +  message );
  }

  public void info( String message ) {
    printConsoleMessage( INFO_STRING,  message, false );
  }
  public void debug( String message ) {
    if( debugEnabled )
      printConsoleMessage( DEBUG_STRING,  message, false );
  }

}
/* *** FIN:INNER ** */
