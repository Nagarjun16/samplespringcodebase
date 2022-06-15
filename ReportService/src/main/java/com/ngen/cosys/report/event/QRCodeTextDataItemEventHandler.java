/**
 * @copyright SATS Singapore 2019-20
 */
package com.ngen.cosys.report.event;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.eventadapter.ImageEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IImageInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeTextDataItemEventHandler extends ImageEventAdapter {
	public static final String PNG = "PNG";
	private static final Logger logger = LoggerFactory.getLogger(QRCodeTextDataItemEventHandler.class);
	private static final String EXCEPTION = "Exception Happened ... ";

	/**
	 * @see org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter#onRender(org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance,
	 *      org.eclipse.birt.report.engine.api.script.IReportContext)
	 */
	@Override
	public void onRender(IImageInstance image, IReportContext reportContext) throws ScriptException {
		try {
			String data = "";
			if (Objects.nonNull(image.getNamedExpressionValue("QRCode"))) {
				data = image.getNamedExpressionValue("QRCode").toString();
				if (data.contains(" ")) {
					String[] dataArray = data.split(" ");
					for (String item : dataArray) {
						if (Objects.isNull(item)) {
							data = "";
							break;
						}
					}
				}
			}

			Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();

			hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			byte[] qrCode;
			if (!data.isEmpty()) {
				qrCode = createQR(data, 3000, 3000);
				image.setData(qrCode);
			} else {
				super.onRender(image, reportContext);
			}

		}

		catch (WriterException wr) {
			logger.error(EXCEPTION, wr);
		} catch (IOException io) {
			logger.error(EXCEPTION, io);
		}
	}

	public static byte[] createQR(String data, int height, int width)
			throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(data,
				BarcodeFormat.QR_CODE, width, height);

		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(matrix, PNG, pngOutputStream);
		return pngOutputStream.toByteArray();
	}

}
