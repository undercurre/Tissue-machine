package com.platform.modules.iot.request;

import com.platform.modules.iot.ApiRuleException;
import com.platform.modules.iot.BaseIoTGatewayRequest;
import com.platform.modules.iot.internal.utils.ApaasSignUtil;
import com.platform.modules.iot.internal.utils.json.JSONWriter;
import com.platform.modules.iot.response.CommonJsonResponse;

import java.util.Map;



public class CommonJsonRequest extends BaseIoTGatewayRequest<CommonJsonResponse> {
	
	private Map<String, Object> params;

	@Override
	public Class<CommonJsonResponse> getResponseClass() {
		return CommonJsonResponse.class;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	@SuppressWarnings("unchecked")
    @Override
	public void execProcessBeforeReqSend(Object[] params) {
		Map<String, Object> map = (Map<String, Object>) params[0];
		this.setTransId((String) map.get(ApaasSignUtil._trans_id));
		this.setReqText(new JSONWriter().write(map));
	}
}
