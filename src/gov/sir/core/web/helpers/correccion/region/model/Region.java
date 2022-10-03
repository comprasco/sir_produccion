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
public class Region implements java.lang.Comparable, java.io.Serializable {

    // inner immutable object
    public static class Binder extends gov.sir.core.web.helpers.correccion.region.model.basic.AbstractBinder {

        public Binder( String bindContextName, String bindId ) {
			super( bindContextName, bindId );
        }

    }

    protected String contextName;

    protected String id;
    protected ConditionalDisplay conditionalDisplay = new ConditionalDisplay();

    public Region( String id ) {
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


    public ConditionalDisplay getConditionalDisplay() {
        return this.conditionalDisplay;
    }

    public int compareTo( Object o ) throws java.lang.ClassCastException {
        if( null == o )
            throw new java.lang.ClassCastException( "objeto no debe ser null" );

        if( !( o instanceof Region ) )
            throw new java.lang.ClassCastException( "este objeto no es Region" );

        Region region = (Region)o;

        int idComparison = region.getId().compareTo( this.id );

        return idComparison;
    }


    public void generateXmlTextFragment_XmlDebugImp( StringBuffer buffer ) {
        StringBuffer impBuffer = new StringBuffer();

        impBuffer.append( "<region " );
        impBuffer.append( "  id='" + this.getId() + "'" );
        impBuffer.append( "  context-name='" + this.getContextName() + "'" );
        impBuffer.append( " >" );

        conditionalDisplay.generateXmlTextFragment_XmlDebugImp( impBuffer );
        impBuffer.append( "</region>" );

        StringBuffer htmDisplay = new StringBuffer();
        htmDisplay.append("<textarea>");
        impBuffer.append( "\n\r" );
        htmDisplay.append( impBuffer );
        impBuffer.append( "\n\r" );
        htmDisplay.append("</textarea>");

        buffer.append( htmDisplay );
    }

    public static class ConditionalDisplay extends gov.sir.core.web.helpers.correccion.region.model.basic.AbstractConditional {

        public static final int SHOW     = (int) Math.pow(2,0);
        public static final int HIDE     = (int) 0;

        public void evaluate( AspectModel aspectModel ){

            // si tiene los flags de lectura o escritura se debe mostrar

            if( ( ( aspectModel.getNivelPermiso() & AspectModel.READ  ) == AspectModel.READ )
              ||( ( aspectModel.getNivelPermiso() & AspectModel.WRITE ) == AspectModel.WRITE ) ) {
                displayResult = SHOW;
            }
            else{
                displayResult = HIDE;
            }
        }

        public void generateXmlTextFragment_XmlDebugImp( StringBuffer buffer ) {
            StringBuffer impBuffer = new StringBuffer();

            impBuffer.append( "<conditional-display>" );
            impBuffer.append( "\n\r" );

            impBuffer.append( " <property name='show' " );
            impBuffer.append( " value='" );
            impBuffer.append( ( ( displayResult & SHOW  ) == SHOW )?("true"):("false") );
            impBuffer.append( "' />" );

            impBuffer.append( "\n\r" );

            impBuffer.append( " <property name='hide' " );
            impBuffer.append( " value='" );
            impBuffer.append( ( ( displayResult & HIDE  ) == HIDE )?("true"):("false") );
            impBuffer.append( "' />" );

            impBuffer.append( "\n\r" );

            impBuffer.append( "</conditional-display>");

            buffer.append( impBuffer );
        }


    }


      public static class AspectModel extends gov.sir.core.web.helpers.correccion.region.model.basic.AbstractPolicyTraverseAspectModel {

			  //TODO: eliminar despues estas constantes

              public static final int NULL   = (int) 0;             // 0000
              public static final int READ   = (int) Math.pow(2,0); // 0001
              public static final int WRITE  = (int) Math.pow(2,1); // 0010
              public static final int APPEND = (int) Math.pow(2,2); // 0100
              public static final int DELETE = (int) Math.pow(2,3); // 1000

              public AspectModel( String contextName ) {
				  super( contextName );
              }

              public static
              void main( String[] args ) {

              }

  }


    public String getId() {
        return id;
    }


    public static
    void main( String[] args ) {
       Region rgn1 = new Region("test1");
       Region rgn2 = new Region("test1");


   }


}
