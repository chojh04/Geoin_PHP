package kr.co.kpcard.messenger.app.components;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestControllerAdvice
public class ResponseJsonFilterAdvice implements ResponseBodyAdvice<Object>{

	private static final Logger logger = LoggerFactory.getLogger(ResponseJsonFilterAdvice.class);
	
	@Autowired
	private ObjectMapper objectMapper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object beforeBodyWrite(Object object, MethodParameter arg1,
			MediaType arg2, Class arg3, ServerHttpRequest arg4,
			ServerHttpResponse arg5) {
		// TODO Auto-generated method stub
		logger.debug("===========> JsonFilterAdvice : beforeBodyWrite ");

	    try 
	    {
	        String temp;
	        temp = objectMapper.writeValueAsString(object);
	        logger.debug("temp : " + temp);
	        Map<String, Object> map = new HashMap<String, Object>();
	        map = objectMapper.readValue(temp, HashMap.class);

	        String code = (String) map.get("resultCode");
	        String message = (String) map.get("message");

	        if(!"100".equals(code))
	        {
	            map.clear();
	            map.put("resultCode", code);
	            map.put("message", message);
	            object = map;
	        }
	        
	    } 
	    catch (IOException e) 
	    {
	        // TODO Auto-generated catch block
	        logger.error("exception : " + e.getMessage());
	        new GlobalException("이슈 : 응답 바디 쓰기 오류", e.getMessage());
	    }
	    return object;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean supports(MethodParameter arg0, Class arg1)
	{
		// TODO Auto-generated method stub
		logger.debug("===========> JsonFilterAdvice : supports ");
		return true;
	}

	
	
	
}
