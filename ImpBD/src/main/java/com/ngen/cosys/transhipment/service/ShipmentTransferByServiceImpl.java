package com.ngen.cosys.transhipment.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.transhipment.dao.ShipmentTransferByCarrierDAO;
import com.ngen.cosys.transhipment.model.TransferByCarrier;
import com.ngen.cosys.transhipment.model.TransferByCarrierSearch;

@Service
public class ShipmentTransferByServiceImpl implements ShipmentTransferByCarrierService {

	@Autowired
	ShipmentTransferByCarrierDAO dao;

	@Override
	public TransferByCarrierSearch search(TransferByCarrierSearch search) throws CustomException {

		// Set the default from and to date
		search.setFromDate(LocalDate.now().minusMonths(3));
		search.setToDate(LocalDate.now());

		TransferByCarrierSearch result = dao.search(search);

		result.getTransferByCarrierList().stream().forEach(data -> data.setReadyToTransfer(checkReadyToTransfer(data)));
		return result;
	}

	private boolean checkReadyToTransfer(TransferByCarrier data) {
		return ((data.isDocumentReceivedFlag() || data.isPhotoCopyAwbFlag()) && (data
				.getTotalPieces() == (data.getPieces() + data.getIrregularityPieces()) + data.getFreightOutPieces()
				|| (data.getManifestPieces() > 0 && (data.getManifestPieces()
						+ data.getFoundPieces()) == (data.getPieces() + data.getIrregularityPieces())
								+ data.getFreightOutPieces())));
	}

}