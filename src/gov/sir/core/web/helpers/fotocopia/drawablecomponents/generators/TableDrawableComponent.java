/*
 * Created on 28-may-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators;

import java.util.List;


/**
 * @author ahurtado
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * // SimpleComposite
 */
public class TableDrawableComponent extends AbstractDrawableComponent {

	List values ; // List of String[] // ColumnValues
	DrawableComponent[] renderers;
        String[] labels;

	int numCols;
	int numRows;

	public TableDrawableComponent() {

	}

        public void setRenderMode( int renderMode ) {
          super.setRenderMode( renderMode );
          if( renderers == null )
            return;
          for(int i=0; i < renderers.length; i++ ) {
            renderers[i].setRenderMode( renderMode );
          }
        }


        // TODO: move to jUnit test

	public static
	void main( String[] args  ) {
		TableDrawableComponent table = new TableDrawableComponent();

		Object[] field1_Values = {"holaMundo1", "holamundo2","holaMundo3","holamiundo4" };
		Object[] field2_Values = {"holaMundo1", "holamundo2","holaMundo3","acacaca" };
		Object[] field3_Values = {Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE };

		java.util.Vector values = new java.util.Vector();
		values.add( 0, field1_Values );
		values.add( 1, field2_Values );
		values.add( 2, field3_Values );

		DrawableComponent[] renderers = new DrawableComponent[3];  // textRenderer;

		renderers[0] = new TextDrawableComponent();
		renderers[1] = new TextDrawableComponent();
		renderers[2] = new BooleanDrawableComponent();

		renderers[0].setId( "field1" );
		renderers[1].setId( "field2" );
		renderers[2].setId( "field3" );

		table.setNumCols( renderers.length );
		table.setNumRows( field1_Values.length );
		table.setValues( values );
		table.setRenderers( renderers );
		// HttpServletRequest request = new HttpServletRequest();
		// request.getParameterValues()

		StringBuffer buffer = new StringBuffer();
		table.generateXmlTextFragment( buffer );
		
	}

	/**
	 * @return
	 */
	public int getNumCols() {
		return numCols;
	}

	/**
	 * @param i
	 */
	public void setNumCols(int i) {
		numCols = i;
	}

	/**
	 * @return
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * @param i
	 */
	public void setNumRows(int i) {
		numRows = i;
	}

	/**
	 * @param list
	 */
	public void setValues(List list) {
		values = list;
	}

	/**
	 * @param components
	 */
	public void setRenderers(DrawableComponent[] components) {
		renderers = components;
	}

  public void setLabels(String[] labels) {
    this.labels = labels;
  }

  /* (non-Javadoc)
	 * @see gov.sir.core.web.helpers.fotocopia.drawablecomponents.AbstractDrawableComponent#generateXmlTextFragment_HtmImp(java.lang.StringBuffer)
	 */
	protected void generateXmlTextFragment_HtmImp(StringBuffer buffer) {

		if( ( null == values )
			|| ( null == renderers ) )
		  return;

	  StringBuffer htmlBuffer;

	  htmlBuffer = new StringBuffer();

	  htmlBuffer.append( "<table " );

          // se colocan los estilos a la tabla, por defecto
          DrawableComponentAttributes atts;
          if( null != ( atts = getAttributes() ) ) {
            atts.generateAttributeValuePairs( htmlBuffer );
          }

          htmlBuffer.append( ">" );

          // no usar thead / tbody
          // inicialmente se pinta el texto
          // luego se debe editar para que el label sea
          // otro tipo de drawablecomponent (tal vez).
          // En cforms no hay equivalente cuando se genera repeater.
          // En xforms hay un tag para los labels, pero
          // no recuerdo si aplica para repeaters tambien.

          // TODO: observar la version actual de cforms, observar la
          // especificacion de xforms.


          if( ( null != labels )
            &&( labels.length == numCols ) ) {

            if(  numRows > 0 )  {

              htmlBuffer.append(" <tr>");

              for (int i = 0; i < labels.length; i++) {
                String currentLabel = labels[i];

                htmlBuffer.append("  <td>");
                htmlBuffer.append(currentLabel);
                htmlBuffer.append("\n\r");
                htmlBuffer.append("  </td>");
              }
              htmlBuffer.append("</tr>");
            }
            else {
              // TODO: displaymessage ?
            }
          }


	  for( int j=0; j < numRows; j++ ){

		  htmlBuffer.append(" <tr>" );

		  for( int i=0; i < numCols; i++ ) {
			  DrawableComponent renderer = renderers[i];
			  Object[] columnValues = (Object[]) values.get( i );
			  renderer.setValue( columnValues[j] );

			  htmlBuffer.append("  <td>"  );
			  renderer.generateXmlTextFragment( htmlBuffer );
			  htmlBuffer.append("\n\r");
			  htmlBuffer.append("  </td>"  );
		  }
		  htmlBuffer.append("</tr>");

	  }
	  htmlBuffer.append("</table>");

	  buffer.append( htmlBuffer );

	  // System.out.print( buffer );

	}


	/* (non-Javadoc)
	 * @see gov.sir.core.web.helpers.fotocopia.drawablecomponents.AbstractDrawableComponent#generateXmlTextFragment_CFormsImp(java.lang.StringBuffer)
	 */
	protected void generateXmlTextFragment_CFormsImp(StringBuffer buffer) {
		// throw new RuntimeException("Not Implemented yet" + TableDrawableComponent.class.getName() );
		// por el momento la implementacion es la misma de html

		generateXmlTextFragment_HtmImp( buffer );
	}

}
