/**
 * 
 */
package com.ngen.cosys.billing.communityportalinvoiceposting.model;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model used for posting data to community portal
 * @author Shubhi.2.S
 *
 */
@ApiModel
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
public class InvoiceRequest extends BaseBO{
	
	private static final long serialVersionUID = 2893360017372735610L;
	private String invoiceNumber;
	private String invoiceType;
	private String invoiceId;
}
