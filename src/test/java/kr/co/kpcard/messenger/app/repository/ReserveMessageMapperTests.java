package kr.co.kpcard.messenger.app.repository;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.messenger.app.repository.backoffice.Message;
import kr.co.kpcard.messenger.app.repository.backoffice.ReserveMessageMapper;


@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "local")
@SpringBootTest
public class ReserveMessageMapperTests {

	private final Logger logger = LoggerFactory.getLogger(ReserveMessageMapperTests.class);
	
	@Autowired
	private ReserveMessageMapper reserveMessageMapper;
	
	
	@Test
	public void testForGetReserveMessages()
	{		
		logger.info("==============> testGetReserveMessages : start ");
		
		try 
		{
			List<Message> resultList;
			
			String type;
			Date startDate;
			Date endDate;
			
			
			//Success case
			// When. 
			type = "SMS";			
			startDate = DateUtil.createDate(2018, 1, 10, 1, 1, 1);
			endDate = DateUtil.createDate(2018, 2, 21, 1, 1, 1);
			
			// Then 
			resultList = reserveMessageMapper.getReserveMessages(type, startDate, endDate);
			logger.debug("resultList : " + resultList);
			
			assertNotEquals(null, resultList);
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			logger.error("exception : " + e.getMessage());
			fail("exception : " + e.getMessage());
		}
		
		logger.info("==============> testGetReserveMessages : end ");
		
		
	}

	
	
	@Test
	public void testForInsertReserveMessage(){
		
		logger.info("==============> testForInsertReserveMessage : start ");
		try 
		{
			Message message;	
			Message resultMessage;
			
			Date reserveDate;
			String content;
			String type; 
			String callbackPhone;
			String receivePhone;
			
			
			// When, Success Case. 
			reserveDate = DateUtil.addDay(DateUtil.createDate(), 2);
			content = "hello, test!!";
			type = "SMS";
			callbackPhone = "01087782172";
			receivePhone = "01087782172";
			
			message = new Message(content, type, callbackPhone, receivePhone, reserveDate);
			
			// Then. Success Case. 
			reserveMessageMapper.insertReserveMessage(message);
			logger.debug("resevedId : " + message.getReserveMsgId());
			assertNotEquals(null, message.getReserveMsgId());
			
			resultMessage = reserveMessageMapper.getReserveMessage(message.getReserveMsgId());
			logger.debug("resultMessage : " + resultMessage);
			assertNotEquals(null, resultMessage);
			
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();//
			logger.error("error : " + e.getMessage());
			fail("exception : " + e.getMessage());
		}	
		logger.info("==============> testForInsertReserveMessage : end ");
	}

	
	@Test
	public void testForUpdateReserveMessage(){		
		
		try 
		{			
			logger.info("==============> testForUpdateReserveMessage : start ");
			
			Message resultMessage;
			int ReserveMsgId;
			Integer status; 
			
			// When, Success Case. 
			ReserveMsgId =  372;
			status = 3;
			
			// Then, Success Case. 
			reserveMessageMapper.updateMessageStatus(ReserveMsgId, status);
			 
			resultMessage = reserveMessageMapper.getReserveMessage(ReserveMsgId);				
			logger.debug("resultMsg : " + resultMessage);
			assertEquals(status, resultMessage.getStatus());			
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			logger.error("error : " + e.getMessage());
			e.printStackTrace();
			fail("exception : " + e.getMessage());
		}
		
		logger.info("==============> testForUpdateReserveMessage : end ");
		
	}
}
