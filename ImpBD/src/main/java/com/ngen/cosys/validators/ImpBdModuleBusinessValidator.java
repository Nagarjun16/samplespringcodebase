/**
 * This is an validation interface which this module business validators implements 
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.validators;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;

public interface ImpBdModuleBusinessValidator {

	/**
	 * Method to validate business object
	 * 
	 * @param baseBO
	 * @throws CustomException
	 */
	void validate(BaseBO baseBO) throws CustomException;

	/**
	 * Method to validate list of business objects
	 * 
	 * @param baseBOList
	 * @throws CustomException
	 */
	void validate(List<?> baseBOList) throws CustomException;

}