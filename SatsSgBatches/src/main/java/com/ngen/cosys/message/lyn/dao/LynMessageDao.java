package com.ngen.cosys.message.lyn.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.lyn.model.LynMessage;

public interface LynMessageDao  {
	public List<LynMessage> getLynMessageDefinition() throws CustomException ;
	
	public List<LynMessage> getFSLMessageDefinition()  throws CustomException ;

}
