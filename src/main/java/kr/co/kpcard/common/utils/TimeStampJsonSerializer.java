package kr.co.kpcard.common.utils;

import java.io.IOException;
import java.util.Date;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TimeStampJsonSerializer extends JsonSerializer<Date>{

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	@Override
	public void serialize(Date date, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
		String foramttedDate = "";
		
		if(date != null)
		{
			if(date.getTime() == DateUtil.createOriginDate().getTime())
			{
				foramttedDate = "";
			}
			else
			{
				foramttedDate = DateUtil.getDateFormatString(date, DATE_FORMAT);
			}
		}
		
		generator.writeString(foramttedDate);
		
	}

}
