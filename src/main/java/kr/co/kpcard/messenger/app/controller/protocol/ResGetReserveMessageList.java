package kr.co.kpcard.messenger.app.controller.protocol;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;
import kr.co.kpcard.messenger.app.repository.backoffice.Message;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({"lsit", "responseDate"})
@Getter
@Setter
public class ResGetReserveMessageList extends ApiResponse{
	
	@ApiModelProperty(value = "list", notes = "메세지 리스트", position = 2, dataType="List<Message>", required=true, hidden = true)
	private List<Message> messageList;

	
	@ApiModelProperty(value = "responseDate", notes = "응답 시간", position = 3, dataType="Date", required=true, hidden = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date responseDate;
	
	
	public ResGetReserveMessageList()
	{
		super(ResultCode.RESULT_CODE_FAILURE, ResultCode.RESULT_MSG_FAILURE);
		this.messageList = null;
	}

	@Override
	public String toString() 
	{
		return String
				.format("ResGetReserveMessageList [getCode()=%s, getMessage()=%s, messageList=%s]",
						getCode(), getMessage(), messageList);
	}
	
	
	
}
