package com.ngen.cosys.message.ndf.model;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NdfMessageModel extends BaseBO {
/**NDF message model for schedular
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String handlingArea;
	private List<String> telexAddress;
	private BigInteger interfaceMessageTelexAddressGroupId;
}
