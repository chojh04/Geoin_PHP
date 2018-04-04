package kr.co.kpcard.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	//private final static String REG_EXP_FOR_ID 			= "[a-z]{1}[a-z0-9]{2,16}";
	private final static String REG_EXP_FOR_ID 				= "[a-zA-Z0-9]{1,16}";
	private final static String REG_EXP_FOR_NAME 			= "[a-zA-Z가-힣\\s]{1,20}"; 
	private final static String REG_EXP_FOR_EMAIL 			= "[_a-zA-Z0-9-\\+]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)*(\\.[a-zA-Z]{2,})$"; 
	private final static String REG_EXP_FOR_IP_ADDRESS 		= "((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})"; 
	private final static String REG_EXP_FOR_PASSWORD 		= "([a-zA-Z0-9,~,`,!,@,#,$,%,^,&,*,(,),-,=,_]){6,12}"; 
	private final static String REG_EXP_FOR_PHONE_NUMBER 	= "01(?:0|[1,6,7,9])(?:\\d{3}|\\d{4})(?:\\d{4})";
	private final static String REG_EXP_FOR_NUMBER 			= "\\d+"; 
	
	
	
	public final static String convertToString(int intValue)
	{
		String result = "";
		
		try 
		{
			result = String.valueOf(intValue);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			return result;
		}
		

		return result;
	}
	
	public final static String convertToString(long longValue)
	{
		String result = "";
		
		try 
		{
			result = String.valueOf(longValue);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			return result;
		}
		
		return result;
	}
	
	public final static Long convertToLong(String longValue)
	{
		Long result = null;
		
		try 
		{
			result = Long.parseLong(longValue);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			return result;
		}
		
		return result;
	}
	
	public final static String RandomNum(int length)
	{
		Random random = new Random();
		
		String randomNum = "";
		
		for(int i = 0; i < length; i++)
		{
			randomNum += convertToString(random.nextInt(10));
		}
		return randomNum;
	}
	
	public final static String RandomCharacter(int length)
	{	
		String rmText = "";
		Random random = new Random(System.currentTimeMillis());

		int rmNum = 0;

		char ch1 = 'a';
		char ch2 = 'A';
		char ch3 = '1';

		int rnd = random.nextInt(3);
		// System.out.println("" + rnd);
		for (int i = 0; i < length; i++) {

			random.setSeed(System.currentTimeMillis() * random.nextInt(1000000)
					* i + random.nextInt(1000000) + i);
			rnd = random.nextInt(3);
			// System.out.println("난수 : " + rnd);
			if (rnd == 2) {
				rmNum = random.nextInt(9);
				ch3 += rmNum;
				// System.out.println("숫자 : " +ch3);
				rmText = rmText + ch3;
			} else if (rnd == 0) {
				rmNum = random.nextInt(25);
				ch1 += rmNum;
				// System.out.println("소문자 : " +ch3);
				rmText = rmText + ch1;
			} else if (rnd == 1) {
				rmNum = random.nextInt(25);
				ch2 += rmNum;
				// System.out.println("대문자 : " +ch3);
				rmText = rmText + ch2;
			}

			ch1 = 'a';
			ch2 = 'A';
			ch3 = '1';
		}

		return rmText;
	}
	
	public final static String[] SubStringPhoneNumber(String phoneNumber)
	{
		String[] number = new String[3];
		
		if(phoneNumber.length() == 10)
		{
			number[0] = phoneNumber.substring(0, 3);
			number[1] = phoneNumber.substring(3, 6);
			number[2] = phoneNumber.substring(6, 10);
		}
		else if (phoneNumber.length() == 11)
		{
			number[0] = phoneNumber.substring(0, 3);
			number[1] = phoneNumber.substring(3, 7);
			number[2] = phoneNumber.substring(7, 11);
		}
		
		return number;
	}
	
	
	public final static boolean isEmailValidByRegExp(String emailValue)
	{
		boolean result = false;
		
		try 
		{
			Pattern pattern = Pattern.compile(REG_EXP_FOR_EMAIL);
			Matcher matcher = pattern.matcher(emailValue);
			
			result = matcher.matches();
		}
		catch (Exception e)
		{
			// TODO: handle exception
			return result;
		}
		
		return result;
	}
	
	
	public final static boolean isIdValidByRegExp(String idValue)
	{
		boolean result = false;
		
		try 
		{
			Pattern pattern = Pattern.compile(REG_EXP_FOR_ID);
			Matcher matcher = pattern.matcher(idValue);
			
			result = matcher.matches();
		}
		catch (Exception e)
		{
			// TODO: handle exception
			return result;
		}
		
		return result;
	}
	
	
	public final static boolean isNameValidByRegExp(String nameValue)
	{
		boolean result = false;
		
		try 
		{
			Pattern pattern = Pattern.compile(REG_EXP_FOR_NAME);
			Matcher matcher = pattern.matcher(nameValue);
			
			result = matcher.matches();
		}
		catch (Exception e)
		{
			// TODO: handle exception
			return result;
		}
		
		return result;
	}
	
	
	public final static boolean isPasswordValidByRegExp(String passwordValue)
	{
		boolean result = false;
		
		try 
		{
			Pattern pattern = Pattern.compile(REG_EXP_FOR_PASSWORD);
			Matcher matcher = pattern.matcher(passwordValue);
			
			result = matcher.matches();
		}
		catch (Exception e)
		{
			// TODO: handle exception
			return result;
		}
		
		return result;
	}
	
	
	public final static boolean isIpAddressValidByRegExp(String ipAdressValue)
	{
		boolean result = false;
		
		try 
		{
			Pattern pattern = Pattern.compile(REG_EXP_FOR_IP_ADDRESS);
			Matcher matcher = pattern.matcher(ipAdressValue);
			
			result = matcher.matches();
		}
		catch (Exception e)
		{
			// TODO: handle exception
			return result;
		}
		
		return result;
	}
	
	
	public final static boolean isPhoneNumberValidByRegExp(String phoneNumberValue)
	{
		boolean result = false;
		
		try 
		{
			Pattern pattern = Pattern.compile(REG_EXP_FOR_PHONE_NUMBER);
			Matcher matcher = pattern.matcher(phoneNumberValue);
			
			result = matcher.matches();
		}
		catch (Exception e)
		{
			// TODO: handle exception
			return result;
		}
		
		return result;
	}
	
	public final static boolean isNumberValidByRegExp(String NumberValue)
	{
		boolean result = false;
		
		try 
		{
			Pattern pattern = Pattern.compile(REG_EXP_FOR_NUMBER);
			Matcher matcher = pattern.matcher(NumberValue);
			
			result = matcher.matches();
		}
		catch (Exception e)
		{
			// TODO: handle exception
			return result;
		}
		
		return result;
	}
	
	public final static String getProcessSequenceString()
	{
		String sequence = "";
		
		try {
			sequence = System.currentTimeMillis() + RandomNum(6);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("failed create TimStamp");
		}
		return sequence;
	}
	
	public final static long getProcessSequence()
	{
		StringBuffer sb = new StringBuffer();
		
		long result = -1;
		
		try {
			sb.append(System.currentTimeMillis());
			sb.append(RandomNum(6));
			result = Long.parseLong(sb.toString());
			
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			System.out.println("failed create TimStamp");
			return -1;
		}
		finally
		{
			sb = null;
		}
		
		return result;
	}
	
	public static String getOrderNoForTest()
	{
		Calendar cal = Calendar.getInstance();
		return String.format("%s-%s", getDateFormatString(cal.getTime(), "yyyyMMdd")
				, getDateFormatString(cal.getTime(), "HHmmss"));
	}
	
	public static String getDateFormatString(Date date, String dateTimeFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		return sdf.format(date);
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		long seq = getProcessSequence();
		System.out.println("seq : " + seq);
		
		
	}
	
		
}
