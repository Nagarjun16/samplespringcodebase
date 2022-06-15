
package com.ngen.cosys.icms.schema.bookingapi.services.capacitybookingservice._2013._06._04_01;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.CancelBookingEnquiryRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.CancelBookingEnquiryResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.CancelBookingRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.CancelBookingResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ComputeVolumeFromDimensionsRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ComputeVolumeFromDimensionsResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookableProductsRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookableProductsResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookingDetailsRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookingDetailsResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookingEnquiryDetailsRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookingEnquiryDetailsResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookingHistoryRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookingHistoryResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookingTemplatesRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookingTemplatesResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookingsAWBDetailsRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookingsAWBDetailsResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookingsRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindBookingsResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindFlightsForBookingRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FindFlightsForBookingResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.GetAWBDetailsForFlightRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.GetAWBDetailsForFlightResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.GetBookingProfileRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.GetBookingProfileResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ListBookingDetailsRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ListBookingDetailsResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ListBookingProfilesRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ListBookingProfilesResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ListFlightInformationRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ListFlightInformationResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.SaveBookingDetailsRequest;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.SaveBookingDetailsResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.SaveBookingEnquiryDetailsRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.SaveBookingEnquiryDetailsResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.SaveBookingTemplateRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.SaveBookingTemplateResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ValidateBookingRequest;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ValidateBookingResponseType;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CapacityBookingService", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
	com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard.ObjectFactory.class,
	com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ObjectFactory.class,
	com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.ObjectFactory.class,
	com.ngen.cosys.icms.schema.bookingapi.framework.services.jaxws.types.common.ObjectFactory.class,
	com.ngen.cosys.icms.schema.bookingapi.services.capacitybookingservice._2013._06._04_01.ObjectFactory.class
})
public interface CapacityBookingService {


