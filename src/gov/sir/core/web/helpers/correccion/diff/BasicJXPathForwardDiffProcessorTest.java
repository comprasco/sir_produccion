package gov.sir.core.web.helpers.correccion.diff;

import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicStringComparator;
import junit.framework.TestCase;

public class BasicJXPathForwardDiffProcessorTest extends TestCase {

	public static
	void main(String[] args) {
		junit.textui.TestRunner.run(BasicJXPathForwardDiffProcessorTest.class);
	}

	String[] pathsToTest;
	java.util.Comparator[] comparatorsToApply;

	Documento docT0 = null;
	Documento docT1 = null;
	Documento docDelta = null;



	protected void setUp() throws Exception {
		super.setUp();

		// colocar el conjunto de paths
		// colocar el conjunto de comparators
		// crear documento t0
		// crear documento t1
		// crear arbol delta

		pathsToTest = new String[] {
				  "numero"
				, "comentario"
				, "tipoDocumento/idTipoDocumento"
			};
		comparatorsToApply = new java.util.Comparator[] {
				  new BasicStringComparator(true,true)
				, new BasicStringComparator(true,true)
				, new BasicStringComparator(true,true)
			};
		docT0 = new Documento();
		docT1 = new Documento();
		docDelta = new Documento();
		docDelta.setTipoDocumento( new TipoDocumento() );

		docT0.setFecha( new java.util.Date() );
		docT0.setNumero("8");
		docT0.setComentario("este es un comentario");

		TipoDocumento docT0_tipoDocumento = new TipoDocumento();
		docT0_tipoDocumento.setIdTipoDocumento("88");

		docT0.setTipoDocumento( docT0_tipoDocumento );

		docT1.setFecha( docT0.getFecha() );
		docT1.setNumero("9");
		docT1.setComentario("este es un comentario ++");
		docT1.setTipoDocumento( docT0_tipoDocumento );
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		pathsToTest = null;
		comparatorsToApply = null;

		docT0 = null;
		docT1 = null;
		docDelta = null;

		System.gc();

	}


	public void testExecute() {

		//docT1 = docT0;

		BasicJXPathForwardDiffProcessor processor
		 = new BasicJXPathForwardDiffProcessor( pathsToTest, comparatorsToApply );

		processor.execute( docT0, docT1, docDelta );

		assertEquals( docDelta.getNumero(), docT1.getNumero() );
		assertEquals( docDelta.getComentario(), docT1.getComentario() );
		assertNull( docDelta.getFecha() );
		assertTrue( processor.objectHasChanges() );

		BasicJXPathForwardDiffProcessor.printComparisonResults( System.out, pathsToTest, processor.getComparisonResults() );

		//logger.debug( docDelta.getNumero() );
		//logger.debug( docDelta.getComentario() );
		//logger.debug( docDelta.getFecha() );
		System.out.println( (null!=docDelta.getTipoDocumento())?(docDelta.getTipoDocumento().getIdTipoDocumento()):(null) );
		// logger.debug(  );


	}

}
