package gov.sir.core.web.helpers.correccion.validate;

import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.web.helpers.correccion.diff.BasicJXPathForwardDiffProcessor;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicStringComparator;
import gov.sir.core.web.helpers.correccion.diff.utils.jxpath.DocumentoAbstractFactory;
import gov.sir.core.web.helpers.correccion.validate.utils.conditions.BasicStaticBooleanCondition;
import gov.sir.core.web.helpers.correccion.validate.utils.conditions.ConditionComponent;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicConditionalValidator;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringNotNullOrEmptyValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.ConditionalValidator;
import junit.framework.TestCase;

public class BasicJxPathConditionProcessorTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(BasicJxPathConditionProcessorTest.class);
	}

	public void testEvaluate() {
		// ----------------------------------------------------------
		// ----------------------------------------------------------
		// Evaluar diferencias hacia adelante 
		
		// -----------------------------------
		String[] pathsToTest;
		java.util.Comparator[] comparatorsToApply;
		
		Documento docT0 = null;
		Documento docT1 = null;
		Documento docDelta = null;
		
		// -----------------------------------
		
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
		
		
		// -----------------------------------
		
		// probar con cualquier validador condicional
		
		BasicJXPathForwardDiffProcessor processor 
		 = new BasicJXPathForwardDiffProcessor( pathsToTest, comparatorsToApply );
		
		processor.setAbstractFactory( new DocumentoAbstractFactory() );
		
		processor.execute( docT0, docT1, docDelta );
		
		// BasicJXPathForwardDiffProcessor.printComparisonResults( System.out, pathsToTest, processor.getComparisonResults() );
		
		
		// ----------------------------------------------------------
		// ----------------------------------------------------------
		// Si ha cambiado, aplicar validaciones respectivas sobre los campos
		
		// construir validador
		ConditionalValidator validator;		
		validator = new BasicStringNotNullOrEmptyValidatorWrapper();		
		
		// construir validador.conditions 		
		ConditionComponent condition;
		
		boolean condition1 = processor.pathHasChanges( "comentario" ); // el atributo ha cambiado
		boolean condition2 = true; // tiene permisos para aplicar el cambio //TODO: aplicar segun la tabla hash; significa que  
		
		// se construye la primera condicion
		condition = new BasicStaticBooleanCondition( condition1 );
		validator.addCondition( condition );
		
		// se construye la segunda condicion
		condition = new BasicStaticBooleanCondition( condition2 );
		validator.addCondition( condition );
		
		// se coloca el valor del campo a validar
		// TODO: hacerlo depender tambien de path
		validator.addParameter( BasicConditionalValidator.OBJECT_TO_VALIDATE, "" );
		// se aplica el validador
		validator.validate();
		
		
		// se obtienen los resultados del paso de validacion
		
		// " ha pasado la validacion" ??
		
		// no debio haber pasado la validacion porque el validador es
		// string not null or not empty y la cadena dada como parametro es vacia
		assertFalse( validator.getResult() );
		// logger.debug("si ha pasado la validacion");
			
		
		// -----------------------------------
		// -----------------------------------
		/*
		condition = new BasicStaticBooleanCondition( false );
		validator.addCondition( condition );
		validator.validate();
		
		// si hay una condicion habilitada
		// pasa como true;
		assertTrue( validator.getResult() );
		
		validator.resetParameters();
		validator.resetConditions();
		
		validator = new BasicStringNotNullOrEmptyValidatorWrapper();
		validator.addParameter( BasicConditionalValidator.OBJECT_TO_VALIDATE, "algo" );

		condition = new BasicStaticBooleanCondition( true );
		validator.addCondition( condition );
		validator.validate();
		
		// la condicion ya esta habilitada
		assertTrue( validator.getResult() );
		
		*/
				
	}

}
