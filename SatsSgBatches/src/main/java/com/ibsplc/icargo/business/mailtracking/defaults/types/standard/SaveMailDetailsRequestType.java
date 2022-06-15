//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB)
// Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source
// schema.
// Generated on: 2018.06.20 at 10:45:55 AM IST
//

package com.ibsplc.icargo.business.mailtracking.defaults.types.standard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

/**
 * <p>
 * Java class for SaveMailDetailsRequestType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="SaveMailDetailsRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="companyCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hhtVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="scanningPort" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="messagePartId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mailDetails" type="{http://www.ibsplc.com/icargo/services/types/MailTrackingDefaultsService/standard/2012/12/12_01}MailDetailsRequestType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SaveMailDetailsRequestType", propOrder = { "companyCode", "hhtVersion", "scanningPort",
      "messagePartId", "mailDetails" })
@XmlRootElement(name = "saveMailDetailsRequest")
public class SaveMailDetailsRequestType {

   @XmlElement(required = true)
   protected String companyCode;
   @XmlElement(required = true)
   protected String hhtVersion;
   @XmlElement(required = true)
   protected String scanningPort;
   protected int messagePartId;
   @XmlElement(name = "mailDetails")
   @JacksonXmlElementWrapper(useWrapping = false)
   protected List<MailDetailsRequestType> mailDetails = new ArrayList<MailDetailsRequestType>();

   /**
    * Gets the value of the companyCode property.
    * 
    * @return possible object is {@link String }
    * 
    */
   public String getCompanyCode() {
      return companyCode;
   }

   /**
    * Sets the value of the companyCode property.
    * 
    * @param value
    *           allowed object is {@link String }
    * 
    */
   public void setCompanyCode(String value) {
      this.companyCode = value;
   }

   /**
    * Gets the value of the hhtVersion property.
    * 
    * @return possible object is {@link String }
    * 
    */
   public String getHhtVersion() {
      return hhtVersion;
   }

   /**
    * Sets the value of the hhtVersion property.
    * 
    * @param value
    *           allowed object is {@link String }
    * 
    */
   public void setHhtVersion(String value) {
      this.hhtVersion = value;
   }

   /**
    * Gets the value of the scanningPort property.
    * 
    * @return possible object is {@link String }
    * 
    */
   public String getScanningPort() {
      return scanningPort;
   }

   /**
    * Sets the value of the scanningPort property.
    * 
    * @param value
    *           allowed object is {@link String }
    * 
    */
   public void setScanningPort(String value) {
      this.scanningPort = value;
   }

   /**
    * Gets the value of the messagePartId property.
    * 
    */
   public int getMessagePartId() {
      return messagePartId;
   }

   /**
    * Sets the value of the messagePartId property.
    * 
    */
   public void setMessagePartId(int value) {
      this.messagePartId = value;
   }

   /**
    * Gets the value of the mailDetails property.
    * 
    * <p>
    * This accessor method returns a reference to the live list, not a snapshot.
    * Therefore any modification you make to the returned list will be present
    * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
    * for the mailDetails property.
    * 
    * <p>
    * For example, to add a new item, do as follows:
    * 
    * <pre>
    * getMailDetails().add(newItem);
    * </pre>
    * 
    * 
    * <p>
    * Objects of the following type(s) are allowed in the list
    * {@link MailDetailsRequestType }
    * 
    * 
    */
   public List<MailDetailsRequestType> getMailDetails() {
      if (mailDetails == null) {
         mailDetails = new ArrayList<>();
      }
      return this.mailDetails;
   }

   /**
    * @param mailDetails
    */
   public void setMailDetails(List<MailDetailsRequestType> mailDetails) {
      this.mailDetails = mailDetails;
   }

}
