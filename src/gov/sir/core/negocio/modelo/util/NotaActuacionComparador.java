/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.util;

/**
 *
 * @author Carlos Torres;
 */
import gov.sir.core.negocio.modelo.NotaActuacion;

import java.util.Comparator;

public class NotaActuacionComparador  implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
              try
		{
			Integer idNota1 = Integer.parseInt(((NotaActuacion) o1).getIdNotaActuacion().toUpperCase());
			Integer idNota2 = Integer.parseInt(((NotaActuacion) o2).getIdNotaActuacion().toUpperCase());
			Integer retorno = 0;
                        if(idNota1>idNota2)
                        {
                            retorno = 1;
                        }else if(idNota1<idNota2)
                        {
                            retorno = -1;
                        }else if(idNota1==idNota2)
                        {
                            retorno = 0;
                        }
                        return retorno;
		}
		catch (NumberFormatException e) 
		{
			return(0);
		}catch(UnsupportedOperationException e)
                {
                    return 0;
                }
                catch(Exception e)
                {
                    return 0;
                }
    }
    
}
