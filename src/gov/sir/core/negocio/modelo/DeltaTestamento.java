package gov.sir.core.negocio.modelo;


import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.core.util.DateFormatUtil;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.auriga.core.modelo.TransferObject;

/**
 * Clase que genera una lista de diferencias que existen entre dos testamentos
 * @author dcantor, ppabon
 *
 */
public class DeltaTestamento implements TransferObject {
	private static final long serialVersionUID = 1L;
	public static final String ANOTACIONID_REPLACESYMBOL = "#";
	private List diferencias;
	private Testamento testamento;
	private Testamento definitivo;
	private Testamento temporal;
        private String turno;
        private Documento docTestamento;
        private Documento docTemporal;
        private Documento docDefinitivo;

	//CONSTANTES PARA MOSTRAR LAS DIFERENCIAS ENTRE LOS TESTAMENTOS
	/**Constante para el testamento*/
	private final String TESTAMENTO = "TESTAMENTO";
	/**Constante para la salvedad*/
	private final String SALVEDAD = "SALVEDAD";
	/**Constante para el ciudadano*/
	private final String TESTADOR = "TESTADOR";
        
        private final String DOCUMENTO = "DOCUMENTO";

	/** Metodo constructor
	 * @param definitivo
	 * @param temporal
	 */
	public DeltaTestamento(Testamento definitivo, Testamento temporal) {
		this( definitivo, temporal, true );
	}
	
	public DeltaTestamento(Testamento definitivo, Testamento temporal, boolean autoprocess ) {
		this(definitivo, temporal, autoprocess, false );
	} // end-contructor
	
	private boolean incluirSalvedadesEnabled;
	
	public DeltaTestamento(Testamento definitivo, Testamento temporal, boolean procesarDelta, boolean incluirSalvedades) {
		this.definitivo = definitivo;
		this.temporal = temporal;
		this.diferencias = new ArrayList();
		this.testamento = new Testamento();
                this.docTestamento = new Documento();
		this.incluirSalvedadesEnabled = incluirSalvedades;
		
		if( procesarDelta ) {
			
			long tiempoInicial =  System.currentTimeMillis();
			Log.getInstance().debug(DeltaTestamento.class, "\n*******************************************************");
			Log.getInstance().debug(DeltaTestamento.class, "((DELTA TESTAMENTO)  LLAMADO cargarCambiosPropuestos)(ANTES PROCESAR) > "+(definitivo!=null?definitivo.getIdTestamento():"TESTAMENTO NULO")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(DeltaTestamento.class, "\n*******************************************************\n");			
			
			procesar();
			
			Log.getInstance().debug(DeltaTestamento.class, "\n*******************************************************");
			Log.getInstance().debug(DeltaTestamento.class, "((DELTA TESTAMENTO)  LLAMADO cargarCambiosPropuestos)(DESPUES PROCESAR) > "+(definitivo!=null?definitivo.getIdTestamento():"TESTAMENTO NULO")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(DeltaTestamento.class, "\n*******************************************************\n");			
			
		} // if		
	}

	public void startProcess() {
		
		procesar();
		
	} // end-method: startProcess
	
	

	/** Retorna el identificador del testamento
	 * @return
	 */
	public Testamento getTestamento() {
		return testamento;
	}

	/** Retorna la lista diferencias
	 * @return
	 */
	public List getDiferencias() {
		return diferencias;
	}

	/**
	 * Método que permite comparar cómo estaba un testamento antes y después para poder
	 * determinar las diferencias y así ver los cambios que se realizarón sobre el testamento
         * **************************************************************************************
         * Modificado por JALCAZAR 20/10/2009 caso Mantis 0002852
         * Problemas para Consultar las Correciones realizadas en las anotaciones de los testamentos
	 */
	private void procesar() {
		try {
			
			long tiempoInicial =  System.currentTimeMillis();			
			Log.getInstance().debug(DeltaTestamento.class, "\n*******************************************************");
			Log.getInstance().debug(DeltaTestamento.class, "((DELTA TESTAMENTO - PROCESAR) 1 LLAMADO cargarCambiosPropuestos)(ANTES OBTENER DIFERENCIAS) > "+(definitivo!=null?definitivo.getIdTestamento():"TESTAMENTO NULO")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(DeltaTestamento.class, "\n*******************************************************\n");			
			
			obtenerDiferencias(docDefinitivo,docTemporal,docTestamento);
                        
                        obtenerDiferencias(definitivo, temporal, testamento);
                                               
			
			Log.getInstance().debug(DeltaTestamento.class, "\n*******************************************************");
			Log.getInstance().debug(DeltaTestamento.class, "((DELTA TESTAMENTO - PROCESAR) 2 LLAMADO cargarCambiosPropuestos)(DESPUES OBTENER DIFERENCIAS) > "+(definitivo!=null?definitivo.getIdTestamento():"TESTAMENTO NULO")+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
			Log.getInstance().debug(DeltaTestamento.class, "\n*******************************************************\n");			

			String id = "";
			String dir = "";
			

			//SE RECORRE LA LISTA DE DIRECCIONES PARA COLOCAR LAS NUEVAS DIRECCIONES
			List testadores = temporal.getTestadores();
			Iterator it = testadores.iterator();
			while (it.hasNext()) {
				Ciudadano testador = (Ciudadano) it.next();
				id = TESTAMENTO + " " + turno + " - " + TESTADOR;
                                // ((direccion.getEje() != null && direccion.getEje().getNombre() != null && !(direccion.getEje().getNombre().equals(WebKeys.SIN_SELECCIONAR))) ? direccion.getEje().getNombre() : "") + "  " + ((direccion.getValorEje() != null) ? direccion.getValorEje() : "") + "   " + ((direccion.getEje1() != null && direccion.getEje1().getNombre() != null && !(direccion.getEje1().getNombre().equals(WebKeys.SIN_SELECCIONAR))) ? direccion.getEje1().getNombre() : "") + "&nbsp;&nbsp;" + ((direccion.getValorEje1() != null) ? direccion.getValorEje1() : "") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
				dir =  ((testador.getNombreCompletoCiudadano() != null) ? testador.getNombreCompletoCiudadano() : "");

                if(testador.getTelefono()!=null && testador.getTelefono().equals("*E")) {
                  this.diferencias.add(new Diferencias( id, dir, "-" ));
                }else if(!isTestadorIn(testador,definitivo.getTestadores())){
                  this.diferencias.add(new Diferencias(id, "-", dir));
                }

	}	
			testamento = temporal;
			
			

		} catch (Exception e) {
			Log.getInstance().error(DeltaTestamento.class, e);
		}
	}
	public boolean isTestadorIn(Ciudadano ciud,List ciudadanos)
        {
            Iterator it = ciudadanos.iterator();
            boolean esta = false;
            while(it.hasNext() && !esta)
            {
                Ciudadano ciudDef = (Ciudadano)it.next();
                if(ciudDef.getIdCiudadano().equals(ciud.getIdCiudadano()))
                {
                    esta = true;
                }
            }
            return esta;
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
						Log.getInstance().debug(DeltaTestamento.class, "error obteniendo getter: clase=" + object.getClass() + " error=" + nsme4);
					}
				}
			}
		}

		return getter;
	}

