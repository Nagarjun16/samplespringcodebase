package com.ngen.cosys.transhipment.helper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWBInfo;

@Component
public class FindShipmentInventoryLinesForTransfer {

	private static final String COMMA = ",";
	private static final String EMPTY = "";

	/**
	 * Method to get matching inventory line items which does not have TRM issued
	 * 
	 * @param shipments
	 *            - List of inventory line items for transfer
	 * @param bookingPieces
	 *            - Booked Pieces
	 * @return List<TranshipmentTransferManifestByAWBInfo> - Return matching list of
	 *         shipment line items
	 */
	public List<TranshipmentTransferManifestByAWBInfo> findByPiece(
			List<TranshipmentTransferManifestByAWBInfo> shipments, BigInteger transferredPieces) {
		// List of line items
		List<TranshipmentTransferManifestByAWBInfo> consideredLineItems = new ArrayList<>();

		// Check for sum of piece/weight of each line items
		String lineItemIndex = getIndexOfLineItemsForPieceMatch(shipments, transferredPieces);

		if (!ObjectUtils.isEmpty(lineItemIndex)) {
			String[] lineItemIndexSplit = lineItemIndex.split(COMMA);

			for (String x : lineItemIndexSplit) {
				try {
					consideredLineItems.add(shipments.get(Integer.valueOf(x)));
				} catch (Exception e) {
					// Do nothing, suppress this exception
				}
			}
		}
		return consideredLineItems;
	}

	private String getIndexOfLineItemsForPieceMatch(List<TranshipmentTransferManifestByAWBInfo> shipments,
			BigInteger transferredPieces) {

		// 1. Check for single line item
		for (int i = 0; i < shipments.size(); i++) {
			if (shipments.get(i).getPieces() == transferredPieces.intValue()) {
				return i + EMPTY;
			}
		}

		// 2. Check for sum of all line items
		StringBuilder lineItems = new StringBuilder();
		int manifestPieces = 0;
		for (int i = 0; i < shipments.size(); i++) {
			manifestPieces = manifestPieces + shipments.get(i).getPieces();
			if (StringUtils.isEmpty(lineItems.toString())) {
				lineItems.append(EMPTY + i);
			} else {
				lineItems.append(COMMA + i);
			}

			if (manifestPieces == transferredPieces.intValue()) {
				return lineItems.toString();
			}
		}

		// 3. Check the sum of each line item with it's right hand side line items
		for (int i = 0; i < shipments.size(); i++) {

			if (shipments.get(i).getPieces() == transferredPieces.intValue()) {
				return i + EMPTY;
			}

			// start from i'th element till last element
			for (int j = i + 1; j < shipments.size(); j++) {
				// if desired sum is found, print it and return
				if (shipments.get(i).getPieces() + shipments.get(j).getPieces() == transferredPieces.intValue()) {
					return i + COMMA + j;
				}

				// start from i'th element till last element
				for (int k = j + 1; k < shipments.size(); k++) {
					// if desired sum is found, print it and return
					if (shipments.get(i).getPieces() + shipments.get(j).getPieces()
							+ shipments.get(k).getPieces() == transferredPieces.intValue()) {
						return i + COMMA + j + COMMA + k;
					}

					// start from i'th element till last element
					for (int l = k + 1; l < shipments.size(); l++) {
						// if desired sum is found, print it and return
						if (shipments.get(i).getPieces() + shipments.get(j).getPieces() + shipments.get(k).getPieces()
								+ shipments.get(l).getPieces() == transferredPieces.intValue()) {
							return i + COMMA + j + COMMA + k + COMMA + l;
						}

						// start from i'th element till last element
						for (int m = l + 1; m < shipments.size(); m++) {
							// if desired sum is found, print it and return
							if (shipments.get(i).getPieces() + shipments.get(j).getPieces()
									+ shipments.get(k).getPieces() + shipments.get(l).getPieces()
									+ shipments.get(m).getPieces() == transferredPieces.intValue()) {
								return i + COMMA + j + COMMA + k + COMMA + l + COMMA + m;
							}

							// start from i'th element till last element
							for (int n = m + 1; n < shipments.size(); n++) {
								// if desired sum is found, print it and return
								if (shipments.get(i).getPieces() + shipments.get(j).getPieces()
										+ shipments.get(k).getPieces() + shipments.get(l).getPieces()
										+ shipments.get(m).getPieces()
										+ shipments.get(n).getPieces() == transferredPieces.intValue()) {
									return i + COMMA + j + COMMA + k + COMMA + l + COMMA + m + COMMA + n;
								}

								// start from i'th element till last element
								for (int o = n + 1; o < shipments.size(); o++) {
									// if desired sum is found, print it and return
									if (shipments.get(i).getPieces() + shipments.get(j).getPieces()
											+ shipments.get(k).getPieces() + shipments.get(l).getPieces()
											+ shipments.get(m).getPieces() + shipments.get(n).getPieces()
											+ shipments.get(o).getPieces() == transferredPieces.intValue()) {
										return i + COMMA + j + COMMA + k + COMMA + l + COMMA + m + COMMA + n + COMMA
												+ o;
									}

									// start from i'th element till last element
									for (int p = o + 1; p < shipments.size(); p++) {
										// if desired sum is found, print it and return
										if (shipments.get(i).getPieces() + shipments.get(j).getPieces()
												+ shipments.get(k).getPieces() + shipments.get(l).getPieces()
												+ shipments.get(m).getPieces() + shipments.get(n).getPieces()
												+ shipments.get(o).getPieces()
												+ shipments.get(p).getPieces() == transferredPieces.intValue()) {
											return i + COMMA + j + COMMA + k + COMMA + l + COMMA + m + COMMA + n + COMMA
													+ o + COMMA + p;
										}

										// start from i'th element till last element
										for (int q = p + 1; q < shipments.size(); q++) {
											// if desired sum is found, print it and return
											if (shipments.get(i).getPieces() + shipments.get(j).getPieces()
													+ shipments.get(k).getPieces() + shipments.get(l).getPieces()
													+ shipments.get(m).getPieces() + shipments.get(n).getPieces()
													+ shipments.get(o).getPieces() + shipments.get(p).getPieces()
													+ shipments.get(q).getPieces() == transferredPieces.intValue()) {
												return i + COMMA + j + COMMA + k + COMMA + l + COMMA + m + COMMA + n
														+ COMMA + o + COMMA + p + COMMA + q;
											}

											// start from i'th element till last element
											for (int r = q + 1; r < shipments.size(); r++) {
												// if desired sum is found, print it and return
												if (shipments.get(i).getPieces() + shipments.get(j).getPieces()
														+ shipments.get(k).getPieces() + shipments.get(l).getPieces()
														+ shipments.get(m).getPieces() + shipments.get(n).getPieces()
														+ shipments.get(o).getPieces() + shipments.get(p).getPieces()
														+ shipments.get(q).getPieces()
														+ shipments.get(r).getPieces() == transferredPieces
																.intValue()) {
													return i + COMMA + j + COMMA + k + COMMA + l + COMMA + m + COMMA + n
															+ COMMA + o + COMMA + p + COMMA + q + COMMA + r;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return EMPTY;
	}

}
