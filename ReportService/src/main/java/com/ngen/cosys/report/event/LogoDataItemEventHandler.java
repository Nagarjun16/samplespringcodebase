package com.ngen.cosys.report.event;

import java.util.Objects;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.eventadapter.ImageEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IImageInstance;

import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.context.TenantRequest;
import com.ngen.cosys.multitenancy.model.TenantThemeLogo;

public class LogoDataItemEventHandler extends ImageEventAdapter {

	@Override
	public void onRender(IImageInstance image, IReportContext reportContext) throws ScriptException {

		TenantRequest tenantRequest = TenantContext.get();
		if (Objects.nonNull(tenantRequest) && Objects.nonNull(tenantRequest.getTenantConfig())
				&& Objects.nonNull(tenantRequest.getTenantConfig().getLogo())) {

			TenantThemeLogo tenantLogo = TenantContext.get().getTenantConfig().getLogo();
			String logo = !StringUtils.isBlank(tenantLogo.getReportLogo()) ? tenantLogo.getReportLogo()
					: tenantLogo.getLogo().split(",")[1];
			if (Objects.nonNull(logo)) {
				byte[] decodedString = Base64.decodeBase64(new String(logo).getBytes());
				reportContext.setParameterValue("Image", logo);
				image.setData(decodedString);
			} else {
				super.onRender(image, reportContext);
			}
		} else {
			super.onRender(image, reportContext);
		}
	}

}
