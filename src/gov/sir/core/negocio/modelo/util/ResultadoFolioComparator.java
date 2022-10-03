package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.ResultadoAnotacion;
import gov.sir.core.negocio.modelo.ResultadoFolio;


import java.util.Comparator;
import java.util.List;


public class ResultadoFolioComparator implements Comparator 
{
	
	public int compare(Object resultadoFolio, Object anotherResultadoFolio)
	{
		ResultadoFolio resultadoFolio1 = (ResultadoFolio) resultadoFolio;
		ResultadoFolio resultadoFolio2 = (ResultadoFolio) anotherResultadoFolio;
		try
		{
		
			
			
			
			List resultadosAnotaciones1 = resultadoFolio1.getResultadosAnotacions();
			List resultadosAnotaciones2 = resultadoFolio2.getResultadosAnotacions();
			
			ResultadoAnotacion resultadoAnotacion1 =null;
			ResultadoAnotacion resultadoAnotacion2 = null;
			if (!resultadosAnotaciones1.isEmpty()&&!resultadosAnotaciones2.isEmpty()) {
                resultadoAnotacion1 = seleccionarResultadoAnotacion(resultadosAnotaciones1);
                resultadoAnotacion2 = seleccionarResultadoAnotacion(resultadosAnotaciones2);
                // Se compara el estado del ultimo propietario
                if(((new Boolean(resultadoAnotacion1.isPropietario()).toString()).compareTo(
                		new Boolean(resultadoAnotacion2.isPropietario()).toString()))==0){
                	//Se compara la matricula - circulo
                	String []idMatricula = (resultadoFolio1.getIdMatricula()).split("-");
                	String []idMatricula2 = (resultadoFolio2.getIdMatricula()).split("-");
                	Integer mat1 = new Integer (idMatricula[1]);
                	Integer mat2 = new Integer (idMatricula2[1]);
                	int comparator = mat1.compareTo(mat2);
                		return comparator;
                }
                else {
                	if(resultadoAnotacion1.isPropietario()){
                		return -1;
                	}else{
                		return 1;
                	}
    				
    			}
			}
		} catch (Exception e) {
			Log.getInstance().error(ResultadoFolioComparator.class, "ERROR AL ORDENAR LAS CONSULTAS"+resultadoFolio1.getIdMatricula());
			Log.getInstance().error(ResultadoFolioComparator.class, "ERROR AL ORDENAR LAS CONSULTAS 222222"+resultadoFolio2.getIdMatricula());
			Log.getInstance().error(ResultadoFolioComparator.class, e);
			return(0);
		}
		return 0;
	}
			
			
			
			// se Compara el anio
/*			Integer anio1 = new Integer(turno1.getAnio());
			Integer anio2 = new Integer(turno2.getAnio());
			
			if (anio1.compareTo(anio2) == 0) {
				// Se comprara el idcirculo
				int comparator = 0;
				try {
					Integer circulo1 = new Integer(turno1.getIdCirculo());
					Integer circulo2 = new Integer(turno2.getIdCirculo());
					comparator = circulo1.compareTo(circulo2);
				} catch (Exception ee) {
					try {
						String cir1 = turno1.getIdCirculo();
						String cir2 = turno2.getIdCirculo();
						comparator = cir1.compareTo(cir2);
					} catch (Exception eee) {}
				}
				
				if ( comparator == 0) {
					// Se compara el idproceso
					Integer proceso1 = new Integer(Long.toString(turno1.getIdProceso()));
					Integer proceso2 = new Integer(Long.toString(turno2.getIdProceso()));
					
					if (proceso1.compareTo(proceso2) == 0) {
						//	Se compara el idTurno
						Integer turnito1 = new Integer(turno1.getIdTurno());
						Integer turnito2 = new Integer(turno2.getIdTurno());
						
						return turnito1.compareTo(turnito2);	
					} else {
						return proceso1.compareTo(proceso2);
					}
				} else {
					return comparator;
				}
			} else {
				return anio1.compareTo(anio2);
			}
			
		} catch (NumberFormatException e) {
			logger.error(e);
			return(0);
		}
	}*/
    private ResultadoAnotacion seleccionarResultadoAnotacion(List resultados) {

        ResultadoAnotacion resultado = null;
        ResultadoAnotacion resultadoTemp = null;

        resultadoTemp = (ResultadoAnotacion)resultados.get(resultados.size() - 1);
        for(int i = resultados.size() - 1; i >= 0; i--) {

            resultado = (ResultadoAnotacion)resultados.get(i);
            if(resultado.isPropietario() &&
               ( ((ResultadoAnotacion)resultado).getAnotacion() != null &&
                 ((ResultadoAnotacion)resultado).getAnotacion().getNaturalezaJuridica() != null &&
                 ((ResultadoAnotacion)resultado).getAnotacion().getNaturalezaJuridica().getDominioNaturalezaJuridica() != null &&
                 ((ResultadoAnotacion)resultado).getAnotacion().getNaturalezaJuridica().getDominioNaturalezaJuridica().getIdDominioNatJur() != null &&
                 ((ResultadoAnotacion)resultado).getAnotacion().getNaturalezaJuridica().getDominioNaturalezaJuridica().getIdDominioNatJur().equals("X"))) {
                return resultado;
            }
        }

        return resultadoTemp;
    }
}