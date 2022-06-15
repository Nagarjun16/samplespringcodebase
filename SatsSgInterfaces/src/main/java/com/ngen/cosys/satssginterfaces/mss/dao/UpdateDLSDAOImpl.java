package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.DLS;
import com.ngen.cosys.satssginterfaces.mss.model.DLSAccessory;
import com.ngen.cosys.satssginterfaces.mss.model.DLSPiggyBackInfo;
import com.ngen.cosys.satssginterfaces.mss.model.DLSULD;
import com.ngen.cosys.satssginterfaces.mss.model.DLSUldtrolleySHCinfo;
import com.ngen.cosys.satssginterfaces.mss.model.DlsContentCode;

@Repository("UpdateDLSDAO")
public class UpdateDLSDAOImpl extends BaseDAO implements UpdateDLSDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;


	@Override
	public DLS insertUpdateDLS(DLS dls) throws CustomException {
		DLS newdls = getDLS(dls);
		dls.setDlsId(newdls.getDlsId());
		for (DLSULD dlsUld : dls.getUldTrolleyList()) {
			dlsUld.setDlsId(newdls.getDlsId());
			dlsUld.setFlightId(dls.getFlightId());
			updateUldTrolley(dlsUld);
			updateUldPiggyBack(dlsUld);
			if (!CollectionUtils.isEmpty(dlsUld.getShcs())) {
				deleteShc(dlsUld);
				for (DLSUldtrolleySHCinfo shc : dlsUld.getShcs()) {
					shc.setDlsUldTrolleyId(dlsUld.getDlsUldTrolleyId());
					insertData("insertSHCInfo", shc, sqlSession);
				}
			}
			if (!CollectionUtils.isEmpty(dlsUld.getContentCodeList())) {
				deleteContentCode(dlsUld);

				for (DlsContentCode contentcode : dlsUld.getContentCodeList()) {
					contentcode.setDlsUldTrolleyId(dlsUld.getDlsUldTrolleyId());
					insertData("insertContentCode", contentcode, sqlSession);
				}
			}
		}
		return dls;
	}


	
	@Override
    public void insertDLS(DLS dls) throws CustomException {
        insertData("insertDLS", dls, sqlSession);
    }
	
	@Override
    public DLS getDLS(DLS dls) throws CustomException {
        DLS updateDLS = fetchObject("selectDLSBasedId", dls, sqlSession);
        if (ObjectUtils.isEmpty(updateDLS)) {
            insertDLS(dls);
        } else {
            return updateDLS;
        }
        return dls;
    }
	
	private void updateUldTrolley(DLSULD dlsUld) throws CustomException {
       String handlingArea = dlsUld.getHandlingArea();
       int count = updateData("updateDLSULDTrolley", dlsUld, sqlSession);
       if (count == 0) {
           dlsUld.setHandlingArea(handlingArea);
           insertData("insertDLSULDTrolley", dlsUld, sqlSession);
       } else {
           DLSULD newDLSULD = fetchObject("getDLSUldTrolleyId", dlsUld, sqlSession);
           dlsUld.setDlsUldTrolleyId(newDLSULD.getDlsUldTrolleyId());
       }
   }
	

    private void updateUldPiggyBack(DLSULD dlsUld) throws CustomException {
        if (!CollectionUtils.isEmpty(dlsUld.getPiggyBackUldList())) {
            updatePiggybackInfo(dlsUld);
        }
    }
    
    private void updatePiggybackInfo(DLSULD uld) throws CustomException {
       if (!CollectionUtils.isEmpty(uld.getPiggyBackUldList())) {
           for (DLSPiggyBackInfo piggyback : uld.getPiggyBackUldList()) {
               if (Action.CREATE.toString().equalsIgnoreCase(piggyback.getFlagCRUD())) {
                   piggyback.setDlsUldTrolleyId(uld.getDlsUldTrolleyId());
                   insertData("insertPiggyBackedInfo", piggyback, sqlSession);
               } else if (Action.UPDATE.toString().equalsIgnoreCase(piggyback.getFlagCRUD())) {
                   updateData("updatePiggyBackInfo", piggyback, sqlSession);
               } else if (Action.DELETE.toString().equalsIgnoreCase(piggyback.getFlagCRUD())) {
                   deleteData("deletePiggyBackInfo", piggyback, sqlSession);
               }
           }
       }
   }
    
    private void deleteShc(DLSULD uld) throws CustomException {
       ArrayList<DLSULD> uldList = new ArrayList<>(1);
       uldList.add(uld);
       sqlSession.delete("deleteSHCInfoList", uldList);
   }
    
    private void deleteContentCode(DLSULD dls) throws CustomException {
       deleteData("deleteContentCode", dls, sqlSession);
   }
    
    @Override
    public void insertUld(DLSULD uld) throws CustomException {
        updateDLSULDTrolly(uld);
        updateAccessory(uld);
        insertContentCode(uld);
        updatePiggybackInfo(uld);
        updateShcInfo(uld);
    }
    
    private void updateDLSULDTrolly(DLSULD uld) throws CustomException {
       if (Action.CREATE.toString().equalsIgnoreCase(uld.getFlagCRUD())) {
           insertData("insertDLSULDTrolley", uld, sqlSession);
       } else if (Action.UPDATE.toString().equalsIgnoreCase(uld.getFlagCRUD())) {
           updateData("updateDLSULDTrolley", uld, sqlSession);
       } else if (Action.DELETE.toString().equalsIgnoreCase(uld.getFlagCRUD())) {
           deleteData("deleteDLSULDTrolley", uld, sqlSession);
       }
   }
    
    private void updateAccessory(DLSULD uld) throws CustomException {
       if (!CollectionUtils.isEmpty(uld.getAccessoryList())) {
           int i = 1;
           for (DLSAccessory accessory : uld.getAccessoryList()) {
               accessory.setTransactionSequenceNo(BigInteger.valueOf(i));
               if (Action.CREATE.toString().equalsIgnoreCase(accessory.getFlagCRUD())) {
                   accessory.setDlsUldTrolleyId(uld.getDlsUldTrolleyId());
                   insertData("insertAccessoryInfo", accessory, sqlSession);
               } else if (Action.UPDATE.toString().equalsIgnoreCase(accessory.getFlagCRUD())) {
                   updateData("updateAccessoryInfo", accessory, sqlSession);
               } else if (Action.DELETE.toString().equalsIgnoreCase(accessory.getFlagCRUD())) {
                   deleteData("deleteAccessoryInfo", accessory, sqlSession);
               }

               // Increase the transaction sequence no
               i++;
           }
       }
   }
    
    private void insertContentCode(DLSULD dls) throws CustomException {
       for (DlsContentCode dlsContentCode : dls.getContentCodeList()) {
           dlsContentCode.setDlsUldTrolleyId(dls.getDlsUldTrolleyId());
           insertData("insertContentCode", dlsContentCode, sqlSession);
       }
   }
    
    private void updateShcInfo(DLSULD uld) throws CustomException {
       if (!CollectionUtils.isEmpty(uld.getShcs())) {
           for (DLSUldtrolleySHCinfo shc : uld.getShcs()) {
               if (!StringUtils.isEmpty(shc.getSpecialHandlingCode())
                       && Action.CREATE.toString().equalsIgnoreCase(shc.getFlagCRUD())) {
                   shc.setDlsUldTrolleyId(uld.getDlsUldTrolleyId());
                   insertData("insertSHCInfo", shc, sqlSession);
               }
           }
       }
   }
    
    @Override
    public void updateUld(DLSULD uld) throws CustomException {
        updateAccessory(uld);
        updatePiggybackInfo(uld);
        deleteContentCode(uld);
        insertContentCode(uld);
        deleteShc(uld);
        updateShcInfo(uld);
        updateDLSULDTrolly(uld);
    }
    
    
    @Override
    public void deleteUldTrollyList(List<DLSULD> uldList) throws CustomException {
        List<DLSULD> deleteList = uldList.stream()
                .filter(uld -> Action.DELETE.toString().equalsIgnoreCase(uld.getFlagCRUD()))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(deleteList)) {
            sqlSession.delete("deletePiggyBackInfoList", deleteList);
            sqlSession.delete("deleteAccessoryInfoList", deleteList);
            sqlSession.delete("deleteSHCInfoList", deleteList);
            sqlSession.delete("deleteContentCodeList", deleteList);
            sqlSession.delete("deleteRouteList", deleteList);
            sqlSession.delete("deleteDLSULDTrolleyList", deleteList);
        }

    }

}
