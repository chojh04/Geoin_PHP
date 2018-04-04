package kr.co.kpcard.messenger.app.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import allbegray.slack.bot.SlackbotClient;
import kr.co.kpcard.messenger.app.components.GlobalException;
import kr.co.kpcard.messenger.app.repository.backoffice.Message;
import kr.co.kpcard.messenger.app.repository.backoffice.ReserveMessageMapper;
import kr.co.kpcard.messenger.app.repository.common.SmtMapper;
import kr.co.kpcard.messenger.app.repository.common.SmsTran;


@Service
public class SenderService {
	
	private static Logger logger = LoggerFactory.getLogger(SenderService.class);
	
	private static final String TYPE_SMS = "SMS";
	private static final String TYPE_TELEGRAM = "TELEGRAM";
	private static final String TYPE_SLACK = "SLACK";
	
	
	@Autowired
	private SmtMapper senderMapper;
	
	@Autowired
	private ReserveMessageMapper reserveMessageMapper;
	
	//@Autowired
	//SlackbotClient slackbotClient;

	
	public boolean sendMessage(long transId, Message message)	
	{
		boolean result = false;

		try
		{
			switch (message.getType()) 
			{
				case TYPE_SMS:
					if(message.getMsgBody().getBytes().length<=90)
					{
						result = sendBySms(transId, message);
					}
					else
					{
						result = sendByMms(transId, message);
					}
						
					break;

				case TYPE_TELEGRAM:
					result = sendByTelegram(); break;
					
				case TYPE_SLACK:
					result = sendBySlack(message); break;
			
				default: break;
			}			
		}
		catch(Exception e)
		{
			logger.error("exception : " + e.toString());
			throw new GlobalException(transId, e.getMessage(), "sendMessage() 이슈, msg :  " + message.toString());
		}
		
		return result;
	}
	
	
	private boolean sendBySms(long transId, Message message)
	{
		boolean result = false;
		
		try
		{
			SmsTran smsParam = new SmsTran();
			smsParam.setSmsTrans(message.getMsgBody(), message.getReserveDt()
									, message.getCallbackPhone(), message.getRecieverPhone());
			
			int resultOfSending = senderMapper.insertSms(smsParam);
			
			if(resultOfSending == 1) result = this.updateMessageStatus(true, message);
			else result = this.updateMessageStatus(false, message);			
			
		}
		catch(Exception e)
		{
			logger.error("exception : "+ e.toString());
			throw new GlobalException(transId, e.getMessage(), "sendBySms() 이슈, msg :  " + message.toString());
		}
	
		return result;
	}
	
	
	
	private boolean sendBySlack(Message message)
	{	
		boolean result = false;		
		//int status = 2;//메세지 상태(1:대기, 2:성공, 3:실패)
		
		try
		{
			String resultOfSending = "";
			//String resultOfSending = slackbotClient.post(message.getRecieverId(), message.getMsgBody());
			
			if(resultOfSending.equals("ok"))
			{
				result = this.updateMessageStatus(true, message);				
			}
			else
			{
				result = this.updateMessageStatus(false, message);
				logger.warn("fail to send message : " + message.toString());
			}
			
		}
		catch(Exception e)
		{
			logger.error("exception : " + e.getMessage());
			
		}
		
		return result;
	}
	
	
	private boolean sendByTelegram()
	{
		return false;
	}
	
	
	private boolean sendByMms(long transId, Message message)
	{
		return false;
	}
	
	
	//메세지 발송 후, 메세지 발송 상태 업데이트 실
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	private boolean updateMessageStatus(boolean isSended, Message message)
	{	
		boolean result = false;
		
		try
		{
			if(message.getRecieverId()==null || message.getRecieverId().equals("")) return false;
			
			if(isSended)
			{
				// 발송 성공한 메세지일 경우. 
				message.setStatus(2);//성공상태 업데이트
				reserveMessageMapper.updateMessageStatus(message.getReserveMsgId(), 2);			
				reserveMessageMapper.insertSentMessage(message);
				reserveMessageMapper.deleteReserveMessage(message.getReserveMsgId());
			}
			else
			{
				// 발송 실패한 메세지일 경우. 
				message.setStatus(3);//실패상태 업데이트
				reserveMessageMapper.updateMessageStatus(message.getReserveMsgId(), 3);
			}
			
			result = true;
		}
		catch(Exception e)
		{
			logger.error("exception : " + e.getMessage());
			throw new GlobalException(e.getMessage(), "updateMessageStatus error!");		
		}
		
		return result;
	}
	
}
