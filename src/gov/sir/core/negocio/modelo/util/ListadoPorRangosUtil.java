package gov.sir.core.negocio.modelo.util;

import gov.sir.hermod.interfaz.HermodServiceInterface;

import java.util.List;


import com.is21.core.web.paginador.IListaPorRangos;
import gov.sir.core.negocio.modelo.Turno;
import java.util.Collections;

public class ListadoPorRangosUtil implements IListaPorRangos {

	private String idListado;
	private HermodServiceInterface hermod;
	private Object [] parametros;
	private int tamanio = 0;
	
	public int getMaximo() {
		if (tamanio == 0)
		{
			try {
				tamanio = hermod.getTamanioLista(idListado, parametros);
			} catch (Throwable e) {
				Log.getInstance().error(ListadoPorRangosUtil.class, "error obteniendo el tamaño de: " + idListado, e);
			}
		}
		return tamanio;
	}

	public List obtenerSubLista(int rangoInf, int rangoSup) {
		if (this.getParametros() == null)
		{
			Log.getInstance().warn(ListadoPorRangosUtil.class, "propidad parametros con valor nulo");
			return null;
		}
		if (this.getIdListado() == null)
		{
			Log.getInstance().warn(ListadoPorRangosUtil.class, "propidad idListado con valor nulo");
			return null;
		}
		if (this.getHermod() == null)
		{
			Log.getInstance().warn(ListadoPorRangosUtil.class, "propidad hermod con valor nulo");
			return null;
		}
		try {
                      /**
                        * @author      :   Carlos Torres
                        * @Caso Mantis :   11604: Acta - Requerimiento No 030_453_Funcionario_Fase_ Entregado
                        */                      
                        
                        List turnos = hermod.getListaPorRangos(idListado, parametros, rangoInf, rangoSup);
                        Collections.sort(turnos,new IDidTurnoComparatorRelacion());
                        String relacion = "";
                        for(Object t: turnos){
                            Turno turno = (Turno)t;
                            if(relacion.equals(turno.getIdRelacionActual())){
                                turno.setIdRelacionActual("");
                            }else if(turno.getIdRelacionActual() == null){
                                turno.setIdRelacionActual("Sin Relacion");
                            }else{
                                relacion = turno.getIdRelacionActual();
                            }
                        }
			return turnos;
		} catch (Throwable e) {
			Log.getInstance().error(ListadoPorRangosUtil.class, "error obteniendo la lista: " + idListado +"("+rangoInf+","+rangoSup+"]", e);
			return null;
		}
	}

	public HermodServiceInterface getHermod() {
		return hermod;
	}

	public void setHermod(HermodServiceInterface hermod) {
		this.hermod = hermod;
	}

	public String getIdListado() {
		return idListado;
	}

	public void setIdListado(String idListado) {
		this.idListado = idListado;
	}

	public Object[] getParametros() {
		return parametros;
	}

	public void setParametros(Object[] parametros) {
		this.parametros = parametros;
	}

}
