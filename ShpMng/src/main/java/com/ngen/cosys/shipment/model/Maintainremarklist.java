package com.ngen.cosys.shipment.model;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_MAINTAIN_REMARK, repository = NgenAuditEventRepository.AWB)
public class Maintainremarklist extends BaseBO {
	
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	@Valid 
	private List<MaintainRemark> maintainremarkdetail;

}
