package kr.co.kpcard.messenger.app.repository.backoffice;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	
	private Integer reserveMsgId;
	private String type;
	private String callbackPhone;
	private String callbackEmail;
	private String callbackId;
	private String callbackNm;
	private String recieverId;
	private String recieverNm;
	private String recieverPhone;
	private String recieverEmail;
	private String recieverDesc;
	private String msgTitle;
	private String msgBody;
	private String msgDesc1;
	private String msgDesc2;
	private Integer status;
	private Date reserveDt;
	private String reqUrl;
	private Date createDt;
	private String desc1;
	private String desc2;
	
	public Message(){}
	
	public Message(String msgBody, String type, String callbackPhone
				, String recieverPhone, Date reserveDt)
	{
		this.msgBody = msgBody;
		this.type = type;
		this.callbackPhone = callbackPhone;
		this.recieverPhone = recieverPhone;
		this.reserveDt = reserveDt;
	}

	
	
//	public boolean validate(String target)
//	{
//		boolean result = false;
//		
//		return result;
//	}
//	
//	public Object validateDetails(String target)
//	{
//		return null;
//	}
//	
	@Override
	public String toString() 
	{
		return String
				.format("Message [reserveMsgId=%s, type=%s, callbackPhone=%s, callbackEmail=%s, callbackId=%s, callbackNm=%s, recieverId=%s, recieverNm=%s, recieverPhone=%s, recieverEmail=%s, recieverDesc=%s, msgTitle=%s, msgBody=%s, msgDesc1=%s, msgDesc2=%s, status=%s, reserveDt=%s, reqUrl=%s, createDt=%s, desc1=%s, desc2=%s]",
						reserveMsgId, type, callbackPhone, callbackEmail,
						callbackId, callbackNm, recieverId, recieverNm,
						recieverPhone, recieverEmail, recieverDesc, msgTitle,
						msgBody, msgDesc1, msgDesc2, status, reserveDt, reqUrl,
						createDt, desc1, desc2);
	}
	
	
	
	
}
