package gov.sir.core.negocio.modelo;


import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.core.util.DateFormatUtil;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.auriga.core.modelo.TransferObject;

/**
 * Clase que genera una lista de diferencias que existen entre dos folios
 * @author dcantor, ppabon
 *
 */
public class DeltaFolio implements TransferObject {
	
	public static final String ANOTACIONID_REPLACESYMBOL = "#";
	private List diferencias;
	private Folio folio;
	private Folio definitivo;
	private Folio temporal;
        private static final long serialVersionUID = 1L;
	//CONSTANTES PARA MOSTRAR LAS DIFERENCIAS ENTRE LOS FOLIOS
	/**Constante para el folio*/
	private final String FOLIO = "FOLIO";
	/**Constante para la anotación*/
	private final String ANOTACION = "ANOTACIÓN";
	/**Constante para decir que se creó una nueva anotación*/
	private final String ANOTACION_CREACION = "SE CREÓ ANOTACIÓN DE ";
	/**Constante para decir el valor de la anotación*/
	private final String ANOTACION_VALOR = " POR UN VALOR DE ";
	/**Constante para la salvedad*/
	private final String SALVEDAD = "SALVEDAD";
	/**Constante para la dirección*/
	private final String DIRECCIÓN = "DIRECCIÓN";
	/**Constante para el ciudadano*/
	private final String CIUDADANO = "CIUDADANO";

	/** Metodo constructor
	 * @param definitivo
	 * @param temporal
	 */
	public DeltaFolio(Folio definitivo, Folio temporal) {
		this( definitivo, temporal, true );
	}
	
	public DeltaFolio(Folio definitivo, Folio temporal, boolean autoprocess ) {
		this(definitivo, temporal, autoprocess, false );
	} // end-contructor
	
	private boolean incluirSalvedadesEnabled;
	
