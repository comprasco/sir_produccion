/*
 * Created on 29/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators;

/**
 * @author andres
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BooleanDrawableComponent extends AbstractDrawableComponent {

	/* (non-Javadoc)
	 * @see pck2.DrawableComponent#generateXmlTextFragment(java.lang.StringBuffer)
	 */
    protected void generateXmlTextFragment_HtmImp(StringBuffer buffer) {
            StringBuffer result = new StringBuffer();

            result.append( "<input" );
            result.append( " id='" + this.getId() + "'" );
            result.append( " name='" + this.getId() + "'" );
            result.append( " type='checkbox'" );
            result.append( " value='"+ this.getValue().toString() +"' " );

            DrawableComponentAttributes atts;
            if( null != ( atts = getAttributes() ) ) {
              atts.generateAttributeValuePairs( result );
            }

            result.append( "/>" );

            buffer.append( result );
    }

	protected void generateXmlTextFragment_CFormsImp(StringBuffer buffer) {
      StringBuffer result = new StringBuffer();

      result.append( "<fi:booleanfield" );
      result.append( " id='" + this.getId() + "'" );
      result.append( ">" );
      result.append( " <fi:value>"+ this.getValue().toString() +"</fi:value>" );
      result.append( "</fi:booleanfield>" );

      buffer.append( result );
    }


    public void setValue(Object object) {
        if (object == null) {
            object = Boolean.FALSE;
        }

        if (!(object instanceof Boolean)) {
            throw new RuntimeException( "Debe darse como parametro un booleano");
        }
        //Object oldValue = this.getValue();

        super.setValue( object );
    }


}
