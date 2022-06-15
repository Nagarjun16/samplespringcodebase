package com.ngen.cosys.mesage.lyn.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.lyn.model.LynMessage;

public interface LynMessageService {
	public List<LynMessage> getLynMessageDefinition() throws CustomException;

}
