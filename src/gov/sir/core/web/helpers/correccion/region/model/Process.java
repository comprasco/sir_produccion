package gov.sir.core.web.helpers.correccion.region.model;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Process implements java.lang.Comparable {

    // inner immutable object
    public static class Binder extends gov.sir.core.web.helpers.correccion.region.model.basic.AbstractBinder {

		public Binder( String bindContextName, String bindId ) {
			super( bindContextName, bindId );
        }

    }

    protected String contextName;

    protected String id;
    protected ConditionalProcess conditionalProcess = new ConditionalProcess();

    public Process( String id ) {
        this.id = id;
    }

    public boolean equals( Object objectInstance ) {
        return compareTo( objectInstance ) == 0;

    }

    public String getContextName() {
        return contextName;
    }
    public void setContextName( String contextName ) {
        this.contextName = contextName;
    }


    public ConditionalProcess getConditionalProcess() {
        return this.conditionalProcess;
    }

    public int compareTo( Object o ) throws java.lang.ClassCastException {
        if( null == o )
            throw new java.lang.ClassCastException( "objeto no debe ser null" );

        if( !( o instanceof Process ) )
            throw new java.lang.ClassCastException( "este objeto no es Process" );

        Process region = (Process)o;

        int idComparison = region.getId().compareTo( this.id );

        return idComparison;
    }

    public static class ConditionalProcess extends gov.sir.core.web.helpers.correccion.region.model.basic.AbstractConditional  {

        public static final int SHOW     = (int) Math.pow(2,0);
        public static final int HIDE     = (int) 0;

        public void evaluate( AspectModel aspectModel ){

            // si tiene los flags de lectura o escritura se debe mostrar

            if( ( aspectModel.getNivelPermiso() & AspectModel.UNLOCK  ) == AspectModel.UNLOCK ) {
                displayResult = SHOW;
            }
            else{
                displayResult = HIDE;
            }
        }

    }


      public static class AspectModel extends gov.sir.core.web.helpers.correccion.region.model.basic.AbstractPolicyTraverseAspectModel {

              public static final int LOCK     = (int) 0;             // 0000
              public static final int UNLOCK   = (int) Math.pow(2,0); // 0001

              public AspectModel( String contextName ) {
				  super( contextName );
              }

  }

    public String getId() {
        return id;
    }


    public static
    void main( String[] args ) {

       Process rgn1 = new Process("test1");
       Process rgn2 = new Process("test1");



   }


}
