package util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 邮件工具类
 * @author Administrator
 *
 */
public class MailUtil implements IMailUtil {

	private JavaMailSender javaMailSender;
		
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	private String fromAddress;//发件人
	
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}


	/**
	 * 发送邮件
	 * @throws MessagingException 
	 */
	public void sendMail(String toAddress, String subject, String text) throws MessagingException {
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mime);
		helper.setFrom(fromAddress);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		helper.setText(text);
		javaMailSender.send(mime);
	}

}
