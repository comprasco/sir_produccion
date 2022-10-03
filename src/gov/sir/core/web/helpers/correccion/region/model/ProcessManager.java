package gov.sir.core.web.helpers.correccion.region.model;

import gov.sir.core.web.helpers.correccion.region.model.basic.AuthTraverseComponentManager;

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
public interface ProcessManager extends AuthTraverseComponentManager {
    public Process getProcessById( String processId );


    // Cumple un rol semejante al TagLibrary en capas logicas
    public static class StaticEvaluator {
        public static boolean evaluate( ProcessManager processManager, String processId ) {

            gov.sir.core.web.helpers.correccion.region.model.Process thisProcess; // region x
            gov.sir.core.web.helpers.correccion.region.model.Process.ConditionalProcess conditionalProcess; // conditionaldisplay region x
            int processCondition; // resultado de despliegue


            thisProcess = processManager.getProcessById( processId );
            if( null != thisProcess ) {
                conditionalProcess = thisProcess.getConditionalProcess();
                processCondition = conditionalProcess.getDisplayResult();

                if( ( processCondition & gov.sir.core.web.helpers.correccion.region.model.Process.ConditionalProcess.SHOW ) > 0 ) {
                    return true;
                }
            }

            return false;
        }
    }




}
