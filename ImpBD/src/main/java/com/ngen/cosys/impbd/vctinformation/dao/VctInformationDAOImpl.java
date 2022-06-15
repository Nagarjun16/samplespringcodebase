package com.ngen.cosys.impbd.vctinformation.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.vctinformation.model.VCTInformation;
import com.ngen.cosys.impbd.vctinformation.model.VCTShipmentInformationModel;

@Repository
public class VctInformationDAOImpl extends BaseDAO implements vctInformationDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public VCTInformation getImportExportInfo(VCTInformation requestModel) throws CustomException {

		VCTInformation responseModel = this.fetchObject("getVctInformationList", requestModel, sqlSession);

		return responseModel;
	}

	@Override
	public VCTInformation save(VCTInformation requesModel) throws CustomException {

		if (requesModel.getType().equalsIgnoreCase("Import")) {
			if (requesModel.getVctIn()) {
				this.updateData("saveVctInformationListImportVctIn", requesModel, sqlSession);
			}
			if (requesModel.getVctOut()) {
				this.updateData("saveVctInformationListImportVctOut", requesModel, sqlSession);

			}

		}
		if (requesModel.getType().equalsIgnoreCase("Export") ) {
			if (requesModel.getVctIn()) {
				this.updateData("saveVctInformationListExportVctIn", requesModel, sqlSession);
			}
			if (requesModel.getVctOut()) {
				this.updateData("saveVctInformationListExportVctOut", requesModel, sqlSession);
			}

		}
		return requesModel;
	}

}
