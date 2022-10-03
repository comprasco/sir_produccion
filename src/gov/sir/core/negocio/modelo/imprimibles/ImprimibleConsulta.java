package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.ResultadoFolio;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.web.WebKeys;

import java.awt.print.PageFormat;
import java.util.Iterator;
import java.util.List;



/**
 * @author gvillal
 * Clase que representa el Imprimible de los resultados de una consulta.
 */
public class ImprimibleConsulta extends ImprimibleBase {

    private Turno turno;
    private static final long serialVersionUID = 1L;
    	/*
     * ATRIBUTOS
     */
    protected SolicitudConsulta solicitud;

	private String fechaImpresion;
	
	/**Determina si el tamaño del papel es carta.**/
	private boolean tamanoCarta = true;
	
	/**id del usuario que genera el imprimible*/
	private String idUsuario;
	
	/**Circulo del usuario que genera el imprimible*/
	private Circulo circulo;

    /*
     * METODOS
     */

    public Circulo getCirculo() {
		return circulo;
	}

	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
     * Constructor de la clase.
     * @param folio
     */
	public ImprimibleConsulta(Turno turno, String fechaImpresion,String tipoImprimible)
	{
		super(tipoImprimible);

		// added: BUG: 0002530 ---------------------------
		setImprimirMargen( false );
		// -----------------------------------------------

		setTransferObject(solicitud);
		this.solicitud = (SolicitudConsulta) turno.getSolicitud();
		this.turno = turno;
		this.fechaImpresion = fechaImpresion;
	}

    /**
     * Metodo que genera el imprimible.
     */
	public void generate(PageFormat pageFormat)
	{
		super.generate(pageFormat);
		List busquedas =  this.solicitud.getBusquedas();
		System.out.println("[gov.sir.core.negocio.modelo.imprimibles::ImprimibleConsulta]: Número de Busquedas="+busquedas.size());

                //Número de matrícula y direcciones
                this.imprimirLinea( ImprimibleConstantes.PLANO2, ImprimibleConstantes.SEPARADOR2 );
                this.imprimirLinea(ImprimibleConstantes.PLANO2, "");
                this.imprimirResultadosBusquedas(this.solicitud);
	}


    /**
     *  Imprime los parametros de búsqueda de la consulta.
     * @param solConsul
     */
    private  void imprimirParametrosDeBusqueda(SolicitudConsulta solConsul)
	{
		List busquedas =  solConsul.getBusquedas();

		int n=busquedas.size();

		if (n>0)
		{
			Busqueda busqueda;

                        this.imprimirLinea(ImprimibleConstantes.PLANO2, "");
			for(int j=1; j<=n; j++)
			{
				busqueda=(Busqueda)busquedas.get(j-1);
                                this.imprimirLinea(ImprimibleConstantes.PLANO2, " " + j + ".");

				String param =busqueda.getApellido1Ciudadano();
				param =  this.getString(param);

				if (!param.equalsIgnoreCase(""))
				  this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Primer Apellido: "+param);

				param =busqueda.getApellido2Ciudadano();
				param =  getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Segundo Apellido: "+param);

				param =busqueda.getDireccion();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
				  this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Dirección: "+param);

				param =busqueda.getMatricula();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Matrícula: "+param);

				param =busqueda.getNombreCiudadano();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Nombre: "+param);

				param =busqueda.getNombreNaturalezaJuridica();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Nombre-Naturaleza Juridica: "+param);

				param =busqueda.getNumeroCatastral();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Número Catastral: "+param);

				param =busqueda.getTipoDocCiudadano();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Tipo de Documento: "+param);


				param =busqueda.getNumeroDocCiudadano();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Número de Documento: "+param);

                                this.imprimirLinea(ImprimibleConstantes.PLANO2, "");
			}
		}
	}



    /**
     * Imprimime el encabezado del imprimible.
     * El encabezado es la parte que se repite en todas las páginas
     * del imprimible.
     */
	protected void makeNewPage() {
		super.makeNewPage();

		String titulo = "SOLICITUD CONSULTA";
		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, titulo);

		String fechaImp = this.fechaImpresion;
		this.imprimirLinea(ImprimibleConstantes.PLANO2, ImprimibleConstantes.MARGEN_IZQ * 4, fechaImp);

		this.imprimirLinea(ImprimibleConstantes.PLANO2, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO2, "");

