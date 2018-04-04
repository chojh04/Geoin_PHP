package kr.co.kpcard.messenger.app.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.common.utils.SequenceUtil;
import kr.co.kpcard.messenger.app.repository.backoffice.Message;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "local")
@SpringBootTest
public class ReservationServiceTests {

	private Logger logger = LoggerFactory.getLogger(ReservationServiceTests.class);
	
	@Autowired
	private ReservationService reservationService;
	
	
	
	/**
	 * 메세지 예약/조회 테스트 
	 */
	@Test
	public void TestForReserveMessage()
	{
		long transId = SequenceUtil.generateTransactionId();
		
		Date reserveDate;
		String type;
		String message;
		String receiver;
		String sender;
		String desc01;
		String desc02;
		
		String reservedMsgId;
		Message reservedMsg;
		
		// When, Success Case.
		reserveDate = DateUtil.addHour(DateUtil.createDate(), 2);
		type = "SMS";
		message = "test message!!";
		receiver = "01087782172";
		sender = "01072821110";
		desc01 = "hello";
		desc02 = "world";
		
		// then. 
		reservedMsgId = this.reservationService.reserveMessage(transId, reserveDate, type, message, receiver, sender, desc01, desc02);
		logger.debug("reservedMsgId : " + reservedMsgId);
		assertNotEquals(null, reservedMsgId);
		
		reservedMsg = this.reservationService.getReservedMessage(transId, Integer.parseInt(reservedMsgId));
		logger.debug("reservedMsg : " + reservedMsg);
		assertNotEquals(null, reservedMsg);
		
	}
	
	/**
	 * 메세지 예약 및 취소 테스트 
	 */
	@Test
	public void TestForCancelReservedMessage()
	{
		long transId = SequenceUtil.generateTransactionId();
		
		Date reserveDate;
		String type;
		String message;
		String receiver;
		String sender;
		String desc01;
		String desc02;
		
		String reservedMsgId;
		Message reservedMsg;
		
		// When, Success Case.
		reserveDate = DateUtil.addHour(DateUtil.createDate(), 2);
		type = "SMS";
		message = "test message for canceling!!";
		receiver = "01087782172";
		sender = "01072821110";
		desc01 = "hello";
		desc02 = "world";
		
		// then. 
		reservedMsgId = this.reservationService.reserveMessage(transId, reserveDate, type, message, receiver, sender, desc01, desc02);
		logger.debug("reservedMsgId : " + reservedMsgId);
		assertNotEquals(null, reservedMsgId);
		
		reservedMsg = this.reservationService.getReservedMessage(transId, Integer.parseInt(reservedMsgId));
		logger.debug("reservedMsg : " + reservedMsg);

		Integer msgStatus = 1;
		logger.debug("reservedMsg.getStatus() : " + reservedMsg.getStatus() ); 
		assertEquals(msgStatus, reservedMsg.getStatus());
		
		int resultVal = this.reservationService.cancelReservedMessage(transId, Integer.parseInt(reservedMsgId));
		logger.debug("cancelReservedMessage->resultVal : " + resultVal ); 
		assertEquals(0, resultVal);
		
		reservedMsg = this.reservationService.getReservedMessage(transId, Integer.parseInt(reservedMsgId));
		logger.debug("reservedMsg : " + reservedMsg);
		logger.debug("msg Status : " + reservedMsg.getStatus());

	}
	
	
	@Test
	public void TestForGetReservedMessageList()
	{
		List<Message> result;
		
		long transId = SequenceUtil.generateTransactionId();
		
		String type = "";
		Date startDate; 
		Date endDate; 
				
		logger.info("======> Start : Failure Case 01 ");
		// When 1. 
		type = null;
		startDate = null;
		endDate = null;
		result = this.reservationService.getReservedMessageList(transId, type, startDate, endDate);
		logger.info("======> result : " + result);
		
		// Then, 1
		assertEquals(null, result);
		
		
		logger.info("======> Start : Failure Case 02 ");
		// When 2. 
		type = "SMS";
		startDate = null;
		endDate = null;
		result = this.reservationService.getReservedMessageList(transId, type, startDate, endDate);
		logger.info("======> result : " + result);
		
		// Then, 2
		assertEquals(null, result);
				
		
		logger.info("======> Start : Failure Case 03 ");
		// When 3. 
		type = "SMS";
		startDate = DateUtil.createDate(2018, 1, 14, 1, 1, 1);;
		endDate = DateUtil.createDate(2018, 1, 2, 1, 1, 1);;
		result = this.reservationService.getReservedMessageList(transId, type, startDate, endDate);
		logger.info("======> result : " + result);
		
		// Then, 3
		assertEquals(null, result);

		
		logger.info("======> Start : Failure Case 04 ");
		// When 4. 
		type = "SMS";
		startDate = DateUtil.createDate(2018, 1, 10, 1, 1, 1);;
		endDate = DateUtil.createDate(2018, 1, 10, 1, 1, 1);;
		result = this.reservationService.getReservedMessageList(transId, type, startDate, endDate);
		logger.info("======> result : " + result);
		
		// Then, 4
		assertEquals(null, result);
		
		
		logger.info("======> Start : Success Case 01 ");
		// When 5. 
		type = "SMS";
		startDate = DateUtil.createDate(2018, 1, 2, 1, 1, 1);;
		endDate = DateUtil.createDate(2018, 1, 12, 1, 1, 1);;
		result = this.reservationService.getReservedMessageList(transId, type, startDate, endDate);
		logger.info("======> result : " + result);						
		// Then, 5
		assertNotEquals(null, result);
		
		
	}
	
	/*
	@Test
	public void insertTest() {	
		String receiverInfo = "01087782172";
		String sendType = "";
		String msgBody = "MessengerAPI Junit Test";		
		String result = "";
		String sender = "15885245";
		Date date = DateUtil.createDate(2018, 1, 12, 1, 1, 1);

		
		logger.info("======> Start : Failure case 01");
		sendType = "SMS_FAIL";
		result = reservationService.reserveMessage(date, sendType, msgBody,	receiverInfo, sender, "", "");
		//then
		assertEquals("", result);
		

		logger.info("======> Start : Success case 02");
		sendType = "SMS";
		result = reservationService.reserveMessage(date, sendType, msgBody,	receiverInfo, sender, "", "");
		assertNotEquals("", result);
		
	}
	
	@Test
	public void updateTest() {
		long transId = SequenceUtil.generateTransactionId();
		
		Message message;
				
		int msgId;
		Integer status;
		boolean	resultValue;
		
		logger.info("======> Start : Failure case 01");
		//Failure case 1
		msgId = -1;			
		resultValue = reservationService.cancelReservedMessage(msgId);
		assertEquals(false, resultValue);
		
		logger.info("======> Start : Success case 01");
		//Success case 1
		msgId = 344;	
		status = 3;
		resultValue = reservationService.cancelReservedMessage(msgId);				
		if(resultValue){
			message = reservationService.getReservedMessage(transId, msgId);
			assertEquals(status, message.getStatus());
		}
	*/
}
