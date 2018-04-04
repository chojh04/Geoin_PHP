package kr.co.kpcard.messenger.app.controller;

import static org.assertj.core.api.BDDAssertions.then;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerTests {

	private final Logger logger = LoggerFactory.getLogger(MessageControllerTests.class);
	
	private String baseUrl;
	
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

	@SuppressWarnings("rawtypes")
	private ResponseEntity<Map> entity;
    
    
	@Before
	public void setUp()
	{
		logger.info("-------> setUp :: test() port : " + port);
        this.baseUrl = "http://localhost:" + String.valueOf(port);
        logger.info("-------> setUp ::baseUrl : " + baseUrl);
	}
	
	
	//@Test
	public void testForGetReserveMessageListApi() throws JsonProcessingException 
	{		
		// 1. Test -> /service/v2/message [GET] : success 
        logger.info("=========> F-1. Test -> /service/v2/message [GET] : failure1");
		String testVals_failure_01 =  "/service/v2/message?type=&startDate=20180101010101&endDate=20180120010101";
		
        entity = this.restTemplate.getForEntity(this.baseUrl
        								+ testVals_failure_01, Map.class);
        
        logger.debug("result entity : " + entity );
        logger.debug("statusCode" + entity.getStatusCode());
        logger.debug("body" + this.jsonStringFromObject(entity.getBody()));
        
        then (entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        
		
        // 2. Test -> /service/v2/message [GET] : success 
        logger.info("=========> F-2. Test -> /service/v2/message [GET] : failure1");
		String testVals_failure_02 =  "/service/v2/message?type=4444&startDate=20180101010101&endDate=20180120010101";
		
        entity = this.restTemplate.getForEntity(this.baseUrl
        								+ testVals_failure_02, Map.class);
        
        logger.debug("result entity : " + entity );
        logger.debug("statusCode" + entity.getStatusCode());
        logger.debug("body" + this.jsonStringFromObject(entity.getBody()));
        
        then (entity.getStatusCode()).isEqualTo(HttpStatus.OK);

        
		
		// 1. Test -> /service/v2/message [GET] : success 
        logger.info("=========> S-1. Test -> /service/v2/message [GET] : success");
		String testVals_success_01 =  "/service/v2/message?type=SMS&startDate=20180101010101&endDate=20180120010101";
		
        entity = this.restTemplate.getForEntity(this.baseUrl
        								+ testVals_success_01, Map.class);
        
        logger.debug("result entity : " + entity );
        logger.debug("statusCode" + entity.getStatusCode());
        logger.debug("body" + this.jsonStringFromObject(entity.getBody()));
        
        then (entity.getStatusCode()).isEqualTo(HttpStatus.OK);        
	}
	
	//@Test
	public void testForReserveMessageApiV2() throws JsonProcessingException{
		
		logger.debug("=======> testForReserveMessageApiV2 Start.");
		
		String url ="/service/v2/message";
		
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		
		// When, Success Case. 
		param.add("sender", "18993206");
		param.add("receiver", "01087782172");
		param.add("sendType", "SMS");
		param.add("reserveDate", "20180126141500");
		param.add("message", "RestAPI____TEST");
		
		// Then, Success Case.
		entity = this.restTemplate.postForEntity(this.baseUrl+url, param, Map.class);	
		logger.debug("result entity : " + entity );
        logger.debug("statusCode" + entity.getStatusCode());
        logger.debug("body" + this.jsonStringFromObject(entity.getBody()));
        then (entity.getStatusCode()).isEqualTo(HttpStatus.OK); 
        
        logger.debug("=======> testForReserveMessageApiV2 End.");
		
	}
	
	
	@Test
	public void testForCancelReservedMessageApi() throws IOException
	{
		logger.debug("=======> testForCancelReservedMessageApi Start.");
		String url ="/service/v2/message";
		
		String sender = "18993206";
		String receiver = "01087782172";
		String sendType = "SMS";
		String reserveDate = "20180126141500";
		String message = "RestAPI____TEST";
		
		
		// When, Success Case. (step.1) : 메세지 예약 조건 
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add("sender", sender);
		param.add("receiver", receiver);
		param.add("sendType", sendType);
		param.add("reserveDate", reserveDate);
		param.add("message", message);
		
		// Then, Success Case. (step.1) : 메세지 예약 결과 
		entity = this.restTemplate.postForEntity(this.baseUrl+url, param, Map.class);	
		logger.debug("result entity : " + entity );
        logger.debug("statusCode" + entity.getStatusCode());
        logger.debug("body" + this.jsonStringFromObject(entity.getBody()));
        then (entity.getStatusCode()).isEqualTo(HttpStatus.OK); 
        
        // When, Success Case. (step.2) : 예약된 메세지 취소 
        String resultReservMsg = this.jsonStringFromObject(entity.getBody());
        Map<String, String> resultMap = this.parseJsonString(resultReservMsg);
        String reservedMsgId = resultMap.get("reservedId");
        logger.debug("reservedMsgId : " + reservedMsgId);
        
        // Then, Success Case. (Step.2) : 예약 취소 결과. 
        String msg_url =  "/service/v2/message?reservedId={reservedId}";
		entity = this.restTemplate.exchange(msg_url, HttpMethod.PUT, entity, Map.class, reservedMsgId);
		logger.debug("result entity : " + entity );
        logger.debug("statusCode" + entity.getStatusCode());
        logger.debug("body" + this.jsonStringFromObject(entity.getBody()));
        then (entity.getStatusCode()).isEqualTo(HttpStatus.OK); 
	
        logger.debug("=======> testForCancelReservedMessageApi End.");
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	private Map<String, String> parseJsonString(String jsonString) throws JsonParseException, JsonMappingException, IOException
	{
		Map<String, String> map;
		map = objectMapper.readValue(jsonString, HashMap.class);
		return map;
	}
	
	private String jsonStringFromObject(Object object) throws JsonProcessingException 
    {
        return objectMapper.writeValueAsString(object);
    }
	
	
	
}
