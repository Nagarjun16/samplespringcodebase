
package com.ngen.cosys.icms.schema.bookingapi.framework.services.jaxws.types.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.BookingsAWBDetailsByPage;
import com.ngen.cosys.icms.schema.bookingapi.business.operations.shipment.types.standard.HouseDetailsByPage;


/**
 * <p>Java class for pageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="totalRecordCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pageSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="startIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="endIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lastPageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="hasNextPage" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pageType", propOrder = {
    "pageNumber",
    "totalRecordCount",
    "pageSize",
    "startIndex",
    "endIndex",
    "lastPageNumber",
    "hasNextPage"
})
@XmlSeeAlso({
    BookingsAWBDetailsByPage.class,
    HouseDetailsByPage.class
})
public class PageType {

    protected int pageNumber;
    protected int totalRecordCount;
    protected int pageSize;
    protected int startIndex;
    protected int endIndex;
    protected int lastPageNumber;
    protected boolean hasNextPage;

    /**
     * Gets the value of the pageNumber property.
     * 
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the value of the pageNumber property.
     * 
     */
    public void setPageNumber(int value) {
        this.pageNumber = value;
    }

    /**
     * Gets the value of the totalRecordCount property.
     * 
     */
    public int getTotalRecordCount() {
        return totalRecordCount;
    }

    /**
     * Sets the value of the totalRecordCount property.
     * 
     */
    public void setTotalRecordCount(int value) {
        this.totalRecordCount = value;
    }

    /**
     * Gets the value of the pageSize property.
     * 
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets the value of the pageSize property.
     * 
     */
    public void setPageSize(int value) {
        this.pageSize = value;
    }

    /**
     * Gets the value of the startIndex property.
     * 
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * Sets the value of the startIndex property.
     * 
     */
    public void setStartIndex(int value) {
        this.startIndex = value;
    }

    /**
     * Gets the value of the endIndex property.
     * 
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Sets the value of the endIndex property.
     * 
     */
    public void setEndIndex(int value) {
        this.endIndex = value;
    }

    /**
     * Gets the value of the lastPageNumber property.
     * 
     */
    public int getLastPageNumber() {
        return lastPageNumber;
    }

    /**
     * Sets the value of the lastPageNumber property.
     * 
     */
    public void setLastPageNumber(int value) {
        this.lastPageNumber = value;
    }

    /**
     * Gets the value of the hasNextPage property.
     * 
     */
    public boolean isHasNextPage() {
        return hasNextPage;
    }

    /**
     * Sets the value of the hasNextPage property.
     * 
     */
    public void setHasNextPage(boolean value) {
        this.hasNextPage = value;
    }

}
