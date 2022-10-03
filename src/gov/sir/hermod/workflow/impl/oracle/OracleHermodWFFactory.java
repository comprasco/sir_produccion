/* Generated by Together */

package gov.sir.hermod.workflow.impl.oracle;

import gov.sir.hermod.workflow.HermodWFFactory;
import gov.sir.hermod.workflow.Processor;
import gov.sir.hermod.workflow.Workflow;


/**
 *
 * @author  mrios
 */

public class OracleHermodWFFactory extends HermodWFFactory {
    //TODO: ELIMINAR GETPROCESSOR
    public Processor getProcessor() {
        return new OracleProcessor();
    }    
    
    public Workflow getWorkflow(){
    	OracleWorkflow wf = new OracleWorkflow();
    	wf.setListener(new CoordinadorWFSAS());
    	return wf;
    }
}