package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CheckItemDevPk;

/**
 * Application identity objectid-class.
 */
public class CheckItemDevEnhancedPk implements java.io.Serializable {

    public String idCheckItemDev;

    public CheckItemDevEnhancedPk() {
    }

    public CheckItemDevEnhancedPk(String s) {
        int i, p = 0;
        idCheckItemDev = s.substring(p);
    }
    
	public CheckItemDevPk getCheckItemDevID(){
		CheckItemDevPk id = new CheckItemDevPk();
		id.idCheckItemDev = idCheckItemDev;
		return id;
	}    
	
	public CheckItemDevEnhancedPk(CheckItemDevPk id){
		idCheckItemDev = id.idCheckItemDev;        	
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckItemDevEnhancedPk)) return false;

        final CheckItemDevEnhancedPk id = (CheckItemDevEnhancedPk) o;

        if (this.idCheckItemDev != null ? !idCheckItemDev.equals(id.idCheckItemDev) : id.idCheckItemDev != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCheckItemDev != null ? idCheckItemDev.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCheckItemDev);
        return buffer.toString();
    }
}