package com.employee.service.responsedto;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ApiResponseEntity {

	public static Map<String,Object> getListStringObject(Object ob1)
	{
		Map<String,Object> responseEntity = new LinkedHashMap<>();
		
		responseEntity.put("status", HttpStatus.OK);
		responseEntity.put("data", ob1);
		
		return responseEntity;
	}
	
	public static Map<String,Object> getStringObject(String message,Object ob1)
	{
		Map<String,Object> responseEntity = new LinkedHashMap<>();
		
		responseEntity.put("status", HttpStatus.OK);
		responseEntity.put("message", message);
		responseEntity.put("data", ob1);
		
		return responseEntity;
	}
}
