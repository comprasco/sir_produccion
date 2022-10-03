package gov.sir.hermod.pagos;

import gov.sir.core.negocio.modelo.DocumentoFotocopia;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.TipoFotocopia;
import gov.sir.core.negocio.modelo.TipoFotocopiaPk;
import java.util.Iterator;

import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.HermodDAOFactory;
import gov.sir.hermod.interfaz.HermodServiceInterface;

import org.auriga.smart.servicio.ServiceLocator;


/**
 * Clase que se encarga de obtener el valor que debe ser liquidado por
 * una solicitud de Fotocopias
 * @author  dlopez
 */
public class LiquidadorFotocopia extends Liquidador {
    
	
	/**
	*  Constante que identifica que el tipo de fotocopia es mecánico
	*/
	public final static String MECANICO = "MECANICO";
	

	/**
	*  Constante que identifica que el tipo de fotocopia es normal.
	*/
	public final static String NORMAL = "NORMAL";
	
	
	/**
	*  Constante que identifica que el tipo de fotocopia es mecánico
	*/
	public final static String TIPO_MECANICO = "1";
	

	/**
	*  Constante que identifica que el tipo de fotocopia es normal.
	*/
	public final static String TIPO_NORMAL = "2";

	
	/** 
	*Crea una nueva instancia de LiquidadorFotocopia
	*/
	public LiquidadorFotocopia() 
	{
		
	}
    
    
	/**
	* Obtiene el valor que debe ser liquidado por concepto de una solicitud de
	* Fotocopias. 
	* @param liquidacion Liquidacion antes de obtener el valor a pagar.
	* @return La liquidación con el valor que debe pagarse. 
	* @see gov.sir.core.negocio.modelo.Liquidacion
	* @throws <code>LiquidarException</code>
	*/
	public Liquidacion liquidar(Liquidacion liquidacion) throws LiquidarException 
	{

				
		LiquidacionTurnoFotocopia liqFotocopia = (LiquidacionTurnoFotocopia) liquidacion;
		SolicitudFotocopia s = (SolicitudFotocopia) liqFotocopia.getSolicitud();
   					    
		SolicitudPk sid = new SolicitudPk();
		sid.idSolicitud = s.getIdSolicitud();
			
	
	   try 
	   {
			HermodDAOFactory factory = HermodDAOFactory.getFactory();
		    ServiceLocator sl = ServiceLocator.getInstancia();
			HermodServiceInterface hermod = (HermodServiceInterface) sl.getServicio("gov.sir.hermod");
			
		   //Se obtiene el valor que debe ser liquidado por concepto
		   //de la solicitud de fotocopias.
		   double valorLiq = 0;
		   String code ="";
		   
		   DocumentoFotocopia doc;
		   double valorDoc;
		   for(Iterator it=s.getDocumentoFotocopia().iterator(); it.hasNext();){
		   		doc = (DocumentoFotocopia)it.next();
		   		if(doc.getTipoFotocopia()==null){
		   			throw new LiquidarException("El documento de fotocopia número "+doc.getIdDocFotocopia()+" no tiene tipo de fotocopia");
		   		}
				
				TipoFotocopiaPk tid = new TipoFotocopiaPk();
				tid.idTipoFotocopia = doc.getTipoFotocopia().getIdTipoFotocopia();
				TipoFotocopia tipoFot = factory.getVariablesOperativasDAO().getTipoFotocopiaByID(tid);
			
				//Se hace la consulta sobre la tabla de tarifas
			    Tarifa tarifaFotocopias = getTarifa(CTipoTarifa.FOTOCOPIAS, tipoFot.getNombre());	
				if (tarifaFotocopias == null)
				{
					throw new DAOException ("No se encontró valor de tarifa de fotocopias para la llave: "+CTipoTarifa.FOTOCOPIAS+" - "+code);
				}
			 	double valorTarifa  = tarifaFotocopias.getValor();
				
				//Se obtiene el valor para el documento:
				/* JAlcaza caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                                 * Redondeo de valorTarifa antes del calculo de copias y numero de hojas.
                                 */
                                valorDoc = roundValue(valorTarifa)*doc.getNumCopias()*doc.getNumHojas();

		   		valorLiq = valorLiq+valorDoc;		   		
		   }
			
			/*
			//Se coloca un tiemp de delay 
			JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
			boolean respuesta = jdoDAO.getObjectByLlavePrimaria(new SolicitudEnhanced.ID(sid), 5);
           */
          
		   //Se asigna el valor obtenido a la liquidación. 
		   liqFotocopia.setValor(valorLiq); 		  				   
            /** @author : HGOMEZ
            *** @change : Se verifica que la liquidación es exenta y se le asigna
            *** el valor 0 y la tarifa EXENTO a las correspondientes variables de
            *** la liquidación fotocopia.
            *** Caso Mantis : 12288
            */
            if (liquidacion.isEsExento()) {
                liqFotocopia.setValor(0.0);
                liqFotocopia.setTarifa("EXENTO");
            }
		   LiquidacionTurnoFotocopia liqPersistente = factory.getLiquidacionesDAO().addLiquidacionToSolicitudFotocopia(liqFotocopia,s);
		 
			if (liqPersistente==null)
			   throw new LiquidarException("No pudo hacerse persistente la liquidación.");
			   
			   	
			
			return liqPersistente;

		}
		   
		catch (Exception e) 
		{
			Log.getInstance().error(LiquidadorFotocopia.class,e.getMessage(),e);
			throw new LiquidarException("No pudo hacerse persistente la liquidación", e);
		}      
		
		catch (Throwable e) 
		{
			Log.getInstance().error(LiquidadorFotocopia.class,e.getMessage(),e);
			throw new LiquidarException("No se pudo avanzar el turno después de la liquidación", e);
			
		}          
        
	}

	/* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
         * Cambio de redondeo a la centena mas cercana
         */
	private static double roundValue (double valor)
	{
		double diferencia = valor%100;
    	
		//Redondeo hacia la decena anterior.
		if (diferencia < 50)
		{
			valor -= diferencia;
		}
    	
		//Redondeo hacia la centena siguiente.
		else
		{
			valor -= diferencia;
			valor +=100;
		}
    	
		return valor;
	}



	
}
