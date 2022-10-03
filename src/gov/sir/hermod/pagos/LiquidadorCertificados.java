/*
 * LiquidadorCertificados.java
 * Created on 6 de agosto de 2004, 17:14
 */

package gov.sir.hermod.pagos;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoCertificadoPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CTarifa;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.HermodDAOFactory;


/**
 * Clase que se encarga de calcular el valor que debe ser liquidado,por concepto
 * de una solicitud de certificados. 
 * @author  mrios, mortiz, dsalas, dlopez
 */
public class LiquidadorCertificados extends Liquidador {
    

    /**
    *  Crea una nueva instancia del LiquidadorCertificados 
    */
    public LiquidadorCertificados() {
    }
    
    
    
    /**
    * Calcula el valor que debe ser liquidado por concepto de una solicitud
    * de certificados. 
    */
    public Liquidacion liquidar(Liquidacion liquidacion) throws LiquidarException {

	   LiquidacionTurnoCertificado liqCert = (LiquidacionTurnoCertificado) liquidacion;
	   SolicitudCertificado s = (SolicitudCertificado) liqCert.getSolicitud();
	   double valorTotal;
	   
	   try {
            
            HermodDAOFactory factory = HermodDAOFactory.getFactory();
			
			//La liquidación no tiene un valor asociado.
			if (Double.isNaN(liqCert.getValor()) || liqCert.getValor()==0)
			{
				if(liqCert.getTipoCertificado()==null) {
					throw new DAOException ("No existe un tipo de certificado asociado");
				}
				
				//Obtener el valor del certificado
				TipoCertificadoPk tipoID = new TipoCertificadoPk();
				tipoID.idTipoCertificado = liqCert.getTipoCertificado().getIdTipoCertificado();
				
				TipoCertificado tipoCert = factory.getVariablesOperativasDAO().getTipoCertificadoByID(tipoID);
				
				if(tipoCert==null){
					throw new DAOException ("No se encontró el tipo de certificado con el ID "+tipoID.idTipoCertificado);
				}
				
				Tarifa tarifaCertificado = getTarifa(CTipoTarifa.CERTIFICADOS, tipoCert.getNombre());
				//NO SE ENCONTRO VALOR  PARA EL TIPO DE CERTIFICADO
				if (tarifaCertificado == null)
				{
					throw new DAOException ("No se encontró valor para la tarifa de certificados con la llave: "+CTipoTarifa.CERTIFICADOS+" - "+liqCert.getTipoCertificado().getNombre());
				}
				double valorCertificado = tarifaCertificado.getValor();
				
				
				Tarifa tarifaApl = getTarifa(CTipoTarifa.CERTIFICADOS, liqCert.getTipoTarifa());
                //NO SE ENCONTRO VALOR  PARA EL TIPO DE TARIFA APLICADO
				if (tarifaApl == null)
				{
					throw new DAOException ("No se encontró valor para la tarifa aplicada con la llave: "+CTipoTarifa.CERTIFICADOS+" - "+liqCert.getTipoTarifa());
				}
				
				
				//CASO ESPECIAL TARIFA CERTIFICADO ESPECIAL
				if (liqCert.getTipoTarifa().equals(CTipoTarifa.TARIFA_ESPECIAL))
				{
				    /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                                     * Redondeo de la variable tarifaApl.getValor()
                                     */	
                                    valorTotal = roundValue(tarifaApl.getValor());
				}
				
				//CASO NORMAL
				else
				{
                                        /**
                                         * @Author: Santiago Vásquez
                                         * @Change: 2062.TARIFAS.REGISTRALES.2017
                                         */
                                        double tarifaAplicada = 0;
                                        if ((tarifaCertificado.getIdCodigo().equals(CTipoCertificado.TIPO_INMEDIATO_NOMBRE) || 
                                                tarifaCertificado.getIdCodigo().equals(CTipoCertificado.TIPO_PERTENENCIA_NOMBRE) || 
                                                tarifaCertificado.getIdCodigo().equals(CTipoCertificado.TIPO_ASOCIADO_NOMBRE) || 
                                                tarifaCertificado.getIdCodigo().equals(CTipoCertificado.AMPLIACION_TRADICION)) &&
                                                liqCert.isEsFolioMayorExtension()) {
                                            Tarifa tarifaMayorExtension = getTarifa(CTipoTarifa.CERTIFICADOS, CTarifa.TARIFA_MAYOR_EXTENSION);
                                            tarifaAplicada = tarifaMayorExtension.getValor() * tarifaApl.getValor();
                                            valorTotal = roundValue(tarifaAplicada * s.getNumeroCertificados());
                                        } else {
                                            tarifaAplicada   = tarifaApl.getValor();
                                            /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                                            * Redondeo del calculo (valorCertificado * tarifaAplicada)
                                            */
                                            valorTotal = roundValue((valorCertificado * tarifaAplicada))*  s.getNumeroCertificados();
                                        }
				}
						
				
				
				if(s.getTurnoAnterior()!= null){
					Turno t = factory.getTurnosDAO().getTurnoByWFId(s.getTurnoAnterior().getIdWorkflow());
					TurnoPk tId = new TurnoPk();
					tId.anio = t.getAnio();
					tId.idCirculo = t.getIdCirculo();
					tId.idProceso = t.getIdProceso();
					tId.idTurno = t.getIdTurno();
					double valorAnt = factory.getLiquidacionesDAO().getValorByTurno(tId);
					
					//Turno tr = factory.getTurnosDAO().getTurnoByID(tId, false);
					//SolicitudCertificado solAnt = (SolicitudCertificado) tr.getSolicitud();
					/*if (tr.getSolicitud()!= null){
						int numLiq = (int) (tr.getSolicitud().getLastIdLiquidacion() - 1);
						if (numLiq > -1){
							Liquidacion liqAnt = (Liquidacion) tr.getSolicitud().getLiquidaciones().get(numLiq);*/
					valorAnt = valorTotal - valorAnt;
					if (valorAnt < 0){
						valorAnt = 0;
					}
					valorTotal = valorAnt;
					
					valorTotal = roundValue (valorTotal);
					
				}
                                /**
                                 * @author Ellery D. Robles Gómez.
                                 * @change Mantis: 8575 - Se seta el tipo de asociado como ASOCIADO".
                                 */
                                if(liqCert.getRol() != null && (CRoles.LIQUIDADOR_REGISTRO).equals(liqCert.getRol().getRolId())){
                                    liqCert.getTipoCertificado().setIdTipoCertificado(CTipoCertificado.TIPO_ASOCIADO_ID);
                                }
				liqCert.setValor(valorTotal);
			}
			
			if(!s.isPreliquidacion()){

				if (s.getIdSolicitud() == null){
					SolicitudPk srID = factory.getSolicitudesDAO().crearSolicitudCertificado(s);
					/*
					//Se coloca un tiemp de delay 
					JDOVariablesOperativasDAO jdoDAO = new JDOVariablesOperativasDAO();
					boolean respuesta = jdoDAO.getObjectByLlavePrimaria(new SolicitudEnhanced.ID(sid), 5);
					*/
					Log.getInstance().info(LiquidadorCertificados.class,"SOLICITUD GENERADA (objID): "+srID);
					if(srID!=null){
						Log.getInstance().info(LiquidadorCertificados.class,"SOLICITUD GENERADA: "+srID.idSolicitud);
					}
					s = (SolicitudCertificado) factory.getSolicitudesDAO().getSolicitudByID(srID);
					if (s.getLiquidaciones()!= null && s.getLiquidaciones().size()>0){
						liqCert = (LiquidacionTurnoCertificado) s.getLiquidaciones().get(0);
					}
				} else{
					if(!s.isEditado()){
						SolicitudPk srID = new SolicitudPk();
						srID.idSolicitud = s.getIdSolicitud();
						liqCert.setSolicitud(s);
						liqCert = factory.getLiquidacionesDAO().addLiquidacionToSolicitudCertificado(liqCert, srID);
						//liqCert = (LiquidacionTurnoCertificado) factory.getSolicitudesDAO().getLiquidacionByID(liqID);
					}else{
						//obtenga la liquidacion anterior
						if (s.getLiquidaciones()!= null && s.getLiquidaciones().size()>0){
							liqCert = (LiquidacionTurnoCertificado) s.getLiquidaciones().get(0);
						}
					}
				}
			}
                        
			return liqCert;

		}
		catch (Exception e) {
			Log.getInstance().error(LiquidadorCertificados.class,e.getMessage(),e);
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