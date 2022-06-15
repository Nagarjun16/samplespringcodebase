/**
 * This is a repository implementation component for capturing break down
 * tonnage summary information
 */
package com.ngen.cosys.impbd.summary.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.summary.constant.BreakDownSummarySqlId;
import com.ngen.cosys.impbd.summary.model.BreakDownSummary;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryModel;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryTonnageHandledModel;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryUldModel;
import com.ngen.cosys.impbd.summary.model.Email;

@Repository
public class BreakDownSummaryDAOImpl extends BaseDAO implements BreakDownSummaryDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.summary.dao.BreakDownSummaryDAO#get(com.ngen.cosys.impbd
	 * .summary.model.BreakDownSummaryModel)
	 */
	@Override
	public BreakDownSummaryModel get(BreakDownSummaryModel requestModel) throws CustomException {
		return this.fetchObject(BreakDownSummarySqlId.SQL_GET_BREAK_DOWN_SUMMARY_INFO.toString(), requestModel,
				sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.summary.dao.BreakDownSummaryDAO#
	 * getInboundBreakDownTonnageInfo(com.ngen.cosys.impbd.summary.model.
	 * BreakDownSummary)
	 */
	@Override
	public List<BreakDownSummaryUldModel> getInboundBreakDownTonnageInfo(BreakDownSummary requestModel)
			throws CustomException {
		return this.fetchList(BreakDownSummarySqlId.SQL_GET_INBOUND_BREAK_TONNAGE_SUMMARY.toString(), requestModel,
				sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.summary.dao.BreakDownSummaryDAO#
	 * getInboundBreakDownOtherTonnageInfo(com.ngen.cosys.impbd.summary.model.
	 * BreakDownSummary)
	 */
	@Override
	public List<BreakDownSummaryTonnageHandledModel> getInboundBreakDownOtherTonnageInfo(BreakDownSummary requestModel)
			throws CustomException {
		return this.fetchList(BreakDownSummarySqlId.SQL_GET_INBOUND_BREAK_OTHER_TONNAGE_SUMMARY.toString(),
				requestModel, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.summary.dao.BreakDownSummaryDAO#
	 * createBreakDownTonnageSummary(com.ngen.cosys.impbd.summary.model.
	 * BreakDownSummary)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.BREAKDOWN_SUMMARY)
	public void createBreakDownTonnageSummary(BreakDownSummary requestModel) throws CustomException {
		// Check whether break down summary data exists
		BigInteger id = this.fetchObject(BreakDownSummarySqlId.SQL_GET_IMP_BREAK_DOWN_SUMMARY_ID.toString(),
				requestModel, sqlSessionTemplate);

		if (!ObjectUtils.isEmpty(id)) {
			// Update if exists
			requestModel.setId(id);
			this.updateData(BreakDownSummarySqlId.SQL_UPDATE_IMP_BREAK_DOWN_SUMMARY.toString(), requestModel,
					sqlSessionTemplate);
		} else {
			// Create summary data
			this.insertData(BreakDownSummarySqlId.SQL_CREATE_IMP_BREAK_DOWN_SUMMARY.toString(), requestModel,
					sqlSessionTemplate);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.summary.dao.BreakDownSummaryDAO#
	 * createBreakDownTonnageULDTrolleySummaryInfo(java.util.List)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.BREAKDOWN_SUMMARY)
	public void createBreakDownTonnageULDTrolleySummaryInfo(List<BreakDownSummaryUldModel> requestModel)
			throws CustomException {
		for (BreakDownSummaryUldModel t : requestModel) {
			// Get the ULD Trolley Tonnage Info Id if exists
			if (ObjectUtils.isEmpty(t.getId())) {
				BigInteger id = this.fetchObject(
						BreakDownSummarySqlId.SQL_GET_IMP_BREAK_DOWN_ULD_TROLLEY_SUMMARY_INFO_ID.toString(), t,
						sqlSessionTemplate);

				// Set the key
				t.setId(id);

				// Set the key to SHC info
				if (!CollectionUtils.isEmpty(t.getShcTonnageInfo())) {
					t.getShcTonnageInfo().forEach(t1 -> t1.setReferenceId(t.getId()));
				}
			}

			if (ObjectUtils.isEmpty(t.getId())) {
				this.insertData(BreakDownSummarySqlId.SQL_CREATE_IMP_BREAK_DOWN_ULD_TROLLEY_SUMMARY_INFO.toString(), t,
						sqlSessionTemplate);

				// Delete the SHC info and re-insert
				this.deleteData(BreakDownSummarySqlId.SQL_DELETE_IMP_BREAK_DOWN_ULD_TROLLEY_SHC_SUMMARY_INFO.toString(),
						t, sqlSessionTemplate);

				// Insert the break down tonnage SHC info
				if (!CollectionUtils.isEmpty(t.getShcTonnageInfo())) {
					t.getShcTonnageInfo().forEach(t1 -> t1.setReferenceId(t.getId()));
					this.insertData(
							BreakDownSummarySqlId.SQL_CREATE_IMP_BREAK_DOWN_ULD_TROLLEY_SHC_SUMMARY_INFO.toString(),
							t.getShcTonnageInfo(), sqlSessionTemplate);
				}

			} else {

				if (!StringUtils.isEmpty(requestModel.get(0).getRequestedFrom())) {
					this.updateData(BreakDownSummarySqlId.SQL_UPDATE_IMP_BREAK_DOWN_ULD_TROLLEY_SUMMARY_INFO.toString(),
							t, sqlSessionTemplate);
				} else {
					this.updateData("sqlUpdateBreakDownTonnageULDTrolleySummaryInfoOnFlightComplete", t,
							sqlSessionTemplate);
				}

			}
		}
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.BREAKDOWN_SUMMARY)
	public void createBreakDownOtherTonnageSummaryInfo(List<BreakDownSummaryTonnageHandledModel> requestModel)
			throws CustomException {
		// Iterate each object and perform CRUD operation
		for (BreakDownSummaryTonnageHandledModel t : requestModel) {
			if ("D".equalsIgnoreCase(t.getFlagCRUD())) {
				this.deleteData(BreakDownSummarySqlId.SQL_DELETE_IMP_BREAK_DOWN_TONNAGE_SUMMARY_INFO.toString(), t,
						sqlSessionTemplate);
			} else {

				Integer recordUpdateCount = this.updateData(
						BreakDownSummarySqlId.SQL_UPDATE_IMP_BREAK_DOWN_TONNAGE_SUMMARY_INFO.toString(), t,
						sqlSessionTemplate);

				if (ObjectUtils.isEmpty(recordUpdateCount)
						|| (!ObjectUtils.isEmpty(recordUpdateCount) && recordUpdateCount.intValue() == 0)) {
					this.insertData(BreakDownSummarySqlId.SQL_CREATE_IMP_BREAK_DOWN_TONNAGE_SUMMARY_INFO.toString(), t,
							sqlSessionTemplate);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.summary.dao.BreakDownSummaryDAO#updateFeedBack(com.ngen.
	 * cosys.impbd.summary.model.BreakDownSummary)
	 */
	@Override
	public void updateFeedBack(BreakDownSummary summaryData) throws CustomException {
		updateData(BreakDownSummarySqlId.SQL_UPDATE_SERVICE_CONTRACTOR_FEEDBACK.toString(), summaryData,
				sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.summary.dao.BreakDownSummaryDAO#clearExistingSummaryInfo
	 * (com.ngen.cosys.impbd.summary.model.BreakDownSummary)
	 */
	@Override
	public void clearExistingSummaryInfo(BreakDownSummary requestModel) throws CustomException {
		this.deleteData(BreakDownSummarySqlId.SQL_DELETE_ALL_IMP_BREAK_DOWN_ULD_TROLLEY_SHC_SUMMARY_INFO.toString(),
				requestModel, sqlSessionTemplate);
		this.deleteData(BreakDownSummarySqlId.SQL_DELETE_ALL_IMP_BREAK_DOWN_TONNAGE_SUMMARY_INFO.toString(),
				requestModel, sqlSessionTemplate);
		this.deleteData(BreakDownSummarySqlId.SQL_DELETE_IMP_BREAK_DOWN_ULD_TROLLEY_SUMMARY_INFO.toString(),
				requestModel, sqlSessionTemplate);
	}

	@Override
	public boolean checkForCargoTypeExistsOrNot(BreakDownSummaryTonnageHandledModel requestModel)
			throws CustomException {
		return this.fetchObject("sqlCheckForCargoTypeExistsOrNot", requestModel, sqlSessionTemplate);

	}

	@Override
	public List<Email> fetchEmails(String groupName) throws CustomException {
		return this.fetchList("getEmailDetails", groupName, sqlSessionTemplate);
	}

	@Override
	public String getServiceContractorName(String serviceContractor) throws CustomException {
		return this.fetchObject("fetchContractorName", serviceContractor, sqlSessionTemplate);
	}

}