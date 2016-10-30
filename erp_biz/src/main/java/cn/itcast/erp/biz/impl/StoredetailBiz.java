package cn.itcast.erp.biz.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.entity.StoreAlert;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.exception.ErpException;
import util.IMailUtil;
/**
 * 仓库库存业务逻辑类
 * @author Administrator
 *
 */
public class StoredetailBiz extends BaseBiz<Storedetail> implements IStoredetailBiz {

	private IStoredetailDao storedetailDao;
	
	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
		setBaseDao(storedetailDao);
	}

	private IMailUtil mailUtil;
	
	public void setMailUtil(IMailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}

	/**
	 * 查询库存预警列表
	 */
	public List<StoreAlert> getStoreAlertList() {		
		return storedetailDao.getStoreAlertList();
	}

	/**
	 * 发送库存预警邮件
	 * @throws MessagingException 
	 */
	public void sendStoreAlertMail() throws MessagingException{
		//得到库存预警列表
		List<StoreAlert> storeAlertList = storedetailDao.getStoreAlertList();
		if(storeAlertList.size()>0){		
			String toAddress="louyingxin@sina.com";
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");			
			String subject="库存生日快乐 时间：[time]".replace("[time]", sdf.format(new Date()));			
			String text="当前ERP系统中有[count]种商品出现生日快乐，请登陆系统查询".replace("[count]", storeAlertList.size()+"");		
			mailUtil.sendMail(toAddress, subject, text);
		}else{
			throw new ErpException("当前系统中没有库存预警信息");	
		}
	}
	
}
