package gov.sir.core.negocio.modelo.imprimibles.base;

import java.util.List;
import java.util.Vector;

import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.Folio;

/**
 * @author gvillal
 * Clase que ayuda al manejo de los datos nulos
 * del folio.
 */
public class ImprimibleHelper {

	/**
	 * Funcion que retorna el texto asociado al nombre.
	 * @param folio
	 * @param nombre palabla clave que indica una varaiable asociada al folio.
	 * @return
	 */
    private static final long serialVersionUID = 1L;
	public static Object getDatoFromFolio(Folio folio,String nombre)
	{
		Object dato = "";
		if (folio==null)
		  return dato;

		if (nombre.equalsIgnoreCase(IGlosarioImprimibles.matricula))
		{
			String matricula = "";
			try
			{
				matricula = folio.getIdMatricula();
				if (matricula==null)
				  matricula = "";

			}
			catch(Exception e)
			{
				e.printStackTrace();
				matricula = "";
			}
			dato = matricula;

		}
        else if (nombre.equalsIgnoreCase(IGlosarioImprimibles.departamento))
		{
			System.out.println("Va a buscar departaento");
			String depto = "";
			try
			{
				depto = folio.getZonaRegistral().getVereda().getMunicipio().getDepartamento().getNombre();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				depto="NO ENCONTRADO";

			}
			dato = depto;
		}
		else if (nombre.equalsIgnoreCase(IGlosarioImprimibles.municipio))
        {
			System.out.println("Va a buscar municipio");
			String municipio = "";
			try
			{
				municipio = folio.getZonaRegistral().getVereda().getMunicipio().getNombre();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				municipio="NO ENCONTRADO";
			}
			dato = municipio;
	    }
		else if (nombre.equalsIgnoreCase(IGlosarioImprimibles.vereda))
		{
			System.out.println("Va a buscar vereda");
			String vereda = "";
			try
			{
				vereda = folio.getZonaRegistral().getVereda().getNombre();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				vereda = "NO ENCONTRADO";
			}
			dato = vereda;

		}
		else if (nombre.equalsIgnoreCase(IGlosarioImprimibles.radicacion))
		{
			String radicacion = "";
			try
			{

				radicacion = folio.getRadicacion();
				if (radicacion==null)
				radicacion = "";

			}
			catch(Exception e)
			{
				e.printStackTrace();
				radicacion="";
			}
			dato = radicacion;
		}
		else if (nombre.equalsIgnoreCase(IGlosarioImprimibles.codCatastral))
		{
			String codCatastral = "";
			try
			{
				codCatastral =folio.getCodCatastral();
				if (codCatastral==null)
				  codCatastral = "";
			}
			catch(Exception e)
			{
				e.printStackTrace();
				codCatastral="";
			}
			dato = codCatastral;
		}
		else if (nombre.equalsIgnoreCase(IGlosarioImprimibles.circuloRegistral))
		{
			String circuloReg = "";
			try
			{
				circuloReg = folio.getZonaRegistral().getCirculo().getNombre();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				circuloReg="";
			}
			dato = circuloReg;
		}
		else if (nombre.equalsIgnoreCase(IGlosarioImprimibles.lindero))
		{
			String lindero  = "";
			try
			{
				lindero = folio.getLindero();
				if(lindero == null){
					lindero = "";
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				lindero="";
			}


			dato = lindero;
		}
		else if (nombre.equalsIgnoreCase(IGlosarioImprimibles.complementacion))
		{
			String complementacion ="";

			Vector cadenas = new Vector();
			try
			{
				complementacion = "";
				Complementacion complemen = folio.getComplementacion();

				if (complemen!=null)
				  complementacion = complemen.getComplementacion();
				if (complementacion==null)
				  complementacion="";


				//System.out.println("complentacion="+complementacion);

				if (complementacion==null)
				complementacion = "";

			}
			catch(Exception e)
			{
				e.printStackTrace();
				complementacion="";
			}
			dato = complementacion;//cadenas;
		}
		else if (nombre.equalsIgnoreCase(IGlosarioImprimibles.estado))
		{
			String estado = "";
			try
			{
                            if( null == folio.getEstado() ) {
                                estado = "";
                            }
                            else {
                               estado = folio.getEstado().getNombre();
                            }
			}
			catch(Exception e)
			{
				e.printStackTrace();
				estado="";
			}
			dato = estado;
		}
		else if ( nombre.equalsIgnoreCase(IGlosarioImprimibles.tipoPredio) )
	    {
			String tipoPredio = "";
			try
			{
				tipoPredio = folio.getTipoPredio().getNombre();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				tipoPredio="";
			}
			dato = tipoPredio;
	    }
		else if ( nombre.equalsIgnoreCase(IGlosarioImprimibles.direcciones) )
		{
			List direcciones = new Vector();
			try
			{
				direcciones = folio.getDirecciones();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				direcciones=new Vector();
			}
			dato = direcciones;
		}

		else if (nombre.equalsIgnoreCase(IGlosarioImprimibles.anotaciones))
		{
			List anotaciones = new Vector();
			try
			{
				anotaciones = folio.getAnotaciones();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				anotaciones=new Vector();
			}
			dato = anotaciones;
		}

		else if (nombre.equalsIgnoreCase(IGlosarioImprimibles.codCatastralAnterior))
		{
			String antCodCatastral = "";
			try
			{
				antCodCatastral =folio.getCodCatastralAnterior();
				if (antCodCatastral==null)
				antCodCatastral = "";
			}
			catch(Exception e)
			{
				e.printStackTrace();
				antCodCatastral="";
			}
			dato = antCodCatastral;

		}

		return dato;
	}

}
