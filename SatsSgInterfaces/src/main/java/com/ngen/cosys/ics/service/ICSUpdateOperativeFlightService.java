/**
 * 
 */
package com.ngen.cosys.ics.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.ICSUpdateOperativeFlightRequestModel;

/**
 * @author Ashwin.Bantoo
 *
 */
public interface ICSUpdateOperativeFlightService {

	   public ICSUpdateOperativeFlightRequestModel getUpdatedOperativeFlightDetails() throws CustomException;
	
}
