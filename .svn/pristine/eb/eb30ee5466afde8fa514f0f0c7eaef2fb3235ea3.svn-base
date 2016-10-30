package cn.itcast.erp.action;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IStoreBiz;
import cn.itcast.erp.entity.Store;

/**
 * 仓库Action 
 * @author Administrator
 *
 */
public class StoreAction extends BaseAction<Store> {

	private IStoreBiz storeBiz;
	
	public void setStoreBiz(IStoreBiz storeBiz) {
		this.storeBiz = storeBiz;
		setBaseBiz(storeBiz);
	}
	
	/**
	 * 我的仓库列表
	 */
	public void mylist(){
		Store store=new Store();//设置查询条件
		store.setEmpuuid(getUser().getUuid());//当前登陆人
		setT1(store);//将查询条件给BaseAction
		super.list();//调用BaseAction的list方法	
	}
	
}
