
package com.ngen.cosys.icms.schema.bookingapi.services.capacitybookingservice._2013._06._04_01;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
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
import com.ngen.cosys.icms.schema.bookingapi.framework.services.jaxws.types.common.InvalidRequestFaultDetail;
import com.ngen.cosys.icms.schema.bookingapi.framework.services.jaxws.types.common.ServiceFaultDetail;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ibsplc.icargo.services.capacitybookingservice._2013._06._04_01 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FindBookableProductsResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookableProductsResponse");
    private final static QName _GetBookingProfileRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "GetBookingProfileRequest");
    private final static QName _ListBookingDetailsRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "ListBookingDetailsRequest");
    private final static QName _ServiceFault_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "ServiceFault");
    private final static QName _InvalidRequestFault_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "InvalidRequestFault");
    private final static QName _CancelBookingEnquiryRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "CancelBookingEnquiryRequest");
    private final static QName _CancelBookingRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "CancelBookingRequest");
    private final static QName _FindBookingHistoryResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookingHistoryResponse");
    private final static QName _FindBookingsRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookingsRequest");
    private final static QName _CancelBookingEnquiryResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "CancelBookingEnquiryResponse");
    private final static QName _FindFlightsForBookingRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindFlightsForBookingRequest");
    private final static QName _ListBookingProfilesRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "ListBookingProfilesRequest");
    private final static QName _ValidateBookingRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "ValidateBookingRequest");
    private final static QName _ListFlightInformationRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "ListFlightInformationRequest");
    private final static QName _FindBookingsResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookingsResponse");
    private final static QName _FindBookableProductsRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookableProductsRequest");
    private final static QName _FindBookingEnquiryDetailsRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookingEnquiryDetailsRequest");
    private final static QName _CancelBookingResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "CancelBookingResponse");
    private final static QName _SaveBookingDetailsResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "SaveBookingDetailsResponse");
    private final static QName _FindBookingDetailsResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookingDetailsResponse");
    private final static QName _GetAWBDetailsForFlightRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "GetAWBDetailsForFlightRequest");
    private final static QName _GetBookingProfileResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "GetBookingProfileResponse");
    private final static QName _ListFlightInformationResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "ListFlightInformationResponse");
    private final static QName _FindBookingEnquiryDetailsResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookingEnquiryDetailsResponse");
    private final static QName _FindBookingHistoryRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookingHistoryRequest");
    private final static QName _GetAWBDetailsForFlightResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "GetAWBDetailsForFlightResponse");
    private final static QName _FindBookingTemplatesResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookingTemplatesResponse");
    private final static QName _FindBookingTemplatesRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookingTemplatesRequest");
    private final static QName _ComputeVolumeFromDimensionsRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "ComputeVolumeFromDimensionsRequest");
    private final static QName _SaveBookingTemplateResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "SaveBookingTemplateResponse");
    private final static QName _ValidateBookingResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "ValidateBookingResponse");
    private final static QName _SaveBookingEnquiryDetailsRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "SaveBookingEnquiryDetailsRequest");
    private final static QName _SaveBookingEnquiryDetailsResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "SaveBookingEnquiryDetailsResponse");
    private final static QName _FindBookingsAWBDetailsRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookingsAWBDetailsRequest");
    private final static QName _FindBookingDetailsRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookingDetailsRequest");
    private final static QName _ListBookingDetailsResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "ListBookingDetailsResponse");
    private final static QName _ListBookingProfilesResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "ListBookingProfilesResponse");
    private final static QName _SaveBookingTemplateRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "SaveBookingTemplateRequest");
    private final static QName _FindBookingsAWBDetailsResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindBookingsAWBDetailsResponse");
    private final static QName _FindFlightsForBookingResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "FindFlightsForBookingResponse");
    private final static QName _SaveBookingDetailsRequest_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "SaveBookingDetailsRequest");
    private final static QName _ComputeVolumeFromDimensionsResponse_QNAME = new QName("http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", "ComputeVolumeFromDimensionsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ibsplc.icargo.services.capacitybookingservice._2013._06._04_01
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookableProductsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookableProductsResponse")
    public JAXBElement<FindBookableProductsResponseType> createFindBookableProductsResponse(FindBookableProductsResponseType value) {
        return new JAXBElement<FindBookableProductsResponseType>(_FindBookableProductsResponse_QNAME, FindBookableProductsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBookingProfileRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "GetBookingProfileRequest")
    public JAXBElement<GetBookingProfileRequestType> createGetBookingProfileRequest(GetBookingProfileRequestType value) {
        return new JAXBElement<GetBookingProfileRequestType>(_GetBookingProfileRequest_QNAME, GetBookingProfileRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListBookingDetailsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "ListBookingDetailsRequest")
    public JAXBElement<ListBookingDetailsRequestType> createListBookingDetailsRequest(ListBookingDetailsRequestType value) {
        return new JAXBElement<ListBookingDetailsRequestType>(_ListBookingDetailsRequest_QNAME, ListBookingDetailsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceFaultDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "ServiceFault")
    public JAXBElement<ServiceFaultDetail> createServiceFault(ServiceFaultDetail value) {
        return new JAXBElement<ServiceFaultDetail>(_ServiceFault_QNAME, ServiceFaultDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidRequestFaultDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "InvalidRequestFault")
    public JAXBElement<InvalidRequestFaultDetail> createInvalidRequestFault(InvalidRequestFaultDetail value) {
        return new JAXBElement<InvalidRequestFaultDetail>(_InvalidRequestFault_QNAME, InvalidRequestFaultDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelBookingEnquiryRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "CancelBookingEnquiryRequest")
    public JAXBElement<CancelBookingEnquiryRequestType> createCancelBookingEnquiryRequest(CancelBookingEnquiryRequestType value) {
        return new JAXBElement<CancelBookingEnquiryRequestType>(_CancelBookingEnquiryRequest_QNAME, CancelBookingEnquiryRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelBookingRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "CancelBookingRequest")
    public JAXBElement<CancelBookingRequestType> createCancelBookingRequest(CancelBookingRequestType value) {
        return new JAXBElement<CancelBookingRequestType>(_CancelBookingRequest_QNAME, CancelBookingRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingHistoryResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookingHistoryResponse")
    public JAXBElement<FindBookingHistoryResponseType> createFindBookingHistoryResponse(FindBookingHistoryResponseType value) {
        return new JAXBElement<FindBookingHistoryResponseType>(_FindBookingHistoryResponse_QNAME, FindBookingHistoryResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookingsRequest")
    public JAXBElement<FindBookingsRequestType> createFindBookingsRequest(FindBookingsRequestType value) {
        return new JAXBElement<FindBookingsRequestType>(_FindBookingsRequest_QNAME, FindBookingsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelBookingEnquiryResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "CancelBookingEnquiryResponse")
    public JAXBElement<CancelBookingEnquiryResponseType> createCancelBookingEnquiryResponse(CancelBookingEnquiryResponseType value) {
        return new JAXBElement<CancelBookingEnquiryResponseType>(_CancelBookingEnquiryResponse_QNAME, CancelBookingEnquiryResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindFlightsForBookingRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindFlightsForBookingRequest")
    public JAXBElement<FindFlightsForBookingRequestType> createFindFlightsForBookingRequest(FindFlightsForBookingRequestType value) {
        return new JAXBElement<FindFlightsForBookingRequestType>(_FindFlightsForBookingRequest_QNAME, FindFlightsForBookingRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListBookingProfilesRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "ListBookingProfilesRequest")
    public JAXBElement<ListBookingProfilesRequestType> createListBookingProfilesRequest(ListBookingProfilesRequestType value) {
        return new JAXBElement<ListBookingProfilesRequestType>(_ListBookingProfilesRequest_QNAME, ListBookingProfilesRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateBookingRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "ValidateBookingRequest")
    public JAXBElement<ValidateBookingRequest> createValidateBookingRequest(ValidateBookingRequest value) {
        return new JAXBElement<ValidateBookingRequest>(_ValidateBookingRequest_QNAME, ValidateBookingRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFlightInformationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "ListFlightInformationRequest")
    public JAXBElement<ListFlightInformationRequestType> createListFlightInformationRequest(ListFlightInformationRequestType value) {
        return new JAXBElement<ListFlightInformationRequestType>(_ListFlightInformationRequest_QNAME, ListFlightInformationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookingsResponse")
    public JAXBElement<FindBookingsResponseType> createFindBookingsResponse(FindBookingsResponseType value) {
        return new JAXBElement<FindBookingsResponseType>(_FindBookingsResponse_QNAME, FindBookingsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookableProductsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookableProductsRequest")
    public JAXBElement<FindBookableProductsRequestType> createFindBookableProductsRequest(FindBookableProductsRequestType value) {
        return new JAXBElement<FindBookableProductsRequestType>(_FindBookableProductsRequest_QNAME, FindBookableProductsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingEnquiryDetailsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookingEnquiryDetailsRequest")
    public JAXBElement<FindBookingEnquiryDetailsRequestType> createFindBookingEnquiryDetailsRequest(FindBookingEnquiryDetailsRequestType value) {
        return new JAXBElement<FindBookingEnquiryDetailsRequestType>(_FindBookingEnquiryDetailsRequest_QNAME, FindBookingEnquiryDetailsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelBookingResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "CancelBookingResponse")
    public JAXBElement<CancelBookingResponseType> createCancelBookingResponse(CancelBookingResponseType value) {
        return new JAXBElement<CancelBookingResponseType>(_CancelBookingResponse_QNAME, CancelBookingResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveBookingDetailsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "SaveBookingDetailsResponse")
    public JAXBElement<SaveBookingDetailsResponseType> createSaveBookingDetailsResponse(SaveBookingDetailsResponseType value) {
        return new JAXBElement<SaveBookingDetailsResponseType>(_SaveBookingDetailsResponse_QNAME, SaveBookingDetailsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingDetailsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookingDetailsResponse")
    public JAXBElement<FindBookingDetailsResponseType> createFindBookingDetailsResponse(FindBookingDetailsResponseType value) {
        return new JAXBElement<FindBookingDetailsResponseType>(_FindBookingDetailsResponse_QNAME, FindBookingDetailsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAWBDetailsForFlightRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "GetAWBDetailsForFlightRequest")
    public JAXBElement<GetAWBDetailsForFlightRequestType> createGetAWBDetailsForFlightRequest(GetAWBDetailsForFlightRequestType value) {
        return new JAXBElement<GetAWBDetailsForFlightRequestType>(_GetAWBDetailsForFlightRequest_QNAME, GetAWBDetailsForFlightRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBookingProfileResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "GetBookingProfileResponse")
    public JAXBElement<GetBookingProfileResponseType> createGetBookingProfileResponse(GetBookingProfileResponseType value) {
        return new JAXBElement<GetBookingProfileResponseType>(_GetBookingProfileResponse_QNAME, GetBookingProfileResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListFlightInformationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "ListFlightInformationResponse")
    public JAXBElement<ListFlightInformationResponseType> createListFlightInformationResponse(ListFlightInformationResponseType value) {
        return new JAXBElement<ListFlightInformationResponseType>(_ListFlightInformationResponse_QNAME, ListFlightInformationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingEnquiryDetailsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookingEnquiryDetailsResponse")
    public JAXBElement<FindBookingEnquiryDetailsResponseType> createFindBookingEnquiryDetailsResponse(FindBookingEnquiryDetailsResponseType value) {
        return new JAXBElement<FindBookingEnquiryDetailsResponseType>(_FindBookingEnquiryDetailsResponse_QNAME, FindBookingEnquiryDetailsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingHistoryRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookingHistoryRequest")
    public JAXBElement<FindBookingHistoryRequestType> createFindBookingHistoryRequest(FindBookingHistoryRequestType value) {
        return new JAXBElement<FindBookingHistoryRequestType>(_FindBookingHistoryRequest_QNAME, FindBookingHistoryRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAWBDetailsForFlightResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "GetAWBDetailsForFlightResponse")
    public JAXBElement<GetAWBDetailsForFlightResponseType> createGetAWBDetailsForFlightResponse(GetAWBDetailsForFlightResponseType value) {
        return new JAXBElement<GetAWBDetailsForFlightResponseType>(_GetAWBDetailsForFlightResponse_QNAME, GetAWBDetailsForFlightResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingTemplatesResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookingTemplatesResponse")
    public JAXBElement<FindBookingTemplatesResponseType> createFindBookingTemplatesResponse(FindBookingTemplatesResponseType value) {
        return new JAXBElement<FindBookingTemplatesResponseType>(_FindBookingTemplatesResponse_QNAME, FindBookingTemplatesResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingTemplatesRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookingTemplatesRequest")
    public JAXBElement<FindBookingTemplatesRequestType> createFindBookingTemplatesRequest(FindBookingTemplatesRequestType value) {
        return new JAXBElement<FindBookingTemplatesRequestType>(_FindBookingTemplatesRequest_QNAME, FindBookingTemplatesRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComputeVolumeFromDimensionsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "ComputeVolumeFromDimensionsRequest")
    public JAXBElement<ComputeVolumeFromDimensionsRequestType> createComputeVolumeFromDimensionsRequest(ComputeVolumeFromDimensionsRequestType value) {
        return new JAXBElement<ComputeVolumeFromDimensionsRequestType>(_ComputeVolumeFromDimensionsRequest_QNAME, ComputeVolumeFromDimensionsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveBookingTemplateResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "SaveBookingTemplateResponse")
    public JAXBElement<SaveBookingTemplateResponseType> createSaveBookingTemplateResponse(SaveBookingTemplateResponseType value) {
        return new JAXBElement<SaveBookingTemplateResponseType>(_SaveBookingTemplateResponse_QNAME, SaveBookingTemplateResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateBookingResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "ValidateBookingResponse")
    public JAXBElement<ValidateBookingResponseType> createValidateBookingResponse(ValidateBookingResponseType value) {
        return new JAXBElement<ValidateBookingResponseType>(_ValidateBookingResponse_QNAME, ValidateBookingResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveBookingEnquiryDetailsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "SaveBookingEnquiryDetailsRequest")
    public JAXBElement<SaveBookingEnquiryDetailsRequestType> createSaveBookingEnquiryDetailsRequest(SaveBookingEnquiryDetailsRequestType value) {
        return new JAXBElement<SaveBookingEnquiryDetailsRequestType>(_SaveBookingEnquiryDetailsRequest_QNAME, SaveBookingEnquiryDetailsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveBookingEnquiryDetailsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "SaveBookingEnquiryDetailsResponse")
    public JAXBElement<SaveBookingEnquiryDetailsResponseType> createSaveBookingEnquiryDetailsResponse(SaveBookingEnquiryDetailsResponseType value) {
        return new JAXBElement<SaveBookingEnquiryDetailsResponseType>(_SaveBookingEnquiryDetailsResponse_QNAME, SaveBookingEnquiryDetailsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingsAWBDetailsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookingsAWBDetailsRequest")
    public JAXBElement<FindBookingsAWBDetailsRequestType> createFindBookingsAWBDetailsRequest(FindBookingsAWBDetailsRequestType value) {
        return new JAXBElement<FindBookingsAWBDetailsRequestType>(_FindBookingsAWBDetailsRequest_QNAME, FindBookingsAWBDetailsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingDetailsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookingDetailsRequest")
    public JAXBElement<FindBookingDetailsRequestType> createFindBookingDetailsRequest(FindBookingDetailsRequestType value) {
        return new JAXBElement<FindBookingDetailsRequestType>(_FindBookingDetailsRequest_QNAME, FindBookingDetailsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListBookingDetailsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "ListBookingDetailsResponse")
    public JAXBElement<ListBookingDetailsResponseType> createListBookingDetailsResponse(ListBookingDetailsResponseType value) {
        return new JAXBElement<ListBookingDetailsResponseType>(_ListBookingDetailsResponse_QNAME, ListBookingDetailsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListBookingProfilesResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "ListBookingProfilesResponse")
    public JAXBElement<ListBookingProfilesResponseType> createListBookingProfilesResponse(ListBookingProfilesResponseType value) {
        return new JAXBElement<ListBookingProfilesResponseType>(_ListBookingProfilesResponse_QNAME, ListBookingProfilesResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveBookingTemplateRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "SaveBookingTemplateRequest")
    public JAXBElement<SaveBookingTemplateRequestType> createSaveBookingTemplateRequest(SaveBookingTemplateRequestType value) {
        return new JAXBElement<SaveBookingTemplateRequestType>(_SaveBookingTemplateRequest_QNAME, SaveBookingTemplateRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingsAWBDetailsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindBookingsAWBDetailsResponse")
    public JAXBElement<FindBookingsAWBDetailsResponseType> createFindBookingsAWBDetailsResponse(FindBookingsAWBDetailsResponseType value) {
        return new JAXBElement<FindBookingsAWBDetailsResponseType>(_FindBookingsAWBDetailsResponse_QNAME, FindBookingsAWBDetailsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindFlightsForBookingResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "FindFlightsForBookingResponse")
    public JAXBElement<FindFlightsForBookingResponseType> createFindFlightsForBookingResponse(FindFlightsForBookingResponseType value) {
        return new JAXBElement<FindFlightsForBookingResponseType>(_FindFlightsForBookingResponse_QNAME, FindFlightsForBookingResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveBookingDetailsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "SaveBookingDetailsRequest")
    public JAXBElement<SaveBookingDetailsRequest> createSaveBookingDetailsRequest(SaveBookingDetailsRequest value) {
        return new JAXBElement<SaveBookingDetailsRequest>(_SaveBookingDetailsRequest_QNAME, SaveBookingDetailsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComputeVolumeFromDimensionsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services/CapacityBookingService/2013/06/04_01", name = "ComputeVolumeFromDimensionsResponse")
    public JAXBElement<ComputeVolumeFromDimensionsResponseType> createComputeVolumeFromDimensionsResponse(ComputeVolumeFromDimensionsResponseType value) {
        return new JAXBElement<ComputeVolumeFromDimensionsResponseType>(_ComputeVolumeFromDimensionsResponse_QNAME, ComputeVolumeFromDimensionsResponseType.class, null, value);
    }

}