	/**
	 * Método que permite llenar las diferencias que existen entre los atributos de dos clases.
	 * Sirve para decir por ejemplo que el código catastral de testamento cambio o el estado del mismo etc.
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
			if (propuesto instanceof Testamento) {
				id = TESTAMENTO + " " + turno + " - " + atributo;
			}else if(propuesto instanceof Documento)
                        {
                                id = DOCUMENTO + " " + ((Documento)propuesto).getIdDocumento() + " - " + atributo;
                        }

			if (value instanceof String && value!=null) {
				this.diferencias.add(new Diferencias(id, (String) actual, (String) value));
			}else if(value instanceof OficinaOrigen && value != null)
                        {
                            this.diferencias.add(new Diferencias(id, ((OficinaOrigen) actual).getNombre(), ((OficinaOrigen) value).getNombre()));
                        }else if(value instanceof TipoDocumento && value != null)
                        {
                            this.diferencias.add(new Diferencias(id, ((TipoDocumento) actual).getNombre(), ((TipoDocumento) value).getNombre()));
                        }else if(value instanceof Date && value!=null)
                        {
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy");
                            this.diferencias.add(new Diferencias(id, df.format((Date) actual), df.format((Date) value)));
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

	public Testamento getDefinitivo() {
		return definitivo;
	}

	public Testamento getTemporal() {
		return temporal;
	}

	private boolean isIncluirSalvedadesEnabled() {
		return incluirSalvedadesEnabled;
	}

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Documento getDocDefinitivo() {
        return docDefinitivo;
    }

    public void setDocDefinitivo(Documento docDefinitivo) {
        this.docDefinitivo = docDefinitivo;
    }

    public Documento getDocTemporal() {
        return docTemporal;
    }

    public void setDocTemporal(Documento docTemporal) {
        this.docTemporal = docTemporal;
    }

    public Documento getDocTestamento() {
        return docTestamento;
    }

    public void setDocTestamento(Documento docTestamento) {
        this.docTestamento = docTestamento;
    }
        
}
