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
public interface RegionManager extends AuthTraverseComponentManager, java.io.Serializable {
    public Region getRegionById( String regionId );

}
