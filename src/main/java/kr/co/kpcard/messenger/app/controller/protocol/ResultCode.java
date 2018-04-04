package kr.co.kpcard.messenger.app.controller.protocol;

public interface ResultCode 
{
	
	public static final String RESULT_CODE_SUCCESS = "100";
	public static final String RESULT_MSG_SUCCESS = "성공";
	
	public static final String RESULT_CODE_FAILURE = "101";
	public static final String RESULT_MSG_FAILURE = "실패";
	
	public static final String RESULT_CODE_ERROR = "102";
	public static final String RESULT_MSG_ERROR = "내부 서비스 이슈 발생";
	
	public static final String RESULT_CODE_INVLID_REQ = "103";
	public static final String RESULT_MSG_INVLID_REQ = "유효하지 않은 요청 값";
	
}
