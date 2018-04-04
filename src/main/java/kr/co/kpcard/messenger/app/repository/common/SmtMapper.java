package kr.co.kpcard.messenger.app.repository.common;

import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;


@Mapper
public interface SmtMapper {
	
	public static String INSERT_SMS_QUERY = "INSERT INTO em_smt_tran (mt_pr, date_client_req, content, callback, service_type, broadcast_yn, msg_status, recipient_num) "
										  + "VALUES (sq_em_smt_tran_01.nextval, #{date}, #{content}, #{callbackPhoneNum,jdbcType=VARCHAR}, #{type}, #{divider}, #{status}, #{receivePhoneNum,jdbcType=VARCHAR})"; 
	
	
	@Insert(INSERT_SMS_QUERY)
	@Resource(name="commonDataSource")
	@SelectKey(statement="SELECT sq_em_smt_tran_01.CURRVAL FROM DUAL", before=false, keyProperty="key", resultType=Integer.class)
	public int insertSms(SmsTran smsTran) throws Exception;
	
}
