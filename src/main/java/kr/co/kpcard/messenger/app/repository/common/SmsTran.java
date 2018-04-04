package kr.co.kpcard.messenger.app.repository.common;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsTran {
	
	public final static String DIVIDER_SINGLE = "N";
	public final static String DIVIDER_NOTI = "Y";
	
	public final static String STATUS_READY_TO_SEND = "1";
	public final static String STATUS_WAIT_FOR_RESULT = "2";
	public final static String STATUS_DONE = "3";
	
	private int key;
	private Date date;
	private String content;
	private String callbackPhoneNum;
	private String type;
	private String divider;//'N' : 단일건, 'Y' :동보건
	private String status; //1-전송대기, 2-결과대기, 3-완료
	private String receivePhoneNum;
	
	public SmsTran(){}
	
	public void setSmsTrans(String content, Date sendDate
							, String callbackPhone, String receivePhone)
	{
		this.key = 0;
		this.date = sendDate;
		this.content = content;
		this.callbackPhoneNum = callbackPhone;
		this.type = "0";
		this.divider = DIVIDER_SINGLE;
		this.status = STATUS_READY_TO_SEND;
		this.receivePhoneNum = receivePhone;
	}
	
	
	

	
	
}
