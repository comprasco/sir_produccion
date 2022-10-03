/*
 * Created on 28-may-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators;

/**
 * @author ahurtado
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TextDrawableComponent extends AbstractDrawableComponent {

  public static int STYLE_TEXTAREA = 1;
  public static int STYLE_OUTPUT   = 2;
  public static int STYLE_FIELD_NOTEDIT = 3;

  private int style;


	/* (non-Javadoc)
	 * @see pck2.DrawableComponent#generateXmlTextFragment(java.lang.StringBuffer)
	 */
  protected void generateXmlTextFragment_HtmImp(StringBuffer buffer) {
          StringBuffer result = new StringBuffer();

          result.append( "<input" );
          result.append( " id='" + this.getId() + "'" );
          result.append( " name='" + this.getId() + "'" );
          result.append( " type='text'" );
          result.append( " value='"+ this.getValue().toString() +"' " );
          
          
          // alt behavior -----------------------------------------
          StringBuffer jsEvent_OnClickText = new StringBuffer();
		  jsEvent_OnClickText.append( "onclick='" + "return overlib(\""+ this.getValue().toString() + "\"" + ", STICKY, MOUSEOFF );"   + "'" );

		  StringBuffer jsEvent_OnMouseOutText = new StringBuffer();
		  jsEvent_OnMouseOutText.append(  "onmouseout='" + "nd();" + "'" );
          
		  result.append( " " + jsEvent_OnClickText.toString() + " " );
		  result.append( " " + jsEvent_OnMouseOutText.toString() + " " );  
          
	      // -----------------------------------------

          DrawableComponentAttributes atts;
          if( null != ( atts = getAttributes() ) ) {
            atts.generateAttributeValuePairs( result );
          }

          result.append( "/>" );

          buffer.append( result );
  }
  protected void generateXmlTextFragment_CFormsImp(StringBuffer buffer) {
    StringBuffer result = new StringBuffer();

    result.append( "<fi:field" );
    result.append( " id='" + this.getId() + "'" );
    result.append( ">" );
    result.append( " <fi:value>"+ this.getValue().toString() +"</fi:value>" );
    if( STYLE_TEXTAREA == this.style ){
      result.append( "<fi:styling type='textarea'/>" );
    }
    if( STYLE_OUTPUT == this.style ){
      result.append( "<fi:styling type='output'/>" );
    }
    result.append( "</fi:field>" );

    buffer.append( result );
  }


}
