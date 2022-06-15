package com.ngen.cosys.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.displayffm.model.SearchDisplayffmModel;
import com.ngen.cosys.validator.dao.FlightValidationDao;
import com.ngen.cosys.validator.enums.FlightType;
import com.ngen.cosys.validator.model.FlightValidateModel;

@Component
public class IncomingFlightValidation {

	@Autowired
	private FlightValidationDao flightValidationDao;

	public void flightValidation(SearchDisplayffmModel requestModel) throws CustomException {

		String type = FlightType.IMPORT.getType();

		FlightValidateModel flight = new FlightValidateModel();
		flight.setFlightType(FlightType.IMPORT.getType());
		flight.setFlightKey(requestModel.getFlightNumber());
		flight.setFlightDate(requestModel.getFlightDate());
		

		boolean isFlightExist = flightValidationDao.isFlightExist(flight, FlightType.IMPORT.getType());

		if (!isFlightExist && FlightType.IMPORT.getType().equalsIgnoreCase(type)) {
			boolean isFlightCancelled = flightValidationDao.isIncomingFlightCancelled(flight,requestModel.getTenantAirport() );
			if (isFlightCancelled) {
				throw new CustomException("incoming.flight.cancelled", "error", ErrorType.ERROR);
			} else {
				throw new CustomException("invalid.import.flight", "error", ErrorType.ERROR);
			}
		} else if (!isFlightExist && FlightType.EXPORT.getType().equalsIgnoreCase(type)) {
			boolean isFlightCancelled = flightValidationDao.isOutgoingFlightCancelled(flight,requestModel.getTenantAirport());
			if (isFlightCancelled) {
				throw new CustomException("outgoing.flight.cancelled", "error", ErrorType.ERROR);
			} else {
				throw new CustomException("invalid.export.flight", "error", ErrorType.ERROR);
			}
		} else if (!isFlightExist) {
			throw new CustomException("invalid.import.flight", "error", ErrorType.ERROR);
		}

	}

}
