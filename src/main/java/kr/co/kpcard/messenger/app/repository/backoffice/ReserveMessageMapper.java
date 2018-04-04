package kr.co.kpcard.messenger.app.repository.backoffice;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import kr.co.kpcard.messenger.app.repository.common.SmsTran;

@Mapper
public interface ReserveMessageMapper {
	
	final static String INSERT_RESERVE_MSG =
				 "INSERT INTO KPC_RESERVE_MSG "
		       + "			("
		       + "			 RESERVE_MSG_ID, "
		       + "			 TYPE, "
		       + "  		 CALLBACK_PHONE, "
		       + "  		 CALLBACK_ID, "
		       + "			 RECIEVER_PHONE, "
		       + "			 RECIEVER_ID,"
		       + "			 STATUS,"
		       + "			 MSG_BODY,"
		       + "			 REQ_URL,"
		       + "			 RESERVE_DT,"
		       + "			 CREATE_DT,"
		       + "			 DESC1,"
		       + "			 DESC2"
		       + "			) "
		       + "	VALUES("
			   + "			 SEQ_RESERVE_MSG_ID.NEXTVAL, "
		       + "			 #{type,jdbcType=VARCHAR}, "
		       + "  		 #{callbackPhone,jdbcType=VARCHAR}, "
		       + "  		 #{callbackId,jdbcType=VARCHAR}, "
		       + "			 #{recieverPhone,jdbcType=VARCHAR}, "
		       + "			 #{recieverId,jdbcType=VARCHAR},"
		       + "			 '1',"
		       + "			 #{msgBody,jdbcType=VARCHAR},"
		       + "			 #{reqUrl,jdbcType=VARCHAR},"
		       + "			 #{reserveDt,jdbcType=TIMESTAMP},"
		       + "			 SYSDATE,"
		       + "			 #{desc1,jdbcType=VARCHAR},"
		       + "			 #{desc2,jdbcType=VARCHAR}"
		       + "			)";
	
	//Nessage 발송예약 등록
	@Resource(name="backOfficeDataSource")
	@Insert(INSERT_RESERVE_MSG)
	@SelectKey(statement="SELECT SEQ_RESERVE_MSG_ID.CURRVAL FROM DUAL", keyProperty="reserveMsgId", resultType=Integer.class, before=false)
	public Integer insertReserveMessage(Message message) throws Exception;
	
	
	
	final static String UPDATE_MSG_STATUS =
			"UPDATE KPC_RESERVE_MSG "
			+ "		SET	STATUS=#{status}"
			+ "		WHERE RESERVE_MSG_ID=#{msgId}";
	
	@Resource(name="backOfficeDataSource")
	@Update(UPDATE_MSG_STATUS)
	public void updateMessageStatus(@Param(value="msgId") int msgId,
			   						@Param(value="status") int status) throws Exception;
	
	
	
	final static String SELECT_RESERVE_MSG_LIST = 
			"<script>"
			+ "SELECT RESERVE_MSG_ID, "
		  + "	  	TYPE, "
		  + "  		CALLBACK_PHONE, "
		  + "  		CALLBACK_ID, "
		  + "		RECIEVER_PHONE, "
		  + "		RECIEVER_ID,"
		  + "		STATUS,"
		  + "		MSG_BODY,"
		  + "		REQ_URL,"
		  + "		RESERVE_DT,"
		  + "		CREATE_DT,"
		  + "		DESC1,"
		  + "		DESC2 "
		  + "		FROM KPC_RESERVE_MSG"
		  + "	WHERE"
		  + "		STATUS=1"
		  + "		AND RESERVE_DT BETWEEN #{startDate} AND #{endDate}"
		  + "		<if test='type!=\"All\" and type!=null '>AND type=#{type}</if>"
		  + "	</script>";
	  	  
	
	@Resource(name="backOfficeDataSource")
	@Select(SELECT_RESERVE_MSG_LIST)
	@Results(value={
			@Result(property="reserveMsgId", column="RESERVE_MSG_ID"),		
			@Result(property="type", column="TYPE"),
			@Result(property="callbackPhone", column="CALLBACK_PHONE"),
			@Result(property="callbackEmail", column="CALLBACK_EMAIL"),
			@Result(property="callbackId", column="CALLBACK_ID"),
			@Result(property="callbackNm", column="CALLBACK_NM"),
			@Result(property="recieverId", column="RECIEVER_ID"),
			@Result(property="recieverNm", column="RECIEVER_NM"),
			@Result(property="recieverPhone", column="RECIEVER_PHONE"),
			@Result(property="recieverEmail", column="RECIEVER_EMAIL"),
			@Result(property="recieverDesc", column="RECIEVER_DESC"),
			@Result(property="msgTitle", column="MSG_TITLE"),
			@Result(property="msgBody", column="MSG_BODY"),
			@Result(property="msgDesc1", column="MSG_DESC1"),
			@Result(property="msgDesc2", column="MSG_DESC2"),
			@Result(property="status", column="STATUS"),
			@Result(property="reserveDt", column="RESERVE_DT"),
			@Result(property="reqUrl", column="REQ_URL"),
			@Result(property="createDt", column="CREATE_DT"),
			@Result(property="desc1", column="DESC1"),
			@Result(property="desc2", column="DESC2")
			})
	public List<Message> getReserveMessages(@Param(value="type") String type,
										   @Param(value="startDate") Date startDate,
										   @Param(value="endDate") Date endDate) throws Exception;
	
	
	final static String SELECT_RESERVE_MSG = 
			"SELECT RESERVE_MSG_ID, "
		  + "	  	TYPE, "
		  + "  		CALLBACK_PHONE, "
		  + "  		CALLBACK_ID, "
		  + "		RECIEVER_PHONE, "
		  + "		RECIEVER_ID,"
		  + "		STATUS,"
		  + "		MSG_BODY,"
		  + "		REQ_URL,"
		  + "		RESERVE_DT,"
		  + "		CREATE_DT,"
		  + "		DESC1,"
		  + "		DESC2 "
		  + "		FROM KPC_RESERVE_MSG"
		  + "	WHERE"
		  + "		RESERVE_MSG_ID=#{reserveId}";
	
