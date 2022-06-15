package com.ngen.cosys.impbd.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ApiModel
@XmlRootElement
@Component
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Scope("prototype")
public class Printer extends BaseBO {
	
	private static final long serialVersionUID = 1L;

	private String ipAddress;
	private String portNo;
	private String pouchId;
	private String flightNo;
	private String flightDate;
	private String offPoint;
	private String locName;
	private String awbNumBarcode;
	private String awbNumTextCode;
	private String pouchLbl;
	private String printerName;
	private boolean photoCopy;
}
