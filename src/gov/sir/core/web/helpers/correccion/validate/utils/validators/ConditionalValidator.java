package gov.sir.core.web.helpers.correccion.validate.utils.validators;

import java.util.Iterator;

import gov.sir.core.web.helpers.correccion.validate.utils.conditions.ConditionComponent;

public abstract class ConditionalValidator {
	
	private java.util.List conditions = new java.util.ArrayList();
	
	public ConditionalValidator(){
		
	}
	
	public void addCondition( ConditionComponent condition ) {
		conditions.add( condition );
	}
	
	protected boolean evaluationActive = true;
	
	/**
	 * Observa el conjunto de condiciones sobre el validador 
	 * si alguna obtiene un resultado falso, se interrumpe la validacion
	 * */
	public void evaluateConditions() {
		Iterator iterator;
		
		for( iterator = conditions.iterator(); iterator.hasNext(); ) {
			ConditionComponent element = (ConditionComponent) iterator.next();
			if( null == element )
				continue;
			element.evaluate();
		}
		
		for( iterator = conditions.iterator(); iterator.hasNext(); ) {
			ConditionComponent element = (ConditionComponent) iterator.next();
			if( null == element )
				continue;
			if( ! element.getResults() ){
				evaluationActive = false;
				return;
			}
		}
		evaluationActive = true;
	}
	
	
	protected boolean result;
	
	public boolean getResult() {
		return this.result;
	}
	
	public static final String OBJECT_TO_VALIDATE = "OBJECT_TO_VALIDATE";
	
	protected java.util.Map parameters = new java.util.HashMap();
	
	public void addParameter( String parameterName, Object parameterValue ) {
		parameters.put( parameterName, parameterValue );
	}
	
	public void resetParameters() {
		parameters.clear();
	}
	
	public void resetConditions() {
		conditions.clear();
	}
	
	public void validate() {
		evaluateConditions();
		
		if( isEvaluationActive() ) {
			// validate
			this.result = execute( this.parameters );
		}
		else {
			// de otra forma el resultado de la validacion se deja pasar
			// TODO: buscar luego otra estategia para informar que los validadores 
			// estan ocultos
			this.result = true;
		}
	}
	
	protected abstract boolean execute( java.util.Map parameters );
	
	// usada para los validadores mas basicos, los que solamente necesitan de un parametro

	public boolean isEvaluationActive() {
		return evaluationActive;
	}
	
}
