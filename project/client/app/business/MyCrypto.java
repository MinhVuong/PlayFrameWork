package business;


public class MyCrypto {
	private final play.api.libs.Crypto crypto;
	
	 
	 public MyCrypto(@SuppressWarnings("deprecation") play.api.libs.Crypto crypto) {
	        this.crypto = crypto;
	 }
	 
	 @SuppressWarnings("deprecation")
	public String encrypt(String value)
	 {
		 return crypto.encryptAES(value);
	 }
	 
	 @SuppressWarnings("deprecation")
	public String decrypt(String value)
	 {
		 return crypto.decryptAES(value);
	 }

}