	@Resource(name="backOfficeDataSource")
	@Select(SELECT_RESERVE_MSG)
	@Results(value={
			@Result(property="reserveMsgId", column="RESERVE_MSG_ID"),		
			@Result(property="type", column="TYPE"),
			@Result(property="callbackPhone", column="CALLBACK_PHONE"),
			@Result(property="callbackEmail", column="CALLBACK_EMAIL"),
			@Result(property="callbackId", column="CALLBACK_ID"),
			@Result(property="callbackNm", column="CALLBACK_NM"),
			@Result(property="recieverId", column="RECIEVER_ID"),
			@Result(property="recieverNm", column="RECIEVER_NM"),
			@Result(property="recieverPhone", column="RECIEVER_PHONE"),
			@Result(property="recieverEmail", column="RECIEVER_EMAIL"),
			@Result(property="recieverDesc", column="RECIEVER_DESC"),
			@Result(property="msgTitle", column="MSG_TITLE"),
			@Result(property="msgBody", column="MSG_BODY"),
			@Result(property="msgDesc1", column="MSG_DESC1"),
			@Result(property="msgDesc2", column="MSG_DESC2"),
			@Result(property="status", column="STATUS"),
			@Result(property="reserveDt", column="RESERVE_DT"),
			@Result(property="reqUrl", column="REQ_URL"),
			@Result(property="createDt", column="CREATE_DT"),
			@Result(property="desc1", column="DESC1"),
			@Result(property="desc2", column="DESC2")
			})
	public Message getReserveMessage(@Param(value="reserveId") long reserveId) throws Exception;
	
	
	final static String INSERT_SENT_MSG =
			 "INSERT INTO KPC_SENT_MSG "
	       + "			("
	       + "			 RESERVE_MSG_ID, "
	       + "			 TYPE, "
	       + "  		 CALLBACK_PHONE, "
	       + "  		 CALLBACK_ID, "
	       + "			 RECIEVER_PHONE, "
	       + "			 RECIEVER_ID,"
	       + "			 STATUS,"
	       + "			 MSG_BODY,"
	       + "			 REQ_URL,"
	       + "			 RESERVE_DT,"
	       + "			 CREATE_DT,"
	       + "			 DESC1,"
	       + "			 DESC2"
	       + "			) "
	       + "	VALUES("
		   + "			 #{reserveMsgId}, "
	       + "			 #{type,jdbcType=VARCHAR}, "
	       + "  		 #{callbackPhone,jdbcType=VARCHAR}, "
	       + "  		 #{callbackId,jdbcType=VARCHAR}, "
	       + "			 #{recieverPhone,jdbcType=VARCHAR}, "
	       + "			 #{recieverId,jdbcType=VARCHAR},"
	       + "			 #{status},"
	       + "			 #{msgBody,jdbcType=VARCHAR},"
	       + "			 #{reqUrl,jdbcType=VARCHAR},"
	       + "			 #{reserveDt,jdbcType=TIMESTAMP},"
	       + "			 SYSDATE,"
	       + "			 #{desc1,jdbcType=VARCHAR},"
	       + "			 #{desc2,jdbcType=VARCHAR}"
	       + "			)";

	//Nessage 발송결과 등록
	@Resource(name="backOfficeDataSource")
	@Insert(INSERT_SENT_MSG)
	Integer insertSentMessage(Message message) throws Exception;
	
	final static String DELETE_RESERVE_MSG =
			" DELETE FROM KPC_RESERVE_MSG "
			+ " WHERE "
			+ "	RESERVE_MSG_ID=#{reserveId}";	
	//Nessage 내역 삭제
	@Resource(name="backOfficeDataSource")
	@Delete(DELETE_RESERVE_MSG)
	Integer deleteReserveMessage(@Param(value="reserveId") int reserveId) throws Exception;
	
}
