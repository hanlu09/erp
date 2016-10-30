package cn.itcast.erp.job;

import java.util.Date;

import javax.mail.MessagingException;

import cn.itcast.erp.biz.IStoredetailBiz;
/**
 * 邮件相关任务类
 * @author Administrator
 *
 */
public class MailJob {

	private IStoredetailBiz storedetailBiz;
	
	public void setStoredetailBiz(IStoredetailBiz storedetailBiz) {
		this.storedetailBiz = storedetailBiz;
	}

	/**
	 * 自动发送库存警报邮件
	 */
	public void sendStoreAlertMail(){
		
		try {
			storedetailBiz.sendStoreAlertMail();
			System.out.println("自动调用发送邮件的程序"+new Date());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
