package business;

import java.util.List;

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
	
	public boolean GiveMailWhenCheckoutSuccess(String name, String emailTo, List<String> names, List<String> counts, List<String> prices){
		try{
			 Email email = new Email();
		     email.setSubject("Shopping || Active Account");
		     email.setFrom("websmartphone527@gmail.com");
		     email.addTo(emailTo);
		     String content = "Chào " + name + "\n";
		     content += "Bạn đã thanh toán thành công giỏ hàng của mình.\n";
		     
		     content += "Đơn hàng của bạn bao gồm: \n";
		     for(int i=0; i<names.size(); i++){
		    	 content +="Tên sản phẩm: "+names.get(i) + ", số lượng: " + counts.get(i) + ", giá tiền: " + prices.get(i) + ".\n";
		     }
		     
		     content += "Đơn hàng của bạn sẽ được sử lý trong vòng 1 tuần!\n";
		     content += "Mọi thắc mắc xin vui lòng liên hệ qua: \n";
		     content += "Số điện thoại: 01694084334\n";
		     content += "Hoặc email: websmartphone527@gmail.com\n";
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
	
	public boolean ChangeStatusOrder(String name, String emailTo, List<String> names, List<String> counts, List<String> prices, int status){
		try{
			 Email email = new Email();
		     email.setSubject("Shopping || Active Account");
		     email.setFrom("websmartphone527@gmail.com");
		     email.addTo(emailTo);
		     String content = "Chào " + name + "\n";
		     
		     content += "Đơn hàng của bạn bao gồm: \n";
		     for(int i=0; i<names.size(); i++){
		    	 content +="Tên sản phẩm: "+names.get(i) + ", số lượng: " + counts.get(i) + ", giá tiền: " + prices.get(i) + ".\n";
		     }
		     switch(status){
		     case 0:{
		    	 content += "Đơn hàng của bạn đã bị HỦY!\n";
		    	 break;
		     }
		     case 2:{
		    	 content += "Đơn hàng của bạn đang được nhân viên ship cho bạn!\n";
		    	 content += "Thời gian ship là 2 ngày, kể từ ngày nhận email này!!\n";
		    	 break;
		     }
		     case 3:{
		    	 content += "Bạn đã nhận đơn hàng. Đơn hàng đã giao thành công. Chúng tôi sẽ đóng đơn hàng.!\n";
		    	 break;
		     }
		     }
		     
		     content += "Mọi thắc mắc xin vui lòng liên hệ qua: \n";
		     content += "Số điện thoại: 01694084334\n";
		     content += "Hoặc email: websmartphone527@gmail.com\n";
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
