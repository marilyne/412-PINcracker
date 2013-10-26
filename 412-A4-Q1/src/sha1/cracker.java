package sha1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class cracker {
	
	private final static String _SALT = new String("nM");
	private final static String _HASHEDPASS = new String ("4547D7ADAC11CE2718281196B1158BF372B49809");
	
	public static void main (String args[]) 
	{
		long start = System.nanoTime();
		
		String password = new String("no password found");
		try {
			password = crackPassword();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println("Password: " + password);
		
		double elapsedTimeInSec = (System.nanoTime() - start) * 1.0e-9;
		System.out.println("Time elapsed: " + elapsedTimeInSec);
	}
	
	private static String crackPassword() throws NoSuchAlgorithmException
	{
		int password;
		String passwordStr;
		String saltedPasswordStr;
		
		MessageDigest md = MessageDigest.getInstance("SHA1");
		
		for (password = 0; password < 10000; password ++) {
			passwordStr = getPaddedPassword (password);
			
			saltedPasswordStr = saltPassword(passwordStr);
			
			byte[] result = md.digest(saltedPasswordStr.getBytes());
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			String hashedPasswordStr = new String(sb.toString());
		
			if ( hashedPasswordStr.toUpperCase().equals(_HASHEDPASS) ) {
				return passwordStr;
			}
		}
		
		return "no password found"; 
	}
	
	private static String getPaddedPassword (int password)
	{
		String stringPass = new String(String.valueOf(password)); 
		if (password < 10 ) {
			stringPass = "000" + stringPass;
		}
		else if (password < 100 ) {
			stringPass = "00" + stringPass;
		}
		else if (password < 1000) {
			stringPass = "0" + stringPass;
		}
		return stringPass;
	}
	
	private static String saltPassword (String password) 
	{
		//return password + _SALT; //appended salt
		return _SALT + password; //prepended salt
	}

}