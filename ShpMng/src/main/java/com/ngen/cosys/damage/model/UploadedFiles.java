package com.ngen.cosys.damage.model;

import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadedFiles {

	private String fileName;
	private String fileType;
	private BigInteger fileId;
	private String remarks;

}
