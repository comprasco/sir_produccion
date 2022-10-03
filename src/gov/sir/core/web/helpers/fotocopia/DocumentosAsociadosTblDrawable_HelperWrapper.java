package gov.sir.core.web.helpers.fotocopia;

import gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators.IndexSelectorComponent;
import gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators.DrawableComponent;
import gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators.ListDrawableComponent;
import gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators.TableDrawableComponent;
import gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators.TextDrawableComponent;

import gov.sir.core.web.helpers.fotocopia.drawablecomponents.transformers.TransformerComponent;
import gov.sir.core.web.helpers.fotocopia.drawablecomponents.transformers.DefaultDOMTransformerComponent;



import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import gov.sir.core.web.acciones.fotocopia.AW_FotocopiasConstants;
import gov.sir.core.web.acciones.fotocopia.DocumentoAsociado_Item;

import gov.sir.core.negocio.modelo.constantes.CDocumento;
import gov.sir.core.negocio.modelo.constantes.CSolicitudFotocopia;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import org.auriga.core.web.HelperException;
import org.auriga.core.web.Helper;
import java.io.Writer;
import java.util.ArrayList;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators.DrawableComponentAttributes;

/**
 * @author ahurtado
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public
class DocumentosAsociadosTblDrawable_HelperWrapper
extends Helper {
  public static String SIR_COMPONENTS_STYLES = "WEB-INF/jspSeguro/fotocopia/forms-field-styling-impl1.xsl";

	protected String documentosAsociadosListName;
        protected String lov_TipoDocumentoContextAttName;

        public void setLov_TipoDocumentoContextAttName( String lov_TipoDocumentoSessionAttName) {
          this.lov_TipoDocumentoContextAttName = lov_TipoDocumentoSessionAttName;
        }


	TableDrawableComponent tableDrawableComponent = new TableDrawableComponent();
        TransformerComponent   templateForDrawableComponents = new DefaultDOMTransformerComponent();

	// private TableDrawableComponent drawableComponent;

	public void setProperties(HttpServletRequest request) {
		// get values
		HttpSession session = request.getSession();
		List documentosAsociados = (List)session.getAttribute( documentosAsociadosListName );

                if( null == documentosAsociados  )
                  return;

		int documentosAsociados_Length = documentosAsociados.size();


		int numRows = documentosAsociados_Length;

		Object[] documentosAsociados_Selector      = new String[numRows];//{Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE };
		Object[] documentosAsociados_TipoDocumento = new String[numRows];
		Object[] documentosAsociados_NumCopias     = new String[numRows];
		Object[] documentosAsociados_Descripcion   = new String[numRows];

		Iterator iterator;
                iterator = documentosAsociados.iterator();

		int i;

		for( i=0;iterator.hasNext();i++){
			DocumentoAsociado_Item documento =(DocumentoAsociado_Item)iterator.next();

			documentosAsociados_Selector[i]      = "" + i;

			documentosAsociados_TipoDocumento[i] = "" + documento.getTipoDocumento_Id();
			documentosAsociados_NumCopias[i]     = "" + documento.getNumCopias();
			documentosAsociados_Descripcion[i]   = "" + documento.getDescripcion();
		}

		java.util.Vector values = new java.util.Vector();
		values.add( 0, documentosAsociados_Selector );
		values.add( 1, documentosAsociados_TipoDocumento );
		values.add( 2, documentosAsociados_NumCopias );
		values.add( 3, documentosAsociados_Descripcion );

		int numCols = values.size();


                // obtener los lov
                List lov_TipoDocumento = null;
                getLov_Tipodocumento: {
                  javax.servlet.ServletContext context = session.getServletContext();

                  List lovSession_TipoDocumento = (List)context.getAttribute( lov_TipoDocumentoContextAttName );

                  if( null == lovSession_TipoDocumento  )
                    break getLov_Tipodocumento;

                  lov_TipoDocumento = new ArrayList();


                  iterator = lovSession_TipoDocumento.iterator();
                  for(i=0; iterator.hasNext();i++){
                    ElementoLista element = (ElementoLista)iterator.next();
                    lov_TipoDocumento.add(i, new ListDrawableComponent.Item4ListDrawableComponent(element.getId(), element.getValor()));
                  }

                }

                // labels
                String[] labels = {"", "Tipo documento", "Num copias", "Descripcion" };

		// renderers

		DrawableComponent[] renderers = new DrawableComponent[numCols];  // textRenderer;

		renderers[0] = new IndexSelectorComponent();
		renderers[1] = new ListDrawableComponent(lov_TipoDocumento);//new TextDrawableComponent();
		renderers[2] = new TextDrawableComponent();
		renderers[3] = new TextDrawableComponent();

		renderers[0].setId( AW_FotocopiasConstants.PARAMETER_SELECTEDITEMS );
		renderers[1].setId( "repeater_" + CDocumento.TIPO_DOCUMENTO );
		renderers[2].setId( "repeater_" + CSolicitudFotocopia.NUMCOPIAS );
		renderers[3].setId( "repeater_" + CTurno.DESCRIPCION );

                // set attributes
                DrawableComponentAttributes attribs;

                attribs = new DrawableComponentAttributes();
                attribs.addProperty( "readonly","true" );
                attribs.addProperty( "class", "campositem" );
					 attribs.addProperty( "style", "width:70px;" );

                renderers[2].setAttributes( attribs );

					 attribs = new DrawableComponentAttributes();
					 attribs.addProperty( "readonly","true" );
					 attribs.addProperty( "class", "campositem" );
					 attribs.addProperty( "style", "width:330px;" );

                renderers[3].setAttributes( attribs );

                attribs = new DrawableComponentAttributes();
                attribs.addProperty( "disabled","disabled" );
                attribs.addProperty( "class", "camposformtext" );
                renderers[1].setAttributes( attribs );

		// fix the values

		tableDrawableComponent.setNumCols( renderers.length );
		tableDrawableComponent.setNumRows( numRows );
		tableDrawableComponent.setValues( values );
		tableDrawableComponent.setRenderers( renderers );
                tableDrawableComponent.setLabels( labels );

                tableDrawableComponent.setRenderMode( DrawableComponent.XHTML_RENDER );

                // set attributes for repeater
                attribs = new DrawableComponentAttributes();
                attribs.addProperty( "class", "camposform"  );
                attribs.addProperty( "style", "border:0px;"  );
                tableDrawableComponent.setAttributes( attribs );
	}

	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {

		/*

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

		tableDrawableComponent.setNumCols( renderers.length );
		tableDrawableComponent.setNumRows( field1_Values.length );
		tableDrawableComponent.setValues( values );
		tableDrawableComponent.setRenderers( renderers );
		// HttpServletRequest request = new HttpServletRequest();
		// request.getParameterValues()
		*/
		//-------------------------------

		StringBuffer buffer = new StringBuffer();
		tableDrawableComponent.generateXmlTextFragment( buffer );
                /*
                try {
                  templateForDrawableComponents.transform(buffer.toString(),
                      SIR_COMPONENTS_STYLES, out);
                }
                catch( Exception e ) {
                  throw new HelperException( "Error al aplicar transformers, solicitud fotocopias" +  e.getMessage() );
                }
                */
		out.write( buffer.toString() );

	}


	public static void main(String[] args) {
	}

	/**
	 * @return
	 */
	public String getDocumentosAsociadosListName() {
		return documentosAsociadosListName;
	}

	/**
	 * @param string
	 */
	public void setDocumentosAsociadosListName(String string) {
		documentosAsociadosListName = string;
	}

        public void transform(StringBuffer xmlSource, String xslFilename, Writer out) {

        }

}
