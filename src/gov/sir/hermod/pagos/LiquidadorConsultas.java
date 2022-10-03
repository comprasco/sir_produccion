/*
 * Clase que se encarga de obteber el valor que debe ser liquidado
 * por concepto de una solicitud de consultas.
 * LiquidadorConsultas.java
 * Created on 21 de septiembre de 2004, 17:14
 */

package gov.sir.hermod.pagos;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.HermodDAOFactory;


/**
 * Clase que se encarga de obtener el valor que debe ser liquidado por
 * una solicitud de consultas.
 * @author  dlopez
 */
public class LiquidadorConsultas extends Liquidador {

    /**
    *Crea una nueva instancia de LiquidadorConsultas
    */
    public LiquidadorConsultas()
    {
    }


    /**
     * Obtiene el valor que debe ser liquidado por concepto de una solicitud de
     * consultas.
     * @param liquidacion Liquidacion antes de obtener el valor a pagar.
     * @return La liquidación con el valor que debe pagarse.
	 * @see gov.sir.core.negocio.modelo.Liquidacion
	 * @throws <code>LiquidarException</code>
     */
    public Liquidacion liquidar(Liquidacion liquidacion) throws LiquidarException
    {


		LiquidacionTurnoConsulta liqCons = (LiquidacionTurnoConsulta) liquidacion;
		SolicitudConsulta s = (SolicitudConsulta) liqCons.getSolicitud();

	   try
	   {

           HermodDAOFactory factory = HermodDAOFactory.getFactory();

           //Se obtiene el valor que debe ser liquidado.
           //Se tienen en cuenta el tipo de alcance geográfico y el tipo
           //de consulta.
           String alcance = liqCons.getAlcanceGeografico().getNombre();
           String tipo = s.getTipoConsulta().getNombre();


           String code = (tipo+'_'+alcance).toUpperCase();

           //SE  OBTIENE LA TARIFA PARA LA CONSULTA.
			Tarifa tarifaConsulta = getTarifa("CONSULTAS", code);
			if (tarifaConsulta == null)
			{
					throw new DAOException ("No se encontró valor de consulta para la llave: "+"CONSULTAS"+" - "+code);
			}
           double valor = tarifaConsulta.getValor();

		   TipoConsulta tipoConsulta  = s.getTipoConsulta();
		   if (tipoConsulta==null)
		   {
			      throw new LiquidarException("La solicitud de consulta es nula");
		   }


		    //Si el tipo de Consulta es compleja, se hace necesario calcular el
		    //valor multiplicando por el numero maximo de Busquedas.
		    String kind = s.getTipoConsulta().getIdTipoConsulta();

		    /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                     * Se elimina codigo comentariado y se aplica el redondeo antes de el calculo de numeros de busquedas.
                     */
                    valor = roundValue(valor);
                    valor *= s.getNumeroMaximoBusquedas();		    
		    liqCons.setValor(valor);

		    SolicitudPk sid = new SolicitudPk();
			sid.idSolicitud = s.getIdSolicitud();
			/*	
			//Se coloca un tiemp de delay 
			JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
			boolean respuesta = jdoDAO.getObjectByLlavePrimaria(new SolicitudEnhanced.ID(sid), 5);
			*/
			
		    LiquidacionTurnoConsulta liqPersistente = factory.getLiquidacionesDAO().addLiquidacionToSolicitudConsulta(liqCons,s);

		    if (liqPersistente==null)
		       throw new LiquidarException("Se obtuvo un valor de liquidación nulo");

			return liqPersistente;

		}

		catch (Exception e)
		{
			Log.getInstance().error(LiquidadorConsultas.class,e.getMessage(),e);
			throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
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
