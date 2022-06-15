package com.ngen.cosys.icms.processor.flightbooking;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.export.commonbooking.model.CommonBooking;
import com.ngen.cosys.export.commonbooking.model.PartSuffix;
import com.ngen.cosys.export.commonbooking.service.CommonBookingService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingRemark;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentPartBookingDimensionDetails;
import com.ngen.cosys.icms.model.BookingInterface.BookingShipment;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightPartBookingDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails;
import com.ngen.cosys.icms.schema.flightbooking.FlightPair;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.icms.util.BookingRemark;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;

public class FlightBookingPartShipment {
	
	@Autowired
	FlightBookingDao flightPublishDao;
	
	@Autowired
	private CommonBookingService commonBookingService;
	
	@Autowired
	CommonUtil commonUtil;
	
	private static final String CANCELLED = "XX";
	
	private static void createPartSuffix(List<FlightPair> flightPairList) {
		//PartSuffix partSuffix = bookingPublishDao.getPartSuffixDetails("SQ");

//		String primaryIdentifier = partSuffix.getPrimaryIdentifier();
//		String startWith = partSuffix.getStartPrefix();
//		String endWith = partSuffix.getEndPrefix();
		String primaryIdentifier = "P";
		String startWith = "A";
		
		int pairCount = 0;
		String suffix = "";
		for (FlightPair flightPair : flightPairList) {
			if(pairCount == 0) {
				suffix = primaryIdentifier;	
				pairCount++;
				System.out.println("suffix if 1::"+suffix);
			}else {
				if(suffix.equals("P")) {
					suffix = startWith;
					System.out.println("suffix else 1::"+suffix);
				}else {
					char prevSuffix = suffix.charAt(0);
					prevSuffix+=1;
					suffix = String.valueOf(prevSuffix);
					System.out.println("suffix else 2::"+suffix);
				}
			}
		}
	}
	
	
	
	
	public static void main(String arg[]) {
		List<FlightPair> flightPairList = new ArrayList<FlightPair>();
		FlightPair fp1 = new FlightPair();
		flightPairList.add(fp1);
		FlightPair fp2 = new FlightPair();
		flightPairList.add(fp2);
		FlightPair fp3 = new FlightPair();
		flightPairList.add(fp3);
		FlightPair fp4 = new FlightPair();
		flightPairList.add(fp4);
		createPartSuffix(flightPairList);
	}

}
