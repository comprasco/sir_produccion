/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.util;

/**
 *
 * @author Carlos Torres;
 */
import gov.sir.core.negocio.modelo.NaturalezaJuridica;

import java.util.Comparator;

public class VersionNaturalezaJuridicaComparador  implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
              try
		{
			long version1 = ((NaturalezaJuridica) o1).getVersion();
			long version2 = ((NaturalezaJuridica) o2).getVersion();
			Integer retorno = 0;
                        if(version1>version2)
                        {
                            retorno = 1;
                        }else if(version1<version2)
                        {
                            retorno = -1;
                        }else if(version1==version2)
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
