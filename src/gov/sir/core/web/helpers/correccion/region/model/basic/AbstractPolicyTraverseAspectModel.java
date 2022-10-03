package gov.sir.core.web.helpers.correccion.region.model.basic;

public abstract class AbstractPolicyTraverseAspectModel implements java.io.Serializable{

    public String contextName;
    public int nivelPermiso = 0;


    public AbstractPolicyTraverseAspectModel( String contextName ) {
            this.contextName = contextName;
    }

    public int getNivelPermiso() {
            return nivelPermiso;
    }

    public void setNivelPermiso(int nivelPermiso) {
            this.nivelPermiso = nivelPermiso;
    }

}
