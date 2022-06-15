package com.ngen.cosys.ulms.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ulms.model.ReleaseULDDetail;

import java.util.List;

public interface ReleaseULDToAgentDao {
    ReleaseULDDetail getCustomerDetail(ReleaseULDDetail releaseULDDetail) throws CustomException;
    List<ReleaseULDDetail> getReleaseUldList(ReleaseULDDetail releaseULDDetail) throws CustomException;
}
