package business;

import play.Logger;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;



public class MailHelper {
	
	final Logger.ALogger loger = Logger.of("email");
	
	MailerClient mailerClient;
	
	public MailHelper(MailerClient mail)
	{
		this.mailerClient = mail;
	}
	// Send mail to Customer to active account
	public boolean SendMailActiveAccount(int id, String name, String emailTo, String token)
	{
		try{
			 Email email = new Email();
		     email.setSubject("Shopping || Active Account");
		     email.setFrom("websmartphone527@gmail.com");
		     email.addTo(emailTo);
		     String content = "Chào " + name + "\n";
		     content += "Bạn đã đăng ký thành công tài khoản online shopping của trang web abcshopping.com\n";
		     content += "Bạn vui lòng nhấn vào link này để kích hoạt tài khoản đã đăng ký.\n";
		     content += "http://localhost:9000/activeAccount/" + token +"/"+ id;
		     content += "\nCảm ơn bạn đã sủ dụng.\n";
		     content += "abcshopping.com";
		     email.setBodyText(content);
		        
		    loger.info("chuan bi gui mail");
		    mailerClient.send(email);
		    loger.info("da gui mail");
			return true;
		}catch(Exception ex){
			loger.info(ex.toString());
			return false;
		}
	}
	
	// Send mail to Customer to RE active account
		public boolean ReSendMailActiveAccount(int id, String name, String emailTo, String token)
		{
			try{
				 Email email = new Email();
			     email.setSubject("Shopping || Active Account");
			     email.setFrom("websmartphone527@gmail.com");
			     email.addTo(emailTo);
			     String content = "Chào " + name + "\n";
			     content += "Bạn đã đăng ký thành công tài khoản online shopping của trang web abcshopping.com\n";
			     content += "Thời gian active account của bạn đã hết. Vui lòng nhấn vào link bên dưới đê re active account\n";
			     content += "Bạn vui lòng nhấn vào link này để kích hoạt tài khoản đã đăng ký.\n";
			     content += "http://localhost:9000/activeAccount/" + token +"/"+ id;
			     content += "\nCảm ơn bạn đã sủ dụng.\n";
			     content += "abcshopping.com";
			     email.setBodyText(content);
			        
			    mailerClient.send(email);
				return true;
			}catch(Exception ex){
				loger.info(ex.toString());
				return false;
			}
		}

}
