/**
* 
 * StockSummary.java
* 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
*
* Version   Date   Author   Reason
* 1.0   29 Jan, 2018   NIIT   -
*/
package com.ngen.cosys.shipment.stockmanagement.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
* Model class for Stock Summary.
* 
 * @author NIIT Technologies Ltd
* @version 1.0
*/
@Component
@Scope(scopeName = "prototype")
@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
public class StockSummary extends BaseBO {
                /**
                * The default serialVersionUID.
                */
                private static final long serialVersionUID = 1L;

                /**
     *This field contains AWB Stock Details ID
     */
                private long awbStockDetailsId;
                
                /**
                *This field contains AWB StockID
                */
                private long awbStockId;
                
                /**
                * This field contains AWB Prefix
                */
                private String awbPrefix;

                /**
                * This field contains AWB Suffix
                */
                private String awbSuffix;
                
                /**
                * This field contains AWBNumber
                */
                private String awbNumber;
                
                
                private String nextAWBNumber;
                private String lastAWBNumber;
                
                /**
                * This field contains ReservedBit
                */
                private BigInteger reserved;
                
                /**
                * This field contains ReservedCount
                */
                private BigInteger reservedCount;

                /**
                * This field contains Reserved By
                */
                private String reservedBy;

                /**
                * This field contains Reserved On
                */
                private LocalDate reservedOn;
                   
                /**
                * This field contains Issued
                */
                private BigInteger issued;
                
                /**
                * This field contains IssuedCount
                */
                private BigInteger issuedCount;
                   
                /**
                * This field contains Issued On
                */
                private LocalDate issuedOn;
                   
                /**
     * This field contains deleted
     */
    private BigInteger deleted;
    
                /**
                * This field contains DeletedCount
                */
                private BigInteger deletedCount;

                /**
                * This field contains Deleted On
                */
                private String deletedOn;
                   
                /**
     * This field contains unused
     */
    private boolean unused;
    
                /**
                * This field contains UnusedCount
                */
                private BigInteger unusedCount;
                   
                /**
                * This field contains Processing
                */
                private LocalDate processing;
                   
    /**
     * This field contains Cancelled
     */
    private LocalDate cancelled;
    
                /**
                * This field contains Booked
                */
                private LocalDate booked;
                   
                /**
                * This field contains Printed
                */
                private LocalDate printed;
                   
                /**
                * This field contains Re-printed
                */
                private LocalDate reprinted;
                   
                /**
                * This field contains Duplicated Flag
                */
                private boolean duplicated;
                   
                /**
                * This field contains Total
                */
                private BigInteger total;
                private String carrierCode;
                private String stockCategoryCode;
                private String stockId;
                private int lowStockLimit;
                private boolean selectULD = false;
                // Flags for Status field
                private boolean flagProcessing;
                private boolean flagBooked;
                private boolean flagCancelled;
                private boolean flagPrinted;
                private boolean flagReprinted;
                
                // SortBy fields for Archive
                private boolean sortByFlag;
                private boolean sortByStockID;
                private boolean sortByCategory;
                private boolean sortByCarrierCode;
                private boolean sortByAwbNumber;
                private boolean soryByTerminalCore;
}
