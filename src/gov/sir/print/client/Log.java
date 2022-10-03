/*
 * Log.java
 *
 * Created on 15 de septiembre de 2004, 15:03
 */

package gov.sir.print.client;

import gov.sir.core.util.DateFormatUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author  dcantor
 */
public class Log {
    
	//ATRIBUTOS
	protected Class c;
    private String rutaLog = "C:\\logsimpresion\\";
    
    //CONSTRUCTOR
    public Log(Class c){

    	this.c = c;
        
        //OBTENER RUTA PARA ESCRIBIR EL ARCHIVO DE LOG
        Properties defaultProps = new Properties();
        try{
        	
        	InputStream i = gov.sir.print.client.Log.class.getResourceAsStream("gov.sir.print.client.printclient.properties");
        	
        	defaultProps.load(i);
        	i.close();
        	this.rutaLog = defaultProps.getProperty("gov.sir.print.client.printclient.ruta.log");
        	
        }catch(Exception e){
        	e.printStackTrace();        	
        }
        
        this.crearArchivoLog();
    }
    public void print(String mensaje){
        
    	StringBuffer sb = new StringBuffer();
        sb.append(new java.util.Date(System.currentTimeMillis()));
        sb.append(" - ");
        sb.append(c.getName());
        sb.append(" - ");
        sb.append(Thread.currentThread().getName());
        sb.append(" - ");
        sb.append(mensaje);

        //System.out.println(sb);
        escribirLog(sb.toString());
        
    }
    
    private void crearArchivoLog(){
    	
    	try{
	    	Date fechaActual = new Date ();

	    	String ruta = this.rutaLog;
	    	File file = new File(ruta);
	    	try{
	    		file.mkdirs();	
	    	}catch(Exception e){
	    		ruta = "";
	    	}
	    	
	    	String archivoLog = ruta + "Log "+DateFormatUtil.format("yyyy-MM-dd", fechaActual)+".txt";
	    	File fileLog = new File(archivoLog);
	    	FileOutputStream fos = new FileOutputStream(fileLog,true);
	    	fos.write("\r\n".getBytes());							
	    	fos.flush();
	    	fos.close();
    	}catch(Exception e){
    		e.printStackTrace();
    		System.out.println("NO FUE POSIBLE CREAR EL ARCHIVO PARA GUARDAR EL LOG DEL DIA" + e.getMessage());
    	}
    } 
    
    private void escribirLog(String log){
    	
    	try{
	    	Date fechaActual = new Date ();

	    	String ruta = this.rutaLog;
	    	
	    	String archivoLog = ruta + "Log "+DateFormatUtil.format("yyyy-MM-dd", fechaActual)+".txt";
	    	File fileLog = new File(archivoLog);
	    	FileOutputStream fos = new FileOutputStream(fileLog,true);
	    	fos.write( (log + "\r\n" ).getBytes());							
	    	fos.flush();
	    	fos.close();
    	}catch(Exception e){
    		e.printStackTrace();
    		System.out.println(log);
    	}
    }     
        
}