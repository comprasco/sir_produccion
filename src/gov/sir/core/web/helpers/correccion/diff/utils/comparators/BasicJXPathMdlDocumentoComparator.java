package gov.sir.core.web.helpers.correccion.diff.utils.comparators;

import org.apache.commons.jxpath.JXPathContext;
import gov.sir.core.negocio.modelo.Documento;

public class BasicJXPathMdlDocumentoComparator extends BasicAbstractComparator {

	String[] pathsToTest;
	java.util.Comparator[] comparatorsToApply;

	public BasicJXPathMdlDocumentoComparator( String[] pathsToTest, java.util.Comparator[] comparatorsToApply ) {
		this.pathsToTest = pathsToTest;
		this.comparatorsToApply = comparatorsToApply;
	}

	public static int compareProperty( JXPathContext sourceContext, JXPathContext targetContext, String path, java.util.Comparator comparatorToApply ) {

		Object nodeSource = null;
		Object nodeTarget = null;

		nodeSource = sourceContext.getValue( path );
		nodeTarget = targetContext.getValue( path );

		return comparatorToApply.compare( nodeSource, nodeTarget );
	}

	public int compare(Object o1, Object o2) {

		int comparison = super.compare( o1, o2 );

		if( comparison < 2 )
			return comparison;

		Documento source = (Documento)o1;
		Documento target = (Documento)o2;

		JXPathContext sourceContext = JXPathContext.newContext( source );
		JXPathContext targetContext = JXPathContext.newContext( target );

		int comparisonResult;
		for (int i = 0; i < pathsToTest.length; i++) {
			String path = pathsToTest[i];
			java.util.Comparator comparatorToApply = comparatorsToApply[i];
			comparisonResult = compareProperty( sourceContext, targetContext, path, comparatorToApply );
			if( comparisonResult != 0 ){
				return comparisonResult;
			}
		}

		return 0;
	}

	public static
	void main( String[] args ) {
		String[] pathsToTest = new String[] {
			  "numero"
			, "comentario"
		};

		java.util.Comparator[] comparatorsToApply = new java.util.Comparator[] {
			  new BasicStringComparator(true,true)
			, new BasicStringComparator(true,true)
		};

		Documento docT0 = new Documento();
		Documento docT1 = new Documento();

		docT0.setFecha( new java.util.Date() );
		docT0.setNumero("8");
		docT0.setComentario("este es un comentario");

		docT1 = docT0;

		BasicJXPathMdlDocumentoComparator comparator
		 = new BasicJXPathMdlDocumentoComparator( pathsToTest, comparatorsToApply );

	}

}
