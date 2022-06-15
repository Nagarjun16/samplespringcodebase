package com.ngen.cosys.ulms.dao;


import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.model.HeaderInfo;
import org.apache.ibatis.annotations.Mapper;

public interface ULMSAuthorityDao {
  Integer  checkClientValid(HeaderInfo headerInfo) throws CustomException;
}
