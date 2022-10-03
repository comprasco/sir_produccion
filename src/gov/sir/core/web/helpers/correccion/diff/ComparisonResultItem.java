package gov.sir.core.web.helpers.correccion.diff;

// initially immutable
public class ComparisonResultItem implements java.io.Serializable {

   private Object  t0Property;
   private Object  t1Property;
   private Integer result;

  // all properties must be serializabe
  public ComparisonResultItem( Object t0Property, Object t1Property, Integer result ) {

     this.t0Property = t0Property;
     this.t1Property = t1Property;
     this.result     = result;


  } // end-constructor

	public Integer getResult() {
		return result;
	}

	public Object getT0Property() {
		return t0Property;
	}

	public Object getT1Property() {
		return t1Property;
	}
}
