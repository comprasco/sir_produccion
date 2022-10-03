package gov.sir.core.web.helpers.correccion.diff;


import gov.sir.core.web.helpers.correccion.diff.utils.BasicJxPathUtils;
import gov.sir.core.web.helpers.correccion.diff.utils.jxpath.FolioAbstractFactory;
import gov.sir.core.web.helpers.correccion.region.model.BindManager;

import org.apache.commons.jxpath.JXPathContext;

// TODO: cambiar esta clase para que reciba el abstractfactory para construir los objetos
// como un parametro mas.
public class BasicJXPathForwardDiffProcessor implements BasicForwardDiffProcessorComponent {

	protected String[] pathsToTest;
	protected java.util.Comparator[] comparatorsToApply;

	// for backward compatibility, folioAbstractFactoryDependence dependence
	protected org.apache.commons.jxpath.AbstractFactory abstractFactory = new FolioAbstractFactory();

	protected int[] comparisonResults;

	public BasicJXPathForwardDiffProcessor( String[] pathsToTest, java.util.Comparator[] comparatorsToApply ) {
		this.pathsToTest = pathsToTest;
		this.comparatorsToApply = comparatorsToApply;
		this.comparisonResults = new int[this.pathsToTest.length];
	}

	public static void printStringArray( java.io.PrintStream out, String[] array ) {
		for (int i = 0; i < array.length; i++) {
			String string = array[i];
			out.println( string );
		}
	}

	public static void printComparisonResults( java.io.PrintStream out, String[] array, int[] comparisonResults ) {
		for (int i = 0; i < array.length; i++) {
			String string = array[i];
			int comparisonResult = comparisonResults[i];
			out.print( ( comparisonResult == 0 )?("no-variation "):("yes-variation"));
			out.print( ": " );
			out.println( string );
		}
	}


	public int countVariations() {
		int variations =0;
		for (int i = 0; i < comparisonResults.length; i++) {
			if( comparisonResults[i] != 0 )
				variations++;
		}
		return variations;
	}

	public boolean pathHasChanges( String path ) {
		// ArrayList list = new ArrayList( comparisonResults.length );
		int index = -1;
		for (int i = 0; i < comparisonResults.length; i++) {
			if( path.equalsIgnoreCase( pathsToTest[i] ) ) {
				index = i;
			}
		}
		if( index < 0 ) {
			return false;
		}
		return( comparisonResults[index] != 0 );

	}

	public boolean objectHasChanges() {
		return( countVariations() != 0 );
	}

	public static int compareProperty( JXPathContext sourceContext, JXPathContext targetContext, String path, java.util.Comparator comparatorToApply ) {
            return BasicJxPathUtils.compareProperty( sourceContext, targetContext, path, comparatorToApply );
	}

	public static void setProperty( JXPathContext context, String path, Object propertyValue ) {
            BasicJxPathUtils.setProperty( context, path, propertyValue );
	}

	public static Object getProperty( JXPathContext context, String path ) {
            return BasicJxPathUtils.getProperty( context, path );
	}

	private JXPathContext sourceContext = null;
	private JXPathContext targetContext = null;
	private JXPathContext deltaContext  = null;