    /**
     * 
     * @param saveBookingDetailsRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.SaveBookingDetailsResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/saveBookingDetails")
    @WebResult(name = "SaveBookingDetailsResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "SaveBookingDetailsResponse")
    public SaveBookingDetailsResponseType saveBookingDetails(
        @WebParam(name = "SaveBookingDetailsRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "SaveBookingDetailsRequest")
        SaveBookingDetailsRequest saveBookingDetailsRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param cancelBookingRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.CancelBookingResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/cancelBookingDetails")
    @WebResult(name = "CancelBookingResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "CancelBookingResponse")
    public CancelBookingResponseType cancelBookingDetails(
        @WebParam(name = "CancelBookingRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "CancelBookingRequest")
        CancelBookingRequestType cancelBookingRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param findBookingDetailsRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.FindBookingDetailsResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/getBookingDetails")
    @WebResult(name = "FindBookingDetailsResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindBookingDetailsResponse")
    public FindBookingDetailsResponseType getBookingDetails(
        @WebParam(name = "FindBookingDetailsRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindBookingDetailsRequest")
        FindBookingDetailsRequestType findBookingDetailsRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param listBookingDetailsRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.ListBookingDetailsResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/listBookingDetails")
    @WebResult(name = "ListBookingDetailsResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "ListBookingDetailsResponse")
    public ListBookingDetailsResponseType listBookingDetails(
        @WebParam(name = "ListBookingDetailsRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "ListBookingDetailsRequest")
        ListBookingDetailsRequestType listBookingDetailsRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param findBookingHistoryRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.FindBookingHistoryResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/getBookingHistoryDetails")
    @WebResult(name = "FindBookingHistoryResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindBookingHistoryResponse")
    public FindBookingHistoryResponseType getBookingHistoryDetails(
        @WebParam(name = "FindBookingHistoryRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindBookingHistoryRequest")
        FindBookingHistoryRequestType findBookingHistoryRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param getAWBDetailsForFlightRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.GetAWBDetailsForFlightResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/external/CapacityBookingExtService/getAWBDetailsForFlight")
    @WebResult(name = "GetAWBDetailsForFlightResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "getAWBDetailsForFlightResponse")
    public GetAWBDetailsForFlightResponseType getAWBDetailsForFlight(
        @WebParam(name = "GetAWBDetailsForFlightRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "getAWBDetailsForFlightRequest")
        GetAWBDetailsForFlightRequestType getAWBDetailsForFlightRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param findFlightsForBookingRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.FindFlightsForBookingResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/findFlightsForBooking")
    @WebResult(name = "FindFlightsForBookingResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindFlightsForBookingResponse")
    public FindFlightsForBookingResponseType findFlightsForBooking(
        @WebParam(name = "FindFlightsForBookingRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindFlightsForBookingRequest")
        FindFlightsForBookingRequestType findFlightsForBookingRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param validateBookingRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.ValidateBookingResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/validateBooking")
    @WebResult(name = "ValidateBookingResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "ValidateBookingResponse")
    public ValidateBookingResponseType validateBooking(
        @WebParam(name = "ValidateBookingRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "ValidateBookingRequest")
        ValidateBookingRequest validateBookingRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param getBookingProfileRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.GetBookingProfileResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/getBookingProfile")
    @WebResult(name = "GetBookingProfileResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "GetBookingProfileResponse")
    public GetBookingProfileResponseType getBookingProfile(
        @WebParam(name = "GetBookingProfileRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "GetBookingProfileRequest")
        GetBookingProfileRequestType getBookingProfileRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param listBookingProfilesRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.ListBookingProfilesResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/listBookingProfiles")
    @WebResult(name = "ListBookingProfilesResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "ListBookingProfilesResponse")
    public ListBookingProfilesResponseType listBookingProfiles(
        @WebParam(name = "ListBookingProfilesRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "ListBookingProfilesRequest")
        ListBookingProfilesRequestType listBookingProfilesRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param computeVolumeFromDimensionsRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.ComputeVolumeFromDimensionsResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/computeVolumeFromDimensions")
    @WebResult(name = "ComputeVolumeFromDimensionsResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "ComputeVolumeFromDimensionsResponse")
    public ComputeVolumeFromDimensionsResponseType computeVolumeFromDimensions(
        @WebParam(name = "ComputeVolumeFromDimensionsRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "ComputeVolumeFromDimensionsRequest")
        ComputeVolumeFromDimensionsRequestType computeVolumeFromDimensionsRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param listFlightInformationRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.ListFlightInformationResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/listFlightInformation")
    @WebResult(name = "ListFlightInformationResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "ListFlightInformationResponse")
    public ListFlightInformationResponseType listFlightInformation(
        @WebParam(name = "ListFlightInformationRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "ListFlightInformationRequest")
        ListFlightInformationRequestType listFlightInformationRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param findBookableProductsRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.FindBookableProductsResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/findBookableProducts")
    @WebResult(name = "FindBookableProductsResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindBookableProductsResponse")
    public FindBookableProductsResponseType findBookableProducts(
        @WebParam(name = "FindBookableProductsRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindBookableProductsRequest")
        FindBookableProductsRequestType findBookableProductsRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param findBookingsRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.FindBookingsResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/findBookings")
    @WebResult(name = "FindBookingsResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindBookingsResponse")
    public FindBookingsResponseType findBookings(
        @WebParam(name = "FindBookingsRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindBookingsRequest")
        FindBookingsRequestType findBookingsRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param findBookingTemplatesRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.FindBookingTemplatesResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/findBookingTemplates")
    @WebResult(name = "FindBookingTemplatesResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindBookingTemplatesResponse")
    public FindBookingTemplatesResponseType findBookingTemplates(
        @WebParam(name = "FindBookingTemplatesRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "findBookingTemplatesRequest")
        FindBookingTemplatesRequestType findBookingTemplatesRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param saveBookingTemplateRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.SaveBookingTemplateResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/saveBookingTemplate")
    @WebResult(name = "SaveBookingTemplateResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "SaveBookingTemplateResponse")
    public SaveBookingTemplateResponseType saveBookingTemplate(
        @WebParam(name = "SaveBookingTemplateRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "SaveBookingTemplateRequest")
        SaveBookingTemplateRequestType saveBookingTemplateRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param saveBookingEnquiryDetailsRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.SaveBookingEnquiryDetailsResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/saveBookingEnquiryDetails")
    @WebResult(name = "SaveBookingEnquiryDetailsResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "SaveBookingEnquiryDetailsResponse")
    public SaveBookingEnquiryDetailsResponseType saveBookingEnquiryDetails(
        @WebParam(name = "SaveBookingEnquiryDetailsRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "SaveBookingEnquiryDetailsRequest")
        SaveBookingEnquiryDetailsRequestType saveBookingEnquiryDetailsRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param cancelBookingEnquiryRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.CancelBookingEnquiryResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/cancelBookingEnquiryDetails")
    @WebResult(name = "CancelBookingEnquiryResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "CancelBookingEnquiryResponse")
    public CancelBookingEnquiryResponseType cancelBookingEnquiryDetails(
        @WebParam(name = "CancelBookingEnquiryRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "CancelBookingEnquiryRequest")
        CancelBookingEnquiryRequestType cancelBookingEnquiryRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param findBookingEnquiryDetailsRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.FindBookingEnquiryDetailsResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/getBookingEnquiryDetails")
    @WebResult(name = "FindBookingEnquiryDetailsResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindBookingEnquiryDetailsResponse")
    public FindBookingEnquiryDetailsResponseType getBookingEnquiryDetails(
        @WebParam(name = "FindBookingEnquiryDetailsRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "FindBookingEnquiryDetailsRequest")
        FindBookingEnquiryDetailsRequestType findBookingEnquiryDetailsRequest)
        throws InvalidRequestFault, ServiceFault
    ;

    /**
     * 
     * @param findBookingsAWBDetailsRequest
     * @return
     *     returns com.ibsplc.icargo.business.capacity.booking.types.standard.FindBookingsAWBDetailsResponseType
     * @throws ServiceFault
     * @throws InvalidRequestFault
     */
    @WebMethod(action = "http://www.ibsplc.com/icargo/services/CapacityBookingService/findBookingsAWBDetails")
    @WebResult(name = "FindBookingsAWBDetailsResponse", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "findBookingsAWBDetailsResponse")
    public FindBookingsAWBDetailsResponseType findBookingsAWBDetails(
        @WebParam(name = "FindBookingsAWBDetailsRequest", targetNamespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", partName = "findBookingsAWBDetailsRequest")
        FindBookingsAWBDetailsRequestType findBookingsAWBDetailsRequest)
        throws InvalidRequestFault, ServiceFault
    ;

}
