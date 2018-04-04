package kr.co.kpcard.messenger.app.service;

import java.util.Date;
import java.util.List;


import kr.co.kpcard.messenger.app.components.GlobalException;
import kr.co.kpcard.messenger.app.repository.backoffice.Message;
import kr.co.kpcard.messenger.app.repository.backoffice.ReserveMessageMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReservationService {

	public static final String COMPANY_SENDER = "15885245";
	
	private Logger logger = LoggerFactory.getLogger(ReservationService.class);
	
	
	@Autowired
	ReserveMessageMapper reserveMessageMapper;
	
	/**
	 * 발송할 메세지를 예약 처리 한다.  
	 * @param reserveDate 예약 발송 시간 (yyyyMMddHHmmss) 
	 * @param type 전송 타입(문자, 이메일, 텔레그램 )
	 * @param message 전송 예약할 메세지 
	 * @param receiver 수신자 (타입에 따라, 전화번호, 이메일, 계정 아이디)
	 * @param sender 발신자(타입에 따라, 전화번호, 이메일, 계정 아이디) 
	 * @param desc01 예약어 01
	 * @param desc02 예약어 02
	 * @return 예약(된) 메세지 아이디 값을 리턴한다. 
	 */
	public String reserveMessage(long transId, Date reserveDate , String type
							,  String message, String receiver, String sender
							, String desc01, String desc02)
	{	
		logger.info("transId : " + transId + ", reserveDate : " + reserveDate
				+ ", type : " + type + ", message : " + message 
				+ ", receiver : " + receiver + ", sender : " + sender
				+ ", desc01 : " + desc01 + ", desc02 : " + desc02);
		
		String reservedMsgId = "";
		
		try 
		{
			// 1. make Message Obj
			Message msg = new Message(message, type, sender, receiver, reserveDate);
			// 2. insert ReserveMsg
			reserveMessageMapper.insertReserveMessage(msg);	
			
			if(msg.getReserveMsgId() != null) reservedMsgId = String.valueOf(msg.getReserveMsgId());
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			logger.error("exception : "+ e.getMessage());
			throw new GlobalException(transId, e.getMessage(), "reserveMessage() service 오류");
		}
		
		logger.info("transId : " + transId + ", reservedMsgId : " + reservedMsgId);
		return reservedMsgId;
	}
	
	
	/**
	 * 2.예약 메세지 취소
	 * @param reservedId 예약 취소 할 메세지의 예약번호
	 * @return int (0:성공, 1:실패, 2:취소할 메세지 없음, 3:취소 불가 메세지 상태.) 
	 */
	public int cancelReservedMessage(long transId, int reservedId)
	{
		logger.info("transId : " + transId + ", reservedId : " + reservedId);
		int result = 1;
		
		try
		{			
			Message message = reserveMessageMapper.getReserveMessage(reservedId);
			
			// 예약된 메세지가 없음. 
			if(message == null) return 2; 
			
			// 예약된 메세지가 존재하고, 메세지 상태가 취소 가능 상태일때. 
			if(message.getStatus()==1)
			{
				int cancelStatus = 3; // 취소 상태 값 
				reserveMessageMapper.updateMessageStatus(reservedId, cancelStatus);
				result = 0;
			}
			// 예약된 메세지가 존재하고, 메세지 상태가 취소 불가능 상태일때.
			else return 3;
			
		}
		catch(Exception e)
		{
			logger.error("exception : "+ e.getMessage());
			throw new GlobalException(transId, e.getMessage(), "cancelReservedMessage() 이슈.");
		}
		
		logger.info("transId : " + transId + ", result : " + result);
		
		return result;
	}	
	
	
	/**
	 * 기간 조건에 따른 예약 메세지 리스트 조회 
	 * @param type 메세지 발송타입(문자, 이메일, 텔레그램 )
	 * @param startDate 조회 시작일
	 * @param endDate 조회 종료일
	 * @return List형태의 Message객체 리턴
	 */
	public List<Message> getReservedMessageList(long transId, String type, Date startDate, Date endDate)
	{
		logger.info("transId : " + transId + ", type : " + type + ", startDate : " + startDate + ", endDate : " + endDate);
		
		List<Message> result = null;
		
		// 1. Validate Input Params
		if("".equals(type) || type == null) return result;
		if(startDate == null || endDate == null) return result;		
	
		// 2. Diff startDate and endDate 
		if((startDate.getTime() == endDate.getTime()) || (startDate.getTime() > endDate.getTime())) return result;
		
		
		// 4. Query Mapper 
		try
		{	
			result = reserveMessageMapper.getReserveMessages(type, startDate, endDate);
		}
		catch(Exception e)
		{
			logger.error("transId : " + transId + "msg : "+ e.getMessage());
			throw new GlobalException(transId, e.getMessage(), "DB Query 이슈");
		}
		
		logger.info("transId : " + transId + ", result : " + result);
		
		return result;
	}
	
	/**
	 * 예약번호에 따른 예약 메세지 조회 
	 * @return Message객체 리턴
	 */
	public Message getReservedMessage(long transId, int reservedMsgId)
	{
		logger.info("transId : " + transId + ", reservedMsgId : " + reservedMsgId);
		
		Message result = null;
		
		// 1. Validate Input Params
		if(reservedMsgId <= 0) return result;
		
		// 4. Query Mapper 
		try
		{	
			result = reserveMessageMapper.getReserveMessage(reservedMsgId);
		}
		catch(Exception e)
		{
			logger.error("transId : " + transId + "msg : "+ e.getMessage());
			throw new GlobalException(transId, e.getMessage(), "DB Query 이슈");
		}
		logger.info("transId : " + transId + ", result : " + result);
		
		return result;
	}
	
	
}
