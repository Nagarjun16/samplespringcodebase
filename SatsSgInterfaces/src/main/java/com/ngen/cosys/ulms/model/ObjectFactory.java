//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.04.29 at 04:11:16 PM SGT 
//


package com.ngen.cosys.ulms.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ngen.cosys.ulms.model package. 
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

    private final static QName _AuthenticationHeader_QNAME = new QName("http://www.sats.com.sg/uldAssociationWebservice", "AuthenticationHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ngen.cosys.ulms.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FlightAssignedULDRequest }
     * 
     */
    public FlightAssignedULDRequest createFlightAssignedULDRequest() {
        return new FlightAssignedULDRequest();
    }

    /**
     * Create an instance of {@link FlightAssignedULDResponse }
     * 
     */
    public FlightAssignedULDResponse createFlightAssignedULDResponse() {
        return new FlightAssignedULDResponse();
    }

    /**
     * Create an instance of {@link ULDDetail }
     * 
     */
    public ULDDetail createULDDetail() {
        return new ULDDetail();
    }

    /**
     * Create an instance of {@link FlightULDPDAssociationRequest }
     * 
     */
    public FlightULDPDAssociationRequest createFlightULDPDAssociationRequest() {
        return new FlightULDPDAssociationRequest();
    }

    /**
     * Create an instance of {@link FlightULDPDAssociationResponse }
     * 
     */
    public FlightULDPDAssociationResponse createFlightULDPDAssociationResponse() {
        return new FlightULDPDAssociationResponse();
    }

    /**
     * Create an instance of {@link FlightAssignedULDChangesRequest }
     * 
     */
    public FlightAssignedULDChangesRequest createFlightAssignedULDChangesRequest() {
        return new FlightAssignedULDChangesRequest();
    }

    /**
     * Create an instance of {@link FlightAssignedULDChangesResponse }
     * 
     */
    public FlightAssignedULDChangesResponse createFlightAssignedULDChangesResponse() {
        return new FlightAssignedULDChangesResponse();
    }

    /**
     * Create an instance of {@link FlightChangeDetail }
     * 
     */
    public FlightChangeDetail createFlightChangeDetail() {
        return new FlightChangeDetail();
    }

    /**
     * Create an instance of {@link AuthenticationHeader }
     * 
     */
    public AuthenticationHeader createAuthenticationHeader() {
        return new AuthenticationHeader();
    }

    /**
     * Create an instance of {@link ULDReleaseToAgentRequest }
     * 
     */
    public ULDReleaseToAgentRequest createULDReleaseToAgentRequest() {
        return new ULDReleaseToAgentRequest();
    }

    /**
     * Create an instance of {@link ULDReleaseToAgentResponse }
     * 
     */
    public ULDReleaseToAgentResponse createULDReleaseToAgentResponse() {
        return new ULDReleaseToAgentResponse();
    }

    /**
     * Create an instance of {@link AgentDetail }
     * 
     */
    public AgentDetail createAgentDetail() {
        return new AgentDetail();
    }

    /**
     * Create an instance of {@link ULD }
     * 
     */
    public ULD createULD() {
        return new ULD();
    }

    /**
     * Create an instance of {@link ServiceStatus }
     * 
     */
    public ServiceStatus createServiceStatus() {
        return new ServiceStatus();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticationHeader }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AuthenticationHeader }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.sats.com.sg/uldAssociationWebservice", name = "AuthenticationHeader")
    public JAXBElement<AuthenticationHeader> createAuthenticationHeader(AuthenticationHeader value) {
        return new JAXBElement<AuthenticationHeader>(_AuthenticationHeader_QNAME, AuthenticationHeader.class, null, value);
    }

}