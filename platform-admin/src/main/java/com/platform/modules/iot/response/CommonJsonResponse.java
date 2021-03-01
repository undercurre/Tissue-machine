package com.platform.modules.iot.response;

import com.platform.modules.iot.Constants;
import com.platform.modules.iot.IoTGatewayResponse;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;



@SuppressWarnings("serial")
public class CommonJsonResponse extends IoTGatewayResponse {

	private Map<String, Object> data;

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	} 
	
    @SuppressWarnings({"rawtypes", "unchecked"})
	public Boolean isSuccess() {
		if(isSuccess != null){
			return isSuccess;
		}
		if(data == null){
			return false;
		}
		if(data.containsKey(Constants.ERROR_CODE)){
			return isSuccess = false;
		}
		Iterator itr = data.entrySet().iterator();
		Entry<String, Object> entry = null;
		String key = null;
		while(itr.hasNext()){
			entry = (Entry<String, Object>) itr.next();
			key = entry.getKey().toLowerCase();
			if(key.equals("respcode") || key.equals("rspcode")){
				if(Integer.valueOf(((String)entry.getValue())).equals(0)){
					return isSuccess = true;
				}else{
					return isSuccess = false;
				}
			}
		}
		return isSuccess = true;
	}


}
