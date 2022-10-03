package gov.sir.core.web.helpers.fotocopia.drawablecomponents.transformers;

import javax.servlet.jsp.JspWriter;

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
public interface TransformerComponent {
  void transform( String xmlSource, String xslFilename, JspWriter out ) throws Exception;
}
