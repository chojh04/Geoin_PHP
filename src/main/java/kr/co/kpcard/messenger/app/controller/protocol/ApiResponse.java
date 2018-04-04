package kr.co.kpcard.messenger.app.controller.protocol;

import lombok.Getter;
import lombok.Setter;
import io.swagger.annotations.ApiModelProperty;

@Getter
@Setter
public class ApiResponse {

	@ApiModelProperty(value = "code", notes = "응답 코드", position = 0, dataType="String", required=true, hidden = false)
	private String code;
	
	@ApiModelProperty(value = "message", notes = "응답메세지", position = 1, dataType="String", required=true, hidden = false)
	private String message;
	
	
	public ApiResponse(String code, String message)
	{
		this.code = code;
		this.message = message;
	}
	
	
	public void setCodeAndMessage(String code, String message)
	{
		this.code = code;
		this.message = message;
	}
	
}
