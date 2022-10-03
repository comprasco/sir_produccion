/*
 * Created on Jan 14, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.comun;

import java.util.Iterator;
import java.util.List;

import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.Vereda;

/**
 * @author mrios
 */

public class ValidacionDatosOficinaOrigen {
	
	public static String obtenerVereda(List deptos, String idDepto, String idMunicip) {
		String idVereda = "";
		
		Iterator itDeptos = deptos.iterator();
		boolean end = false;
				
		while ( itDeptos.hasNext() ) {
			if ( end ) {
				break;
			}
			
			Departamento depto = (Departamento) itDeptos.next();
			
			if ( idDepto != null ) {
				if ( idDepto.equals(depto.getIdDepartamento()) ) {
					List munics = depto.getMunicipios();
					Iterator itMunic = munics.iterator();
					
					while ( itMunic.hasNext() ) {
						if ( end ) {
							break;
						}
						
						Municipio munic = (Municipio) itMunic.next();
						
						if ( idMunicip != null ) {
							if ( idMunicip.equals(munic.getIdMunicipio()) ) {
								List veredas = munic.getVeredas();
								Iterator itVeredas = veredas.iterator();
							 	
								while ( itVeredas.hasNext() ) {
									Vereda vereda = (Vereda) itVeredas.next();
									
									if ( vereda.isCabecera() ) {
										idVereda = vereda.getIdVereda();
										end = true;
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		
		return idVereda;
	}
}