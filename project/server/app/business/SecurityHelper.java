package business;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityHelper {
	public String hash(String value)
	{
		String encrypt= BCrypt.hashpw(value, BCrypt.gensalt());
		return encrypt;
	}
	
	public boolean checkHash(String password, String value)
	{
		return BCrypt.checkpw(password, value);
	}
}
