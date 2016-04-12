package business;

import javax.inject.Inject;

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
	
	public boolean SendMailActiveAccount(String emailTo)
	{
		try{
			 Email email = new Email();
		        email.setSubject("Shopping || Active Account");
		        email.setFrom("websmartphone527@gmail.com");
		        email.addTo(emailTo);
		        email.setBodyText("hello");
		        
		    loger.info("chuan bi gui mail");
		    mailerClient.send(email);
		    loger.info("da gui mail");
			return true;
		}catch(Exception ex){
			loger.info(ex.toString());
			return false;
		}
	}

}
