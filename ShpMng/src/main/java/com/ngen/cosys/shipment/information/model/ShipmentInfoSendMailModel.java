package com.ngen.cosys.shipment.information.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.damage.model.FileUpload;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Getter
@Setter
@NoArgsConstructor
public class ShipmentInfoSendMailModel extends BaseBO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	List<FileUpload> uploadFilesList;
	private String emailTo;
	private String shipmentNumber;

}
