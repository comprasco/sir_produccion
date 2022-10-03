package gov.sir.core.negocio.modelo.util;

public final class InfoLog {
	
	public static final String INICIA  = "INICIA";
	public static final String TERMINA = "TERMINA";
	
	public static final String VACIO   = "";
	
	public static final String LLAMADO_PROPIO  = "LLAMADO_PROPIO";
	public static final String LLAMADO_OTROS = "LLAMADO_OTROS";
	
	
    public static void informarLogs( String itemKey, String ejecucionId, Object object, String methodId, String iniciaOTerminaOVacio, String tipoAccion, String descripcion, String valorDescripcion ) {
	    	/*
	    	StringBuffer buffer;
	    	buffer = new StringBuffer(2048);
	    	buffer.append(",$$$LOG-EJECUCION$$$");
	    	
	        long currentTimeMillis = System.currentTimeMillis();
	        buffer.append( ","+currentTimeMillis );
	        
	        String currentTimeNormal = null;
	        
	        formatDate: {
	        	
	        	Date date = new Date( currentTimeMillis );
	        	final String format = "yyyy-MM-dd HH:mm:ss SSS";
	
	            SimpleDateFormat df = new SimpleDateFormat();
	            df.applyPattern( format );
	            df.setLenient( false );
	
	            currentTimeNormal = df.format( date );
	
	        } // :toString        
	        
	        buffer.append( ","+ currentTimeNormal );
	        buffer.append( ","+ itemKey );
	        buffer.append( ","+ ejecucionId);
	        
	        String clazzId = "";
	        if( null != object ) {
	        	clazzId = object.getClass().getName();
	        }
	        buffer.append( ","+ clazzId );
	        buffer.append( ","+ methodId );
	        
	        buffer.append(","+iniciaOTerminaOVacio);
	        buffer.append(","+tipoAccion);
	        buffer.append(","+descripcion);
	        buffer.append(","+valorDescripcion);
	        
	        buffer.append(",$$$END-LOG-EJECUCION$$$");
	        
	        System.out.println( buffer.toString() );
	        */
	} // end-method	
    
    public static void main( String[] args ) {
    	
    	InfoLog log = new InfoLog();
    	
    	informarLogs("2006-400-3-1","5001",log,"hacer log",InfoLog.INICIA,InfoLog.LLAMADO_PROPIO,"log/voyaHacerAlgo","");
    	
    } // end

}
