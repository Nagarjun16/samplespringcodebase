package com.ngen.cosys.message.ndf.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.ndf.model.NdfMessageModel;

public interface NdfMessageJobDao {
List<NdfMessageModel> getNdfMessageDefinition() throws CustomException;
String fetchOriginatorAddress() throws CustomException;
BigInteger fetchPreviousDaystoTrigger()throws CustomException;
}
