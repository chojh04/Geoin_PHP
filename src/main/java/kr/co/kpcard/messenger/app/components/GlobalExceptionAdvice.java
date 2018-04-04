package kr.co.kpcard.messenger.app.components;

import javax.servlet.http.HttpServletRequest;

import kr.co.kpcard.messenger.app.controller.protocol.ApiResponse;
import kr.co.kpcard.messenger.app.controller.protocol.ResultCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice implements ResultCode {
	
	 private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

	 @ExceptionHandler(value = { GlobalException.class })
	 @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	 public ApiResponse unknownException(HttpServletRequest req, Exception ex, GlobalException be) 
	 {
		 
		 logger.info("===================================================================================");
		 logger.info("=====> getRequestURI : " + req.getRequestURI());
		 logger.info("=====> getAttributeNames : " + req.getAttributeNames());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> Exception, getMessage : " + ex.getMessage());
		 logger.info("=====> Exception, getLocalizedMessage : " + ex.getLocalizedMessage());
		 logger.info("=====> Exception, toString() : " + ex.toString());
		 logger.info("-----------------------------------------------------------------------------------");
		 logger.info("=====> BE : issueNo = " + be.getIssueNo());
		 logger.info("=====> BE : message = " + be.getMessage());
		 logger.info("=====> BE : customMsg = " + be.getCustomMsg());
		 logger.info("===================================================================================");
		 
		 logger.error("issueNo : " + be.getIssueNo() + ", msg : " + be.getMessage() + ", customMsg : " + be.getCustomMsg());
		 
	     return new ApiResponse(RESULT_CODE_ERROR
	    		 				, RESULT_MSG_ERROR
	    		 				+ String.format("[이슈번호 : %d, 담당자에게 해당 이슈에 대한 문의 부탁드립니다.| requestUrl : %s]"
	    		 				, be.getIssueNo(), req.getRequestURI()));
	 }
	 
	 
}
