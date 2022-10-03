package gov.sir.core.web.helpers.correccion.diff;

/**
 * en cada una de las evaliaciones que se hagan,
 * se pregunta si tiene el permiso para midificar el atributo o no;
 * si no tiene permiso,no aplica la comparqacion de diferencias hacia adelante.
 */
public class
BasicJxPathBooleanAuthForwardDiffProcessor extends BasicJXPathForwardDiffProcessor {

    private boolean[] applyActiveConditions;
    java.util.Map applyActiveConditions_Map;


    public BasicJxPathBooleanAuthForwardDiffProcessor( String[] pathsToTest, java.util.Comparator[] comparatorsToApply, boolean[] applyActiveConditions ) {
        super( pathsToTest, comparatorsToApply );
        this.applyActiveConditions = applyActiveConditions;
        compileApplyActiveConditions( this.applyActiveConditions, this.pathsToTest );
    }

    public void compileApplyActiveConditions( boolean[] conditions, String[] paths ){
        applyActiveConditions_Map = new java.util.HashMap(); // conditions.length );

        for( int i = 0; i < paths.length; i++ ) {
            applyActiveConditions_Map.put( pathsToTest[i], new Boolean( conditions[i] )  );
        }
    }

    // por defecto siempre se hace la evaluacion
    public boolean evaluatePropertyActivation( String path ) {
        return pathHasConditionActive( path );
    }

    public boolean pathHasConditionActive( String path ) {
        // unbound the property as a bool
        Boolean boolWrapper;
        boolWrapper = (Boolean)applyActiveConditions_Map.get( path );
        if( null == boolWrapper )  {
            return false;
        }

        return boolWrapper.booleanValue();

    }


}
