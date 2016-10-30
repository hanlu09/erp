package cn.itcast.erp.action;
import java.util.List;

import javax.mail.MessagingException;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.entity.StoreAlert;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.exception.ErpException;

/**
 * 仓库库存Action 
 * @author Administrator
 *
 */
public class StoredetailAction extends BaseAction<Storedetail> {

	private IStoredetailBiz storedetailBiz;
	
	public void setStoredetailBiz(IStoredetailBiz storedetailBiz) {
		this.storedetailBiz = storedetailBiz;
		setBaseBiz(storedetailBiz);
	}
	
	/**
	 * 库存预警列表
	 */
	public void storeAlertList(){
		List<StoreAlert> storeAlertList = storedetailBiz.getStoreAlertList();
		String jsonString = JSON.toJSONString(storeAlertList);
		write(jsonString);
	}
	
	
	/**
	 * 发送库存警报邮件
	 */
	public void sendStoreAlertMail(){
		
		try {
			storedetailBiz.sendStoreAlertMail();
			write(ajaxReturn(true, "成功发送预警邮件"));
		} catch (MessagingException e) {
			e.printStackTrace();
			write(ajaxReturn(false, "预警邮件发送失败"));
		}catch (ErpException e) {			
			e.printStackTrace();
			write(ajaxReturn(false,  e.getMessage()));
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			write(ajaxReturn(false, "发送邮件时发生错误"));
		}
		
	}
	
}
