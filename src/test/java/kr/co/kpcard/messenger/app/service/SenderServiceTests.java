package kr.co.kpcard.messenger.app.service;

import static org.junit.Assert.*;

import java.util.Date;

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
import kr.co.kpcard.messenger.app.service.SenderService;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "local")
@SpringBootTest
public class SenderServiceTests {
	Logger logger = LoggerFactory.getLogger(SenderServiceTests.class);
	
	@Autowired
	SenderService senderService;
	
	@Test
	public void testForSendMessage() {
		Date date;
		Message message;
		boolean resultValue;
		
		logger.info("===========> testForSendMessage start");
		
		//Failure Case 1
		//then
		date = DateUtil.createDate(2018, 1, 26, 14, 0, 0);
		message = new Message("testNewSMS", "", "18993206", "01087782172", date);
		message.setStatus(2);
		resultValue = this.senderService.sendMessage(0, message);		
		//then
		assertEquals(false, resultValue);
		
		
		//Success Case 1
		date = DateUtil.createDate(2018, 2, 26, 14, 0, 0);
		message = new Message("testNewSMS", "SMS", "18993206", "01087782172", date);
		message.setStatus(1);
		resultValue = senderService.sendMessage(0, message);		
		//then
		assertEquals(true, resultValue);
		logger.info("===========> testForSendMessage end");
	}

}
