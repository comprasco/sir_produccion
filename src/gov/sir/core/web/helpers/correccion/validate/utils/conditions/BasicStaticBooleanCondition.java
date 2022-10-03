package gov.sir.core.web.helpers.correccion.validate.utils.conditions;

public class BasicStaticBooleanCondition implements ConditionComponent {

	boolean condition;

	public BasicStaticBooleanCondition( boolean condition) {
		this.condition = condition;
	}

	public void evaluate() {
	}

	public boolean getResults() {
		return this.condition;
	}

}