		this.imprimirLinea(ImprimibleConstantes.TITULO2,
						   ImprimibleConstantes.MARGEN_IZQ * 4,
						   "No. RADICACIÓN: ", false);
		this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
						   ImprimibleConstantes.MARGEN_IZQ * 8,
						   turno.getIdWorkflow());

		//this.imprimirLinea(ImprimibleConstantes.PLANO2, ImprimibleConstantes.margenIzq,"Página: " + this.getNumberOfPages());

		this.imprimirLinea(ImprimibleConstantes.PLANO2, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO2, ImprimibleConstantes.MARGEN_IZQ * 4, "");


	}


	/**
	 * Imprimbe los resultados de la búsqueda de la consulta.
	 * @param solConsul
	 */
	private void imprimirResultadosBusquedas(SolicitudConsulta solConsul)
	{
		System.out.println("Imprimiendo los resultados de la busqueda.....");

		List solicitudesFolio=solConsul.getSolicitudFolios();


		if (solicitudesFolio == null || solicitudesFolio.size()== 0){
			if(solicitudesFolio !=null){
				System.out.println("Numero de resultados: "+solicitudesFolio.size());
			}
			List busquedas=solConsul.getBusquedas();
			for (int j=0; j<busquedas.size(); j++){
				Busqueda busqueda=(Busqueda)busquedas.get(j);
				int numBusq=j+1;
				this.imprimirLinea(ImprimibleConstantes.PLANO2,"");
				this.imprimirLinea(ImprimibleConstantes.TITULO2, "CRITERIO " + numBusq + ".");

				String param =busqueda.getApellido1Ciudadano();
				param =  this.getString(param);

				if (!param.equalsIgnoreCase(""))
				  this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Primer Apellido: "+param);

				param =busqueda.getApellido2Ciudadano();
				param =  getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Segundo Apellido: "+param);

				param =busqueda.getDireccion();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
				  this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Dirección: "+param);

				param =busqueda.getMatricula();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Matrícula: "+param);

				param =busqueda.getNombreCiudadano();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Nombre: "+param);

				param =busqueda.getNombreNaturalezaJuridica();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Nombre-Naturaleza Juridica: "+param);

				param =busqueda.getNumeroCatastral();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Número Catastral: "+param);

				param =busqueda.getTipoDocCiudadano();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Tipo de Documento: "+param);


				param =busqueda.getNumeroDocCiudadano();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Número de Documento: "+param);

				this.imprimirLinea(ImprimibleConstantes.PLANO2," ");
				this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Resultados:");
				this.imprimirLinea(ImprimibleConstantes.PLANO2,"		la consulta no arrojó ningún resultado");
			}
		}else{
			List busquedas=solConsul.getBusquedas();
			for (int j=0; j<busquedas.size(); j++){
				Busqueda busqueda=(Busqueda)busquedas.get(j);
				int numBusq=j+1;
				this.imprimirLinea(ImprimibleConstantes.PLANO2,"");
				this.imprimirLinea(ImprimibleConstantes.TITULO2, "CRITERIO " + numBusq + ".");

				String param =busqueda.getApellido1Ciudadano();
				param =  this.getString(param);

				if (!param.equalsIgnoreCase(""))
				  this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Primer Apellido: "+param);

				param =busqueda.getApellido2Ciudadano();
				param =  getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Segundo Apellido: "+param);

				param =busqueda.getDireccion();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
				  this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Dirección: "+param);

				param =busqueda.getMatricula();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Matrícula: "+param);

				param =busqueda.getNombreCiudadano();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Nombre: "+param);

				param =busqueda.getNombreNaturalezaJuridica();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Nombre-Naturaleza Juridica: "+param);

				param =busqueda.getNumeroCatastral();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Número Catastral: "+param);

				param =busqueda.getTipoDocCiudadano();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Tipo de Documento: "+param);


				param =busqueda.getNumeroDocCiudadano();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Número de Documento: "+param);

				param =busqueda.getNombreEje();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Eje: "+param);
				
				param =busqueda.getValorEje();
				param =  this.getString(param);
				if (!param.equalsIgnoreCase(""))
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Valor de Eje: "+param);
				
				this.imprimirLinea(ImprimibleConstantes.PLANO2," ");
				this.imprimirLinea(ImprimibleConstantes.PLANO2,"   Resultados:");
				int num=1;
				boolean busquedaSinResultados=true;
				for (int i=0; i<solicitudesFolio.size(); i++ ){

					SolicitudFolio solFol = (SolicitudFolio)solicitudesFolio.get(i);
					String idmatricula=solFol.getIdMatricula();
						if (busqueda.getIdBusqueda().equals(solFol.getIdBusquedaConsulta())){
							this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO1,"");
                            // BUG 5779
                            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,"    "+num+". MATRICULA: "+idmatricula);
                            if(turno.isNacional()){
	                            try{
	                            	this.imprimirLinea(ImprimibleConstantes.PLANO2,"       Ciudad del Folio: "+solFol.getFolio().getDocumento().getOficinaOrigen().getVereda().getMunicipio().getNombre());
	                            }catch(Exception e){
	                            	
	                            }
                            }                                                 
							Folio folio = solFol.getFolio();

							if (folio!=null)
							{
								List direcciones=folio.getDirecciones();
								if (direcciones!=null)
									this.imprimirDirecciones(folio,false);
							}
							num++;
							busquedaSinResultados=false;
						}
				}
				if(busquedaSinResultados==true){
					this.imprimirLinea(ImprimibleConstantes.PLANO2,"    la consulta no arrojó ningún resultado");
				}


			}

		}
		if(turno.isNacional()){
			if((idUsuario!=null || !idUsuario.equals(""))&&(circulo != null)){
				this.imprimirLinea(ImprimibleConstantes.PLANO, " " );
				this.imprimirLinea(ImprimibleConstantes.PLANO, "Usuario: " + idUsuario);
				this.imprimirLinea(ImprimibleConstantes.PLANO, "Circulo: " + circulo.getIdCirculo()+" - "+circulo.getNombre());
			}
		}



		System.out.println(".....Saliendo de <imprimirResultadosBusquedas>");

	}

	/**
     * Imprime las direcciones del folio.
     * @param folio
     * @param formulario
     */
    protected void imprimirDirecciones(Folio folio, boolean formulario) {
        if (formulario) {
            this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO2,
                "DIRECCION DEL INMUEBLE");
        }

        List direcciones = folio.getDirecciones();

        if (direcciones!=null && direcciones.size() > 0) {
            Direccion direccion;
			int j = 1;

			Iterator it = direcciones.iterator();
			while(it.hasNext()){
                direccion = (Direccion)it.next();
                if(direccion!=null){
					String linea = "   " + j + ") ";
					if(direccion.getEspecificacion()!=null){
						linea += ((direccion.getEspecificacion() != null) ? direccion.getEspecificacion() : "");
					}
					else{
					  linea += (((direccion.getEje() != null) && (direccion.getEje().getNombre() != null)
								 && !(direccion.getEje().getNombre().equals(WebKeys.SIN_SELECCIONAR))) ?
								direccion.getEje().getNombre() : "");
					  linea += " ";
					  linea += ((direccion.getValorEje() != null) ? direccion.getValorEje() : "");
					  linea += " ";
					  linea += (((direccion.getEje1() != null) && (direccion.getEje1().getNombre() != null)
								 && !(direccion.getEje1().getNombre().equals(WebKeys.SIN_SELECCIONAR))) ?
								direccion.getEje1().getNombre() : "");
					  linea += " ";
					  linea += ((direccion.getValorEje1() != null) ? direccion.getValorEje1() : "");
					}
					linea = linea.trim();
					if (!linea.equals("")){
						this.imprimirLinea(ImprimibleConstantes.PLANO2,"       "+ linea);
					}
					j++;
                }
            }
        }
    }

    /**
     * Imprimbe los resultados de una búsqueda.
     * @param busqueda
     */
	protected void imprimirResultadosBusqueda(Busqueda busqueda)
	{
		System.out.println(".....Entrando a <imprimirResultadosBusqueda>");
		List resultadosFolios =busqueda.getResultadosFolios();
		ResultadoFolio resultadoFolio;
		int n=resultadosFolios.size();
		for(int j=1; j<=n; j++)
		{
			resultadoFolio=(ResultadoFolio)resultadosFolios.get(j-1);

			this.imprimirLinea(ImprimibleConstantes.TITULO1,"Matrícula: "+resultadoFolio.getFolio().getIdMatricula());
			this.imprimirLinea(ImprimibleConstantes.TITULO2,"DIRECCION(ES): ");
			this.imprimirDirecciones(resultadoFolio.getFolio(),false);
			this.imprimirLinea(ImprimibleConstantes.TITULO2,"");
			//this.imprimirLinea(ImprimibleConstantes.titulo1,"Matrícula: "+resultadoFolio.getFolio().getDIdMatricula());

		}
		System.out.println(".....Saliendo de <imprimirResultadosBusqueda>");
	}

	public Turno getTurno() {
		return turno;
	}

	public boolean isTamanoCarta() {
		return tamanoCarta;
	}

	public void setTamanoCarta(boolean tamanoCarta) {
		this.tamanoCarta = tamanoCarta;
	}




}
