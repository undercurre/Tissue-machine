package com.platform.modules.iot;

public abstract class BaseIoTGatewayRequest<T extends IoTGatewayResponse> implements IoTGatewayRequest<T> {

	protected String apiName; 
	protected String apiVer; 
	protected String apiType;
	protected String reqText;
	protected String transId;
	
	public String getContentType(){
		if(apiType == null){
			return null;
		}
		if(apiType.equals(Constants.API_TYPE_JSON)){
			return "application/json";
		}else{
			return "text/xml";
		}
	}
	
	public String getTransId() {
        return transId;
    }

    public void setTransId(String transId){
        this.transId = transId;
    }

    public String getApiName() {
		return apiName;
	}
    
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	
	public String getApiVer() {
		return apiVer;
	}
	
	public void setApiVer(String apiVer) {
		this.apiVer = apiVer;
	}
	
	public String getApiType() {
		return apiType;
	}
	
	public void setApiType(String apiType) {
		this.apiType = apiType;
	}
	
	public String getReqText() {
		return reqText;
	}
	
	public void setReqText(String reqText) {
		this.reqText = reqText;
	}
}
