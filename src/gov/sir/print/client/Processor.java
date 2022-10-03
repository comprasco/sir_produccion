package gov.sir.print.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import gov.sir.core.negocio.modelo.ImprimiblePdf;
import gov.sir.core.negocio.modelo.util.PdfCreator;
import gov.sir.print.common.Bundle;
import gov.sir.print.common.Imprimible;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class Processor {
    
    protected boolean DOCUMENTO_IMPRESO = false;
    protected Log logger;
    private transient Object lock = new Object();
    
    public Processor() {
        logger = new Log(Processor.class);
    }
    
    public void procesar(ByteArrayOutputStream baos) throws Exception{
    	//ObjectOutputStream obj = new ObjectOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Bundle b = Bundle.decode(bais);
        procesar(b);
    }
    
    
    protected void procesar(Bundle b) throws Exception{
        
        //Imprimible imp = b.getImprimible();
        //ImprimiblePdf impPdf = b.getImprimiblePdf();
        //byte []bytesComprimidos = b.getDatosComprimidos();
		int numeroCopias = b.getNumeroCopias();
		
		logger.print(" SE QUIERE IMPRIMIR CON LA IMPRESORA : " + (b.getNombreImpresora()!=null ? b.getNombreImpresora() : " CUALQUIERA") );		
		logger.print("NUMERO DE IMPRESIONES ES:" + numeroCopias);		
		        
        for (int i = 0; i < numeroCopias; i++){
        	if (b.getImprimible() != null) {
        		logger.print("METODO	NORMAL");
    			Imprimible tempImprimible  = (Imprimible)copy (b.getImprimible());
    			tempImprimible.imprimir();	
    			tempImprimible = null;	
        	} else if(b.getImprimiblePdf()!=null){
        		logger.print("METODO	PDF");
        		PrintService servicio = null;
        		servicio = PrintServiceLookup.lookupDefaultPrintService();
        		PdfCreator pdfCreator = new PdfCreator();
        		pdfCreator.generarProcesoImpresion(b.getImprimiblePdf().getDatosImprimible(),servicio);
        	}else if(b.getDatosComprimidos()!=null){
        		logger.print("METODO	ZIP");
        		
				//Se obtiene un nombre único para colocarle al archivo
				//que estara compuesto por los últimos cinco digitos del millisegundo
				//actual + un número aleatorio entre 0 y 100000
				long time = System.currentTimeMillis();
				String timeString = String.valueOf(time);
				int temp = (int)Math.ceil(Math.random()*100000);
				String nombreArchivo = timeString.substring((timeString.length()- 6), timeString.length()) + temp;        		
        		
        		Imprimible imprimible = getImprimibleFromBytes(b.getDatosComprimidos(), nombreArchivo);
    			Imprimible tempImprimible = (Imprimible)copy (imprimible);
    			tempImprimible.imprimir();	
    			tempImprimible = null;	
        	}
        }    
   }
    
    /**
     * @author Fernando Padilla Velez
     * @change Modificado para el caso MANTIS 7602: Logs de Impresion, se realizó refactory, ya que,
     * si resultaba algún problema o error en el proceso no se borraban los archivos del temporales.
     */    
    public Imprimible getImprimibleFromBytes(byte bytesComprimidos[], String nombreArchivo) throws Exception{
    	
        //SE DETERMINA UN ARCHIVO FISICO DONDE DESCOMPRIMIR EL .ZIP
    	Properties defaultProps = new Properties();
        String directorio="C:\\";
       	InputStream i = null;
        Imprimible imprimible = null;
        try{        	
        	i = gov.sir.print.client.Log.class.getResourceAsStream("gov.sir.print.client.printclient.properties");        	
        	defaultProps.load(i);
        	directorio = defaultProps.getProperty("gov.sir.print.client.printclient.ruta.log");        	

        
    	nombreArchivo = directorio+ nombreArchivo;
        
    	//SE DESCOMPRIME EL ARCHIVO
        ZipInputStream zin = new ZipInputStream(new ByteArrayInputStream(bytesComprimidos) );
    
        // Get the first entry
        ZipEntry entry2 = zin.getNextEntry();
    
        // Open the output file
        OutputStream out2 = new FileOutputStream(nombreArchivo);
    
        // Transfer bytes from the ZIP file to the output file
        byte[] buf2 = new byte[1024];
        int len2;
        while ((len2 = zin.read(buf2)) > 0) {
            out2.write(buf2, 0, len2);
        }
    
        // Close the streams
        out2.close();
        zin.close();
        
        
        //SE OBTIENEN LOS BYTES COMPRIMIDOS
        FileInputStream fis = new FileInputStream(nombreArchivo);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        byte[] bufTemp = new byte[1024];
        int len3;
        while ((len3 = fis.read(bufTemp)) > 0) {
            baos.write(bufTemp, 0, len3);
        }
    
        // Close the streams
        baos.close();
        fis.close();
        
        //SE OBTIENEN Y SE RETORNAN LOS BYTES QUE ESTABAN COMPRIMIDOS A UN OBJETO IMPRIMIBLE
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray()) ;
		ObjectInputStream ois = new ObjectInputStream(bais);
		imprimible = (Imprimible)ois.readObject();
		
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(i != null){
                i.close();
            }		
		//SE ELIMINA EL ARCHIVO TEMPORAL
		try{
			java.io.File file = new File (nombreArchivo);
	        if(file.exists ()){
	        	file.delete ();
	        }		
		}catch(Exception e){			
		}
        }
		logger.print("NOMBRE ARCHIVO : "  + nombreArchivo);		
		
		return imprimible;
         
    }
   
	/**
	 * Hace una copia de un objeto, para evitar referencias de apuntadores en memoria.
	 * be serialized.
	 * @param orig
	 * @return
	 **/
	protected static Object copy(Object orig) {
		Object obj = null;

		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(orig);
			out.flush();
			out.close();

			// Make an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			obj = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}

		return obj;
	}	    
}