	public void execute( Object o1, Object o2, Object delta ) {

		Object source = o1;
		Object target = o2;


                // adicionados lenient para no tener problema al realizar
                // consultas


		sourceContext = JXPathContext.newContext( source );
                sourceContext.setLenient( false );
		targetContext = JXPathContext.newContext( target );
                targetContext.setLenient( false );
		deltaContext  = JXPathContext.newContext( delta  );
                deltaContext .setLenient( true );


                // solo se fija el factory del objeto que va a tener cambios

		if( null != this.abstractFactory ) {
			deltaContext.setFactory( this.abstractFactory );
		}
                boolean activateProperty;

		int comparisonResult=0;
               
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                int[] nj=null;
                int[] of=null;
                String[] pathNj = null;
                String[] pathOf = null;
		for (int i = 0; i < pathsToTest.length; i++) {
			String path = pathsToTest[i];

                        activateProperty = evaluatePropertyActivation( path );

                        if( !activateProperty ) {
                            continue;
                        }
                           /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
                        if(path.contains("/idNaturalezaJuridica")||path.contains("/idOficinaOrigen")||path.contains("/version"))
                        {
                            java.util.Comparator comparatorToApply = comparatorsToApply[i];
                            comparisonResult = compareProperty( sourceContext, targetContext, path, comparatorToApply );
                            if(path.contains("oficinaOrigen/idOficinaOrigen"))
                            {
                                of = new int[2];
                                pathOf = new String[2];
                                pathOf[0] = path;
                                of[0] = comparisonResult;
                            }else if(path.contains("oficinaOrigen/version"))
                            {
                                pathOf[1] = path;
                                of[1] = comparisonResult;
                            }else if(path.contains("naturalezaJuridica/idNaturalezaJuridica"))
                            {
                                pathNj = new String[2];
                                pathNj[0] = path;
                                nj = new int[2];
                                nj[0] = comparisonResult;
                            }else if(path.contains("naturalezaJuridica/version"))
                            {
                                pathNj[1] = path;
                                nj[1] = comparisonResult;
                            }
                        }else{
                            java.util.Comparator comparatorToApply = comparatorsToApply[i];
                            comparisonResult = compareProperty( sourceContext, targetContext, path, comparatorToApply );
                            if( comparisonResult != 0 ){
                                    Object propertyValue = null;
                                    propertyValue = getProperty( targetContext, path );
                                    setProperty(deltaContext,path, propertyValue );
                            }
                        }
			comparisonResults[i] = comparisonResult;
		}

            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
                if(of!=null)
                {
                    if(of[0]==1 || of[1]==1)
                    {
                        Object propertyValue = null;
                        propertyValue = getProperty( targetContext, pathOf[0] );
                        setProperty(deltaContext,pathOf[0], propertyValue );
                        propertyValue = getProperty( targetContext, pathOf[1] );
                        setProperty(deltaContext,pathOf[1], propertyValue );
                    }
                }
                if(nj!=null)
                {
                    if(nj[0]==1 || nj[1]==1)
                    {
                        Object propertyValue = null;
                        propertyValue = getProperty( targetContext, pathNj[0] );
                        setProperty(deltaContext,pathNj[0], propertyValue );
                        propertyValue = getProperty( targetContext, pathNj[1] );
                        setProperty(deltaContext,pathNj[1], propertyValue );
                    }
                }

	}

        // por defecto siempre se hace la evaluacion
        public boolean evaluatePropertyActivation( String path ) {
            return true;
        }


	public int[] getComparisonResults() {
		return comparisonResults;
	}

        //

        public java.util.Map getComparisonResultsMap( BindManager local_BindManager ) {

           // Map< String, ComparisonResultItem >
           java.util.Map local_Result;

           local_Result = new java.util.HashMap();

           String                local_ResultItemKey;
           ComparisonResultItem  local_ResultItemValue;
           int comparisonResult;
           Object propertyValue0;
           Object propertyValue1;

           for( int i = 0; i < pathsToTest.length; i++ ) {
              local_ResultItemKey   = pathsToTest[i];
              comparisonResult      = comparisonResults[i];

              // TODO: if needed: values that changes
              propertyValue0 = null;
              propertyValue1 = null;

              if( comparisonResult != 0 ) {

                      propertyValue0 = getProperty( sourceContext, local_ResultItemKey );
                      propertyValue1 = getProperty( targetContext, local_ResultItemKey );

              }
              local_ResultItemValue = new ComparisonResultItem( propertyValue0, propertyValue1, new Integer( comparisonResult ) );

              // si el binder no es nulo, se busca
              // el identificador de campo; en otro caso se trabaja con el path
              if( null != local_BindManager ) {
                 BindManager.Binder local_Binder;
                 local_Binder = local_BindManager.getBinderByPath( local_ResultItemKey );

                 if( null == local_Binder ) {
                    continue;
                 } // if
                 local_ResultItemKey = local_Binder.getId();
              }

              local_Result.put( local_ResultItemKey, local_ResultItemValue );

           } // for

           return local_Result;

        } // end-method

	public JXPathContext getSourceContext() {
		return sourceContext;
	}

	public JXPathContext getTargetContext() {
		return targetContext;
	}

	public JXPathContext getDeltaContext() {
		return deltaContext;
	}

	public void setAbstractFactory(org.apache.commons.jxpath.AbstractFactory abstractFactory) {
		this.abstractFactory = abstractFactory;
	}

}
