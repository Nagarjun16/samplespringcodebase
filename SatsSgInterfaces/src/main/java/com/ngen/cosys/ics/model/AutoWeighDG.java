package com.ngen.cosys.ics.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
@Validated
public class AutoWeighDG extends BaseBO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String classCode;
	@NgenAuditField(fieldName=NgenAuditFieldNameType.SHC)
	private String specialHandlingCode;
	private long autoWeighBupHeaderId;
}
