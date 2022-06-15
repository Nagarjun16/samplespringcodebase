package com.ngen.cosys.impbd.model;

import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author NIIT Technologies Ltd.
 *
 */
@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@NgenAudit(entityFieldName = "flight", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT, repository = NgenAuditEventRepository.FLIGHT)
public class RampCheckInModel extends BaseBO {

	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Valid
	private List<RampCheckInUld> uldList;

	private String uldManifested;

	private String uldReceived;

	private String trollyReceived;

}
