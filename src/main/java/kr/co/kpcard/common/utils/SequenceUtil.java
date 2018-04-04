package kr.co.kpcard.common.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceUtil {
	
	public final static long getSeqNo()
	{
		long result = 0;
		
		try 
		{
			String sequence = System.currentTimeMillis() + StringUtil.RandomNum(6);
			result = Long.parseLong(sequence);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			return 0;
		}
		
		return result;
	}
	
	
	private static final AtomicInteger counter = new AtomicInteger();
	
	public static long generateTransactionId()
	{
		long transId = 0;
		
		try 
		{
			String curDateTime = DateUtil.getCurrentDate("yyyyMMddHHmmss");
			int random = counter.getAndIncrement();
			
			String uid = String.format("%s%04d", curDateTime,random);
			transId = Long.parseLong(uid);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			transId = 0;
		}
		
		return transId;
	}

}
