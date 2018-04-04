package kr.co.kpcard.messenger.app.controller.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class ResReserveMessage {
	
	private String resultCode;
	private String message; 
	
	private String reservedId;

	
	public ResReserveMessage()
	{
		this.resultCode = ResultCode.RESULT_CODE_FAILURE;
		this.message = ResultCode.RESULT_MSG_FAILURE;
		this.reservedId = "";
	}
	
	@JsonProperty(required = true)
	@ApiModelProperty(notes = "결과 코드 값(100:성공, 101:실패, 102:에러, 103:유효하지않은 요청", required = true)
	public String getResultCode() {
		return resultCode;
	}

	@JsonProperty(required = true)
	@ApiModelProperty(notes = "결과 메세지", required = true)
	public String getMessage() {
		return message;
	}

	public void setResult(String code, String message)
	{
		this.resultCode = code;
		this.message = message;
	}
	
	@JsonProperty(required = true)
	@ApiModelProperty(notes = "예약(된) 번호", required = true)
	public String getReservedId() {
		return reservedId;
	} 
	
	public void setReservedId(String reservedId)
	{
		this.reservedId = reservedId;
	}

	@Override
	public String toString() 
	{
		return String.format(
				"ResReserveMessage [resultCode=%s, message=%s, reservedId=%s]",
				resultCode, message, reservedId);
	}
}
