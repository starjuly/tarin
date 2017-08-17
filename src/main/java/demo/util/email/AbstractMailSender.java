package demo.util.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class AbstractMailSender {
 
    private static Properties props = new Properties(); //创建 Session 实例时需要传递的基本参数  
  
    protected static Session session;  
    protected Message message;  
  
     
    static{
		//服务器认证开启
		props.setProperty("mail.smtp.auth", "true");
		//开启服务器的SMTP服务
		props.setProperty("mail.transport.protocol", "smtp");
		//QQ的必须加上这一行
		props.put("mail.smtp.starttls.enable", "true"); 
		session = Session.getInstance(props);
		session.setDebug(true);
    }
  
    /** 
     * 创建邮件即创建 java mail 的 session 和 message实例 
     */  
    public void createMail() {   
        message = new MimeMessage(session);  
        try {  
            //设置邮件发送地址  
        	message.setFrom(new InternetAddress("939697374@qq.com")); 
        } catch (AddressException e) {  
            e.printStackTrace();  
        } catch (MessagingException e) {
			e.printStackTrace();
		} 
    }  
  
    /** 
     * 发送邮件 
     */  
    abstract void sendMail(List<String> toAddresses);
}

