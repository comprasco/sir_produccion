package gov.sir.core.web.helpers.correccion.region.model.basic;

public abstract class AbstractBinder implements java.io.Serializable{
	
    protected String bindContextName;
    protected String bindId;

    public AbstractBinder( String bindContextName, String bindId ) {
        this.bindContextName = bindContextName;
        this.bindId = bindId;
    }

    public String getBindContextName() {
        return this.bindContextName;
    }
    public String getBindId() {
        return this.bindId;
    }
}