	public DeltaFolio(Folio definitivo, Folio temporal, boolean procesarDelta, boolean incluirSalvedades) {
		this.definitivo = definitivo;
		this.temporal = temporal;
		this.diferencias = new ArrayList();
		this.folio = new Folio();
		this.incluirSalvedadesEnabled = incluirSalvedades;
		
		if( procesarDelta ) {
			
			long tiempoInicial =  System.currentTimeMillis();
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************");
			Log.getInstance().debug(DeltaFolio.class, "((DELTA FOLIO)  LLAMADO cargarCambiosPropuestos)(ANTES PROCESAR) > "+(definitivo!=null?definitivo.getIdMatricula():"MATRICULA NULA")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************\n");			
			
			procesar();
			
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************");
			Log.getInstance().debug(DeltaFolio.class, "((DELTA FOLIO)  LLAMADO cargarCambiosPropuestos)(DESPUES PROCESAR) > "+(definitivo!=null?definitivo.getIdMatricula():"MATRICULA NULA")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************\n");			
			
		} // if		
	}

	public void startProcess() {
		
		procesar();
		
	} // end-method: startProcess
	
	

	/** Retorna el identificador del folio
	 * @return
	 */
	public Folio getFolio() {
		return folio;
	}

	/** Retorna la lista diferencias
	 * @return
	 */
	public List getDiferencias() {
		return diferencias;
	}

	/**
	 * Método que permite comparar cómo estaba un folio antes y después para poder
	 * determinar las diferencias y así ver los cambios que se realizarón sobre el folio
         * **************************************************************************************
         * Modificado por JALCAZAR 20/10/2009 caso Mantis 0002852
         * Problemas para Consultar las Correciones realizadas en las anotaciones de los folios
	 */
	private void procesar() {
		try {
			
			long tiempoInicial =  System.currentTimeMillis();			
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************");
			Log.getInstance().debug(DeltaFolio.class, "((DELTA FOLIO - PROCESAR) 1 LLAMADO cargarCambiosPropuestos)(ANTES OBTENER DIFERENCIAS) > "+(definitivo!=null?definitivo.getIdMatricula():"MATRICULA NULA")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************\n");			
			
			obtenerDiferencias(definitivo, temporal, folio);
			
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************");
			Log.getInstance().debug(DeltaFolio.class, "((DELTA FOLIO - PROCESAR) 2 LLAMADO cargarCambiosPropuestos)(DESPUES OBTENER DIFERENCIAS) > "+(definitivo!=null?definitivo.getIdMatricula():"MATRICULA NULA")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************\n");			

			String id = "";
			String dir = "";
			String anot = "";
			List salvedadesAnotacion = null;
			List ciudadanosAnotacion = null;

			//SE RECORRE LA LISTA DE DIRECCIONES PARA COLOCAR LAS NUEVAS DIRECCIONES
			List direcciones = temporal.getDirecciones();
			Iterator it = direcciones.iterator();
			while (it.hasNext()) {
				Direccion direccion = (Direccion) it.next();
				id = FOLIO + " " + ((Folio) temporal).getIdMatricula() + " - " + DIRECCIÓN;
                                // ((direccion.getEje() != null && direccion.getEje().getNombre() != null && !(direccion.getEje().getNombre().equals(WebKeys.SIN_SELECCIONAR))) ? direccion.getEje().getNombre() : "") + "  " + ((direccion.getValorEje() != null) ? direccion.getValorEje() : "") + "   " + ((direccion.getEje1() != null && direccion.getEje1().getNombre() != null && !(direccion.getEje1().getNombre().equals(WebKeys.SIN_SELECCIONAR))) ? direccion.getEje1().getNombre() : "") + "&nbsp;&nbsp;" + ((direccion.getValorEje1() != null) ? direccion.getValorEje1() : "") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
				dir =  ((direccion.getEspecificacion() != null) ? direccion.getEspecificacion() : "");

                if( direccion.isToDelete() ) {
                  this.diferencias.add(new Diferencias( id, dir, "-" ));
                }
                else {
                  this.diferencias.add(new Diferencias(id, "-", dir));
                }

			}
			
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************");
			Log.getInstance().debug(DeltaFolio.class, "((DELTA FOLIO - PROCESAR) 3 LLAMADO cargarCambiosPropuestos)(DESPUES DIRECCIONES) > "+(definitivo!=null?definitivo.getIdMatricula():"MATRICULA NULA")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************\n");			

			// ----------------------------------------------------
			// Bug 03561: Incluir las salvedades [salvedades-folio] (modifica a bug 3561)
			//SE RECORRE LA LISTA DE SALVEDADES PARA COLOCAR LAS NUEVAS SALVEDADES

			List salvedades = temporal.getSalvedades();
			it = salvedades.iterator();
			
			if( isIncluirSalvedadesEnabled() ) {

				while (it.hasNext()) {
					SalvedadFolio salvedad = (SalvedadFolio) it.next();
					id = FOLIO + " " + ((Folio) temporal).getIdMatricula() + " - " + SALVEDAD;
					this.diferencias.add(new Diferencias(id, "-", salvedad.getDescripcion()));
				} // while
				
			} // if
			
			// ----------------------------------------------------
			
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************");
			Log.getInstance().debug(DeltaFolio.class, "((DELTA FOLIO - PROCESAR) 4 LLAMADO cargarCambiosPropuestos)(DESPUES SALVEDADES) > "+(definitivo!=null?definitivo.getIdMatricula():"MATRICULA NULA")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************\n");			
			

			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************");
			Log.getInstance().debug(DeltaFolio.class, "((DELTA FOLIO - PROCESAR) 4.1 LLAMADO cargarCambiosPropuestos)(ANOTACIONES:"+((temporal!=null&&temporal.getAnotaciones()!=null)?""+temporal.getAnotaciones().size():"TAMANO DE ANOTACIONES SIN DETERMINAR")+") > "+(definitivo!=null?definitivo.getIdMatricula():"MATRICULA NULA")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************\n");			
			
			
			//SE RECORRE LA LISTA DE ANOTACIONES PARA COLOCAR LAS NUEVAS ANOTACIONES Y VER LAS DIFERENCIAS DE LAS NUEVAS
			List anotaciones = temporal.getAnotaciones();
			it = anotaciones.iterator();

			Iterator itTemp = null;

			Anotacion anotacionDefinitiva 	= null; 			// t0_Anotacion
			Anotacion anotacion 			= null;    			// t1_Anotacion
			/*JALCAZAR 20/10/2009 caso Mantis 0002852 (Modificación Nº 1)
                         * El objeto que representa los cambios en cada Anotacion debe ser creado en el siguiente
                         * ciclo que recorre las anotaciones temporales (ver Modificación Nº 2).
                         */
                        Anotacion anotacionDelta; 	// t2_Anotacion
			
			String local_AnotacionId = null;
			while (it.hasNext()) {
				anotacion = (Anotacion) it.next();

                                /*JALCAZAR 20/10/2009 caso Mantis 0002852 (Modificación Nº 2)
                                * (ver Modificación Nº 1).
                                */
                                anotacionDelta = new Anotacion(); 	// t2_Anotacion
				// obtener la anotacion_t0 (definitivo) a partir de la anotacion_t2 (temporal) 

				anotacionDefinitiva = obtenerAnotacionOriginal(definitivo.getAnotaciones(), anotacion);
				
				
				local_AnotacionId
				  = filterExtract_AnotacionDelta_Orden( anotacionDefinitiva, anotacion, anotacionDelta );
				
				// si se encotrn el identificador, colocarlo
				// en la anotacion delta para que lo pueda reporta en 
				// las diferencias
				if( ( null != local_AnotacionId )
				  &&( !ANOTACIONID_REPLACESYMBOL.equals( local_AnotacionId ) ) ) {
					
					if( null != anotacionDelta ) {
						
						if( null == anotacionDelta.getOrden() ) {
							anotacionDelta.setOrden( local_AnotacionId );
						}
						
					} // if 
					
					
				} // if
				

				//SE DETERMINA SI ES UNA NUEVA ANOTACIÓN, SI ES ASÍ SE COLOCA COMO DIFERENCIA QUE SE CREO UNA NUEVA ANOTACIÓN
				if (anotacion.isTemporal()) {
					id = FOLIO + " " + ((Folio) temporal).getIdMatricula() + " - " + ( ANOTACION + " " + local_AnotacionId ) ;
					anot = ANOTACION_CREACION + (anotacion != null && anotacion.getNaturalezaJuridica() != null ? "(" + anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() + ") " + anotacion.getNaturalezaJuridica().getNombre() : "") + ANOTACION_VALOR + NumberFormat.getInstance().format(anotacion.getValor());
					this.diferencias.add(new Diferencias(id, "-", anot));
				}
                else {

					//SINO, SE COMPARA CON LA ANOTACIÓN QUE SE MODIFICÓ PARA COLOCAR LOS CAMBIOS QUE SE LE HICIERON A LA ANOTACIÓN
        			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************");
        			Log.getInstance().debug(DeltaFolio.class, "((DELTA FOLIO - PROCESAR) 4.2 LLAMADO cargarCambiosPropuestos)(DESPUES ANOTACION) > "+(definitivo!=null?definitivo.getIdMatricula():"MATRICULA NULA")+(anotacionDefinitiva!=null?anotacionDefinitiva.getIdAnotacion():"ANOTACION NULA")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
        			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************\n");			
                	
					obtenerDiferencias(anotacionDefinitiva, anotacion, anotacionDelta);

        			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************");
        			Log.getInstance().debug(DeltaFolio.class, "((DELTA FOLIO - PROCESAR) 4.2 LLAMADO cargarCambiosPropuestos)(DESPUES ANOTACION) > "+(definitivo!=null?definitivo.getIdMatricula():"MATRICULA NULA")+(anotacionDefinitiva!=null?anotacionDefinitiva.getIdAnotacion():"ANOTACION NULA")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
        			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************\n");			
					
					// // (anotacion.getOrden()!=null?anotacion.getOrden(): "#")
					// local_AnotacionId = filterExtract_AnotacionDelta_Orden( anotacionDefinitiva, anotacion, anotacionDelta );

					salvedadesAnotacion = anotacion.getSalvedades();
					itTemp = salvedadesAnotacion.iterator();
					
					// ----------------------------------------------------
					// Bug 03561: Incluir las salvedades [salvedades-anotacion] (modifica a bug 3561)

					if( isIncluirSalvedadesEnabled() ) {

						while (itTemp.hasNext()) {
							
							SalvedadAnotacion salvedad = (SalvedadAnotacion) itTemp.next();
							id = FOLIO + " " + anotacion.getIdMatricula() + " - " + ( ANOTACION + " " + local_AnotacionId ) + " - " + SALVEDAD;
							this.diferencias.add(new Diferencias(id, "-", salvedad.getDescripcion()));
							
						} // while
						
					} // if
                                        
					// ----------------------------------------------------
                                        /*JALCAZAR 20/10/2009 caso Mantis 0002852 (Modificación Nº 3)
                                        * Se invoca el metodo anotacionesCuidadanosActualizadas(Anotacion anotacion) el cual muestra al usuario
                                        * si los datos de algun cuidadano de una anotacion dada fueron actualizados.
                                        */
					anotacionesCuidadanosActualizadas(anotacionDefinitiva);
                                        ciudadanosAnotacion = anotacion.getAnotacionesCiudadanos();
					itTemp = ciudadanosAnotacion.iterator();
                                        String deltaCiudadanoTxt;
					while (itTemp.hasNext()) {
						AnotacionCiudadano anotCiud = (AnotacionCiudadano) itTemp.next();

                        id = FOLIO + " " + anotacion.getIdMatricula() + " - " + ( ANOTACION + " " + local_AnotacionId ) + " - " + CIUDADANO;

                        //
                        deltaCiudadanoTxt  = "";
                        deltaCiudadanoTxt += ( ( null != anotCiud.getCiudadano().getTipoDoc() )?(anotCiud.getCiudadano().getTipoDoc()):("") )
                                          +  " "
                                          +  ( ( null != anotCiud.getCiudadano().getDocumento() )? (anotCiud.getCiudadano().getDocumento()) :( "" ) )
                                          +  ": ";
                        deltaCiudadanoTxt += ( ( null != anotCiud.getCiudadano().getNombreCompletoCiudadano() )?( anotCiud.getCiudadano().getNombreCompletoCiudadano() ):("") );

                        if( anotCiud.isToDelete() ) {
                          this.diferencias.add(new Diferencias( id, deltaCiudadanoTxt, "-" ) );
                        }
                        /*JALCAZAR 20/10/2009 caso Mantis 0002852 (Modificación Nº 4)
                        * Se invoca el metodo diferenciasAnotacionesCiudadanos(String IdAnotTemp, String deltaCiudTemp, Anotacion anotacion )
                        * el cual verifica si el cuidadano fue agregado agregado a la anotacion en las Correciones.
                        */
                        else if (!diferenciasAnotacionesCiudadanos(anotCiud , anotacionDefinitiva )){
                          this.diferencias.add(new Diferencias( id, "-", deltaCiudadanoTxt ) );
                        }
                     } // end while

					} // end if

				} // end while


			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************");
			Log.getInstance().debug(DeltaFolio.class, "((DELTA FOLIO - PROCESAR) 5 LLAMADO cargarCambiosPropuestos)(DESPUES ANOTACIONES) > "+(definitivo!=null?definitivo.getIdMatricula():"MATRICULA NULA")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(DeltaFolio.class, "\n*******************************************************\n");
			
			folio = temporal;
			
			

		} catch (Exception e) {
			Log.getInstance().error(DeltaFolio.class, e);
		}
	}
	
	/**JALCAZAR 20/10/2009 caso Mantis 0002852 (Nuevo Metodo Nº 1)
         * Verifica si un cuidadano existe en la anotacion definitiva
         *
         * @param anotCiud Cuidadano de la anotacion temporal
         * @param anotacionDef Anotacion Definitiva
         * @return
         * true: si el cuidadano existe.
         * false: si el cuidadano no existe.
         */
        private boolean diferenciasAnotacionesCiudadanos(AnotacionCiudadano anotCiud, Anotacion anotacionDef ){
            
            List CiudaDefinitivos = anotacionDef.getAnotacionesCiudadanos();
            Iterator itTemp = CiudaDefinitivos.iterator();
	    while (itTemp.hasNext()) {
                 AnotacionCiudadano anotDef = (AnotacionCiudadano) itTemp.next();
	         if(anotCiud.getIdCiudadano().equals(anotDef.getIdCiudadano())  ){
                      return true;
                  }
            }
            return false;
        }

        /**JALCAZAR 20/10/2009 caso Mantis 0002852 (Nuevo Metodo Nº 2)
         * Agrega a la lista de modificaciones de cuidadanos en la anotacion
         * aquellos usuarios que su informacion fue actualizada
         *
         * @param anotacionDef Anotacion Definitiva
         */
        private void anotacionesCuidadanosActualizadas(Anotacion anotacionDef){
            StringBuffer deltaCiudadanoTxt;
            String id;
            String local_AnotacionId = filterAnotacion_ExtractIdentifier(anotacionDef);
            List CiudaDefinitivos = anotacionDef.getAnotacionesCiudadanos();
            Iterator itTemp = CiudaDefinitivos.iterator();
	    while (itTemp.hasNext()) {
	          AnotacionCiudadano anotCiud = (AnotacionCiudadano) itTemp.next();
                  id = FOLIO + " " + anotacionDef.getIdMatricula() + " - " + ( ANOTACION + " " + local_AnotacionId ) + " - " + CIUDADANO;
                  deltaCiudadanoTxt = new StringBuffer();
                  if (null != anotCiud.getCiudadano().getTipoDoc() )
                    deltaCiudadanoTxt.append(anotCiud.getCiudadano().getTipoDoc());
                  deltaCiudadanoTxt.append(" ");
                  if(null != anotCiud.getCiudadano().getDocumento())
                    deltaCiudadanoTxt.append( anotCiud.getCiudadano().getDocumento());
                  deltaCiudadanoTxt.append(": ");
                  if(null != anotCiud.getCiudadano().getNombreCompletoCiudadano())
                    deltaCiudadanoTxt.append(anotCiud.getCiudadano().getNombreCompletoCiudadano());
                  if(anotCiud.isToUpdate() ){
                     this.diferencias.add(new Diferencias( id, "MODIFICACION CIUDADANO", deltaCiudadanoTxt.toString() ) );
                  }
            }
        }
        // obtiene el identificador de una anotacion
	
	private String filterAnotacion_ExtractIdentifier( Anotacion t2_Anotacion ) {
		
		boolean founded;
		String local_Result;
		local_Result = null;
		
		founded = false;
		
		if( !(founded) && ( null != t2_Anotacion ) ) {
			
			if( ( null != t2_Anotacion.getOrden() )
			  &&( !( "".equals( t2_Anotacion.getOrden().trim() ) ) ) ) {
				
				founded = true;
				local_Result = t2_Anotacion.getOrden();
			}
			
			
		} // if
		
		return local_Result;
			
	}

	// transform: 	
	// Extraer el identificador de la anotacion 
	private String filterExtract_AnotacionDelta_Orden( Anotacion t0_Anotacion, Anotacion t1_Anotacion, Anotacion t2_Anotacion ) {
		return filterExtract_AnotacionDelta_Orden(t0_Anotacion, t1_Anotacion, t2_Anotacion, true );
		
	} // end-method: filterExtract_AnotacionDelta_Orden 
	
	
	// transform: 	
	// Extraer el identificador de la 
	private String filterExtract_AnotacionDelta_Orden( Anotacion t0_Anotacion, Anotacion t1_Anotacion, Anotacion t2_Anotacion, boolean useReplaceSymbol ) {
		
		// jx-Search: "./orden"
		
		boolean founded;
		String local_Result;
		local_Result = null;
		
		founded = false;
		
		if( !founded  ) {
			
			local_Result = filterAnotacion_ExtractIdentifier( t2_Anotacion );
			if( null != local_Result ) {
				founded = true;
			}
		} // if
		if( !founded  ) {
			
			local_Result = filterAnotacion_ExtractIdentifier( t1_Anotacion );
			if( null != local_Result ) {
				founded = true;
			}
		} // if
		if( !founded  ) {
			
			local_Result = filterAnotacion_ExtractIdentifier( t0_Anotacion );
			if( null != local_Result ) {
				founded = true;
			}
		} // if
		
		if( !founded  ) {
			
			if( useReplaceSymbol ) {
				return ANOTACIONID_REPLACESYMBOL;
			}
			else{
				return null;
			} // if
				

		} // if		
		
		return local_Result;
		
		
	} // end-method: filterDelta_ExtractIdentifier

	/**
	 * Determina cuál es el elemento anotación que esta en una lista
	 * y que es el equivalente de una anotación que se le pasa.
	 * Sirve para encontrar una anotación que esta en las tablas definitivas y que tiene otra anotación
	 * en las tablas temporales, la idea es encontrar cuál es la anotación definitiva para poderla comparar con la
	 * anotación temporal.
	 * @param anotaciones
	 * @param anotTemporal
	 * @return
	 * @throws Exception
	 */
	private Anotacion obtenerAnotacionOriginal(List anotaciones, Anotacion anotTemporal) throws Exception {
		Anotacion anotDefinitiva = null;
		Anotacion anotDef = null;
		try {
			Iterator it = anotaciones.iterator();
			while (it.hasNext()) {
				anotDef = (Anotacion) it.next();

				if (anotDef != null && anotTemporal != null && anotDef.getIdAnotacion().equals(anotTemporal.getIdAnotacion()) && anotDef.getIdMatricula().equals(anotTemporal.getIdMatricula())) {
					anotDefinitiva = anotDef;
					break;
				}

			}
		} catch (Exception e) {
		}
		return anotDefinitiva;
	}

	/**
	 * Método que permite determinar las diferecias que existen en el valor de los
	 * atributos de dos clases.
	 * @param actual
	 * @param propuesto
	 * @param delta
	 * @throws Exception
	 */
	private void obtenerDiferencias(Object actual, Object propuesto, Object delta) throws Exception {
		List setters = new ArrayList();
		// 1. Obtener metodos setters (set)
		Method[] methods = actual.getClass().getMethods();

		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().startsWith("set") || methods[i].getName().startsWith("add")) {
				setters.add(methods[i]);
			}
		}

		//2. Para cada método setter obtener el getter (get, is)
		for (Iterator i = setters.iterator(); i.hasNext();) {
			Method setter = (Method) i.next();
			//Log.getInstance().debug(DeltaFolio.class, "procesando-->" + setter.getName());
			Method getterActual = getGetterMethod(actual, setter);

			if (getterActual == null) {
				continue;
			}

			//3. Invocar getter en el objeto actual y en el objeto propuesto
			Method getterPropuesto = propuesto.getClass().getMethod(getterActual.getName(), getterActual.getParameterTypes());

			Object propiedadActual = getterActual.invoke(actual, null);
			if (propiedadActual instanceof List) {
				continue;
			}
			Object propiedadPropuesta = getterPropuesto.invoke(propuesto, null);
			//	4. Comparar
			if (propiedadPropuesta != null) {
				//if (!propiedadPropuesta.equals(propiedadActual)) {
				//	5. Si hay diferencia almacenar en delta
				doSet(propuesto, delta, setter.getName(), propiedadPropuesta, propiedadActual);
				// 6. almacenar la diferencia en strings

				//}
			}

		}
	}

	/**
	 * Método que devuelve el nombre de un método setter a partir de uno
	 * que comienza con set o add.
	 * @param object
	 * @param setter
	 * @return
	 */
	private static Method getGetterMethod(Object object, Method setter) {
		String sourceProperty = setter.getName().substring(3);
		String getterMethodName = "get" + sourceProperty;
		Method getter = null;

		try {
			getter = object.getClass().getMethod(getterMethodName, null);
		} catch (NoSuchMethodException nsme) {
			getterMethodName = "get" + sourceProperty + "s";

			try {
				getter = object.getClass().getMethod(getterMethodName, null);
			} catch (NoSuchMethodException nsme2) {
				getterMethodName = "get" + sourceProperty + "es";

				try {
					getter = object.getClass().getMethod(getterMethodName, null);
				} catch (NoSuchMethodException nsme3) {
					getterMethodName = "is" + sourceProperty;

					try {
						getter = object.getClass().getMethod(getterMethodName, null);
					} catch (NoSuchMethodException nsme4) {
						Log.getInstance().debug(DeltaFolio.class, "error obteniendo getter: clase=" + object.getClass() + " error=" + nsme4);
					}
				}
			}
		}

		return getter;
	}

	/**
	 * Método que permite llenar las diferencias que existen entre los atributos de dos clases.
	 * Sirve para decir por ejemplo que el código catastral de folio cambio o el estado del mismo etc.
	 * @param propuesto
	 * @param target
	 * @param setterName
	 * @param value
	 * @param actual
	 */
	private void doSet(Object propuesto, Object target, String setterName, Object value, Object actual) {

		Class targetClass = target.getClass();
		String targetClassName = targetClass.getName();
		Class[] types = { value.getClass()};
		Object[] params = { value };
		Method setterTarget = null;

		if (!value.equals(actual)) {
			String id = "";
			String atributo = "";
			if (setterName.length() > 3) {
				atributo = setterName.substring(3, setterName.length());
				atributo = atributo.toUpperCase();
			}
			if (propuesto instanceof Folio) {
				id = FOLIO + " " + ((Folio) propuesto).getIdMatricula() + " - " + atributo;
			} else if (propuesto instanceof Anotacion) {
				Anotacion anotTemp = (Anotacion) propuesto;
				
				// String orden = "";				
				// orden = (anotTemp.getOrden()!=null?anotTemp.getOrden(): ANOTACIONID_REPLACESYMBOL);
				
				String local_AnotacionId;
				local_AnotacionId = "";
				
				if( target instanceof Anotacion ) {
					local_AnotacionId
					  = filterExtract_AnotacionDelta_Orden( (Anotacion)target, anotTemp, (Anotacion) null );
				}
				
				
				id = FOLIO + " " + ((Anotacion) propuesto).getIdMatricula() + " - " + ( ANOTACION + " " + local_AnotacionId ) + " - " + atributo;
			}

			if (value instanceof String && value!=null) {
				this.diferencias.add(new Diferencias(id, (String) actual, (String) value));
			} else if (value instanceof EstadoFolio && value!=null) {
				this.diferencias.add(new Diferencias(id, actual!=null? ((EstadoFolio) actual).getNombre():"", value!=null? ((EstadoFolio) value).getNombre():""));
			} else if (value instanceof Complementacion && value!=null) {
				this.diferencias.add(new Diferencias(id, actual!=null? ((Complementacion) actual).getComplementacion():"", value!=null?((Complementacion) value).getComplementacion():""));
			} else if (value instanceof TipoPredio && value!=null) {
				this.diferencias.add(new Diferencias(id, actual!=null? ((TipoPredio) actual).getNombre():"", value!=null?((TipoPredio) value).getNombre():""));
			} else if (value instanceof NaturalezaJuridica && value!=null) {

				NaturalezaJuridica njActual = (NaturalezaJuridica)actual;
				NaturalezaJuridica njDespues = (NaturalezaJuridica)value;

				String antes="";
				String despues="";

				if(njActual!=null && njActual.getIdNaturalezaJuridica()!=null){
					antes = njActual.getIdNaturalezaJuridica();
				}
				if(njActual!=null && njActual.getNombre()!=null){
					antes = antes + " - " + njActual.getNombre();
				}

				if(njDespues!=null && njDespues.getIdNaturalezaJuridica()!=null){
					despues = njDespues.getIdNaturalezaJuridica();
				}
				if(njDespues!=null && njDespues.getNombre()!=null){
					despues = despues + " - " + njDespues.getNombre();
				}

				this.diferencias.add(new Diferencias(id, antes, despues));

			} else if (value instanceof TipoAnotacion && value!=null) {
				this.diferencias.add(new Diferencias(id, actual!=null?((TipoAnotacion) actual).getNombre():"", value!=null?((TipoAnotacion) value).getNombre():""));
			} else if (value instanceof EstadoAnotacion && value!=null) {
				this.diferencias.add(new Diferencias(id, actual!=null?((EstadoAnotacion) actual).getNombre():"", value!=null?((EstadoAnotacion) value).getNombre() :""));
			} else if (value instanceof Documento && value!=null) {

				Documento documentoAntes   = (Documento)actual;
				Documento documentoDespues = (Documento)value;

				String antes="";
				String despues="";

				if(documentoAntes!=null && documentoAntes.getTipoDocumento()!=null && documentoAntes.getTipoDocumento().getNombre()!=null){
					antes = documentoAntes.getTipoDocumento().getNombre();
				}
				if(documentoAntes!=null && documentoAntes.getNumero()!=null){
					antes = antes + " - " + documentoAntes.getNumero();
				}
				if(documentoAntes!=null && documentoAntes.getFecha()!=null){
					antes = antes + " - " + imprimirFecha(documentoAntes.getFecha());
				}

				if(documentoAntes!=null && documentoAntes.getOficinaOrigen()!=null){
					antes = antes + " - " + ((documentoAntes.getOficinaOrigen().getNombre()!=null)?(documentoAntes.getOficinaOrigen().getNombre()):"");
				}else if(documentoAntes!=null && documentoAntes.getOficinaInternacional()!=null){
					antes = antes + " - " + documentoAntes.getOficinaInternacional();
				}
				else if(documentoAntes!=null && documentoAntes.getComentario()!=null){
					antes = antes + " - " + documentoAntes.getComentario();
				}

				if(documentoDespues!=null && documentoDespues.getTipoDocumento()!=null && documentoDespues.getTipoDocumento().getNombre()!=null){
					despues = documentoDespues.getTipoDocumento().getNombre();
				}
				if(documentoDespues!=null&& documentoDespues.getNumero()!=null){
					despues = despues + " - " +  documentoDespues.getNumero();
				}
				if(documentoDespues!=null&& documentoDespues.getFecha()!=null){
					despues = despues + " - " +  imprimirFecha(documentoDespues.getFecha());
				}
				if(documentoDespues!=null && documentoDespues.getOficinaOrigen()!=null){
					despues = despues + " - " + ((documentoDespues.getOficinaOrigen().getNombre()!=null)?(documentoDespues.getOficinaOrigen().getNombre()):"");
				}else if(documentoDespues!=null && documentoDespues.getOficinaInternacional()!=null){
					despues = despues + " - " + documentoDespues.getOficinaInternacional();
				}
				else if(documentoDespues!=null && documentoDespues.getComentario()!=null){
					despues = despues + " - " + documentoDespues.getComentario();
				}

				this.diferencias.add(new Diferencias(id, antes, despues));

			} else if (value instanceof ZonaRegistral && value!=null) {

				ZonaRegistral zrActual = (ZonaRegistral)actual;
				ZonaRegistral zrDespues = (ZonaRegistral)value;

				String antes="";
				String despues="";
                                /**
                                 * Modificado por JALCAZAR 27/10/2009 caso Mantis 0002852
                                 * Se modifico la forma como se muestran al usuario los datos del folio para incluir el nombre de la vereda
                                 * y ademas se elimino un bug en el datos temporal del folio que incluia información
                                 * del folio definitivo en la información correspondiente a la temporal.
                                 */

				if(zrActual!=null && zrActual.getCirculo()!=null && zrActual.getCirculo().getNombre()!=null){
					antes = zrActual.getCirculo().getNombre();
				}
				if(zrActual!=null && zrActual.getVereda()!=null && zrActual.getVereda().getMunicipio()!=null && zrActual.getVereda().getMunicipio().getDepartamento()!=null && zrActual.getVereda().getMunicipio().getDepartamento().getNombre()!=null){
					antes = antes + " - " + zrActual.getVereda().getMunicipio().getDepartamento().getNombre();
				}
				if(zrActual!=null && zrActual.getVereda()!=null && zrActual.getVereda().getMunicipio()!=null && zrActual.getVereda().getMunicipio().getNombre()!=null ){
					antes = antes + " - " + zrActual.getVereda().getMunicipio().getNombre();
				}
                                if(zrActual!=null && zrActual.getVereda()!=null && zrActual.getVereda().getNombre()!= null){
					antes = antes + " - " + zrActual.getVereda().getNombre();
				}

				if(zrDespues!=null && zrDespues.getCirculo()!=null && zrDespues.getCirculo().getNombre()!=null){
					despues = zrDespues.getCirculo().getNombre();
				}
				if(zrDespues!=null && zrDespues.getVereda()!=null && zrDespues.getVereda().getMunicipio()!=null && zrDespues.getVereda().getMunicipio().getDepartamento()!=null && zrDespues.getVereda().getMunicipio().getDepartamento().getNombre()!=null){
					despues = despues + " - " + zrDespues.getVereda().getMunicipio().getDepartamento().getNombre();
				}
				if(zrDespues!=null && zrDespues.getVereda()!=null && zrDespues.getVereda().getMunicipio()!=null && zrDespues.getVereda().getMunicipio().getNombre()!=null ){
					despues = despues + " - " + zrDespues.getVereda().getMunicipio().getNombre();
				}
                                if(zrDespues!=null && zrDespues.getVereda()!=null && zrDespues.getVereda().getNombre()!= null){
					despues = despues + " - " + zrDespues.getVereda().getNombre();
				}
                                /************************************************************************************************/
				this.diferencias.add(new Diferencias(id, antes, despues));

			} else if (value instanceof Double && value!=null) {
				this.diferencias.add(new Diferencias(id, actual!=null? NumberFormat.getInstance().format(((Double) actual).doubleValue()):"", value!=null? NumberFormat.getInstance().format(((Double) value).doubleValue()):""));
			} else if (value instanceof Boolean && value!=null) {
				this.diferencias.add(new Diferencias(id, actual!=null? ((Boolean) actual).toString():"", value!=null? ((Boolean) value).toString():""));
			} else if (value instanceof Long && value!=null) {
                            // Bug 3544
                            // filter: lastIdSalvedad

                            if( "lastIdSalvedad".equalsIgnoreCase( atributo ) ) {

                            }
                            else {
                               this.diferencias.add(new Diferencias(id, actual!=null? NumberFormat.getInstance().format(((Long) actual).doubleValue()):"", value!=null? NumberFormat.getInstance().format(((Long) value).doubleValue()):""));
                            }


			} else if( ( value instanceof Date )
                                 &&( null != atributo )
                                 &&( "fechaApertura".equalsIgnoreCase( atributo ) )  ) {

                         // bug: 3580
                         // cambio en fechaApertura

                             Diferencias local_Diferencia;

                             Date local_Object_T0;
                             Date local_Object_T1;

                             local_Object_T0 = (Date)actual;
                             local_Object_T1 = (Date)value;

                             String local_Object_T0_AsString = "";
                             String local_Object_T1_AsString = "";

                             // convertors (2String)
                             if( null != local_Object_T0 ) {
                                local_Object_T0_AsString = imprimirFecha( local_Object_T0 );
                             }
                             if( null != local_Object_T1 ) {
                                local_Object_T1_AsString = imprimirFecha( local_Object_T1 );
                             }

                             local_Diferencia = new Diferencias( id, local_Object_T0_AsString, local_Object_T1_AsString );
                             this.diferencias.add( local_Diferencia );
                        }
		}
	}

	/**
	 *
	 * @param actual
	 * @return
	 */
	private String imprimirFecha(Date actual) {
		String rta = "";
		try{
			rta = DateFormatUtil.format(actual);
			if(rta == null){
				rta = "";
			}
		}
		catch(RuntimeException e){
			rta = "";
		}
		return rta;

	}

	/**
	 * Devuelve el tipo primitivo de dato de una clase determinada.
	 * @param c
	 * @return
	 */
	private static Class getPrimitiveType(Class c) {
		String className = c.getName();

		if (className.equals("java.lang.Boolean")) {
			return Boolean.TYPE;
		} else if (className.equals("java.lang.Character")) {
			return Character.TYPE;
		} else if (className.equals("java.lang.Byte")) {
			return Byte.TYPE;
		} else if (className.equals("java.lang.Short")) {
			return Short.TYPE;
		} else if (className.equals("java.lang.Integer")) {
			return Integer.TYPE;
		} else if (className.equals("java.lang.Long")) {
			return Long.TYPE;
		} else if (className.equals("java.lang.Float")) {
			return Float.TYPE;
		} else if (className.equals("java.lang.Double")) {
			return Double.TYPE;
		} else {
			return null;
		}
	}

	public Folio getDefinitivo() {
		return definitivo;
	}

	public Folio getTemporal() {
		return temporal;
	}

	private boolean isIncluirSalvedadesEnabled() {
		return incluirSalvedadesEnabled;
	}
}
