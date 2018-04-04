package kr.co.kpcard.messenger.app.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.net.HttpURLConnection;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kpcard.common.utils.DateUtil;
import kr.co.kpcard.common.utils.SequenceUtil;
import kr.co.kpcard.messenger.app.components.GlobalException;
import kr.co.kpcard.messenger.app.controller.protocol.ResCancelMessage;
import kr.co.kpcard.messenger.app.controller.protocol.ResGetReserveMessageList;
import kr.co.kpcard.messenger.app.controller.protocol.ResReserveMessage;
import kr.co.kpcard.messenger.app.controller.protocol.ResultCode;
import kr.co.kpcard.messenger.app.repository.backoffice.Message;
import kr.co.kpcard.messenger.app.service.ReservationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MessageController implements ResultCode{

	private Logger logger = LoggerFactory.getLogger(MessageController.class);

	private static final String MESSAGE_URI_V1 = "service/reserve/messages/message";
	private final static String MESSAGE_URI_V2 = "/service/v2/message";
	
	
	@Autowired
	private ReservationService reservationService;
	
	@ApiOperation(value="전송할 메세지 예약", nickname="메세지 예약 v1")
	@ApiResponses(value={@ApiResponse(code=200, message="Success", response=ResReserveMessage.class)})
	@RequestMapping(value=MESSAGE_URI_V1, method=RequestMethod.POST, produces = "application/json")
	public ResReserveMessage reserveMessage(
			 @ApiParam(name="recieverPhone", value="수신자정보", required=true)
			@RequestParam(value="recieverPhone") String recieverPhone // 수진자 전화 번호 
			,@ApiParam(name="reserveDate", value="예약발송일", required=true)
			@RequestParam(value="reserveDate", defaultValue="") @DateTimeFormat(pattern=DateUtil.YYYYMMDDHH24MMSS) Date reserveDate //yyyy-MM-dd'T'HH:mm:ss			
			,@ApiParam(name="sendType", value="발송타입", required=true) 
			@RequestParam(value="sendType") String sendType // 발신 수단 (SMS / MMS)
			,@ApiParam(name="message", value="발송메세지", required=true)
			@RequestParam(value="message") String message
			, @ApiParam(name="senderPhone", value="발신자정보", required=true)
			@RequestParam(value="senderPhone") String callbackPhone // 발신자 전화 번호 
											, HttpServletRequest request
											, HttpServletResponse response)
	{
		long transId = SequenceUtil.generateTransactionId();
		
		ResReserveMessage resBody = new ResReserveMessage();
		
		try 
		{
			// 1.1 요청 값 유효성 체크 (요청값이 비유효할 경우. 리턴 처리) 
			if("".equals(recieverPhone) || "".equals(callbackPhone))
			{
				resBody.setResult(RESULT_CODE_INVLID_REQ, RESULT_MSG_INVLID_REQ+"[sender : " + recieverPhone + ", receiver : " + callbackPhone + "]");
				return resBody;
			}
			
			// 1.2 전화 번호, 문자열 체크  
			recieverPhone.replace("-", "");
			recieverPhone.trim();
			
			callbackPhone.replace("-", "");
			callbackPhone.trim();
			
			// 1.3 sendTyp 유효성 체크 
			if("".equals(sendType) || !"SMS".equals(sendType) || "".equals(message))
			{
				resBody.setResult(RESULT_CODE_INVLID_REQ, RESULT_MSG_INVLID_REQ+"[sendType : " + sendType + ", message : " + message + "]");
				return resBody;
			}
			
			
			// 2. 서비스 로직 호출 
			String reservedId = reservationService.reserveMessage(transId, reserveDate, sendType, message, recieverPhone, callbackPhone, "", "");
			
			// 2.1 예약 실패 케이스 처리. 
			if("".equals(reservedId))
			{
				resBody.setResult(RESULT_CODE_SUCCESS
								, RESULT_MSG_FAILURE + String.format("(메세지 예약 실패, 이슈번호 : %d)", transId));
			}
			
			// 2.2 예약 성공. 
			resBody.setResult(RESULT_CODE_SUCCESS, RESULT_MSG_SUCCESS);
			resBody.setReservedId(reservedId);
			
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
			throw new GlobalException(transId, e.getMessage(), String.format("URL : %s, [%s]", request.getRequestURI(), request.getMethod()));
		}	
		
		return resBody;
	}
	
	

	@ApiOperation(value = "전송할 메세지를 예약 요청한다.", nickname = "메세지 예약")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = ResReserveMessage.class),
			@ApiResponse(code = 500, message = "Server Error") })
	@RequestMapping(value = MESSAGE_URI_V2, method = RequestMethod.POST, produces = "application/json")
	public ResReserveMessage reserveMessageV2(
			HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name = "sender", value = "발신자(전화번호/이메일/아이디)", defaultValue = "", required = true) 
			@RequestParam(value = "sender", required=true, defaultValue = "") String sender,
			@ApiParam(name = "receiver", value = "수신자(전화번호/이메일/아이디)", defaultValue = "", required = true) 
			@RequestParam(value = "receiver", required=true, defaultValue = "") String receiver,
			@ApiParam(name = "sendType", value = "전송 타입(sms, email, telegram)", defaultValue = "", required = true) 
			@RequestParam(value = "sendType", required=true, defaultValue = "") String sendType,
			@ApiParam(name = "reserveDate", value = "전송 예약시간("+DateUtil.YYYYMMDDHH24MMSS+")", defaultValue = "", required = true) 
			@RequestParam(value = "reserveDate", required=true, defaultValue = "")  @DateTimeFormat(pattern=DateUtil.YYYYMMDDHH24MMSS) Date reserveDate,
			@ApiParam(name = "message", value = "(전송할) 메세지", defaultValue = "", required = true) 
			@RequestParam(value = "message", required=true, defaultValue = "") String message,
			@ApiParam(name = "desc01", value = "비고  01", defaultValue = "", required = false) 
			@RequestParam(value = "desc01", required=false, defaultValue = "") String desc01,
			@ApiParam(name = "desc02", value = "비고  02", defaultValue = "", required = false) 
			@RequestParam(value = "desc02", required=false, defaultValue = "") String desc02) 
	{
		long transId = SequenceUtil.generateTransactionId();
		
		ResReserveMessage resBody = new ResReserveMessage();
		
		try 
		{
			// 1.2 전화 번호, 문자열 체크  
			sender.replace("-", "");
			sender.trim();
			
			receiver.replace("-", "");
			receiver.trim();
			
			// 1.3 sendTyp 유효성 체크 
			if(!"SMS".equals(sendType))
			{
				resBody.setResult(RESULT_CODE_INVLID_REQ, RESULT_MSG_INVLID_REQ+"[sendType : " + sendType + ", message : " + message + "]");
				return resBody;
			}
			
			
			// 2. 서비스 로직 호출 
			String reservedId = reservationService.reserveMessage(transId, reserveDate, sendType, message, receiver, sender, desc01, desc02);
			
			// 2.1 예약 실패 케이스 처리. 
			if("".equals(reservedId))
			{
				resBody.setResult(RESULT_CODE_SUCCESS
								, RESULT_MSG_FAILURE + String.format("(메세지 예약 실패, 이슈번호 : %d)", transId));
			}
			
			// 2.2 예약 성공. 
			resBody.setResult(RESULT_CODE_SUCCESS, RESULT_MSG_SUCCESS);
			resBody.setReservedId(reservedId);
			
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
			throw new GlobalException(transId, e.getMessage(), String.format("URL : %s, [%s]", request.getRequestURI(), request.getMethod()));
		}	
		
		return resBody;
	}

	@ApiOperation(value = "예약된 메세지 취소 컨트롤러", nickname = "메세지 예약취소")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = ResCancelMessage.class),
			@ApiResponse(code = 500, message = "Server Error") })
	@RequestMapping(value = MESSAGE_URI_V2, method = RequestMethod.PUT, produces = "application/json")
	public ResCancelMessage cancelMessage(
			HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name = "reservedId", value = "메세지 예약번호", defaultValue = "plain", required = true) 
			@RequestParam(value = "reservedId", required=true, defaultValue = "plain") int reservedId)
	{
		long transId = SequenceUtil.generateTransactionId();
		
		ResCancelMessage resBody = new ResCancelMessage();
		
		try 
		{
			int resultVal = this.reservationService.cancelReservedMessage(transId, reservedId);
			
			logger.debug("[취소 결과] resultVal : " + resultVal);
			
			// 3:취소 불가 메세지 상태
			if(resultVal == 3)
			{
				resBody.setResult(RESULT_CODE_FAILURE
						, RESULT_MSG_FAILURE 
						+ String.format("(예약 취소가 불가능 메세지 입니다. 예약 메세지 번호 %d, 이슈번호 : %d)", reservedId, transId) );
			}
			// 2:취소 불가 메세지 상태
			else if(resultVal == 2)
			{
				resBody.setResult(RESULT_CODE_FAILURE
						, RESULT_MSG_FAILURE 
						+ String.format("(예약 메세지가 없습니다. 예약 메세지 번호 %d, 이슈번호 : %d)", reservedId, transId) );
			}
			// 1:실패 
			else if(resultVal == 1)
			{
				resBody.setResult(RESULT_CODE_FAILURE
						, RESULT_MSG_FAILURE 
						+ String.format("(예약 메세지 번호 %d, 이슈번호 : %d)", reservedId, transId) );
			}
			// 0:성공 
			else if(resultVal == 0)
			{
				resBody.setReservedId(String.valueOf(reservedId));
				resBody.setResult(RESULT_CODE_SUCCESS, RESULT_MSG_SUCCESS);
			}			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			logger.error("exception : " + e.getMessage());
			throw new GlobalException(transId, e.getMessage(), String.format("URL : %s, [%s]", request.getRequestURI(), request.getMethod()));
		}
		
		
		return resBody;
	}
	
	
	
	@ApiOperation(value = "메세지 리스트를 날짜조건으로 조회하는 컨트롤러", nickname = "날짜 조건 메세지 조회")
	@ApiResponses(value = 
		{
			@ApiResponse(code = HttpsURLConnection.HTTP_OK, message = "success", response = ResGetReserveMessageList.class),
            @ApiResponse(code = HttpsURLConnection.HTTP_BAD_REQUEST, message = "Bad Request", response = ResGetReserveMessageList.class),
            @ApiResponse(code = HttpsURLConnection.HTTP_NOT_FOUND, message = "Not Found", response = ResGetReserveMessageList.class),
            @ApiResponse(code = HttpsURLConnection.HTTP_INTERNAL_ERROR, message = "Internal Server Problems")
		}
	)
	@RequestMapping(value = MESSAGE_URI_V2, method = RequestMethod.GET, produces = "application/json")
	public ResGetReserveMessageList getReserveMessageList(
			HttpServletRequest request, HttpServletResponse response,
			@ApiParam(name = "type", value = "발송타입", required = true) 
				@RequestParam(value = "type", required=true, defaultValue = "") String type,
			@ApiParam(name = "startDate", value = "조회 시작일시(포맷 : "+ DateUtil.YYYYMMDDHH24MMSS +")", allowableValues = "range[1,14]", required = true) 
                @RequestParam(value="startDate", required=true) @DateTimeFormat(pattern=DateUtil.YYYYMMDDHH24MMSS) Date startDate,	
            @ApiParam(name = "endDate", value = "조회 종료일시(포맷 : "+ DateUtil.YYYYMMDDHH24MMSS +")", allowableValues = "range[1,14]", required = true) 
                @RequestParam(value="endDate", required=true) @DateTimeFormat(pattern=DateUtil.YYYYMMDDHH24MMSS) Date endDate	
            ) 
	{
		long transId = SequenceUtil.generateTransactionId();
		
		ResGetReserveMessageList resBody = new ResGetReserveMessageList();
		
		
		try 
		{
			logger.info("transId : " + transId + ", type : " + type + ", startDate : " + startDate + ", endDate : " + endDate);
			
			// 1. 요청 값 유효성 체크 (요청값이 비유효할 경우. 리턴 처리) 
			if(!"SMS".equals(type))
			{
				resBody.setCodeAndMessage(RESULT_CODE_INVLID_REQ, RESULT_MSG_INVLID_REQ + String.format("[type : %s]", type) );
				return resBody;
			}
			
			
			// 2. 서비스 로직 호출 
			List<Message> messageList = reservationService.getReservedMessageList(transId, type, startDate, endDate);
			
			if(messageList==null)
			{
				String failureMsg = String.format("(type : %s, startDate : %s, endDate : %s, 이슈 번호 : %d)" 
								, type, DateUtil.getDateFormatString(startDate, DateUtil.YYYYMMDDHH24MMSS)
								, DateUtil.getDateFormatString(startDate, DateUtil.YYYYMMDDHH24MMSS), transId);
				
				resBody.setCodeAndMessage(RESULT_CODE_FAILURE, RESULT_MSG_FAILURE + failureMsg);
				logger.warn("transId : " + transId + ", failure : : " + failureMsg);
				
				return resBody;
			}
			
			// 3. 결과 응답 바디 생성 
			Date curDate = DateUtil.createDate();
			resBody.setResponseDate(curDate);
			resBody.setMessageList(messageList);
			resBody.setCodeAndMessage(RESULT_CODE_SUCCESS, RESULT_MSG_SUCCESS);
			
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.error("transId : " + transId + "msg : " + e.getMessage());
			throw new GlobalException(transId, e.getMessage(), String.format("URL : %s, [%s]", request.getRequestURI(), request.getMethod()));
		}	
		
		logger.info("transId : " + transId + ", resBody : " + resBody.toString());
		
		return resBody;
	}

	
}
