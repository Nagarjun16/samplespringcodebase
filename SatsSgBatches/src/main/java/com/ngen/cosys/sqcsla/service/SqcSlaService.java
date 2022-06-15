package com.ngen.cosys.sqcsla.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.sqcsla.model.Emails;

public interface SqcSlaService {
   
   public List<Emails> getSqcGroupEmail() throws CustomException;
   public List<Emails> getCagGroupEmail() throws CustomException;

}
