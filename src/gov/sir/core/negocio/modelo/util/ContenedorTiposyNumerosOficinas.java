package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.constantes.CTipoOficina;
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.List;
import java.util.Map;

public class ContenedorTiposyNumerosOficinas {
	private List listaTiposOficinas;
	
	private List listaNumerosOficinas;

	public List getListaNumerosOficinas() {
		return listaNumerosOficinas;
	}

	public void setListaNumerosOficinas(List listaNumerosOficinas) {
		List oficinasOrganizadas = listaNumerosOficinas;
		if(oficinasOrganizadas!=null){
			//Se organiza la lista de oficinas origen
	    	OficinaOrigen of1, of2;
	    	int tamano = oficinasOrganizadas.size()-1;
	    	for(int i=0; i<tamano; i++){
	    		for(int j=0; j<tamano; j++){
	    			of1 = (OficinaOrigen)oficinasOrganizadas.get(j);
	    			of2 = (OficinaOrigen)oficinasOrganizadas.get(j+1);
	    			if(!of1.getTipoOficina().getIdTipoOficina().equals(CTipoOficina.TIPO_OFICINA_NOTARIA)
	    					&& !of1.getTipoOficina().getIdTipoOficina().equals(CTipoOficina.TIPO_OFICINA_JUZGADO)
	    					&& !of1.getTipoOficina().getIdTipoOficina().equals(CTipoOficina.TIPO_OFICINA_FISCALIA)
	    					&& of1.getNombre().compareTo(of2.getNombre())>0){
	    				oficinasOrganizadas.remove(j+1);
	    				oficinasOrganizadas.add(j, of2);
	    			}
	    		}
                }
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */

                        Map objetos = new LinkedHashMap();
                        for(Object ofo : oficinasOrganizadas)
                        {
                            OficinaOrigen of = (OficinaOrigen)ofo;
                            String[] id = of.getIdOficinaOrigen().split("-");
                            of.setIdOficinaOrigen(of.getIdOficinaOrigen()+"-"+of.getVersion());
                            if(objetos.containsKey(id[0]))
                            {
                                OficinaOrigen ofi1 = (OficinaOrigen)objetos.get(id[0]);
                                if(of.getVersion()>ofi1.getVersion())
                                {
                                    objetos.put(id[0], of);
                                }
                            }else
                            {
                                objetos.put(id[0], of);
                            }

                        }
                        List lista = new ArrayList(objetos.values());
                 this.listaNumerosOficinas = lista;
		}
	}

	public List getListaTiposOficinas() {
		return listaTiposOficinas;
	}

	public void setListaTiposOficinas(List listaTiposOficinas) {
		this.listaTiposOficinas = listaTiposOficinas;
	}
	
	
	public ContenedorTiposyNumerosOficinas(List listaTiposOficinas,
			List listaNumerosOficinas){
		this.listaTiposOficinas = listaTiposOficinas;
		this.listaNumerosOficinas = listaNumerosOficinas;
	}
	
	public ContenedorTiposyNumerosOficinas(){
		
	}
	
}
