package kr.co.kpcard.messenger.app.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.common.utils.SequenceUtil;
import kr.co.kpcard.messenger.app.repository.backoffice.Message;
import kr.co.kpcard.messenger.app.service.ReservationService;
import kr.co.kpcard.messenger.app.service.SenderService;


public class SendingMessageJob implements Job {   

	private static Logger logger = LoggerFactory.getLogger(SendingMessageJob.class);
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	SenderService senderService;
	
	@Override
    public void execute(JobExecutionContext jobExecutionContext) 
    {
		long transId = SequenceUtil.generateTransactionId();
		
		boolean result = false;
		
		int successCount = 0;
		int failureCount = 0;
		
		try
		{
			logger.debug("SendingMessageJob Start");						
			
			Date curDate = DateUtil.createDate();
			Date fromDate = DateUtil.addMinute(curDate, -1);
        	
        	List<Message> reservedMsgList = new ArrayList<Message>();
        	reservedMsgList = reservationService.getReservedMessageList(transId, "All", fromDate, curDate);

        	if(reservedMsgList!=null && reservedMsgList.size()>0)
        	{
        		for(Message message : reservedMsgList)
        		{
        			result = senderService.sendMessage(transId, message);
        				
        			if(result) successCount++;
        			else
        			{
        				failureCount++;
        				logger.warn("fail to send msg : " + message.toString());
        			}
        		}       		
        	}else{
        		
        	}
       
        	logger.info("Sending Job Result [ TotalCount : "+reservedMsgList.size()+",  SuccessCount : "+successCount+", failCount : "+failureCount +" ]");
        	logger.debug("SendingMessageJob End");
		}
		catch(Exception e)
		{
			logger.error("exception : " + e.toString());
		}
		
    }
}
