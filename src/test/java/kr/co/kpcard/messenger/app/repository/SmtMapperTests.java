package kr.co.kpcard.messenger.app.repository;

import static org.junit.Assert.*;

import java.util.Date;

import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.messenger.app.repository.common.SmtMapper;
import kr.co.kpcard.messenger.app.repository.common.SmsTran;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "local")
@SpringBootTest
public class SmtMapperTests {

	private final Logger logger = LoggerFactory.getLogger(SmtMapperTests.class);
	
	@Autowired
	private SmtMapper commonMapper;
	
	
	@Test
	public void testForInsertSms() 
	{
		
		SmsTran smsTran = new SmsTran();
		
		Date sendDate;
		String content;
		String callbackPhone;
		String receivePhone;
		
		// When, Success Case. 
		sendDate = DateUtil.createDate();
		content = "hello. test.";
		callbackPhone = "01087782172";
		receivePhone = "01087782172";
		
		smsTran.setSmsTrans(content, sendDate, callbackPhone, receivePhone);
		
		
		// Then, Success Case
		try 
		{
			int result = commonMapper.insertSms(smsTran);
			logger.debug("result : " + result);
			assertEquals(1, result);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			fail();
			logger.error("error : " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
	}

}
