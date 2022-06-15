
package com.ngen.cosys.icms.schema.bookingapi.framework.services.jaxws.types.common;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ibsplc.icargo.framework.services.jaxws.types.common package. 
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

    private final static QName _SecurityHeader_QNAME = new QName("http://www.ibsplc.com/icargo/services", "SecurityHeader");
    private final static QName _LoginHeader_QNAME = new QName("http://www.ibsplc.com/icargo/services", "LoginHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ibsplc.icargo.framework.services.jaxws.types.common
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InvalidRequestFaultDetail }
     * 
     */
    public InvalidRequestFaultDetail createInvalidRequestFaultDetail() {
        return new InvalidRequestFaultDetail();
    }

    /**
     * Create an instance of {@link ServiceFaultDetail }
     * 
     */
    public ServiceFaultDetail createServiceFaultDetail() {
        return new ServiceFaultDetail();
    }

    /**
     * Create an instance of {@link SecurityHeaderType }
     * 
     */
    public SecurityHeaderType createSecurityHeaderType() {
        return new SecurityHeaderType();
    }

    /**
     * Create an instance of {@link LoginHeaderType }
     * 
     */
    public LoginHeaderType createLoginHeaderType() {
        return new LoginHeaderType();
    }

    /**
     * Create an instance of {@link PageType }
     * 
     */
    public PageType createPageType() {
        return new PageType();
    }

    /**
     * Create an instance of {@link PageFilter }
     * 
     */
    public PageFilter createPageFilter() {
        return new PageFilter();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SecurityHeaderType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services", name = "SecurityHeader")
    public JAXBElement<SecurityHeaderType> createSecurityHeader(SecurityHeaderType value) {
        return new JAXBElement<SecurityHeaderType>(_SecurityHeader_QNAME, SecurityHeaderType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginHeaderType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ibsplc.com/icargo/services", name = "LoginHeader")
    public JAXBElement<LoginHeaderType> createLoginHeader(LoginHeaderType value) {
        return new JAXBElement<LoginHeaderType>(_LoginHeader_QNAME, LoginHeaderType.class, null, value);
    }

}
